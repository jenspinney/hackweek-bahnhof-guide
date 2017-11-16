package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import me.ipackfor.bahnhof.bahnhofinfo.dbapi.UrlGenerator;

public class FetchDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = FetchDepartureBoardTask.class.getSimpleName();

    private static final String DEPARTURE_BOARD_BASE_URL = "https://api.deutschebahn.com/fahrplan-plus";
    private final String mLocation;
    private final DateTime mDateTime;
    private String mApiKey;

    public FetchDepartureBoardTask(String location, DateTime dateTime, String apiKey) {
        mLocation = location;
        mDateTime = dateTime;
        mApiKey = apiKey;
    }

    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");
        HttpURLConnection urlConnection = null;
        LinkedList<DepartureContent.DepartureItem> items = new LinkedList<>();

        try {

            urlConnection = (HttpURLConnection) new URL(UrlGenerator.generateDepartureBoardURL(DEPARTURE_BOARD_BASE_URL, mLocation, mDateTime)).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + mApiKey);
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.connect();

            int status = urlConnection.getResponseCode();

            if (!(200 >= status && status > 300)) {
                Log.d(TAG, "Error status: " + status);
                throw new IOException("Error status: " + status);
            }

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();

            List<DepartureBoardJSONObject> objs = JSON.parseArray(response, DepartureBoardJSONObject.class);
            for (DepartureBoardJSONObject obj: objs) {
                items.add(new DepartureContent.DepartureItem(obj, new LinkedList<JourneyDetailItem>()));
            }

            Log.d(TAG, "Run success! " + items.size() + " items found.");
        } catch (MalformedURLException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            return items;
        }
    }

}
