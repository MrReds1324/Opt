import itertools
from collections.abc import Iterable

import numpy as np
from numpy._typing import NDArray

from maplestory_py_opt.configs.familiar_config import FamiliarConfig
from maplestory_py_opt.configs.item_config import ItemConfig
from maplestory_py_opt.constants.classes import DAMAGE_LINKS, ClassType
from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.constants.familiars import (
    EPIC_FAMILIAR_LINES,
    LEGENDARY_FAMILIAR_LINES,
    RARE_FAMILIAR_LINES,
    UNIQUE_FAMILIAR_LINES,
    FamiliarLine,
    FamiliarTier,
)
from maplestory_py_opt.constants.hyper_stats import HYPER_STATS_LEVEL_TO_POINTS, MAX_HYPER_STATS_POINTS
from maplestory_py_opt.constants.items import (
    LEGENDARY_EMBLEM_LINES,
    LEGENDARY_SECONDARY_LINES,
    LEGENDARY_WEAPON_LINES,
    UNIQUE_EMBLEM_LINES,
    UNIQUE_SECONDARY_LINES,
    UNIQUE_WEAPON_LINES,
    PotentialLine,
)
from maplestory_py_opt.constants.legion import MAX_LEGION_POINTS
from maplestory_py_opt.constants.souls import Soul
from maplestory_py_opt.containers.compute_container import ComputeContainer
from maplestory_py_opt.containers.numpy import (
    structured_compute_container,
    structured_familiar_container,
    structured_familiars_container,
    structured_item_container,
    structured_link_skills_container,
)


def compute_hyper_stats(hyper_stat_points: int) -> tuple[NDArray[structured_compute_container], NDArray]:
    hyper_stat_points = max(min(hyper_stat_points, MAX_HYPER_STATS_POINTS), 0)

    possible_levels = np.arange(16)
    all_level_combinations = compute_combination(possible_levels, possible_levels, possible_levels, possible_levels)
    hyper_stat_costs = np.apply_along_axis(compute_hyper_stat_combination_cost, 1, all_level_combinations)

    # Find the closest value to our current hyper stat points value
    closest_cost = hyper_stat_costs.flat[np.abs(hyper_stat_costs - hyper_stat_points).argmin()]

    filtered_hyper_stats = all_level_combinations[np.where(hyper_stat_costs == closest_cost, True, False)]
    return np.array(
        [ComputeContainer.from_hyper_stats(ContainerType.HYPER_STATS, hyper_combo, idx) for idx, hyper_combo in enumerate(filtered_hyper_stats)]
    ), filtered_hyper_stats


def compute_hyper_stat_combination_cost(hyper_stat_arr: NDArray[int]) -> int:
    cost = 0
    for hyper_level in hyper_stat_arr:
        cost += HYPER_STATS_LEVEL_TO_POINTS[hyper_level]
    return cost


def compute_legion_grid(max_grid_coverage: int) -> tuple[NDArray[structured_compute_container], NDArray]:
    max_grid_coverage = max(min(max_grid_coverage, MAX_LEGION_POINTS), 0)
    possible_grid_coverage = np.arange(41)

    all_legion_grid_combinations = compute_combination(possible_grid_coverage, possible_grid_coverage, possible_grid_coverage)
    legion_grid_costs = np.apply_along_axis(sum, 1, all_legion_grid_combinations)

    # Find the closest value to our current hyper stat points value
    closest_cost = legion_grid_costs.flat[np.abs(legion_grid_costs - max_grid_coverage).argmin()]

    filtered_legion_grid = all_legion_grid_combinations[np.where(legion_grid_costs == closest_cost, True, False)]
    return np.array(
        [ComputeContainer.from_legion_grid(ContainerType.LEGION_GRID, legion_combo, idx) for idx, legion_combo in enumerate(filtered_legion_grid)]
    ), filtered_legion_grid


def compute_souls(souls: tuple[Soul, ...]) -> tuple[NDArray[structured_compute_container], NDArray]:
    return np.array([ComputeContainer.from_soul(ContainerType.SOUL, soul, idx) for idx, soul in enumerate(souls)]), np.array(souls)


