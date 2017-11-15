package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import me.ipackfor.bahnhof.bahnhofinfo.R;

public class DepartureListLoader extends AsyncTaskLoader<List<DepartureContent.DepartureItem>>{
    private static final String TAG = DepartureListLoader.class.getSimpleName();
    private final String mLocationID;

    private String mApiKey;
    private Context mContext;

    public DepartureListLoader(Context context, String locationID) {
        super(context);
        this.mLocationID = locationID;

        Log.d(TAG, "Constructor");
        mContext = context;
    }

    @Override
    public List<DepartureContent.DepartureItem> loadInBackground() {
        Log.d(TAG, "load-in-background");
        return FetchDepartureBoardTaskFactory.create(mContext, mLocationID).Run();
    }
}
