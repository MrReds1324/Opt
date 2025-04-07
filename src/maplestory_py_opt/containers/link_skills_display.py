import dataclasses

import numpy as np

from maplestory_py_opt.constants.classes import DAMAGE_LINKS, ClassType
from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.link_skill import LinkSkill
from maplestory_py_opt.containers.numpy import structured_compute_container, structured_link_skills_container


@dataclasses.dataclass(slots=True)
class LinkSkillsDisplay:
    link_skills: structured_link_skills_container = dataclasses.field(
        default_factory=lambda: np.record((ClassType.NONE,) * 12, dtype=structured_link_skills_container)
    )
    link_skills_values: structured_compute_container = dataclasses.field(
        default_factory=lambda: np.record((0, 0, 0, 0, 0, 0, 0), dtype=structured_compute_container)
    )
    link_skills_display: tuple[LinkSkill | None, ...] = dataclasses.field(default_factory=lambda: (None,) * 12)
    disabled_link_skills: set[ClassType] = dataclasses.field(default_factory=set)

    def update_from_combination(self, index_into: int, computation_spaces: ComputationSpaces, disabled_link_skills: set[ClassType]):
        self.link_skills = computation_spaces.link_skill_combos[index_into]
        self.link_skills_values = computation_spaces.link_skill_space[index_into]
        self.link_skills_display = tuple(DAMAGE_LINKS.get(link_skill) for link_skill in self.link_skills)
        self.disabled_link_skills = disabled_link_skills

    def __str__(self) -> str:
        all_link_skills_str = []
        for index, link_skill in enumerate(zip(self.link_skills, self.link_skills_display, strict=True), start=1):
            link_name, link_values = link_skill

            disabled_str = ""
            if link_name in self.disabled_link_skills:
                disabled_str = "(DISABLED) "

            link_skill_str = f"\n\tLINK SKILL {index} {disabled_str}- {link_name.replace('_', ' ')}"
            if link_values is not None:
                if attack := link_values.attack:
                    link_skill_str += f"\n\t\t(M)ATTACK: {attack:.02%}"
                if boss_damage := link_values.boss_damage:
                    link_skill_str += f"\n\t\tBOSS DAMAGE: {boss_damage:.02%}"
                if crit_damage := link_values.crit_damage:
                    link_skill_str += f"\n\t\tCRIT DAMAGE: {crit_damage:.02%}"
                if damage := link_values.damage:
                    link_skill_str += f"\n\t\tDAMAGE: {damage:.02%}"
                if ied := link_values.ied:
                    link_skill_str += f"\n\t\tIED: {ied:.02%}"
            all_link_skills_str.append(link_skill_str)

        return f"\n---------- LINK SKILLS ----------{''.join(all_link_skills_str)}"
