from enum import StrEnum

from maplestory_py_opt.containers.link_skill import LinkSkill


class ClassType(StrEnum):
    ADELE = "ADELE"
    ANGELIC_BUSTER = "ANGELIC_BUSTER"
    ARAN = "ARAN"
    ARK = "ARK"
    CADENA = "CADENA"
    CYGNUS_KNIGHT = "CYGNUS_KNIGHT"
    DEMON_AVENGER = "DEMON_AVENGER"
    DEMON_SLAYER = "DEMON_SLAYER"
    EVAN = "EVAN"
    EXPLORER_ARCHER = "EXPLORER_ARCHER"
    EXPLORER_MAGE = "EXPLORER_MAGE"
    EXPLORER_PIRATE = "EXPLORER_PIRATE"
    EXPLORER_THIEF = "EXPLORER_THIEF"
    EXPLORER_WARRIOR = "EXPLORER_WARRIOR"
    HAYATO = "HAYATO"
    HOYOUNG = "HOYOUNG"
    ILLIUM = "ILLIUM"
    KAIN = "KAIN"
    KAISER = "KAISER"
    KANNA = "KANNA"
    KHALI = "KHALI"
    KINESIS = "KINESIS"
    LARA = "LARA"
    LUMINOUS = "LUMINOUS"
    LYNN = "LYNN"
    MERCEDES = "MERCEDES"
    MIHILE = "MIHILE"
    PHANTOM = "PHANTOM"
    RESISTANCE = "RESISTANCE"
    SHADE = "SHADE"
    XENON = "XENON"
    ZERO = "ZERO"
    NONE = "NONE"


# ATTACK, BOSS DAMAGE, CRIT DAMAGE, DAMAGE, IED
DAMAGE_LINKS: dict[ClassType, LinkSkill] = {
    ClassType.ADELE: LinkSkill(0, 0.08, 0, 0, 0),
    ClassType.ANGELIC_BUSTER: LinkSkill(0, 0, 0, 1.2, 0),
    ClassType.ARK: LinkSkill(0, 0, 0, 0.16, 0),
    ClassType.CADENA: LinkSkill(0, 0, 0, 0.12, 0),
    ClassType.DEMON_AVENGER: LinkSkill(0, 0, 0, 0.15, 0),
    ClassType.DEMON_SLAYER: LinkSkill(0, 0.2, 0, 0, 0),
    ClassType.EXPLORER_MAGE: LinkSkill(0, 0, 0, 0.09, 0.09),
    ClassType.EXPLORER_THIEF: LinkSkill(0, 0, 0, 0.18, 0),
    ClassType.ILLIUM: LinkSkill(0, 0, 0, 0.12, 0),
    ClassType.KAIN: LinkSkill(0, 0.17, 0, 0, 0),
    ClassType.KANNA: LinkSkill(0, 0, 0, 0.1, 0),
    ClassType.KHALI: LinkSkill(0, 0, 0, 0.05, 0),
    ClassType.KINESIS: LinkSkill(0, 0, 0.04, 0, 0),
    ClassType.LUMINOUS: LinkSkill(0, 0, 0, 0, 0.2),
    ClassType.LYNN: LinkSkill(0, 0.1, 0, 0, 0),
    ClassType.ZERO: LinkSkill(0, 0, 0, 0, 0.1),
}
