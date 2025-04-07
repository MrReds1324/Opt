import numpy as np
from numpy._typing import NDArray

from maplestory_py_opt.constants.classes import DAMAGE_LINKS, ClassType
from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.constants.familiars import (
    EPIC_FAMILIAR_ATT,
    EPIC_FAMILIAR_BOSS,
    EPIC_FAMILIAR_IED,
    LEGENDARY_FAMILIAR_ATT,
    LEGENDARY_FAMILIAR_BOSS,
    LEGENDARY_FAMILIAR_CRIT,
    LEGENDARY_FAMILIAR_IED,
    RARE_FAMILIAR_ATT,
    RARE_FAMILIAR_BOSS,
    RARE_FAMILIAR_IED,
    UNIQUE_FAMILIAR_ATT,
    UNIQUE_FAMILIAR_BOSS,
    UNIQUE_FAMILIAR_CRIT,
    UNIQUE_FAMILIAR_IED,
    FamiliarLine,
)
from maplestory_py_opt.constants.hyper_stats import HYPER_BOSS_DAMAGE, HYPER_CRIT_DAMAGE, HYPER_DAMAGE, HYPER_IED
from maplestory_py_opt.constants.items import (
    LEGENDARY_POTENTIAL_ATT,
    LEGENDARY_POTENTIAL_BOSS,
    LEGENDARY_POTENTIAL_IED,
    UNIQUE_POTENTIAL_ATT,
    UNIQUE_POTENTIAL_BOSS,
    UNIQUE_POTENTIAL_IED,
    PotentialLine,
)
from maplestory_py_opt.constants.legion import LEGION_BOSS_GRID, LEGION_CRIT_GRID, LEGION_IED_GRID
from maplestory_py_opt.constants.souls import SOUL_ATT, SOUL_BOSS, SOUL_IED, Soul
from maplestory_py_opt.containers.numpy import structured_compute_container, structured_familiar_container


