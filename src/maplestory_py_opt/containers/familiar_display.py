import dataclasses

import numpy as np

from maplestory_py_opt.constants.familiars import FamiliarLine
from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.numpy import structured_familiars_container


@dataclasses.dataclass(slots=True)
class FamiliarsDisplay:
    familiars: structured_familiars_container = dataclasses.field(
        default_factory=lambda: np.record(((FamiliarLine.NA, FamiliarLine.NA),) * 3, dtype=structured_familiars_container)
    )
    familiars_display: tuple[tuple[float, float], ...] = dataclasses.field(default_factory=lambda: ((0, 0), (0, 0), (0, 0)))

    def update_from_combination(self, index_into: int, computation_spaces: ComputationSpaces):
        self.familiars = computation_spaces.familiar_combos[index_into]

        from maplestory_py_opt.utilities.enum_utilties import get_familiar_line_value

        self.familiars_display = tuple((get_familiar_line_value(familiar["top"]), get_familiar_line_value(familiar["bottom"])) for familiar in self.familiars)

    def __str__(self) -> str:
        from maplestory_py_opt.utilities.enum_utilties import make_words_from_enum

        all_familiar_str = []
        for index, familiar in enumerate(zip(self.familiars, self.familiars_display, strict=True), start=1):
            familiar_str = f"\n\tFAMILIAR {index}"
            familiar_line, familiar_value = familiar
            familiar_str += f"\n\t\t{make_words_from_enum(familiar_line[0])}: {familiar_value[0]:.02%}"
            familiar_str += f"\n\t\t{make_words_from_enum(familiar_line[1])}: {familiar_value[1]:.02%}"
            all_familiar_str.append(familiar_str)

        return f"\n---------- FAMILIARS ----------{''.join(all_familiar_str)}"
