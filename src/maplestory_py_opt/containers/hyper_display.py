import dataclasses

import numpy as np
from numpy._typing import NDArray

from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.numpy import structured_compute_container


@dataclasses.dataclass(slots=True)
class HyperDisplay:
    hyper_values: structured_compute_container = dataclasses.field(default_factory=lambda: np.record((3, 0, 0, 0, 0, 0, 0), dtype=structured_compute_container))
    hyper_levels: NDArray[int] = dataclasses.field(default_factory=lambda: np.array((0, 0, 0, 0), dtype=np.int8))

    def update_from_combination(self, index_into: structured_compute_container, computation_spaces: ComputationSpaces):
        self.hyper_values = computation_spaces.hyper_space[index_into]
        self.hyper_levels = computation_spaces.hyper_combos[index_into]

    def __str__(self) -> str:
        boss_damage_str = f"\n\tBOSS DAMAGE: {self.hyper_levels[0]}/15 = {self.hyper_values['boss_damage']:.02%}"
        crit_damage_str = f"\n\tCRIT DAMAGE: {self.hyper_levels[1]}/15 = {self.hyper_values['crit_damage']:.02%}"
        damage_str = f"\n\tDAMAGE: {self.hyper_levels[2]}/15 = {self.hyper_values['damage']:.02%}"
        ied_str = f"\n\tIED: {self.hyper_levels[3]}/15 = {self.hyper_values['ied']:.02%}"

        return f"\n---------- HYPER STATS ----------{boss_damage_str}{crit_damage_str}{damage_str}{ied_str}"
