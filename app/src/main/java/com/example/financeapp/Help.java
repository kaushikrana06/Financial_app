package com.example.financeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.Reminder.RemindActivity;
import com.google.android.material.navigation.NavigationView;

public class Help extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null)
            navigationView.setCheckedItem(R.id.help);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        gst = (Button) findViewById(R.id.gst);

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(Help.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(Help.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(Help.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                recreate();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hlp(View view) {
        Intent intent=new Intent((Intent.ACTION_SEND));
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"yadav331vinod@gmail.com"});
        intent.setType("message/rfc822");
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else {
            Toast.makeText(Help.this,"No application supports this action",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
        {
            Intent intent = new Intent(Help.this, More_Tools.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }

    }

}
