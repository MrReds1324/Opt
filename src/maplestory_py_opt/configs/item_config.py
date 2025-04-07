from pydantic import BaseModel, Field

from maplestory_py_opt.constants.items import PotentialLine


class ItemConfig(BaseModel):
    item_level: int = Field(..., ge=0, le=300)
    main_lines: list[PotentialLine] = Field(default_factory=list, max_length=3)
