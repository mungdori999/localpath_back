package com.mungdori.localpath.common.constants;

import java.util.Set;

public final class SpendingFocusIds {
    public static final String ROUTE1 = "route1";
    public static final String ROUTE2 = "route2";
    public static final String ROUTE3 = "route3";

    private static final Set<String> ALL = Set.of(ROUTE1, ROUTE2, ROUTE3);

    private SpendingFocusIds() {
    }

    public static boolean isValid(String id) {
        return id != null && ALL.contains(id);
    }
}
