package com.example.financeapp.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financeapp.News.Model.NewsHeadlines;
import com.squareup.picasso.Picasso;
import com.example.financeapp.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<CustomView> {
   private Context context;
   private List<NewsHeadlines> headlines;
    private SelectListner listner;
    public Adapter(Context context, List<NewsHeadlines> headlines,SelectListner listner) {
        this.context = context;
        this.headlines = headlines;
        this.listner=listner;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomView(LayoutInflater.from(context).inflate(R.layout.headlines,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, @SuppressLint("RecyclerView") int position) {
     holder.title.setText(headlines.get(position).getTitle());
     holder.source.setText(headlines.get(position).getSource().getName());
     if(headlines.get(position).getUrlToImage()!=null){
         Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.headlines);
     }
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
listner.OnnewsClick(headlines.get(position));         }
     });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
