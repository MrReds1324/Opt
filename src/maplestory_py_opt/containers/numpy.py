import numpy as np

structured_compute_container = np.dtype(
    [
        ("container_type", np.int8),
        ("index_into", np.int64),
        ("attack", np.float64),
        ("boss_damage", np.float64),
        ("crit_damage", np.float64),
        ("damage", np.float64),
        ("ied", np.float64),
    ]
)


structured_item_container = np.dtype([("line_1", "U2"), ("line_2", "U2"), ("line_3", "U2")])

structured_familiar_container = np.dtype([("top", "U2"), ("bottom", "U2")])
structured_familiars_container = np.dtype(
    [("one", structured_familiar_container), ("two", structured_familiar_container), ("three", structured_familiar_container)]
)

structured_link_skills_container = np.dtype(
    [
        ("link_1", "U14"),
        ("link_2", "U14"),
        ("link_3", "U14"),
        ("link_4", "U14"),
        ("link_5", "U14"),
        ("link_6", "U14"),
        ("link_7", "U14"),
        ("link_8", "U14"),
        ("link_9", "U14"),
        ("link_10", "U14"),
        ("link_11", "U14"),
        ("link_12", "U14"),
    ]
)

structured_result_container = np.dtype(
    [
        ("multiplier", np.float64),
        ("total_attack", np.float64),
        ("total_boss_damage", np.float64),
        ("total_crit_damage", np.float64),
        ("total_ied", np.float64),
        ("weapon", np.int8),
        ("soul", np.int8),
        ("secondary", np.int8),
        ("emblem", np.int8),
        ("familiars", np.int16),
        ("hyper_stats", np.int32),
        ("legion_grid", np.int32),
        ("link_skills", np.int32),
    ]
)
