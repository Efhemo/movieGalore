package com.example.efhemo.platingapp.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.efhemo.platingapp.Model.VidModel;
import com.example.efhemo.platingapp.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String  LOG_TAG = MovieAdapter.class.getSimpleName();


    //private List<VidModel> mTaskEntries;
    Context context;
    private List<VidModel> vidModelList;


    public MovieAdapter(Context context ){ //watch out next time, this should be public
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.video_list_item, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        VidModel result = vidModelList.get(position);

        String keyResult = result.getKey();
        String videoname = result.getName();
        holder.textViewvideoname.setText(videoname);
        Log.w(LOG_TAG, "keyResult will be "+ keyResult);

        Glide.with(context).load("https://img.youtube.com/vi/"+keyResult+"/0.jpg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewThumbnail);

        //Toast.makeText(context, keyResult, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        if (vidModelList == null) {
            return 0;
        }
        return vidModelList.size();
    }

    /**
     * When data changes, this method updates the list of mTaskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<VidModel> taskEnter) {
        vidModelList = taskEnter;
        notifyDataSetChanged();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView textViewvideoname;
        ImageView imageViewThumbnail;


        public MovieViewHolder(View itemView) {
            super(itemView);
            textViewvideoname =itemView.findViewById(R.id.video_name);
            imageViewThumbnail = itemView.findViewById(R.id.thumbnail_imageView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            String videoKey = vidModelList.get(getAdapterPosition()).getKey();
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+videoKey+"")));
            Log.d("Video", "Video Playing...");
        }
    }
}
