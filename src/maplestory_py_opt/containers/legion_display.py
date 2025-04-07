import dataclasses

import numpy as np
from numpy._typing import NDArray

from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.numpy import structured_compute_container


@dataclasses.dataclass(slots=True)
class LegionDisplay:
    legion_values: structured_compute_container = dataclasses.field(
        default_factory=lambda: np.record((6, 0, 0, 0, 0, 0, 0), dtype=structured_compute_container)
    )
    legion_grid: NDArray[int] = dataclasses.field(default_factory=lambda: np.array((0, 0, 0), dtype=np.int8))

    def update_from_combination(self, index_into: int, computation_spaces: ComputationSpaces):
        self.legion_values = computation_spaces.legion_space[index_into]
        self.legion_grid = computation_spaces.legion_combos[index_into]

    def __str__(self) -> str:
        boss_damage_str = f"\n\tBOSS DAMAGE: {self.legion_grid[0]}/40 = {self.legion_values['boss_damage']:.02%}"
        crit_damage_str = f"\n\tCRIT DAMAGE: {self.legion_grid[1]}/40 = {self.legion_values['crit_damage']:.02%}"
        ied_str = f"\n\tIED: {self.legion_grid[2]}/40 = {self.legion_values['ied']:.02%}"

        return f"\n---------- LEGION ----------{boss_damage_str}{crit_damage_str}{ied_str}"
