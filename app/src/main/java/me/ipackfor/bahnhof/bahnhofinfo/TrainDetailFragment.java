package me.ipackfor.bahnhof.bahnhofinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ipackfor.bahnhof.bahnhofinfo.content.DepartureContent;
import me.ipackfor.bahnhof.bahnhofinfo.content.JourneyDetailItem;

/**
 * A fragment representing a single Train detail screen.
 * This fragment is either contained in a {@link TrainListActivity}
 * in two-pane mode (on tablets) or a {@link TrainDetailActivity}
 * on handsets.
 */
public class TrainDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The departure information this fragment is presenting.
     */
    private DepartureContent.DepartureItem mItem;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DepartureContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.train_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.train_track)).setText(mItem.getPlatform());
            ((TextView) rootView.findViewById(R.id.train_destination)).setText(mItem.getDestinationStopName());

            View recyclerView = rootView.findViewById(R.id.stops_list);
            assert recyclerView != null;
            mRecyclerView = (RecyclerView) recyclerView;
            setupRecyclerView(mRecyclerView);
        }

        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new TrainDetailRecyclerViewAdapter(mItem.getJourneyDetails()));
    }

    private class TrainDetailRecyclerViewAdapter extends RecyclerView.Adapter<TrainDetailRecyclerViewAdapter.ViewHolder> {
        private final List<JourneyDetailItem> mItems;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JourneyDetailItem item = (JourneyDetailItem) view.getTag();

                Context context = view.getContext();
                Intent intent = new Intent(context, TrainListActivity.class);
                intent.putExtra(TrainListActivity.LOCATION_ID, String.valueOf(item.getStopId()));
                intent.putExtra(TrainListActivity.LOCATION_NAME, item.getStopName());

                context.startActivity(intent);
            }
        };

        public TrainDetailRecyclerViewAdapter(List<JourneyDetailItem> items) {
            mItems = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.train_detail_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mStationName.setText(mItems.get(position).getStopName());

            holder.itemView.setTag(mItems.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mStationName;

            ViewHolder(View view) {
                super(view);
                mStationName = view.findViewById(R.id.tv_station_name);
            }
        }
    }
}
