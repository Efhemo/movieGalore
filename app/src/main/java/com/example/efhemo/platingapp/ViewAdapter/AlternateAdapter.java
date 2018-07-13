package com.example.efhemo.platingapp.ViewAdapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.efhemo.platingapp.Model.GenericListItem;
import com.example.efhemo.platingapp.R;

import java.util.List;

public class AlternateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int LARGE_VIEW_TYPE = 0;
    private static final int SMALL_VIEW_TYPE = 1;


    private List<GenericListItem> mTaskEntries;
    Context context;

    public OnOneClickItem onOneClickItem;
    private int pos;


    public interface OnOneClickItem{
        void onOneClick(int position,
                        int ident, double voteAverage,
                        String backdrop, String poster, String title, String descript,String releaseDate );
    }

    public AlternateAdapter(Context context, OnOneClickItem onOneClickItem) {
        this.context = context;
        this.onOneClickItem = onOneClickItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType == LARGE_VIEW_TYPE){
                return new HolderLarge(LayoutInflater.from(parent.getContext()).inflate(R.layout.third_item_layout, parent, false));

            }else{
                return new HolderSmaall(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false));

            }

    }


    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        pos = holder.getAdapterPosition();
        int viewtype = getItemViewType(position);

        GenericListItem result = mTaskEntries.get(position);
        final String titleResult = result.getTitle();
        final double voteAverage = result.getVote_average();
        final String backdroppath = result.getBackdrop_path();
        final String posterPathe = result.getPoster_path();
        final int iden = result.getIdent();
        final String releaseDate = result.getRelease_date();


        //String popularityResult =  Double.toString(result.getPopularity());
        final String descript = result.getOverview();
        String imageResult = result.getPoster_path();

        if(viewtype == LARGE_VIEW_TYPE){
            HolderLarge holderLarge = (HolderLarge) holder;
            holderLarge.textViewTitle.setText(titleResult);
            holderLarge.textViewDescription.setText(descript);

            Glide.with(context).load("https://image.tmdb.org/t/p/w200/"+imageResult)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holderLarge.imageViewRight);
        }else {
            HolderSmaall holderSmaall = (HolderSmaall) holder;
            holderSmaall.textViewTitle.setText(titleResult);

            Glide.with(context).load("https://image.tmdb.org/t/p/w200/"+imageResult)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holderSmaall.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOneClickItem.onOneClick(pos, iden, voteAverage,
                        backdroppath, posterPathe, titleResult, descript, releaseDate);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            int mod = position % 5;
            if(mod == 4){
                return LARGE_VIEW_TYPE;
            }else {
                return SMALL_VIEW_TYPE;
            }
        }else {
            int mod = position % 3;
            if (mod == 2) {
                return LARGE_VIEW_TYPE;
            } else {
                return SMALL_VIEW_TYPE;
            }
        }
    }

    /**
     * When data changes, this method updates the list of mTaskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<GenericListItem> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public class HolderLarge extends RecyclerView.ViewHolder{


        TextView textViewDescription;
        TextView textViewTitle;
        ImageView imageViewRight;

        public HolderLarge(View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.descript);
            textViewTitle =itemView.findViewById(R.id.title_large);
            imageViewRight = itemView.findViewById(R.id.imageView_right);
        }
    }

    public class HolderSmaall extends RecyclerView.ViewHolder{


        TextView textViewTitle;
        TextView textViewPopular;
        ImageView imageView;

        public HolderSmaall(View itemView) {
            super(itemView);

            textViewTitle =itemView.findViewById(R.id.text_film);
            //textViewPopular = itemView.findViewById(R.id.popularity);
            imageView = itemView.findViewById(R.id.poster_path_image);
        }
    }

}
