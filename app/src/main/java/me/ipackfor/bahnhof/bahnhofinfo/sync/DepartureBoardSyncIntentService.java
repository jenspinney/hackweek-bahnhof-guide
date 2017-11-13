package me.ipackfor.bahnhof.bahnhofinfo.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import me.ipackfor.bahnhof.bahnhofinfo.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class DepartureBoardSyncIntentService extends IntentService {
    private static final String ACTION_DOWNLOAD_DEPARTURE_BOARD = "me.ipackfor.bahnhof.bahnhofinfo.sync.action.DOWNLOAD_DEPARTURE_BOARD";

    private static final String LOCATION_NAME = "me.ipackfor.bahnhof.bahnhofinfo.sync.extra.LOCATION_NAME";
    private static final String API_KEY = "me.ipackfor.bahnhof.bahnhofinfo.sync.extra.API_KEY";

    public DepartureBoardSyncIntentService() {
        super("DepartureBoardSyncIntentService");
    }

    /**
     * Starts this service to perform action DOWNLOAD_DEPARTURE_BOARD with the given location name. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownloadDepartureBoard(Context context, String location) {
        Intent intent = new Intent(context, DepartureBoardSyncIntentService.class);
        intent.setAction(ACTION_DOWNLOAD_DEPARTURE_BOARD);
        intent.putExtra(LOCATION_NAME, location);
        intent.putExtra(API_KEY, context.getString(R.string.db_api_key));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_DEPARTURE_BOARD.equals(action)) {
                final String location = intent.getStringExtra(LOCATION_NAME);
                final String apiKey = intent.getStringExtra(API_KEY);
                handleActionDownloadDepartureBoard(location, apiKey);
            }
        }
    }

    /**
     * Handle action DOWNLOAD_DEPARTURE_BOARD in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownloadDepartureBoard(String location, String apiKey) {
        new DepartureBoardSyncTask(location, apiKey).Run();
    }
}
