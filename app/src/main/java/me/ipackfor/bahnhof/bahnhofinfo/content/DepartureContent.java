package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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

    @NonNull
    public static LinkedList<DepartureItem> createDepartureItemsFromJSONString(String jsonArrayString) {
        List<DepartureBoardJSONObject> objs = JSON.parseArray(jsonArrayString, DepartureBoardJSONObject.class);
        LinkedList<DepartureItem> items = new LinkedList<>();
        for (DepartureBoardJSONObject obj: objs) {
            items.add(new DepartureItem(obj));
        }
        return items;
    }

    /**
     * An item representing a single departure.
     */
    public static class DepartureItem {
        public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        public final String id;
        public final String name;
        public final String type;
        public final String boardId;
        public final String stopId;
        public final String stopName;
        public final Date departureTime;
        public final String platform;


        public DepartureItem(DepartureBoardJSONObject obj) {
            this.id = obj.getDetailsId();
            this.name = obj.getName();
            this.type = obj.getType();
            this.boardId = String.valueOf(obj.getBoardId());
            this.stopId = String.valueOf(obj.getStopId());
            this.stopName = obj.getStopName();
            this.platform = obj.getTrack();

            Date departureTime;
            try{
                departureTime = DATE_FORMAT.parse(obj.getDateTime());
            } catch (ParseException e) {
                e.printStackTrace();
                departureTime = new Date();
            }
            this.departureTime = departureTime;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
