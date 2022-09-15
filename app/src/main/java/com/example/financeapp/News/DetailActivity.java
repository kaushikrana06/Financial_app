package com.example.financeapp.News;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financeapp.News.Model.NewsHeadlines;
import com.example.financeapp.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    NewsHeadlines headlines;
    TextView title,author,time,details,context;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail2);
        headlines= (NewsHeadlines) getIntent().getSerializableExtra("data");
        title=findViewById(R.id.text_details);
        author=findViewById(R.id.text_details_author);
        time=findViewById(R.id.text_details_time);
        details=findViewById(R.id.text_details_detail);
        context=findViewById(R.id.text_details_content);
        img=findViewById(R.id.img_details);

        title.setText(headlines.getTitle());
        author.setText(headlines.getAuthor());
        time.setText(headlines.getPublishedAt());
        details.setText(headlines.getDescription());
        context.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(img);

    }
}