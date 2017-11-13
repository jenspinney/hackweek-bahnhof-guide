package me.ipackfor.bahnhof.bahnhofinfo.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartureContent {
    public static final List<DepartureItem> ITEMS = new ArrayList<DepartureItem>();

    public static final Map<String, DepartureItem> ITEM_MAP = new HashMap<String, DepartureItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDepartureItem(i));
        }
    }

    private static void addItem(DepartureItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DepartureItem createDepartureItem(int position) {
        return new DepartureItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * An item representing a single departure.
     */
    public static class DepartureItem {
        public final String id;
        public final String content;
        public final String details;

        public DepartureItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
