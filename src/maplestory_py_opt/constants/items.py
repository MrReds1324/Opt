from enum import StrEnum


class PotentialLine(StrEnum):
    NA = "NA"  # Other or Not available as an option
    LA = "LA"  # Legendary ATT
    LI = "LI"  # Legendary IED
    LB = "LB"  # Legendary BOSS
    UA = "UA"  # Unique ATT
    UI = "UI"  # Unique IED
    UB = "UB"  # Unique BOSS


LEGENDARY_POTENTIAL_ATT = 0.12
LEGENDARY_POTENTIAL_BOSS = 0.4
LEGENDARY_POTENTIAL_IED = 0.4
UNIQUE_POTENTIAL_ATT = 0.09
UNIQUE_POTENTIAL_BOSS = 0.3
UNIQUE_POTENTIAL_IED = 0.3

LEGENDARY_WEAPON_LINES = (PotentialLine.LA, PotentialLine.LB, PotentialLine.LI)
UNIQUE_WEAPON_LINES = (PotentialLine.UA, PotentialLine.UB, PotentialLine.UI)

LEGENDARY_SECONDARY_LINES = (PotentialLine.LA, PotentialLine.LB, PotentialLine.LI)
UNIQUE_SECONDARY_LINES = (PotentialLine.UA, PotentialLine.UB, PotentialLine.UI)

LEGENDARY_EMBLEM_LINES = (PotentialLine.LA, PotentialLine.LI)
UNIQUE_EMBLEM_LINES = (PotentialLine.UA, PotentialLine.UI)
