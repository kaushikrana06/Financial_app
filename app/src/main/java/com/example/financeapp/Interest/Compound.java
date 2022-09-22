package com.example.financeapp.Interest;

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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financeapp.Appinfo;
import com.example.financeapp.Help;
import com.example.financeapp.Invoice.BillsActivity;
import com.example.financeapp.MainActivity;
import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.News.NewsActivity;
import com.example.financeapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Compound extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    Button calculate;
    EditText editamount,rate;
    double time=6,amount=1000,annualrate=8;
    SeekBar seekbar;
    TextView interest,tenure;
    BottomNavigationView navigationView1;

    double compoundinterest;
    double n=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compound);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tenure=findViewById(R.id.textView9);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gst1:
                        Intent intent1 = new Intent(Compound.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(Compound.this, More_Tools.class);
                        startActivity(intent);
                        break;
                    case R.id.sici:
                        Intent intent4 = new Intent(Compound.this, Simple.class);
                        startActivity(intent4);
                        break;
                    case R.id.bills:
                        Intent intent2 = new Intent(Compound.this, BillsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.news:
                        Intent intent3 = new Intent(Compound.this, NewsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return  true;
            }
        };
        navigationView1=findViewById(R.id.bottomnav);
        navigationView1.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);
        navigationView1.getMenu().findItem(R.id.sici).setChecked(true);




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Compound Interest Calculator");

        calculate=(Button)findViewById(R.id.button);
        interest=(TextView)findViewById(R.id.finalvalue);
        rate=(EditText)findViewById(R.id.annualrate);
        editamount=(EditText)findViewById(R.id.amount);
        seekbar=(SeekBar)findViewById(R.id.sb);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time=progress+1;
                tenure.setText("Tenure : "+String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editamount.getText().toString()!=null&& rate.getText().toString()!=null){
                    amount=Double.parseDouble(editamount.getText().toString());
                    annualrate=Double.parseDouble(rate.getText().toString());
                    if(annualrate>100)
                    {

                        Toast.makeText(Compound.this,"Rate must be under hundred",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        annualrate = annualrate / 100;
                        compoundinterest = (amount * Math.pow((1 + (annualrate / n)), (n * time))) - amount;
                        // interest.setText(String.valueOf(compoundinterest));
                        if(compoundinterest<1000){

                            interest.setText(""+" Rs." + String.format("%.2f", compoundinterest) );

                        }
                        if (compoundinterest > 1000 && compoundinterest < 100000) {
                            compoundinterest = compoundinterest / 1000;
                            interest.setText("" + " Rs."+String.format("%.2f", compoundinterest) + " thou.");
                        }
                        if (compoundinterest > 100000 && compoundinterest < 10000000) {
                            compoundinterest = compoundinterest / 100000;
                            interest.setText("" +" Rs."+ String.format("%.2f", compoundinterest) + " Lac");
                        }
                        if (compoundinterest > 10000000) {
                            compoundinterest = compoundinterest / 10000000;
                            interest.setText("" +" Rs."+ String.format("%.2f", compoundinterest) + " Cr.");
                        }
                    }
                }}
        });


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(Compound.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(Compound.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(Compound.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(Compound.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cnn(View view) {
        Intent intent = new Intent(Compound.this, Simple.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        navigationView1.getMenu().findItem(R.id.sici).setChecked(true);

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
        {
            Intent intent = new Intent(Compound.this, More_Tools.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }

    }
}
