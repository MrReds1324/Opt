from enum import StrEnum


class Soul(StrEnum):
    NA = "NA"  # Other or not available
    SA = "SA"  # Soul ATT
    SI = "SI"  # Soul IED
    SB = "SB"  # Soul BOSS


SOULS = (Soul.SA, Soul.SB, Soul.SI)

SOUL_ATT = 0.03
SOUL_BOSS = 0.07
SOUL_IED = 0.07
