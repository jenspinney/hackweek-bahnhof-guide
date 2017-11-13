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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import me.ipackfor.bahnhof.bahnhofinfo.R;
import me.ipackfor.bahnhof.bahnhofinfo.content.DepartureContent;

public class FetchDepartureBoardTask {
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
                JSONObject departureInfo = departuresJSON.getJSONObject(i);
                String trainName = departureInfo.getString("name");
                items.add(new DepartureContent.DepartureItem(departureInfo.getString("detailsId"), trainName, "Track: " + departureInfo.getString("track")));
                /* departureInfo has:
                  {
    "name": "ICE 1617",
    "type": "ICE",
    "boardId": 8000284,
    "stopId": 8000284,
    "stopName": "Nürnberg Hbf",
    "dateTime": "2017-11-13T00:31",
    "track": "8",
    "detailsId": "614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D8000284"
  }
                 */
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
