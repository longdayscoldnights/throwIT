package de.fh_dortmund.throwit.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.fh_dortmund.throwit.R;
import de.fh_dortmund.throwit.menu.HighscoreFragment.OnListFragmentInteractionListener;
import de.fh_dortmund.throwit.menu.dummy.UserScoreContent.UserScoreItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserScoreItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHighscoreRecyclerViewAdapter extends RecyclerView.Adapter<MyHighscoreRecyclerViewAdapter.ViewHolder> {

    private final List<UserScoreItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHighscoreRecyclerViewAdapter(List<UserScoreItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_highscore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mScoreView.setText(String.valueOf(mValues.get(position).score));
        holder.mHeightView.setText(String.valueOf(mValues.get(position).height)+" m");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mScoreView;
        public final TextView mHeightView;

        public UserScoreItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mScoreView =(TextView) view.findViewById(R.id.score);
            mHeightView = (TextView) view.findViewById(R.id.height);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
