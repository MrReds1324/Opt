from pydantic import BaseModel, Field

from maplestory_py_opt.configs.familiar_config import FamiliarConfig
from maplestory_py_opt.configs.item_config import ItemConfig
from maplestory_py_opt.constants.classes import ClassType
from maplestory_py_opt.constants.familiars import FamiliarTier
from maplestory_py_opt.constants.hyper_stats import MAX_HYPER_STATS_POINTS
from maplestory_py_opt.constants.souls import Soul


class OptConfig(BaseModel):
    class_type: ClassType = Field(default=ClassType.NONE)
    pdr: float = Field(default=3.00)
    base_attack: float = Field(..., ge=0)
    base_boss_damage: float = Field(..., ge=0)
    base_crit_damage: float = Field(..., ge=0)
    base_damage: float = Field(..., ge=0)
    base_ied: float = Field(..., ge=0)
    weapon: ItemConfig = Field(...)
    secondary: ItemConfig = Field(...)
    emblem: ItemConfig = Field(...)
    legion_points: int = Field(default=0, le=120, ge=0)
    hyper_points: int = Field(default=0, le=MAX_HYPER_STATS_POINTS, ge=0)
    familiar_tier: FamiliarTier = Field(default=FamiliarTier.EPIC)
    used_familiars: list[FamiliarConfig] = Field(default_factory=list, max_length=3)
    soul: Soul | None = Field(default=None)
    used_link_skills: set[ClassType] = Field(default_factory=set, max_length=12)
    disabled_link_skills: set[ClassType] = Field(default_factory=set)
