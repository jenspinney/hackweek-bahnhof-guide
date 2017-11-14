package me.ipackfor.bahnhof.bahnhofinfo.content;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartureContent {
    public static final List<DepartureItem> ITEMS = new ArrayList<DepartureItem>();

    public static final Map<String, DepartureItem> ITEM_MAP = new HashMap<String, DepartureItem>();

    public static void replaceItems(List<DepartureItem> items) {
        ITEMS.clear();
        ITEM_MAP.clear();

        for (DepartureItem item : items) {
            addItem(item);
        }
    }

    private static void addItem(DepartureItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * An item representing a single departure.
     */
    public static class DepartureItem {
        public final String id;
        public final String content;
        public final String details;
        public final String departureTime;

        public DepartureItem(String id, String content, String details, Date departureTime) {
            this.id = id;
            this.content = content;
            this.details = details;

            this.departureTime = new SimpleDateFormat("dd MMM HH:mm").format(departureTime);
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
