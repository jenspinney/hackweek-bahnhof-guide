package me.ipackfor.bahnhof.bahnhofinfo;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import me.ipackfor.bahnhof.bahnhofinfo.content.DepartureContent;
import me.ipackfor.bahnhof.bahnhofinfo.content.DepartureListLoader;

import java.util.List;

/**
 * An activity representing a list of Trains. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TrainDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TrainListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DepartureContent.DepartureItem>> {
    private static final String TAG = TrainListActivity.class.getSimpleName();
    public static final String LOCATION_ID = "location_id";
    public static final String LOCATION_NAME = "location_name";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private RecyclerView mRecyclerView;
    private String mLocationName = "Nürnberg";
    private String mLocationID = "8000284";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.train_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mLocationName = getIntent().getStringExtra(TrainListActivity.LOCATION_NAME);
        mLocationID = getIntent().getStringExtra(TrainListActivity.LOCATION_ID);

        if (mLocationID == null) {
            mLocationID = "8000284";
        }

        if (mLocationName == null) {
            mLocationName = "Nürnberg";
        }
        Log.d(TAG, "Location ID = " + mLocationID);

        TextView locationNameView = findViewById(R.id.tv_location_name);
        locationNameView.setText(mLocationName);

        View recyclerView = findViewById(R.id.train_list);
        assert recyclerView != null;
        mRecyclerView = (RecyclerView) recyclerView;
        setupRecyclerView(mRecyclerView);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DepartureContent.ITEMS, mTwoPane));
    }

    @Override
    public Loader<List<DepartureContent.DepartureItem>> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "on-create-loader");
        return new DepartureListLoader(TrainListActivity.this, mLocationID);
    }


    @Override
    public void onLoadFinished(Loader<List<DepartureContent.DepartureItem>> loader, List<DepartureContent.DepartureItem> departureItems) {
        Log.d(TAG, "on-load-finished");
        DepartureContent.replaceItems(departureItems);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<DepartureContent.DepartureItem>> loader) {
        Log.d(TAG, "on-load-reset");
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TrainListActivity mParentActivity;
        private final List<DepartureContent.DepartureItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepartureContent.DepartureItem item = (DepartureContent.DepartureItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(TrainDetailFragment.ARG_ITEM_ID, item.getId());
                    TrainDetailFragment fragment = new TrainDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.train_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TrainDetailActivity.class);
                    intent.putExtra(TrainDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(TrainListActivity parent,
                                      List<DepartureContent.DepartureItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.train_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mDepartureTimeView.setText(DepartureContent.DepartureItem.DATE_FORMAT.format(mValues.get(position).getDepartureTime()));
            holder.mContentView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mDepartureTimeView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mDepartureTimeView = (TextView) view.findViewById(R.id.departure_time);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
