package me.ipackfor.bahnhof.bahnhofinfo.dbapi;


import android.support.annotation.NonNull;

import org.joda.time.DateTime;

public class UrlGenerator {
    @NonNull
    public static String generateDepartureBoardURL(String baseUrl, String locationID, DateTime mDateTime) {
        return baseUrl + "/v1/departureBoard/" + locationID + "?date=" + mDateTime.toString("YYYY-MM-dd");
    }
}
