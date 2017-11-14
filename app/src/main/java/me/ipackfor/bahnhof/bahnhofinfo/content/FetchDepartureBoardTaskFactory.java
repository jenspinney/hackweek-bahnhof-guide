package me.ipackfor.bahnhof.bahnhofinfo.content;


import android.content.Context;

import me.ipackfor.bahnhof.bahnhofinfo.R;

class FetchDepartureBoardTaskFactory {
    public static IFetchDepartureBoardTask create(Context context, String location) {
        boolean apiIsWorking = context.getResources().getBoolean(R.bool.db_api_is_working);
        String apiKey = context.getString(R.string.db_api_key);

        if (apiIsWorking) {
            return new FetchDepartureBoardTask(location, apiKey);
        }

        return new LoadDummyDepartureBoardTask();
    }
}
