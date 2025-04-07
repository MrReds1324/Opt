from pydantic import BaseModel, Field

from maplestory_py_opt.constants.familiars import FamiliarLine


class FamiliarConfig(BaseModel):
    lines: list[FamiliarLine] = Field(..., max_length=2)
