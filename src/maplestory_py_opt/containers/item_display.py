import dataclasses

import numpy as np

from maplestory_py_opt.configs.item_config import ItemConfig
from maplestory_py_opt.constants.classes import ClassType
from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.constants.items import PotentialLine
from maplestory_py_opt.constants.souls import SOUL_ATT, SOUL_BOSS, SOUL_IED, Soul
from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.numpy import structured_compute_container


@dataclasses.dataclass(slots=True)
class ItemDisplay:
    item_type: ContainerType
    item_values: structured_compute_container = dataclasses.field(default_factory=lambda: np.record((0, 0, 0, 0, 0, 0, 0), dtype=structured_compute_container))
    line_1: PotentialLine = PotentialLine.NA
    line_1_display: float = 0
    line_2: PotentialLine = PotentialLine.NA
    line_2_display: float = 0
    line_3: PotentialLine = PotentialLine.NA
    line_3_display: float = 0
    soul: Soul = Soul.NA
    soul_display: float = 0

    def update_from_item_combination(
        self, container_type: ContainerType, index_into: int, computation_spaces: ComputationSpaces, class_type: ClassType, item_config: ItemConfig
    ):
        match container_type:
            case ContainerType.WEAPON:
                item_lines = computation_spaces.weapon_combos[index_into]
                self.item_values = computation_spaces.weapon_space[index_into]
            case ContainerType.SECONDARY:
                item_lines = computation_spaces.secondary_combos[index_into]
                self.item_values = computation_spaces.secondary_space[index_into]
            case ContainerType.EMBLEM:
                item_lines = computation_spaces.emblem_combos[index_into]
                self.item_values = computation_spaces.emblem_space[index_into]
            case _:
                raise ValueError(f"Invalid container type for item {container_type}")

        from maplestory_py_opt.utilities.enum_utilties import get_potential_line_value

        self.line_1 = item_lines[0]
        self.line_1_display = get_potential_line_value(self.line_1, item_level=item_config.item_level)
        self.line_2 = item_lines[1]
        self.line_2_display = get_potential_line_value(self.line_2, item_level=item_config.item_level)
        self.line_3 = item_lines[2]
        self.line_3_display = get_potential_line_value(self.line_3, item_level=item_config.item_level)

    def update_from_soul_combination(self, index_into: int, computation_spaces: ComputationSpaces):
        self.soul = computation_spaces.soul_combos[index_into]

        match self.soul:
            case Soul.SA:
                self.soul_display = SOUL_ATT
            case Soul.SB:
                self.soul_display = SOUL_BOSS
            case Soul.SI:
                self.soul_display = SOUL_IED
            case Soul.NA:
                self.soul_display = 0
            case _:
                raise ValueError(f"Invalid soul type {self.soul}")

    def __str__(self) -> str:
        from maplestory_py_opt.utilities.enum_utilties import make_words_from_enum

        first_line_str = f"\n\t{make_words_from_enum(self.line_1)}: {self.line_1_display:.02%}"
        second_line_str = f"\n\t{make_words_from_enum(self.line_2)}: {self.line_2_display:.02%}"
        third_line_str = f"\n\t{make_words_from_enum(self.line_3)}: {self.line_3_display:.02%}"

        soul = ""
        match self.item_type:
            case ContainerType.WEAPON:
                item_type = "WEAPON"
                soul = f"\n\tSOUL - {make_words_from_enum(self.soul)}: {self.soul_display:.02%}"
            case ContainerType.SECONDARY:
                item_type = "SECONDARY"
            case ContainerType.EMBLEM:
                item_type = "EMBLEM"

        return f"\n---------- {item_type} ----------{first_line_str}{second_line_str}{third_line_str}{soul}"
