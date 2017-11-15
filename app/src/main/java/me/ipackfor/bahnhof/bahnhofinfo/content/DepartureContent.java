package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

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
        ITEM_MAP.put(item.getId(), item);
    }

    /**
     * An item representing a single departure.
     */
    public static class DepartureItem {
        public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        private final DepartureBoardJSONObject mTopLevelObject;
        private final List<JourneyDetailItem> mDetails;

        private Date mDepartureTime = null;

        public DepartureItem(DepartureBoardJSONObject obj, List<JourneyDetailItem> details) {
            mTopLevelObject = obj;
            mDetails = details;
        }

        @Override
        public String toString() {
            return getName();
        }

        public List<String> getStopNamesInJourney() {
            List<String> result = new LinkedList<>();
            for (JourneyDetailItem detail:
                 mDetails) {
                result.add(detail.getStopName());
            }
            return result;
        }

        public String getId() {
            return mTopLevelObject.getDetailsId();
        }

        public String getName() {
            return mTopLevelObject.getName();
        }

        public String getType() {
            return mTopLevelObject.getType();
        }

        public String getBoardId() {
            return String.valueOf(mTopLevelObject.getBoardId());
        }

        public String getStopId() {
            return String.valueOf(mTopLevelObject.getStopId());
        }

        public String getStopName() {
            return String.valueOf(mTopLevelObject.getStopName());
        }

        public Date getDepartureTime() {
            if (mDepartureTime != null) {
                return mDepartureTime;
            }

            try{
                mDepartureTime = DATE_FORMAT.parse(mTopLevelObject.getDateTime());
            } catch (ParseException e) {
                e.printStackTrace();
                mDepartureTime = new Date();
            }
            return mDepartureTime;
        }

        public String getPlatform() {
            return mTopLevelObject.getTrack();
        }

        public String getDestinationStopName() {
            if (mDetails.size() < 1) {
                return "Unknown";
            }

            return mDetails.get(mDetails.size() - 1).getStopName();
        }

        public List<JourneyDetailItem> getJourneyDetails() {
            return mDetails;
        }
    }
}
