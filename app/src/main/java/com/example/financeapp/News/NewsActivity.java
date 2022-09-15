package com.example.financeapp.News;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financeapp.MainActivity;
import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.Mortgage.MortgageCalc;
import com.example.financeapp.R;
import com.example.financeapp.News.Model.NewsApiResponse;
import com.example.financeapp.News.Model.NewsHeadlines;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SelectListner{
 RecyclerView recyclerView;
    DrawerLayout drawerLayout;
 com.example.financeapp.News.Adapter adapter;
 ProgressDialog progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        progressBar1=new ProgressDialog(NewsActivity.this);
        progressBar1.setTitle("Fetching news....!!!! ");
        progressBar1.show();
        com.example.financeapp.News.RequestManager manager = new com.example.financeapp.News.RequestManager(this);
        manager.getNewsHeadlines(listener,"business",null);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(NewsActivity.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(NewsActivity.this, MainActivity.class);
                startActivity(intent1);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private final DataListener<NewsApiResponse> listener=new DataListener<NewsApiResponse>() {
        @Override
        public void onFetch(List<NewsHeadlines> list, String msg) {
            showNews(list);
            progressBar1.dismiss();
        }

        private void showNews(List<NewsHeadlines> list) {
        recyclerView=findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(NewsActivity.this,1));
        adapter=new com.example.financeapp.News.Adapter(NewsActivity.this,list,NewsActivity.this);
        recyclerView.setAdapter(adapter);
        }

        @Override
        public void onErroor(String msg) {

        }
    };

    @Override
    public void OnnewsClick(NewsHeadlines headlines) {
startActivity(new Intent(NewsActivity.this,DetailActivity.class).
        putExtra("data",headlines));

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
        {
            finish();

            super.onBackPressed();
        }

    }
}