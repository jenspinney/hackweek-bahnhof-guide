package me.ipackfor.bahnhof.bahnhofinfo.sync;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import me.ipackfor.bahnhof.bahnhofinfo.R;

public class DepartureBoardSyncTask {
    private static final String DEPARTURE_BOARD_URL = "https://api.deutschebahn.com/fahrplan-plus/v1/departureBoard/8000284?date=2017-11-13";
    private String mApiKey;

    public DepartureBoardSyncTask(String location, String apiKey) {
        mApiKey = apiKey;
    }

    public void Run() {
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) new URL(DEPARTURE_BOARD_URL).openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + mApiKey);
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
                String trainName = departureInfo.getString("name"); // DO SOMETHING WITH THIS INFO
                Log.i("TEST", "Train :" + trainName);
                // TODO: Connect with UI
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }
}
