package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LoadDummyDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = LoadDummyDepartureBoardTask.class.getSimpleName();

    public LoadDummyDepartureBoardTask() {
    }

    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");
        LinkedList<DepartureContent.DepartureItem> list = new LinkedList<DepartureContent.DepartureItem>();
        Random r = new Random();

        try {
            for (int i = 0; i < 50; i++)
                list.add(generateRandomItem(r));

            Log.d(TAG, "Run succeeded");
        } catch (Exception e) {
           Log.e(TAG, e.getMessage());
           e.printStackTrace();
        } finally {
            return list;
        }
    }

    private DepartureContent.DepartureItem generateRandomItem(Random r) {
        JSONObject obj = new JSONObject();

        try {
            obj = obj
                    .put(DepartureContent.DepartureItem.NAME, "ICE " + r.nextInt(2000) + 1)
                    .put(DepartureContent.DepartureItem.TYPE, "ICE")
                    .put(DepartureContent.DepartureItem.BOARD_ID, 8000284)
                    .put(DepartureContent.DepartureItem.STOP_ID, 8000284)
                    .put(DepartureContent.DepartureItem.STOP_NAME, "Nürnberg Hbf")
                    .put(DepartureContent.DepartureItem.DATE_TIME, DepartureContent.DepartureItem.DATE_FORMAT.format(getRandomDate(r)))
                    .put(DepartureContent.DepartureItem.TRACK, String.valueOf(r.nextInt(20) + 1))
                    .put(DepartureContent.DepartureItem.DETAILS_ID, java.util.UUID.randomUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new DepartureContent.DepartureItem(obj);
    }

    private Date getRandomDate(Random r) {
        return new Date((long) (1293861599+r.nextDouble()*60*60*24*365*1000));
    }
}