def compute_familiars(familiar_tier: FamiliarTier, used_familiars: list[FamiliarConfig]) -> tuple[NDArray[structured_compute_container], NDArray]:
    match familiar_tier:
        case FamiliarTier.EPIC:
            top_line_targets = EPIC_FAMILIAR_LINES
            bottom_line_targets = RARE_FAMILIAR_LINES
        case FamiliarTier.UNIQUE:
            top_line_targets = UNIQUE_FAMILIAR_LINES
            bottom_line_targets = EPIC_FAMILIAR_LINES
        case FamiliarTier.LEGENDARY:
            top_line_targets = LEGENDARY_FAMILIAR_LINES
            bottom_line_targets = UNIQUE_FAMILIAR_LINES
        case _:
            raise ValueError(f"Invalid familiar tier {familiar_tier}")
    all_line_combinations = np.array(list(itertools.product(top_line_targets, bottom_line_targets)), dtype=structured_familiar_container)

    if not used_familiars:
        all_familiar_combinations = np.array(list(itertools.combinations_with_replacement(all_line_combinations, r=3)), dtype=structured_familiars_container)

        return np.array(
            [ComputeContainer.from_familiars(ContainerType.FAMILIAR, familiar_combo, idx) for idx, familiar_combo in enumerate(all_familiar_combinations)]
        ), all_familiar_combinations

    pre_built_combinations: list[NDArray[structured_familiar_container]] = []
    for used_familiar in used_familiars:
        if (length := len(used_familiar.lines)) == 2:
            pre_built_combinations.append(np.array([tuple(x.value for x in used_familiar.lines)], dtype=structured_familiar_container))

        elif length == 1:
            top_line = next(iter(used_familiar.lines))

            match top_line.value[0]:
                case "R":
                    bottom_line_targets = (FamiliarLine.NA,)
                case "E":
                    bottom_line_targets = RARE_FAMILIAR_LINES
                case "U":
                    bottom_line_targets = EPIC_FAMILIAR_LINES
                case "L":
                    bottom_line_targets = UNIQUE_FAMILIAR_LINES
                case _:
                    raise ValueError(f"Invalid Familiar Line {top_line}")
            top_line_targets = (top_line,)
            pre_built_combinations.append(np.array(list(itertools.product(top_line_targets, bottom_line_targets)), dtype=structured_familiar_container))

        else:
            pre_built_combinations.append(all_line_combinations)

    # Ensure that we have 3 familiars built before we build all valid combinations
    for _ in range(3 - len(pre_built_combinations)):
        pre_built_combinations.append(all_line_combinations)  # noqa: PERF401

    all_familiar_combinations = np.array(list(itertools.product(*pre_built_combinations)), dtype=structured_familiars_container)
    return np.array(
        [ComputeContainer.from_familiars(ContainerType.FAMILIAR, familiar_combo, idx) for idx, familiar_combo in enumerate(all_familiar_combinations)]
    ), all_familiar_combinations


