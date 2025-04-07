import dataclasses

from numpy._typing import NDArray

from maplestory_py_opt.configs.opt_config import OptConfig
from maplestory_py_opt.constants.classes import (
    ClassType,
)
from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.familiar_display import FamiliarsDisplay
from maplestory_py_opt.containers.hyper_display import HyperDisplay
from maplestory_py_opt.containers.item_display import ItemDisplay
from maplestory_py_opt.containers.legion_display import LegionDisplay
from maplestory_py_opt.containers.link_skills_display import LinkSkillsDisplay
from maplestory_py_opt.containers.numpy import structured_compute_container, structured_result_container


@dataclasses.dataclass(slots=True)
class ResultDisplay:
    result_container: structured_result_container
    compute_containers: NDArray[structured_compute_container]
    class_type: ClassType = ClassType.NONE
    weapon: ItemDisplay = dataclasses.field(default_factory=lambda: ItemDisplay(item_type=ContainerType.WEAPON))
    secondary: ItemDisplay = dataclasses.field(default_factory=lambda: ItemDisplay(item_type=ContainerType.SECONDARY))
    emblem: ItemDisplay = dataclasses.field(default_factory=lambda: ItemDisplay(item_type=ContainerType.EMBLEM))
    hyper_stats: HyperDisplay = dataclasses.field(default_factory=HyperDisplay)
    legion_stats: LegionDisplay = dataclasses.field(default_factory=LegionDisplay)
    familiars: FamiliarsDisplay = dataclasses.field(default_factory=FamiliarsDisplay)
    link_skills: LinkSkillsDisplay = dataclasses.field(default_factory=LinkSkillsDisplay)
    pdr: float = 0

    def __init__(
        self,
        *,
        result_container: structured_result_container,
        computation_spaces: ComputationSpaces,
        config: OptConfig,
    ):
        for field in dataclasses.fields(self):
            if field.name in ("result_container", "compute_containers", "class_type", "pdr"):
                continue
            setattr(self, field.name, field.default_factory())
        self.result_container = result_container
        self.class_type = config.class_type

        self.weapon.update_from_item_combination(ContainerType.WEAPON, result_container["weapon"], computation_spaces, config.class_type, config.weapon)
        self.weapon.update_from_soul_combination(result_container["soul"], computation_spaces)
        self.secondary.update_from_item_combination(
            ContainerType.SECONDARY, result_container["secondary"], computation_spaces, config.class_type, config.secondary
        )
        self.emblem.update_from_item_combination(ContainerType.EMBLEM, result_container["emblem"], computation_spaces, config.class_type, config.emblem)
        self.familiars.update_from_combination(result_container["familiars"], computation_spaces)
        self.hyper_stats.update_from_combination(result_container["hyper_stats"], computation_spaces)
        self.legion_stats.update_from_combination(result_container["legion_grid"], computation_spaces)
        self.link_skills.update_from_combination(result_container["link_skills"], computation_spaces, config.disabled_link_skills)
        self.pdr = config.pdr

    def __str__(self) -> str:
        return (
            f"\n---------- OVERALL MULTIPLIER: {self.result_container['multiplier']} ----------"
            f"\n\tTOTAL (M)ATTACK: {self.result_container['total_attack']:.02%}"
            f"\n\tTOTAL BOSS + DAMAGE: {self.result_container['total_boss_damage']:.02%}"
            f"\n\tTOTAL CRIT DAMAGE: {self.result_container['total_crit_damage']:.02%}"
            f"\n\tTOTAL IED: {self.result_container['total_ied']:.02%}"
            f"\n\tPERCENTAGE DAMAGE APPLIED AGAINST {self.pdr:.02%} PDR: {round(1.0 - (self.pdr * (1.0 - self.result_container['total_ied'])), 4):.02%}"
            f"{''.join(str(x) for x in (self.weapon, self.secondary, self.emblem, self.familiars, self.legion_stats, self.hyper_stats, self.link_skills))}\n\n"
        )
