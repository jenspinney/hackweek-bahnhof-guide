package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import me.ipackfor.bahnhof.bahnhofinfo.R;
import me.ipackfor.bahnhof.bahnhofinfo.content.DepartureContent;

public class FetchDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = FetchDepartureBoardTask.class.getSimpleName();

    private static final String DEPARTURE_BOARD_URL = "https://api.deutschebahn.com/fahrplan-plus/v1/departureBoard/8000284?date=2017-11-13";
    private String mApiKey;

    public FetchDepartureBoardTask(String location, String apiKey) {
        mApiKey = apiKey;
    }

    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");
        HttpURLConnection urlConnection = null;
        LinkedList<DepartureContent.DepartureItem> items = new LinkedList<DepartureContent.DepartureItem>();

        try {

            urlConnection = (HttpURLConnection) new URL(DEPARTURE_BOARD_URL).openConnection();
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

            JSONArray departuresJSON = new JSONArray(response);

            for (int i=0; i < departuresJSON.length(); i++) {
                items.add(new DepartureContent.DepartureItem(departuresJSON.getJSONObject(i)));
            }

            Log.d(TAG, "Run success! " + departuresJSON.length() + " items found.");
        } catch (MalformedURLException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            return items;
        }
    }
}
