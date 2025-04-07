import pytest

from maplestory_py_opt.utilities.combination_utilities import update_ied, update_ied_numba_reflected


class TestUtilities:
    @pytest.mark.parametrize(
        ("start_value", "input_values", "expected_value"),
        [(0, [0.3], 0.3), (0, [0.3, 0.3], 0.51), (0.3, [0.3], 0.51), (0.8323, [0, 0, 0.706, 0.4, 0.3, 0.42, 0.4, 0.272], 0.9948)],
    )
    def test_update_ied(self, start_value, input_values, expected_value):
        assert update_ied(start_value, *input_values) == expected_value
        assert update_ied_numba_reflected(start_value, input_values) == expected_value


if __name__ == "__main__":
    import sys

    sys.exit(pytest.main())
