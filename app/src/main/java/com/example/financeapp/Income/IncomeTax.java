package com.example.financeapp.Income;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financeapp.Appinfo;
import com.example.financeapp.Help;
import com.example.financeapp.MainActivity;
import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.R;
import com.google.android.material.navigation.NavigationView;

public class IncomeTax extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
        EditText et;
        TextView tx;
        TextView tx1;
        Button b1;
    private DrawerLayout drawerLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_income_tax);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawerLayout=findViewById(R.id.drawer_layout);
            NavigationView navigationView=findViewById(R.id.nav);
            navigationView.setNavigationItemSelectedListener( this);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            et=findViewById(R.id.edit1);
            tx=findViewById(R.id.text1);
            tx1=findViewById(R.id.text2);
            b1=findViewById(R.id.button3);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Integer.parseInt(String.valueOf(et.getText()));
                        calculate();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "You have exceeded the Input Limit!", Toast.LENGTH_LONG).show();
                        tx1.setText("");
                    }
                }
            });
        }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(IncomeTax.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(IncomeTax.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(IncomeTax.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(IncomeTax.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @SuppressLint("SetTextI18n")
        public void calculate()
        {
            long Total = 0;
            long Tax=0;
            long in= Integer.parseInt(et.getText().toString());
            if (in >= 200000 && in < 1000000)
            {
                Tax= (in * 5)/100;
                Total = in + Tax;
            }
            else if(in >= 1000000 && in < 2000000)
            {
                Tax = (in * 10)/100;
                Total = in + Tax;
            }
            else if(in >= 2000000 && in < 3000000)
            {
                Tax= (in * 15)/100;
                Total = in + Tax;
            }
            else if (in >= 3000000 && in < 4000000)
            {
                Tax = (in * 20)/100;
                Total = in + Tax;
            }
            else if(in >= 4000000 && in < 5000000)
            {
                Tax = (in * 25)/100;
                Total = in + Tax;
            }
            else if (in >= 5000000 && in < 7000000)
            {
                Tax = (in * 30)/100;
                Total = in + Tax;
            }
            else if (in >= 7000000 && in < 10000000)
            {
                Tax = (in * 35)/100;
                Total = in + Tax;
            }
            else if (in >= 10000000)
            {
                Tax = (in * 40)/100;
                Total = in + Tax;
            }
            else {
                Tax=0;
                Total=in;
            }
            tx1.setText("Tax on your income Rs."+et.getText()+" : \n Rs."+Tax+"\n \n"+
                    "Total Income (Inclusion of Tax) "+": \n Rs."+Total);
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
