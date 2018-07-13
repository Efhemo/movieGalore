package com.example.efhemo.platingapp.ViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.efhemo.platingapp.Model.ReviewsModel;
import com.example.efhemo.platingapp.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    List<ReviewsModel> reviewsModelList;

    Context context;

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reviews_layout, parent, false);

        return new ReviewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewsModel reviewsModel = reviewsModelList.get(holder.getAdapterPosition());
        holder.textViewAuthor.setText(reviewsModel.getAuthor());
        holder.textViewReview.setText(reviewsModel.getReviews());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(reviewsModelList == null){
            return 0;
        }
        return reviewsModelList.size();
    }

    public void setTaskReview(List<ReviewsModel> reviewsModels){
        this.reviewsModelList = reviewsModels;
        notifyDataSetChanged();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView textViewAuthor;
        TextView textViewReview;
        public ReviewHolder(View itemView) {
            super(itemView);

            textViewAuthor = itemView.findViewById(R.id.authors_name);
            textViewReview = itemView.findViewById(R.id.authors_review);
        }
    }
}
