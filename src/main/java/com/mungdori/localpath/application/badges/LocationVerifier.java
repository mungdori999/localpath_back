package com.mungdori.localpath.application.badges;

public final class LocationVerifier {

    private static final double EARTH_RADIUS_METERS = 6_371_000;
    private static final double MAX_DISTANCE_METERS = 300;

    private LocationVerifier() {
    }

    public static boolean isWithinRange(double spotLat, double spotLng, double userLat, double userLng) {
        return distanceMeters(spotLat, spotLng, userLat, userLng) <= MAX_DISTANCE_METERS;
    }

    static double distanceMeters(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METERS * c;
    }
}
