package com.example.efhemo.platingapp.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.efhemo.platingapp.DetailActivity;
import com.example.efhemo.platingapp.Model.GenericListItem;
import com.example.efhemo.platingapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String  LOG_TAG = MovieAdapter.class.getSimpleName();


    private List<GenericListItem> mTaskEntries;
    Context context;


    public MovieAdapter(Context context){ //watch out next time, this should be public
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {



        GenericListItem result = mTaskEntries.get(position);
        String titleResult = result.getTitle();
        //String popularityResult =  Double.toString(result.getPopularity()) ;
        String imageResult = result.getPoster_path();

        holder.textViewTitle.setText(titleResult);
        //holder.textViewPopular.setText(popularityResult);


        Glide.with(context).load("https://image.tmdb.org/t/p/w200/"+imageResult)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    /**
     * When data changes, this method updates the list of mTaskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<GenericListItem> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewTitle;
        //TextView textViewPopular;
        ImageView imageView;


        MovieViewHolder(View itemView) {
            super(itemView);
            textViewTitle =itemView.findViewById(R.id.text_film);
            //textViewPopular = itemView.findViewById(R.id.popularity);
            imageView = itemView.findViewById(R.id.poster_path_image);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int postion = getAdapterPosition();
            Log.d(LOG_TAG, "clicked "+ postion);
            Toast.makeText(context, "clicked: "+postion, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailActivity.class);
            context.startActivity(intent);
        }
    }
}
