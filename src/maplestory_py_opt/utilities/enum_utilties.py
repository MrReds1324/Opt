from maplestory_py_opt.constants.familiars import (
    EPIC_FAMILIAR_ATT,
    EPIC_FAMILIAR_BOSS,
    EPIC_FAMILIAR_IED,
    LEGENDARY_FAMILIAR_ATT,
    LEGENDARY_FAMILIAR_BOSS,
    LEGENDARY_FAMILIAR_CRIT,
    LEGENDARY_FAMILIAR_IED,
    RARE_FAMILIAR_ATT,
    RARE_FAMILIAR_BOSS,
    RARE_FAMILIAR_IED,
    UNIQUE_FAMILIAR_ATT,
    UNIQUE_FAMILIAR_BOSS,
    UNIQUE_FAMILIAR_CRIT,
    UNIQUE_FAMILIAR_IED,
    FamiliarLine,
)
from maplestory_py_opt.constants.items import (
    LEGENDARY_POTENTIAL_ATT,
    LEGENDARY_POTENTIAL_BOSS,
    LEGENDARY_POTENTIAL_IED,
    UNIQUE_POTENTIAL_ATT,
    UNIQUE_POTENTIAL_BOSS,
    UNIQUE_POTENTIAL_IED,
    PotentialLine,
)
from maplestory_py_opt.utilities.helper_utilities import get_attack_addition


def get_potential_line_value(value: PotentialLine, item_level: int) -> float:
    match value:
        case PotentialLine.LA:
            return LEGENDARY_POTENTIAL_ATT + get_attack_addition(item_level)
        case PotentialLine.LB:
            return LEGENDARY_POTENTIAL_BOSS
        case PotentialLine.LI:
            return LEGENDARY_POTENTIAL_IED

        case PotentialLine.UA:
            return UNIQUE_POTENTIAL_ATT + get_attack_addition(item_level)
        case PotentialLine.UB:
            return UNIQUE_POTENTIAL_BOSS
        case PotentialLine.UI:
            return UNIQUE_POTENTIAL_IED

        case PotentialLine.NA:
            return 0

        case _:
            raise ValueError("Invalid Potential Line in item")


def get_familiar_line_value(value: FamiliarLine) -> float:
    match value:
        case FamiliarLine.LA:
            return LEGENDARY_FAMILIAR_ATT
        case FamiliarLine.LB:
            return LEGENDARY_FAMILIAR_BOSS
        case FamiliarLine.LC:
            return LEGENDARY_FAMILIAR_CRIT
        case FamiliarLine.LI:
            return LEGENDARY_FAMILIAR_IED

        case FamiliarLine.UA:
            return UNIQUE_FAMILIAR_ATT
        case FamiliarLine.UB:
            return UNIQUE_FAMILIAR_BOSS
        case FamiliarLine.UC:
            return UNIQUE_FAMILIAR_CRIT
        case FamiliarLine.UI:
            return UNIQUE_FAMILIAR_IED

        case FamiliarLine.EA:
            return EPIC_FAMILIAR_ATT
        case FamiliarLine.EB:
            return EPIC_FAMILIAR_BOSS
        case FamiliarLine.EI:
            return EPIC_FAMILIAR_IED

        case FamiliarLine.RA:
            return RARE_FAMILIAR_ATT
        case FamiliarLine.RB:
            return RARE_FAMILIAR_BOSS
        case FamiliarLine.RI:
            return RARE_FAMILIAR_IED

        case FamiliarLine.NA:
            return 0

        case _:
            raise ValueError("Invalid Familiar Line in familiar")


def first_letter_to_word(value: str) -> str:
    match value[0]:
        case "L":
            return "Legendary"
        case "U":
            return "Unique"
        case "E":
            return "Epic"
        case "R":
            return "Rare"
        case "N":
            return "Nothing"
        case _:
            return ""


def second_letter_to_word(value: str) -> str:
    match value[1]:
        case "A":
            return "(M)Attack"
        case "B":
            return "Boss Damage"
        case "C":
            return "Crit Damage"
        case "I":
            return "IED"
        case _:
            return ""


def make_words_from_enum(value: str) -> str:
    first_word = first_letter_to_word(value)
    if not first_word:
        return second_letter_to_word(value)

    if first_word == "Nothing":
        return first_word

    return f"{first_word} {second_letter_to_word(value)}"
