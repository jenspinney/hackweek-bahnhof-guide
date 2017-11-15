package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LoadDummyDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = LoadDummyDepartureBoardTask.class.getSimpleName();
    private final String mLocation;
    private final AssetManager mAssetManager;

    public LoadDummyDepartureBoardTask(String location, AssetManager assetManager) {
        mLocation = location;
        mAssetManager = assetManager;
    }

    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");
        LinkedList<DepartureContent.DepartureItem> list = new LinkedList<>();

        try {
            InputStream is = mAssetManager.open("testdata/v1/departureBoard/8000284?date=2017-11-15");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            JSONArray jsonArray = new JSONArray(new String(buffer, "UTF-8"));
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new DepartureContent.DepartureItem(jsonArray.getJSONObject(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }
}
