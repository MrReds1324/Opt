import numba
from numpy._typing import NDArray

from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.containers.numba import structured_compute_container_numba, structured_result_container_numba
from maplestory_py_opt.containers.numpy import (
    structured_compute_container,
    structured_result_container,
)


@numba.njit(numba.float64(numba.types.List(numba.float64)), cache=True)
def calculate_product(values: list[numba.float64]) -> numba.float64:
    new_value = 1.0
    for value in values:
        new_value = round(new_value * value, 4)
    return new_value


@numba.njit(numba.float64(numba.float64, numba.types.List(numba.float64)), cache=True)
def update_ied_numba(current_value: numba.float64, ied_values: list[numba.float64]) -> numba.float64:
    flipped_values = []
    flipped_values.append(round(1.0 - current_value, 4))
    for value in ied_values:
        flipped_values.append(round(1.0 - value, 4))  # noqa: PERF401

    return round(1.0 - calculate_product(flipped_values), 4)


@numba.njit(numba.float64(numba.float64, numba.types.List(numba.float64, reflected=True)), cache=True)
def update_ied_numba_reflected(current_value: numba.float64, ied_values: list[numba.float64]) -> numba.float64:
    flipped_values = []
    flipped_values.append(round(1.0 - current_value, 4))
    for value in ied_values:
        flipped_values.append(round(1.0 - value, 4))  # noqa: PERF401

    return round(1.0 - calculate_product(flipped_values), 4)


@numba.njit(numba.float64(numba.float64, numba.types.List(numba.float64)), cache=True)
def calculate_attack_numba(base_attack: numba.float64, attack_values: list[numba.float64]) -> numba.float64:
    return round(sum(attack_values, start=1.0 + base_attack), 4)


@numba.njit(numba.float64(numba.float64, numba.float64, numba.types.List(numba.float64)), cache=True)
def calculate_boss_damage_numba(base_boss_damage: numba.float64, base_damage: numba.float64, boss_and_damage_values: list[numba.float64]) -> numba.float64:
    return round(sum(boss_and_damage_values, start=1.0 + base_boss_damage + base_damage), 4)


@numba.njit(numba.float64(numba.float64, numba.types.List(numba.float64)), cache=True)
def calculate_crit_damage_numba(base_crit_damage: numba.float64, crit_damage_values: list[numba.float64]) -> numba.float64:
    return round(sum(crit_damage_values, start=1.35 + base_crit_damage), 4)


@numba.njit(
    numba.void(
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.types.Array(structured_compute_container_numba, 1, "C"),
        structured_result_container_numba,
    ),
    cache=True,
)
def calculate_multiplier_numba(
    base_attack: numba.float64,
    base_boss_damage: numba.float64,
    base_crit_damage: numba.float64,
    base_damage: numba.float64,
    base_ied: numba.float64,
    pdr: numba.float64,
    compute_containers: NDArray[structured_compute_container],
    result_record: structured_result_container,
) -> None:
    all_ied_values = []
    all_attack_values = []
    all_boss_and_damage_values = []
    all_crit_damage_values = []

    for compute_container in compute_containers:
        all_attack_values.append(compute_container["attack"])
        all_boss_and_damage_values.append(compute_container["boss_damage"])
        all_crit_damage_values.append(compute_container["crit_damage"])
        all_boss_and_damage_values.append(compute_container["damage"])
        all_ied_values.append(compute_container["ied"])

    total_ied = update_ied_numba(base_ied, all_ied_values)

    damage_reduction = round(1.0 - (pdr * (1.0 - total_ied)), 4)
    if damage_reduction <= 0.0:
        return

    total_attack = calculate_attack_numba(base_attack, all_attack_values)

    total_boss_damage = calculate_boss_damage_numba(base_boss_damage, base_damage, all_boss_and_damage_values)

    total_crit_damage = calculate_crit_damage_numba(base_crit_damage, all_crit_damage_values)

    multiplier = round(total_crit_damage * total_attack * total_boss_damage * damage_reduction, 4)

    result_record["multiplier"] = multiplier
    result_record["total_attack"] = total_attack - 1.0
    result_record["total_boss_damage"] = total_boss_damage - 1.0
    result_record["total_crit_damage"] = total_crit_damage - 1.35
    result_record["total_ied"] = total_ied

    for compute_container in compute_containers:
        container_type = compute_container["container_type"]
        index_into = compute_container["index_into"]
        match container_type:
            case ContainerType.WEAPON:
                result_record["weapon"] = index_into
            case ContainerType.SOUL:
                result_record["soul"] = index_into
            case ContainerType.SECONDARY:
                result_record["secondary"] = index_into
            case ContainerType.EMBLEM:
                result_record["emblem"] = index_into
            case ContainerType.FAMILIAR:
                result_record["familiars"] = index_into
            case ContainerType.HYPER_STATS:
                result_record["hyper_stats"] = index_into
            case ContainerType.LEGION_GRID:
                result_record["legion_grid"] = index_into
            case ContainerType.LINK_SKILLS:
                result_record["link_skills"] = index_into


@numba.njit(
    numba.types.void(
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.float64,
        numba.types.Array(structured_compute_container_numba, 2, "C"),
        numba.types.Array(structured_result_container_numba, 1, "C"),
    ),
    parallel=True,
    cache=True,
)
def calculate_all_multipliers_numba(
    base_attack: numba.float64,
    base_boss_damage: numba.float64,
    base_crit_damage: numba.float64,
    base_damage: numba.float64,
    base_ied: numba.float64,
    pdr: numba.float64,
    chunk_combinations: NDArray[structured_compute_container],
    chunk_results: NDArray[structured_result_container],
) -> None:
    for i in numba.prange(chunk_combinations.shape[0]):
        calculate_multiplier_numba(
            base_attack=base_attack,
            base_boss_damage=base_boss_damage,
            base_crit_damage=base_crit_damage,
            base_damage=base_damage,
            base_ied=base_ied,
            pdr=pdr,
            compute_containers=chunk_combinations[i],
            result_record=chunk_results[i],
        )
