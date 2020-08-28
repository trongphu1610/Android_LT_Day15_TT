package com.ddona.day15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ddona.day15.R;
import com.ddona.day15.callback.OnNewsItemClickListener;
import com.ddona.day15.model.News;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News> arrNews;
    private OnNewsItemClickListener callback;

    public NewsAdapter(Context context, List<News> arrNews, OnNewsItemClickListener callback) {
        this.context = context;
        this.arrNews = arrNews;
        this.callback = callback;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, final int position) {
        News news = arrNews.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvDescription.setText(news.getDescription());
        holder.tvDate.setText(news.getDate());
        Glide.with(context).load(news.getImage()).into(holder.imgNews);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onNewsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView tvTitle, tvDescription, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.img_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
