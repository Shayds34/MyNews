package com.example.theshayds.mynewstest.Views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.theshayds.mynewstest.Controller.ArticleActivity;
import com.example.theshayds.mynewstest.Models.NYTimesNews;
import com.example.theshayds.mynewstest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private static final String TAG = "ArticleAdapter";

    // Data
    private List<NYTimesNews> nyTimesNewsList;
    private Context mContext;

    // Constructor
    public ArticleAdapter(Context context, List<NYTimesNews> article){
        nyTimesNewsList = article;
        mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create view holder and inflate its xml layout
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);

        final ArticleViewHolder mViewHolder = new ArticleViewHolder(mView);

        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "position " + mViewHolder.getAdapterPosition());
                Toast.makeText(mContext, "URL: " + nyTimesNewsList.get(mViewHolder.getAdapterPosition()).getUrl(), Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(v.getContext(), ArticleActivity.class);
                mIntent.putExtra("URL", nyTimesNewsList.get(mViewHolder.getAdapterPosition()).getUrl());
                v.getContext().startActivity(mIntent);
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: called");
        holder.updateWithArticles(this.nyTimesNewsList.get(position));

    }

    public void clearNews(){
        nyTimesNewsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (nyTimesNewsList == null)
            return 0;
        return nyTimesNewsList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title) TextView mTitle;
        @BindView(R.id.item_category) TextView mSection;
        @BindView(R.id.item_date) TextView mDate;
        @BindView(R.id.item_icon) ImageView mURL;

        ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void updateWithArticles(NYTimesNews news){
            mTitle.setText(news.getTitle());
            mSection.setText(news.getSection());
            mDate.setText(news.getPublishedDate());

            // Setup default options for GLIDE
            RequestOptions mOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_menu_camera)
                    .error(R.drawable.ic_menu_camera)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            Glide.with(mContext)
                    .load(news.getImageURL())
                    .apply(mOptions)
                    .into(mURL);
        }
    }
}
