import argparse
import json
import logging
import sys
from argparse import ArgumentError
from pathlib import Path

from maplestory_py_opt.configs.opt_config import OptConfig
from maplestory_py_opt.containers.result_display import ComputationSpaces
from maplestory_py_opt.utilities.helper_utilities import display_results

logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s: %(message)s", handlers=[logging.StreamHandler(sys.stdout)])
LOGGER = logging.getLogger()

COMPUTATION_SPACES = ComputationSpaces()


def main():
    parser = argparse.ArgumentParser(description="Optimize a Maplestory characters WSE/Legion/HyperStats/Familiars/Link Skills")
    parser.add_argument("-c", "--config-path", required=True, type=Path, help="Path to configuration file for this run")
    parser.add_argument("--chunk-size", type=int, default=10, help="Size of each chunk when calculating results")
    parser.add_argument("--result-count", type=int, default=10, help="Number of results to show")
    parser.add_argument("-d", "--debug", action="store_true", help="Run with debug log messages")

    arguments = parser.parse_args()
    if not arguments.config_path.exists():
        raise ArgumentError(None, message="Invalid path to configs")

    if arguments.debug:
        LOGGER.setLevel(logging.DEBUG)

    with arguments.config_path.open("r") as _fh:
        config_json = json.load(_fh)
        config = OptConfig(**config_json)

    COMPUTATION_SPACES.setup_computation_spaces(config)

    results_to_display = COMPUTATION_SPACES.generate_all_results(config, results_to_show=arguments.result_count)

    display_results(results_to_display=results_to_display, computation_spaces=COMPUTATION_SPACES, config=config)

    sys.exit()


if __name__ == "__main__":
    main()
