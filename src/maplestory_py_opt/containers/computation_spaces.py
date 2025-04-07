import dataclasses
import logging

import numpy as np
from numpy._typing import ArrayLike, NDArray

from maplestory_py_opt.configs.opt_config import OptConfig
from maplestory_py_opt.constants.classes import ClassType
from maplestory_py_opt.constants.container_type import ContainerType
from maplestory_py_opt.constants.souls import (
    SOULS,
)
from maplestory_py_opt.containers.numpy import structured_result_container

LOGGER = logging.getLogger(__name__)


@dataclasses.dataclass(slots=True, kw_only=True)
class ComputationSpaces:
    legion_space: ArrayLike = dataclasses.field(default_factory=list)
    legion_combos: ArrayLike = dataclasses.field(default_factory=list)

    hyper_space: ArrayLike = dataclasses.field(default_factory=list)
    hyper_combos: ArrayLike = dataclasses.field(default_factory=list)

    weapon_space: ArrayLike = dataclasses.field(default_factory=list)
    weapon_combos: ArrayLike = dataclasses.field(default_factory=list)

    soul_space: ArrayLike = dataclasses.field(default_factory=list)
    soul_combos: ArrayLike = dataclasses.field(default_factory=list)

    secondary_space: ArrayLike = dataclasses.field(default_factory=list)
    secondary_combos: ArrayLike = dataclasses.field(default_factory=list)

    emblem_space: ArrayLike = dataclasses.field(default_factory=list)
    emblem_combos: ArrayLike = dataclasses.field(default_factory=list)

    familiar_space: ArrayLike = dataclasses.field(default_factory=list)
    familiar_combos: ArrayLike = dataclasses.field(default_factory=list)

    link_skill_space: ArrayLike = dataclasses.field(default_factory=list)
    link_skill_combos: ArrayLike = dataclasses.field(default_factory=list)

    def setup_computation_spaces(self, config: OptConfig):
        from maplestory_py_opt.utilities.combination_utilities import (
            compute_familiars,
            compute_hyper_stats,
            compute_item,
            compute_legion_grid,
            compute_link_skills,
            compute_souls,
        )

        LOGGER.info(f"Computing Hyper Stat combinations using the available {config.hyper_points} points")
        self.hyper_space, self.hyper_combos = compute_hyper_stats(config.hyper_points)

        LOGGER.info(f"Computing Legion Grid combinations using the available {config.legion_points} points")
        self.legion_space, self.legion_combos = compute_legion_grid(config.legion_points)

        if config.soul is None:
            LOGGER.info("Using all available Souls")
            self.soul_space, self.soul_combos = compute_souls(SOULS)
        else:
            LOGGER.info(f"Using a single Soul: {config.soul}")
            self.soul_space, self.soul_combos = compute_souls((config.soul,))

        LOGGER.info(f"Computing Familiar combinations using the target tier {config.familiar_tier} and used familiars {config.used_familiars}")
        self.familiar_space, self.familiar_combos = compute_familiars(config.familiar_tier, config.used_familiars)

        LOGGER.info(f"Computing Weapon combinations using the available configuration ({config.weapon})")
        self.weapon_space, self.weapon_combos = compute_item(item_config=config.weapon, class_type=config.class_type, container_type=ContainerType.WEAPON)

        if config.class_type == ClassType.ZERO:
            LOGGER.info(f"Ignoring Secondary combinations because class type is {config.class_type}")
            self.secondary_space = np.array([])
            self.secondary_combos = np.array([])
        else:
            LOGGER.info(f"Computing Secondary combinations using the available configuration ({config.secondary})")
            self.secondary_space, self.secondary_combos = compute_item(
                item_config=config.secondary, class_type=config.class_type, container_type=ContainerType.SECONDARY
            )

        LOGGER.info(f"Computing Emblem combinations using the available configuration ({config.emblem})")
        self.emblem_space, self.emblem_combos = compute_item(item_config=config.emblem, class_type=config.class_type, container_type=ContainerType.EMBLEM)

        LOGGER.info(
            f"Computing Link Skill combinations using the available used links ({config.used_link_skills}) and disabled links ({config.disabled_link_skills})"
        )
        self.link_skill_space, self.link_skill_combos = compute_link_skills(config.class_type, config.used_link_skills, config.disabled_link_skills)

    def generate_all_results(self, config: "OptConfig", *, chunk_size: int = 10, results_to_show: int = 10) -> NDArray[structured_result_container]:
        from maplestory_py_opt.utilities.combination_utilities import compute_combination
        from maplestory_py_opt.utilities.helper_utilities import chunk_generator
        from maplestory_py_opt.utilities.numba_utilities import calculate_all_multipliers_numba

        total_combinations = (
            len(self.weapon_space)
            * len(self.soul_space)
            * len(self.secondary_space)
            * len(self.emblem_space)
            * len(self.familiar_space)
            * len(self.legion_space)
            * len(self.hyper_space)
            * len(self.link_skill_space)
        )
        LOGGER.info(f"STARTING COMPUTATIONS ON {total_combinations} TOTAL COMBINATIONS")

        all_results = np.full(shape=1, fill_value=np.record((0,) * 13, dtype=structured_result_container), dtype=structured_result_container)

        chunk_iter = 0

        for weapon_chunk in chunk_generator(self.weapon_space, chunk_size=chunk_size):
            for soul_chunk in chunk_generator(self.soul_space, chunk_size=chunk_size):
                for secondary_chunk in chunk_generator(self.secondary_space, chunk_size=chunk_size):
                    for emblem_chunk in chunk_generator(self.emblem_space, chunk_size=chunk_size):
                        for familiar_chunk in chunk_generator(self.familiar_space, chunk_size=chunk_size):
                            for hyper_chunk in chunk_generator(self.hyper_space, chunk_size=chunk_size):
                                for legion_chunk in chunk_generator(self.legion_space, chunk_size=chunk_size):
                                    for link_chunk in chunk_generator(self.link_skill_space, chunk_size=chunk_size):
                                        chunk_combos = compute_combination(
                                            weapon_chunk, soul_chunk, secondary_chunk, emblem_chunk, familiar_chunk, hyper_chunk, legion_chunk, link_chunk
                                        )
                                        chunk_results = np.full(
                                            shape=chunk_combos.shape[0],
                                            fill_value=np.record((0,) * 13, dtype=structured_result_container),
                                            dtype=structured_result_container,
                                        )

                                        calculate_all_multipliers_numba(
                                            base_attack=config.base_attack,
                                            base_boss_damage=config.base_boss_damage,
                                            base_crit_damage=config.base_crit_damage,
                                            base_damage=config.base_damage,
                                            base_ied=config.base_ied,
                                            pdr=config.pdr,
                                            chunk_combinations=chunk_combos,
                                            chunk_results=chunk_results,
                                        )
                                        chunk_iter += chunk_combos.shape[0]
                                        LOGGER.info(f"CALCULATED {chunk_iter}/{total_combinations} ({chunk_iter / total_combinations:.04%}) RESULTS")

                                        chunk_results = chunk_results[chunk_results["multiplier"] > 0]

                                        all_results = np.append(all_results, chunk_results)
                                        all_results[::-1].sort(order="multiplier")
                                        all_results = all_results[:results_to_show]

        LOGGER.info("FINISHED COMPUTATIONS, NOW DISPLAYING")
        return all_results
