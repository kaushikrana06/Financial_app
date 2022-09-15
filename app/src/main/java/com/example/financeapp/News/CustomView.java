package com.example.financeapp.News;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.financeapp.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomView extends RecyclerView.ViewHolder {
    TextView title,source;
    ImageView headlines;
    CardView cardView;
    public CustomView(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.text_title);
        source=itemView.findViewById(R.id.text_source);
        headlines=itemView.findViewById(R.id.headlines);
        cardView=itemView.findViewById(R.id.main_container);

    }
}
