package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartureContent {
    private static final String TAG = DepartureContent.class.getSimpleName();

    public static final List<DepartureItem> ITEMS = new ArrayList<DepartureItem>();

    public static final Map<String, DepartureItem> ITEM_MAP = new HashMap<String, DepartureItem>();

    public static void replaceItems(List<DepartureItem> items) {
        Log.d(TAG, "replace-items");
        ITEMS.clear();
        ITEM_MAP.clear();

        for (DepartureItem item : items) {
            addItem(item);
        }

        Log.d(TAG, "replace-items-done");
    }

    private static void addItem(DepartureItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * An item representing a single departure.
     */
    public static class DepartureItem {
        public static final String DETAILS_ID = "detailsId";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String BOARD_ID = "boardId";
        public static final String STOP_ID = "stopId";
        public static final String STOP_NAME = "stopName";
        public static final String TRACK = "track";
        public static final String DATE_TIME = "dateTime";
        public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        public final String id;
        public final String name;
        public final String type;
        public final String boardId;
        public final String stopId;
        public final String stopName;
        public final Date departureTime;

        public final String platform;

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
        public DepartureItem(JSONObject obj) {
            String id = "";
            String name = "";
            String type = "";
            String boardId = "";
            String stopId = "";
            String stopName = "";
            String platform = "";
            Date departureTime = null;

            try {
                id = obj.getString(DETAILS_ID);
                name = obj.getString(NAME);
                type = obj.getString(TYPE);
                boardId = obj.getString(BOARD_ID);
                stopId = obj.getString(STOP_ID);
                stopName = obj.getString(STOP_NAME);
                platform = obj.getString(TRACK);
                departureTime = DATE_FORMAT.parse(obj.getString(DATE_TIME));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                this.id = id;
                this.name = name;
                this.type = type;
                this.boardId = boardId;
                this.stopId = stopId;
                this.stopName = stopName;
                this.platform = platform;
                this.departureTime = departureTime;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
