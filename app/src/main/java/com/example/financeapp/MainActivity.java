package com.example.financeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.financeapp.Interest.Simple;
import com.example.financeapp.Invoice.BillsActivity;
import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.News.NewsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    BottomNavigationView navigationView1;
    private Button _btnOne;
    private Button _btnTwo;
    private Button _btnThree;
    private Button _btnFour;
    private Button _btnFive;
    private Button _btnSix;
    private Button _btnSeven;
    private Button _btnEight;
    private Button _btnNine;
    private Button _btnZero;
    private Button _btnPoint;
    private FrameLayout _btnClear;
    private TextView _finalPrice;
    private TextView _beforeTaxPrice;
    private Spinner _GSTTaxes;
    private double _finalValue;
    private TextView _tvFinalPrice;
    private TextView _tvBeforeTax;
    private ImageView _dots;
    private static int _count = 0;
    private  Button gst;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null)
            navigationView.setCheckedItem(R.id.gst);
        BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gst1:
                        recreate();
                        break;
                    case R.id.home:
                        Intent intent = new Intent(MainActivity.this, More_Tools.class);
                        startActivity(intent);
                        break;
                    case R.id.sici:
                        Intent intent1 = new Intent(MainActivity.this, Simple.class);
                        startActivity(intent1);
                        break;
                    case R.id.bills:
                        Intent intent2 = new Intent(MainActivity.this, BillsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.news:
                        Intent intent3 = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(intent3);
                        break;
                }
                return  true;
            }
        };
        navigationView1=findViewById(R.id.bottomnav);
        navigationView1.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);
        navigationView1.getMenu().findItem(R.id.gst1).setChecked(true);



        Typeface mFontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesomeicon.ttf");
        Typeface mOdin = Typeface.createFromAsset(getAssets(), "odin.otf");
        Typeface mDosisSemiBold = Typeface.createFromAsset(getAssets(), "dosis-semibold.ttf");


     //   _dots = (ImageView) findViewById(R.id.im_dots);

        TextView mClear = (TextView) findViewById(R.id.tv_clearValue);
        mClear.setTypeface(mFontAwesomeFont);
        _tvFinalPrice = (TextView) findViewById(R.id.tv_finalPrice);
        _tvFinalPrice.setTypeface(mOdin);
        TextView mTvGST = (TextView) findViewById(R.id.tv_GST);
        mTvGST.setTypeface(mOdin);
        _tvBeforeTax = (TextView) findViewById(R.id.tv_beforeTax);
        _tvBeforeTax.setTypeface(mOdin);

        RelativeLayout mClearValue = (RelativeLayout) findViewById(R.id.layout_clearValue);

        _finalPrice = (TextView) findViewById(R.id.tv_finalPriceNo);
        _finalPrice.setTypeface(mDosisSemiBold);

        _beforeTaxPrice = (TextView) findViewById(R.id.tv_beforeTaxNo);
        _beforeTaxPrice.setTypeface(mDosisSemiBold);
        _beforeTaxPrice.setInputType(0);

        _btnOne = (Button) findViewById(R.id.btn_one);


        _btnTwo = (Button) findViewById(R.id.btn_two);

        _btnThree = (Button) findViewById(R.id.btn_three);

        _btnFour = (Button) findViewById(R.id.btn_four);

        _btnFive = (Button) findViewById(R.id.btn_five);

        _btnSix = (Button) findViewById(R.id.btn_six);

        _btnSeven = (Button) findViewById(R.id.btn_seven);

        _btnEight = (Button) findViewById(R.id.btn_eight);

        _btnNine = (Button) findViewById(R.id.btn_nine);

        _btnZero = (Button) findViewById(R.id.btn_zero);

        _btnPoint = (Button) findViewById(R.id.btn_point);

        _btnClear = (FrameLayout) findViewById(R.id.layout_clear);

        _GSTTaxes = (Spinner) findViewById(R.id.sp_listOfTaxes);
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this,
                R.array.tax_array, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _GSTTaxes.setAdapter(mAdapter);

        _btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "1");
                calculateFinalPrice();
            }
        });

        _btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "2");

                calculateFinalPrice();
            }
        });

        _btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "3");

                calculateFinalPrice();
            }
        });

        _btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "4");
                calculateFinalPrice();
            }
        });

        _btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "5");
                calculateFinalPrice();
            }
        });

        _btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "6");
                calculateFinalPrice();
            }
        });

        _btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "7");

                calculateFinalPrice();
            }
        });

        _btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "8");
                calculateFinalPrice();
            }
        });

        _btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "9");
                calculateFinalPrice();
            }
        });

        _btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "0");
                calculateFinalPrice();
            }
        });

        _btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_count == 0) {
                    if (_beforeTaxPrice.length() == 0) {
                        _beforeTaxPrice.setText(_beforeTaxPrice.getText() + "0.");
                        _count++;
                    } else {
                        _beforeTaxPrice.setText(_beforeTaxPrice.getText() + ".");
                        _count++;
                    }
                }
                calculateFinalPrice();
            }
        });

        _btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (_beforeTaxPrice.getText().length() != 0) {
                    String str = _beforeTaxPrice.getText().toString();
                    str = str.substring(0, str.length() - 1);
                    _beforeTaxPrice.setText(str);
                }

                if (_beforeTaxPrice.getText().length() == 0) {
                    _finalPrice.setText("");
                    _count = 0;
                }

                if (_beforeTaxPrice.getText().length() != 0) {
                    String mString = _beforeTaxPrice.getText().toString();
                    if (mString.contains(".")) {
                        _count++;
                    } else {
                        _count = 0;
                    }
                }

                calculateFinalPrice();
            }
        });

        mClearValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _beforeTaxPrice.setText("");
                _finalPrice.setText("");
                _count = 0;
            }
        });

        _GSTTaxes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                calculateFinalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void calculateFinalPrice() {
        String beforeTaxPrice = _beforeTaxPrice.getText().toString().trim();
        if (!TextUtils.isEmpty(beforeTaxPrice)) {
            double value = Double.parseDouble(beforeTaxPrice);
            String s = _GSTTaxes.getSelectedItem().toString();
            String p = s.substring(0, s.length() - 1);
            double percentageGST = Double.parseDouble(p);
            _finalValue = value + ((value * percentageGST) / 100);
            _finalValue = Double.parseDouble(new DecimalFormat("#.###").format(_finalValue));
            String stringValue = Double.toString(_finalValue);

            int mFinalPriceLength = stringValue.length();

            if (mFinalPriceLength <= 10) {
                _finalPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
            } else if (mFinalPriceLength > 10 && mFinalPriceLength <= 13) {
                _finalPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            } else if (mFinalPriceLength > 13 && mFinalPriceLength <= 16) {
                _finalPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            } else {
                _finalPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            }

            _finalPrice.setText("\u20B9 " + stringValue);


        } else {
            String s = _GSTTaxes.getSelectedItem().toString();

        }
    }
//    public void ClickMenu(Menu view){
//        openDrawer(drawer);
//    }
//
//    public static void openDrawer(DrawerLayout drawer) {
//        drawer.openDrawer(GravityCompat.START);
//    }
//    public void Clicklog(View view){
//        closeDrawer(drawer);
//    }
//
//    public static void closeDrawer(DrawerLayout drawer) {
//    if(drawer.isDrawerOpen(GravityCompat.START)){
//        drawer.closeDrawer(GravityCompat.START);
//    }
//    }
//public void clickhome(View view){
//        recreate();
//}
//public void clickdashboard(View view)
//{
//
//redirectActivity(this,More_Tools.class);
//}


    public static void redirectActivity(Activity activity,Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        gst = (Button) findViewById(R.id.gst);

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(MainActivity.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                recreate();
                break;
            case R.id.about:
                Intent intent2 = new Intent(MainActivity.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(MainActivity.this, Help.class);
                startActivity(intent4);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        navigationView1.getMenu().findItem(R.id.gst1).setChecked(true);
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
        {
            Intent intent = new Intent(MainActivity.this, More_Tools.class);
            startActivity(intent);
            finish();
            finish();
            super.onBackPressed();
        }

    }

}
