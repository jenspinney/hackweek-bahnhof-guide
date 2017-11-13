package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import me.ipackfor.bahnhof.bahnhofinfo.R;

public class DepartureListLoader extends AsyncTaskLoader<List<DepartureContent.DepartureItem>>{
    private static final String TAG = DepartureListLoader.class.getSimpleName();

    private String mApiKey;

    public DepartureListLoader(Context context) {
        super(context);

        Log.d(TAG, "Constructor");
        mApiKey = context.getString(R.string.db_api_key);
    }

    @Override
    public List<DepartureContent.DepartureItem> loadInBackground() {
        Log.d(TAG, "load-in-background");
        return new FetchDepartureBoardTask("", mApiKey).Run();
    }
}