def compute_item(item_config: ItemConfig, class_type: ClassType, container_type: ContainerType) -> tuple[NDArray[structured_compute_container], NDArray]:
    match container_type:
        case ContainerType.WEAPON:
            unique_possibilities = UNIQUE_WEAPON_LINES
            legendary_possibilities = LEGENDARY_WEAPON_LINES
        case ContainerType.SECONDARY:
            unique_possibilities = UNIQUE_SECONDARY_LINES
            legendary_possibilities = LEGENDARY_SECONDARY_LINES
        case ContainerType.EMBLEM:
            unique_possibilities = UNIQUE_EMBLEM_LINES
            legendary_possibilities = LEGENDARY_EMBLEM_LINES
        case _:
            raise ValueError(f"Invalid container type: {container_type}")

    def calc_all_item_potentials(l_potentials: Iterable[PotentialLine], all_u_potentials: Iterable[tuple[PotentialLine, PotentialLine]]):
        return np.array([(legendary, *unique) for legendary, unique in itertools.product(l_potentials, all_u_potentials)], dtype=structured_item_container)

    all_unique_potentials = list(itertools.combinations_with_replacement(unique_possibilities, r=2))

    if item_config.main_lines:
        if (line_length := len(item_config.main_lines)) == 1:
            line = next(iter(item_config.main_lines))
            match line[0]:
                case "L":
                    legendary_possibilities = (line,)
                case "U" | "N":
                    all_unique_potentials = list(itertools.product((line,), unique_possibilities))

            all_potentials = calc_all_item_potentials(legendary_possibilities, all_unique_potentials)

        elif line_length == 2:
            legendary_lines = []
            unique_lines = []

            for line in item_config.main_lines:
                match line[0]:
                    case "L":
                        legendary_lines.append(line)
                    case "U" | "N":
                        unique_lines.append(line)

            if len(legendary_lines) == 2:
                all_potentials = np.array(
                    [(*legendary, unique) for legendary, unique in itertools.product([tuple(legendary_lines)], unique_possibilities)],
                    dtype=structured_item_container,
                )

            elif len(unique_lines) == 2:
                all_potentials = calc_all_item_potentials(legendary_possibilities, [tuple(unique_lines)])

            else:
                all_unique_potentials = list(itertools.product(unique_lines, unique_possibilities))
                all_potentials = calc_all_item_potentials(legendary_lines, all_unique_potentials)

        else:
            all_potentials = np.array(sorted(item_config.main_lines), dtype=structured_item_container)

    else:
        all_potentials = calc_all_item_potentials(legendary_possibilities, all_unique_potentials)

    return np.array(
        [ComputeContainer.from_item(container_type, class_type, item_config.item_level, item_combo, idx) for idx, item_combo in enumerate(all_potentials)]
    ), all_potentials


def compute_link_skills(
    class_type: ClassType, used_link_skills: set[ClassType], disabled_link_skills: set[ClassType]
) -> tuple[NDArray[structured_compute_container], NDArray]:
    if len(used_link_skills) == 12:
        used_link_skills = np.array([tuple(used_link_skills)], dtype=structured_link_skills_container)
        return np.array(
            [
                ComputeContainer.from_link_skills(ContainerType.LINK_SKILLS, link_skills, disabled_link_skills, idx)
                for idx, link_skills in enumerate(used_link_skills)
            ]
        ), used_link_skills

    available_damage_links = tuple(
        set(DAMAGE_LINKS.keys())
        - (
            used_link_skills
            | disabled_link_skills
            | {
                class_type,
            }
        )
    )
    # If we have too few damage links to choose from, ensure we add extra links to always make 12
    total_links_available = len(available_damage_links) + len(used_link_skills)
    used_link_skills = (*used_link_skills, *([ClassType.NONE] * (12 - total_links_available)))

    if len(used_link_skills) == 12:
        used_link_skills = np.array([used_link_skills], dtype=structured_link_skills_container)
        return np.array(
            [
                ComputeContainer.from_link_skills(ContainerType.LINK_SKILLS, link_skills, disabled_link_skills, idx)
                for idx, link_skills in enumerate(used_link_skills)
            ]
        ), used_link_skills

    choose_count = 12 - len(used_link_skills)
    chosen_links = list(itertools.combinations(available_damage_links, r=choose_count))
    if choose_count == 12:
        chosen_links = np.array(chosen_links, dtype=structured_link_skills_container)
        return np.array(
            [ComputeContainer.from_link_skills(ContainerType.LINK_SKILLS, link_skills, disabled_link_skills, idx) for idx, link_skills in enumerate()]
        ), chosen_links
    all_link_combos = np.array(
        [(*used, *chosen) for used, chosen in itertools.product([used_link_skills], chosen_links)], dtype=structured_link_skills_container
    )
    return np.array(
        [
            ComputeContainer.from_link_skills(ContainerType.LINK_SKILLS, link_skills, disabled_link_skills, idx)
            for idx, link_skills in enumerate(all_link_combos)
        ]
    ), all_link_combos


def compute_combination(*args) -> NDArray:
    return np.array(np.meshgrid(*args)).T.reshape(-1, len(args))
