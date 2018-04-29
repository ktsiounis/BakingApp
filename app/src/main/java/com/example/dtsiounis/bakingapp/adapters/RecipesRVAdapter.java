package com.example.dtsiounis.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dtsiounis.bakingapp.R;
import com.example.dtsiounis.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesRVAdapter extends RecyclerView.Adapter<RecipesRVAdapter.RecipeViewHolder> {

    private ItemClickListener mListener;
    private ArrayList<Recipe> mRecipes;

    public RecipesRVAdapter(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.recipe_title.setText(mRecipes.get(position).getName());
        holder.recipe_servings.setText("Servings: " + mRecipes.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        else return mRecipes.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_title) public TextView recipe_title;
        @BindView(R.id.recipe_servings) public TextView recipe_servings;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onItemClickListener(position);
        }
    }

    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void swapList(ArrayList<Recipe> recipes){
        if(mRecipes != null){
            mRecipes.clear();
            mRecipes.addAll(recipes);
        }
        else {
            mRecipes = recipes;
        }

        notifyDataSetChanged();
    }

}