class ComputeContainer:
    @staticmethod
    def from_item(
        container_type: ContainerType, class_type: ClassType, item_level: int, item_lines: NDArray[str], index_into: int
    ) -> NDArray[structured_compute_container]:
        from maplestory_py_opt.utilities.helper_utilities import update_ied

        if container_type not in (ContainerType.WEAPON, ContainerType.SECONDARY, ContainerType.EMBLEM):
            raise ValueError(f"Invalid container type for item - {container_type}")

        attack = 0
        boss_damage = 0
        ied = 0

        from maplestory_py_opt.utilities.helper_utilities import get_attack_addition

        for line in item_lines:
            match line:
                case PotentialLine.LA:
                    attack += (LEGENDARY_POTENTIAL_ATT + get_attack_addition(item_level)) * (1 if class_type != ClassType.ZERO else 2)
                case PotentialLine.LB:
                    boss_damage += LEGENDARY_POTENTIAL_BOSS * (1 if class_type != ClassType.ZERO else 2)
                case PotentialLine.LI:
                    ied = update_ied(ied, *((LEGENDARY_POTENTIAL_IED,) if class_type != ClassType.ZERO else (LEGENDARY_POTENTIAL_IED, LEGENDARY_POTENTIAL_IED)))

                case PotentialLine.UA:
                    attack += (UNIQUE_POTENTIAL_ATT + get_attack_addition(item_level)) * (1 if class_type != ClassType.ZERO else 2)
                case PotentialLine.UB:
                    boss_damage += UNIQUE_POTENTIAL_BOSS * (1 if class_type != ClassType.ZERO else 2)
                case PotentialLine.UI:
                    ied = update_ied(ied, *((UNIQUE_POTENTIAL_IED,) if class_type != ClassType.ZERO else (UNIQUE_POTENTIAL_IED, UNIQUE_POTENTIAL_IED)))

                case PotentialLine.NA:
                    continue

                case _:
                    raise ValueError("Invalid Potential Line in item")

        # Haku fan only applies magic att to the character, make sure we ignore this
        if class_type == ClassType.KANNA and container_type == ContainerType.SECONDARY:
            boss_damage = 0
            ied = 0

        return np.array((container_type.value, index_into, round(attack, 4), round(boss_damage, 4), 0, 0, round(ied, 4)), dtype=structured_compute_container)

    @staticmethod
    def from_soul(container_type: ContainerType, soul: Soul, index_into: int) -> NDArray[structured_compute_container]:
        if container_type != ContainerType.SOUL:
            raise ValueError(f"Invalid container type for soul - {container_type}")

        attack = 0
        boss_damage = 0
        ied = 0

        match soul:
            case Soul.SA:
                attack = SOUL_ATT
            case Soul.SB:
                boss_damage = SOUL_BOSS
            case Soul.SI:
                ied = SOUL_IED
            case Soul.NA:
                ...
            case _:
                raise ValueError("Invalid Soul type")

        return np.array((container_type.value, index_into, attack, boss_damage, 0, 0, ied), dtype=structured_compute_container)

    @staticmethod
    def from_familiars(
        container_type: ContainerType, familiars: NDArray[structured_familiar_container], index_into: int
    ) -> NDArray[structured_compute_container]:
        from maplestory_py_opt.utilities.helper_utilities import update_ied

        if container_type != ContainerType.FAMILIAR:
            raise ValueError(f"Invalid container type for familiar - {container_type}")

        attack = 0
        boss_damage = 0
        crit_damage = 0
        ied = 0

        for familiar_lines in familiars:
            for line in familiar_lines:
                match line:
                    case FamiliarLine.LA:
                        attack += LEGENDARY_FAMILIAR_ATT
                    case FamiliarLine.LB:
                        boss_damage += LEGENDARY_FAMILIAR_BOSS
                    case FamiliarLine.LC:
                        crit_damage += LEGENDARY_FAMILIAR_CRIT
                    case FamiliarLine.LI:
                        ied = update_ied(ied, LEGENDARY_FAMILIAR_IED)

                    case FamiliarLine.UA:
                        attack += UNIQUE_FAMILIAR_ATT
                    case FamiliarLine.UB:
                        boss_damage += UNIQUE_FAMILIAR_BOSS
                    case FamiliarLine.UC:
                        crit_damage += UNIQUE_FAMILIAR_CRIT
                    case FamiliarLine.UI:
                        ied = update_ied(ied, UNIQUE_FAMILIAR_IED)

                    case FamiliarLine.EA:
                        attack += EPIC_FAMILIAR_ATT
                    case FamiliarLine.EB:
                        boss_damage += EPIC_FAMILIAR_BOSS
                    case FamiliarLine.EI:
                        ied = update_ied(ied, EPIC_FAMILIAR_IED)

                    case FamiliarLine.RA:
                        attack += RARE_FAMILIAR_ATT
                    case FamiliarLine.RB:
                        boss_damage += RARE_FAMILIAR_BOSS
                    case FamiliarLine.RI:
                        ied = update_ied(ied, RARE_FAMILIAR_IED)

                    case FamiliarLine.NA:
                        continue

                    case _:
                        raise ValueError("Invalid Potential Line in item")

        # Familiars have a maximum total boss of 120%
        boss_damage = min(boss_damage, 1.20)

        return np.array(
            (container_type.value, index_into, round(attack, 4), round(boss_damage, 4), round(crit_damage, 4), 0, round(ied, 4)),
            dtype=structured_compute_container,
        )

    @staticmethod
    def from_hyper_stats(container_type: ContainerType, hyper_stats: NDArray[int], index_into: int) -> NDArray[structured_compute_container]:
        if container_type != ContainerType.HYPER_STATS:
            raise ValueError(f"Invalid container type for hyper stats - {container_type}")

        return np.array(
            (
                container_type.value,
                index_into,
                0,
                HYPER_BOSS_DAMAGE[hyper_stats[0]],
                HYPER_CRIT_DAMAGE[hyper_stats[1]],
                HYPER_DAMAGE[hyper_stats[2]],
                HYPER_IED[hyper_stats[3]],
            ),
            dtype=structured_compute_container,
        )

    @staticmethod
    def from_legion_grid(container_type: ContainerType, legion_grid: NDArray[int], index_into: int) -> NDArray[structured_compute_container]:
        if container_type != ContainerType.LEGION_GRID:
            raise ValueError(f"Invalid container type for hyper stats - {container_type}")

        return np.array(
            (
                container_type.value,
                index_into,
                0,
                round(legion_grid[0] * LEGION_BOSS_GRID, 4),
                round(legion_grid[1] * LEGION_CRIT_GRID, 4),
                0,
                round(legion_grid[2] * LEGION_IED_GRID, 4),
            ),
            dtype=structured_compute_container,
        )

    @classmethod
    def from_link_skills(
        cls, container_type: ContainerType, link_skills: NDArray[ClassType], disabled_link_skills: set[ClassType], index_into: int
    ) -> NDArray[structured_compute_container]:
        if container_type != ContainerType.LINK_SKILLS:
            raise ValueError(f"Invalid container type for hyper stats - {container_type}")

        from maplestory_py_opt.utilities.helper_utilities import update_ied

        attack = 0
        boss_damage = 0
        crit_damage = 0
        damage = 0
        ied = 0

        for link_skill in link_skills:
            if link_skill in disabled_link_skills:
                continue

            if link_skill_values := DAMAGE_LINKS.get(link_skill):
                attack += link_skill_values.attack
                boss_damage += link_skill_values.boss_damage
                crit_damage += link_skill_values.crit_damage
                damage += link_skill_values.damage
                ied = update_ied(ied, link_skill_values.ied)

        return np.array(
            (container_type.value, index_into, round(attack, 4), round(boss_damage, 4), round(crit_damage, 4), round(damage, 4), round(ied, 4)),
            dtype=structured_compute_container,
        )
