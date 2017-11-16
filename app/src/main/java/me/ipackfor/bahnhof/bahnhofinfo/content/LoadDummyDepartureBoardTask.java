package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.ipackfor.bahnhof.bahnhofinfo.dbapi.UrlGenerator;

public class LoadDummyDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = LoadDummyDepartureBoardTask.class.getSimpleName();
    public static final String TEST_DATA_PATH = "testdata";
    private final String mLocationID;
    private final DateTime mDateTime;
    private final AssetManager mAssetManager;

    public LoadDummyDepartureBoardTask(String locationID, DateTime dateTime, AssetManager assetManager) {
        mLocationID = locationID;
        mDateTime = dateTime;
        mAssetManager = assetManager;
    }

    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");

        if (!mLocationID.matches("[0-9]+") || mLocationID.length() > 20) {
            Log.i(TAG, "invalid location id: " + mLocationID);
        }

        String path = UrlGenerator.generateDepartureBoardURL("",  mLocationID , mDateTime);
        String jsonArrayString = getJSONStringFromAsset(path);

        List<DepartureBoardJSONObject> objs = JSON.parseArray(jsonArrayString, DepartureBoardJSONObject.class);
        LinkedList<DepartureContent.DepartureItem> items = new LinkedList<>();
        for (DepartureBoardJSONObject topLevelJSONObject: objs) {
            List<JourneyDetailItem> details = JSON.parseArray(getJSONStringFromAsset("/v1/journeyDetails/" + topLevelJSONObject.getDetailsId()), JourneyDetailItem.class);
            DepartureContent.DepartureItem departureItem = new DepartureContent.DepartureItem(topLevelJSONObject, details);
            items.add(departureItem);
        }
        return items;
    }

    private String getJSONStringFromAsset(String path) {
        String jsonArrayString = "[]";
        try {
            InputStream is = mAssetManager.open(TEST_DATA_PATH + path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonArrayString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.w(TAG, "Failed to load " + path);
        }
        return jsonArrayString;
    }
}

