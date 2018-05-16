package com.example.dtsiounis.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dtsiounis.bakingapp.R;
import com.example.dtsiounis.bakingapp.activities.RecipeStepsListActivity;
import com.example.dtsiounis.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantinos Tsiounis on 02-May-18.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<Step> mValues;
    private final boolean mTwoPane;
    private ItemClickListener mClickListener;
    private Context context;

    public SimpleItemRecyclerViewAdapter(RecipeStepsListActivity parent,
                                  List<Step> items,
                                  boolean twoPane,
                                  ItemClickListener mClickListener,
                                  Context context) {
        mValues = items;
        mTwoPane = twoPane;
        this.mClickListener = mClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getShortDescription());
        holder.mContentView.setText(mValues.get(position).getDescription());

        if(!mValues.get(position).getThumbnailURL().equals("")){
            Picasso.with(context)
                    .load(mValues.get(position).getThumbnailURL())
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.thumbnail);
        }

        //holder.itemView.setTag(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.id_text) TextView mIdView;
        @BindView(R.id.content) TextView mContentView;
        @BindView(R.id.thumbnail) ImageView thumbnail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickListener.onItemClickListener(position);
        }
    }
}
