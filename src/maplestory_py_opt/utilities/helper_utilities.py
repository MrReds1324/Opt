import logging
import math
from collections.abc import Generator
from typing import Any

from numpy._typing import NDArray

from maplestory_py_opt.configs.opt_config import OptConfig
from maplestory_py_opt.containers.computation_spaces import ComputationSpaces
from maplestory_py_opt.containers.numpy import (
    structured_result_container,
)
from maplestory_py_opt.containers.result_display import ResultDisplay

LOGGER = logging.getLogger(__name__)


def get_attack_addition(item_level: int) -> float:
    if item_level >= 250:
        return 0.02
    if item_level >= 160:
        return 0.01
    return 0.0


def update_ied(current_value: float, *args) -> float:
    return round(1 - ((1 - current_value) * math.prod(round(1 - x, 4) for x in args)), 4)


def display_results(
    *,
    results_to_display: NDArray[structured_result_container],
    computation_spaces: ComputationSpaces,
    config: OptConfig,
) -> None:
    for result_to_display in results_to_display:
        result_display = ResultDisplay(result_container=result_to_display, config=config, computation_spaces=computation_spaces)

        LOGGER.info(str(result_display))


def chunk_generator(array: NDArray, chunk_size: int) -> Generator[NDArray, Any]:
    for i in range(0, len(array), chunk_size):
        yield array[i : i + chunk_size]
