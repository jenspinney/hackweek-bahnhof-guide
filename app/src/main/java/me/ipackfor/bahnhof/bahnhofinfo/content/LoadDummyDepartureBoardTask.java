package me.ipackfor.bahnhof.bahnhofinfo.content;

import android.util.Log;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LoadDummyDepartureBoardTask implements IFetchDepartureBoardTask {
    private static final String TAG = LoadDummyDepartureBoardTask.class.getSimpleName();

    public LoadDummyDepartureBoardTask() {
    }
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
    public List<DepartureContent.DepartureItem> Run() {
        Log.d(TAG, "Run");
        LinkedList<DepartureContent.DepartureItem> list = new LinkedList<DepartureContent.DepartureItem>();
        Random r = new Random();

            list.addAll(Arrays.asList(
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fsttion_evaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstaion_evaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstaton_evaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstatin_evaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstatio_evaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstationevaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_vaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_eaId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evId%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evad%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaI%3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId3D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%D8000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%38000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D000284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800284", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800084", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800024", "ICE 1617", "Track: 8", getRandomDate(r)),
                    new DepartureContent.DepartureItem("614604%2F208451%2F397100%2F6318%2F80%3fstation_evaId%3D800028", "ICE 1617", "Track: 8", getRandomDate(r))
            ));

            Log.d(TAG, "Run succeeded");

            return list;
    }

    private Date getRandomDate(Random r) {
        return new Date((long) (1293861599+r.nextDouble()*60*60*24*365*1000));
    }
}
