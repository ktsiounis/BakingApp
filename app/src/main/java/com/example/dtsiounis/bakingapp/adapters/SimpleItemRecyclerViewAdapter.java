package com.example.dtsiounis.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dtsiounis.bakingapp.R;
import com.example.dtsiounis.bakingapp.activities.RecipeStepsDetailActivity;
import com.example.dtsiounis.bakingapp.activities.RecipeStepsListActivity;
import com.example.dtsiounis.bakingapp.fragments.RecipeStepsDetailFragment;
import com.example.dtsiounis.bakingapp.activities.dummy.DummyContent;
import com.example.dtsiounis.bakingapp.model.Ingredient;
import com.example.dtsiounis.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Konstantinos Tsiounis on 02-May-18.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final RecipeStepsListActivity mParentActivity;
    private final List<Step> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(RecipeStepsDetailFragment.ARG_ITEM_ID, item.id);
                RecipeStepsDetailFragment fragment = new RecipeStepsDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeStepsDetailActivity.class);
                intent.putExtra(RecipeStepsDetailFragment.ARG_ITEM_ID, item.id);

                context.startActivity(intent);
            }
        }
    };

    public SimpleItemRecyclerViewAdapter(RecipeStepsListActivity parent,
                                  List<Step> items,
                                  boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
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

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
