from enum import StrEnum


class FamiliarTier(StrEnum):
    LEGENDARY = "LEGENDARY"
    UNIQUE = "UNIQUE"
    EPIC = "EPIC"


class FamiliarLine(StrEnum):
    NA = "NA"  # Other or Not available as an option
    LA = "LA"  # Legendary ATT
    LI = "LI"  # Legendary IED
    LB = "LB"  # Legendary BOSS
    LC = "LC"  # Legendary CRIT
    UA = "UA"  # Unique ATT
    UI = "UI"  # Unique IED
    UB = "UB"  # Unique BOSS
    UC = "UC"  # Unique CRIT
    EA = "EA"  # Epic ATT
    EI = "EI"  # Epic IED
    EB = "EB"  # Epic BOSS
    RA = "RA"  # Rare ATT
    RI = "RI"  # Rare IED
    RB = "RB"  # Rare BOSS


LEGENDARY_FAMILIAR_LINES = (FamiliarLine.LA, FamiliarLine.LB, FamiliarLine.LI, FamiliarLine.LC)
UNIQUE_FAMILIAR_LINES = (FamiliarLine.UA, FamiliarLine.UB, FamiliarLine.UI, FamiliarLine.UC)
EPIC_FAMILIAR_LINES = (FamiliarLine.EA, FamiliarLine.EB, FamiliarLine.EI)
RARE_FAMILIAR_LINES = (FamiliarLine.RA, FamiliarLine.RB, FamiliarLine.RI)

LEGENDARY_FAMILIAR_ATT = 0.09
LEGENDARY_FAMILIAR_IED = 0.50
LEGENDARY_FAMILIAR_BOSS = 0.50
LEGENDARY_FAMILIAR_CRIT = 0.06

UNIQUE_FAMILIAR_ATT = 0.05
UNIQUE_FAMILIAR_IED = 0.40
UNIQUE_FAMILIAR_BOSS = 0.40
UNIQUE_FAMILIAR_CRIT = 0.03

EPIC_FAMILIAR_ATT = 0.03
EPIC_FAMILIAR_IED = 0.30
EPIC_FAMILIAR_BOSS = 0.30

RARE_FAMILIAR_ATT = 0.02
RARE_FAMILIAR_IED = 0.15
RARE_FAMILIAR_BOSS = 0.02
