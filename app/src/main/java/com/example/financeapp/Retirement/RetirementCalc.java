package com.example.financeapp.Retirement;


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.financeapp.Appinfo;
import com.example.financeapp.Help;
import com.example.financeapp.MainActivity;
import com.example.financeapp.MoreTools.More_Tools;
import com.example.financeapp.R;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetirementCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText edtRequiredIncome, edtSocialSecurity, edtPension,
            edtPartTimeIncome, edtRetirementAge,
            edtMoneyInSavings, edtRetiringIn;
    private String rate1;
    private String[] rates = {"INR","USD","AUS","EUR","JPY","RUB","SGD","LKR","BDT"};

    DrawerLayout drawerLayout;
    TextView textNeedToSave, textNeededForRetirement, textNeededPerYear;
    double requiredIncome = 0, socialSecurity = 0, pension = 0,
            partTimeIncome = 0, anticipatedRetirementAge = 0,
            moneyInSavings = 0, retiringIn = 0, retirementShortfall = 0,
            needToSave = 0, neededForRetirement = 0, neededPerYear = 0, tempResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_retirement_calc);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Spinner spin=findViewById(R.id.spinn);
        spin.setVisibility(View.VISIBLE);
        TextView tx= findViewById(R.id.ttt);
        tx.setVisibility(View.VISIBLE);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, rates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void spinnerInflater(){

        Spinner spin=findViewById(R.id.spinn);
        spin.setVisibility(View.VISIBLE);
        TextView tx= findViewById(R.id.ttt);
        tx.setVisibility(View.VISIBLE);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, rates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                rate1 = adapterView.getItemAtPosition(i).toString().trim();
//                INR","USD","AUS","EUR","JPY","RUB","SGD","LKR","BDT"
                switch (rate1) {
                    case "INR":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requiredIncome1 = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSecurity1 = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pension1 = Double.parseDouble(edtPension.getText().toString());
                        double partTimeIncome1 = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirementAge1 = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSavings1 = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retiringIn1 = Double.parseDouble(edtRetiringIn.getText().toString());
                        requiredIncome = requiredIncome1;
                        socialSecurity = socialSecurity1;
                        pension = pension1;
                        partTimeIncome = partTimeIncome1;
                        anticipatedRetirementAge = retirementAge1;
                        moneyInSavings = moneyInSavings1;
                        retiringIn = retiringIn1;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNeedToSave = String.format("%1.2f", needToSave);
                        String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
                        String strNeededPerYear = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: ₹" + strNeedToSave);
                        textNeededForRetirement.setText("Needed for Retirement: ₹" + strNeededForRetirement);
                        textNeededPerYear.setText("Needed per Year: ₹" + strNeededPerYear);
                        break;
                    case "USD":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requiredIncom = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSecurit = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pensio = Double.parseDouble(edtPension.getText().toString());
                        double partTimeIncom = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirementAg = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSaving = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retiringI = Double.parseDouble(edtRetiringIn.getText().toString());
                        requiredIncome = requiredIncom*0.013;
                        socialSecurity = socialSecurit*0.013;
                        pension = pensio*0.013;
                        partTimeIncome = partTimeIncom*0.013;
                        anticipatedRetirementAge = retirementAg;
                        moneyInSavings = moneyInSaving*0.013;
                        retiringIn = retiringI;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNeedToSav = String.format("%1.2f", needToSave);
                        String strNeededForRetiremen = String.format("%1.2f", neededForRetirement);
                        String strNeededPerYea = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: $" + strNeedToSav);
                        textNeededForRetirement.setText("Needed for Retirement: $" + strNeededForRetiremen);
                        textNeededPerYear.setText("Needed per Year: $" + strNeededPerYea);
                        break;
                    case "AUS":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requiredInc = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSecur = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pensi = Double.parseDouble(edtPension.getText().toString());
                        double partTimeInco = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirementA = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSavin = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retiring = Double.parseDouble(edtRetiringIn.getText().toString());
                        requiredIncome = requiredInc*0.019;
                        socialSecurity = socialSecur*0.019;
                        pension = pensi*0.019;
                        partTimeIncome = partTimeInco*0.019;
                        anticipatedRetirementAge = retirementA;
                        moneyInSavings = moneyInSavin*0.019;
                        retiringIn = retiring;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNeedTo = String.format("%1.2f", needToSave);
                        String strNeededForRetire = String.format("%1.2f", neededForRetirement);
                        String strNeededPe = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: A$ " + strNeedTo);
                        textNeededForRetirement.setText("Needed for Retirement: A$ " + strNeededForRetire);
                        textNeededPerYear.setText("Needed per Year: A$ " + strNeededPe);
                        break;
                    case "EUR":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requiredIn = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSecu = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pens = Double.parseDouble(edtPension.getText().toString());
                        double partTimeInc = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirement = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSavi = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retirin = Double.parseDouble(edtRetiringIn.getText().toString());
                        requiredIncome = requiredIn*0.013;
                        socialSecurity = socialSecu*0.013;
                        pension = pens*0.013;
                        partTimeIncome = partTimeInc*0.013;
                        anticipatedRetirementAge = retirement;
                        moneyInSavings = moneyInSavi*0.013;
                        retiringIn = retirin;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNeedT = String.format("%1.2f", needToSave);
                        String strNeededForRetir = String.format("%1.2f", neededForRetirement);
                        String strNeeded = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: € " + strNeedT);
                        textNeededForRetirement.setText("Needed for Retirement: € " + strNeededForRetir);
                        textNeededPerYear.setText("Needed per Year: € " + strNeeded);
                        break;
                    case "JPY":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requiredI = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSec = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pen = Double.parseDouble(edtPension.getText().toString());
                        double partTimeIn = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retiremen = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSav = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retiri = Double.parseDouble(edtRetiringIn.getText().toString());

                        requiredIncome = requiredI*1.79;
                        socialSecurity = socialSec*1.79;
                        pension = pen*1.79;
                        partTimeIncome = partTimeIn*1.79;
                        anticipatedRetirementAge = retiremen;
                        moneyInSavings = moneyInSav*1.79;
                        retiringIn = retiri;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNeed = String.format("%1.2f", needToSave);
                        String strNeededForReti = String.format("%1.2f", neededForRetirement);
                        String strNeede = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: ¥ " + strNeed);
                        textNeededForRetirement.setText("Needed for Retirement: ¥ " + strNeededForReti);
                        textNeededPerYear.setText("Needed per Year: ¥ " + strNeede);
                        break;
                    case "RUB":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double required = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialSe = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double pe = Double.parseDouble(edtPension.getText().toString());
                        double partTimeI = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retireme = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyInSa = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double retir = Double.parseDouble(edtRetiringIn.getText().toString());

                        requiredIncome = required*0.76;
                        socialSecurity = socialSe*0.76;
                        pension = pe*0.76;
                        partTimeIncome = partTimeI*0.76;
                        anticipatedRetirementAge = retireme;
                        moneyInSavings = moneyInSa*0.76;
                        retiringIn = retir;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNee = String.format("%1.2f", needToSave);
                        String strNeededForRet = String.format("%1.2f", neededForRetirement);
                        String strNeedMM = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: ₽ " + strNee);
                        textNeededForRetirement.setText("Needed for Retirement: ₽ " + strNeededForRet);
                        textNeededPerYear.setText("Needed per Year: ₽ " + strNeedMM);
                        break;
                    case "SGD":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double require = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socialS = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double peLLL = Double.parseDouble(edtPension.getText().toString());
                        double partTime = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirem = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyIn = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double reti = Double.parseDouble(edtRetiringIn.getText().toString());

                        requiredIncome = require*0.018;
                        socialSecurity = socialS*0.018;
                        pension = peLLL*0.018;
                        partTimeIncome = partTime*0.018;
                        anticipatedRetirementAge = retirem;
                        moneyInSavings = moneyIn*0.018;
                        retiringIn = reti;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strNe = String.format("%1.2f", needToSave);
                        String strNeededForRe = String.format("%1.2f", neededForRetirement);
                        String strNeeKK = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: S$ " + strNe);
                        textNeededForRetirement.setText("Needed for Retirement: S$ " + strNeededForRe);
                        textNeededPerYear.setText("Needed per Year: S$ " + strNeeKK);
                        break;
                    case "LKR":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requir = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double social = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double peLL = Double.parseDouble(edtPension.getText().toString());
                        double partTim = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retire = Double.parseDouble(edtRetirementAge.getText().toString());
                        double moneyI = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double ret = Double.parseDouble(edtRetiringIn.getText().toString());

                        requiredIncome = requir*4.51;
                        socialSecurity = social*4.51;
                        pension = peLL*4.51;
                        partTimeIncome = partTim*4.51;
                        anticipatedRetirementAge = retire;
                        moneyInSavings = moneyI*4.51;
                        retiringIn = ret;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String strN = String.format("%1.2f", needToSave);
                        String strNeededForR = String.format("%1.2f", neededForRetirement);
                        String strNeeK = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: රු, " + strN);
                        textNeededForRetirement.setText("Needed for Retirement: රු, " + strNeededForR);
                        textNeededPerYear.setText("Needed per Year: රු, " + strNeeK);
                        break;
                    case "BDT":
                        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                        double requi = Double.parseDouble(edtRequiredIncome.getText().toString());
                        double socia = Double.parseDouble(edtSocialSecurity.getText().toString());
                        double peL = Double.parseDouble(edtPension.getText().toString());
                        double partTi = Double.parseDouble(edtPartTimeIncome.getText().toString());
                        double retirMM = Double.parseDouble(edtRetirementAge.getText().toString());
                        double mone = Double.parseDouble(edtMoneyInSavings.getText().toString());
                        double rEE = Double.parseDouble(edtRetiringIn.getText().toString());

                        requiredIncome = requi*1.31;
                        socialSecurity = socia*1.31;
                        pension = peL*1.31;
                        partTimeIncome = partTi*1.31;
                        anticipatedRetirementAge = retirMM;
                        moneyInSavings = mone*1.31;
                        retiringIn = rEE;

                        retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                        if(anticipatedRetirementAge <= 55)
                        {
                            needToSave = retirementShortfall * 21;
                        }
                        else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                        {
                            needToSave = retirementShortfall * 18.9;
                        }
                        else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                        {
                            needToSave = retirementShortfall * 16.4;
                        }
                        else
                        {
                            needToSave = retirementShortfall * 13.6;
                        }

                        if(retiringIn <= 10)
                        {
                            tempResult = moneyInSavings * 1.3;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            tempResult = moneyInSavings * 1.6;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            tempResult = moneyInSavings * 1.8;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            tempResult = moneyInSavings * 2.1;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            tempResult = moneyInSavings * 2.4;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            tempResult = moneyInSavings * 2.8;
                        }
                        else
                        {
                            tempResult = moneyInSavings * 3.3;
                        }

                        neededForRetirement = needToSave - tempResult;
                        if(retiringIn <= 10)
                        {
                            neededPerYear = neededForRetirement * .085;
                        }
                        else if(retiringIn > 10 && retiringIn <= 15)
                        {
                            neededPerYear = neededForRetirement * .052;
                        }
                        else if(retiringIn > 15 && retiringIn <= 20)
                        {
                            neededPerYear = neededForRetirement * .036;
                        }
                        else if(retiringIn > 20 && retiringIn <= 25)
                        {
                            neededPerYear = neededForRetirement * .027;
                        }
                        else if(retiringIn > 25 && retiringIn <= 30)
                        {
                            neededPerYear = neededForRetirement * .020;
                        }
                        else if(retiringIn > 30 && retiringIn <= 35)
                        {
                            neededPerYear = neededForRetirement * .016;
                        }
                        else
                        {
                            neededPerYear = neededForRetirement * .013;
                        }

                        String str = String.format("%1.2f", needToSave);
                        String strNeededFo = String.format("%1.2f", neededForRetirement);
                        String strNeeKM = String.format("%1.2f", neededPerYear);

                        textNeedToSave.setText("Need to Save: ৳ " + str);
                        textNeededForRetirement.setText("Needed for Retirement: ৳ " + strNeededFo);
                        textNeededPerYear.setText("Needed per Year: ৳ " + strNeeKM);
                        break;

                }
                }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
                textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
                textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
                double requiredIncome1 = Double.parseDouble(edtRequiredIncome.getText().toString());
                double socialSecurity1 = Double.parseDouble(edtSocialSecurity.getText().toString());
                double pension1 = Double.parseDouble(edtPension.getText().toString());
                double partTimeIncome1 = Double.parseDouble(edtPartTimeIncome.getText().toString());
                double retirementAge1 = Double.parseDouble(edtRetirementAge.getText().toString());
                double moneyInSavings1 = Double.parseDouble(edtMoneyInSavings.getText().toString());
                double retiringIn1 = Double.parseDouble(edtRetiringIn.getText().toString());
                requiredIncome = requiredIncome1;
                socialSecurity = socialSecurity1;
                pension = pension1;
                partTimeIncome = partTimeIncome1;
                anticipatedRetirementAge = retirementAge1;
                moneyInSavings = moneyInSavings1;
                retiringIn = retiringIn1;

                retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

                if(anticipatedRetirementAge <= 55)
                {
                    needToSave = retirementShortfall * 21;
                }
                else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
                {
                    needToSave = retirementShortfall * 18.9;
                }
                else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
                {
                    needToSave = retirementShortfall * 16.4;
                }
                else
                {
                    needToSave = retirementShortfall * 13.6;
                }

                if(retiringIn <= 10)
                {
                    tempResult = moneyInSavings * 1.3;
                }
                else if(retiringIn > 10 && retiringIn <= 15)
                {
                    tempResult = moneyInSavings * 1.6;
                }
                else if(retiringIn > 15 && retiringIn <= 20)
                {
                    tempResult = moneyInSavings * 1.8;
                }
                else if(retiringIn > 20 && retiringIn <= 25)
                {
                    tempResult = moneyInSavings * 2.1;
                }
                else if(retiringIn > 25 && retiringIn <= 30)
                {
                    tempResult = moneyInSavings * 2.4;
                }
                else if(retiringIn > 30 && retiringIn <= 35)
                {
                    tempResult = moneyInSavings * 2.8;
                }
                else
                {
                    tempResult = moneyInSavings * 3.3;
                }

                neededForRetirement = needToSave - tempResult;
                if(retiringIn <= 10)
                {
                    neededPerYear = neededForRetirement * .085;
                }
                else if(retiringIn > 10 && retiringIn <= 15)
                {
                    neededPerYear = neededForRetirement * .052;
                }
                else if(retiringIn > 15 && retiringIn <= 20)
                {
                    neededPerYear = neededForRetirement * .036;
                }
                else if(retiringIn > 20 && retiringIn <= 25)
                {
                    neededPerYear = neededForRetirement * .027;
                }
                else if(retiringIn > 25 && retiringIn <= 30)
                {
                    neededPerYear = neededForRetirement * .020;
                }
                else if(retiringIn > 30 && retiringIn <= 35)
                {
                    neededPerYear = neededForRetirement * .016;
                }
                else
                {
                    neededPerYear = neededForRetirement * .013;
                }

                String strNeedToSave = String.format("%1.2f", needToSave);
                String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
                String strNeededPerYear = String.format("%1.2f", neededPerYear);

                textNeedToSave.setText("Need to Save: ₹" + strNeedToSave);
                textNeededForRetirement.setText("Needed for Retirement: ₹" + strNeededForRetirement);
                textNeededPerYear.setText("Needed per Year: ₹" + strNeededPerYear);


            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(RetirementCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(RetirementCalc.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(RetirementCalc.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(RetirementCalc.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateLogic(View v)
    {
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);
        spinnerInflater();
        String text1 = edtRequiredIncome.getText().toString().trim();
        String text2 = edtSocialSecurity.getText().toString().trim();
        String text3 = edtPension.getText().toString().trim();
        String text4 = edtPartTimeIncome.getText().toString().trim();
        String text5 = edtRetirementAge.getText().toString().trim();
        String text6 = edtMoneyInSavings.getText().toString().trim();
        String text7 = edtRetiringIn.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter required income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter Social Security.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter pension.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter part-time income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter retirement age.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text6.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter $ in savings.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text7.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter years retiring in.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double requiredIncome1 = Double.parseDouble(edtRequiredIncome.getText().toString());
            double socialSecurity1 = Double.parseDouble(edtSocialSecurity.getText().toString());
            double pension1 = Double.parseDouble(edtPension.getText().toString());
            double partTimeIncome1 = Double.parseDouble(edtPartTimeIncome.getText().toString());
            double retirementAge1 = Double.parseDouble(edtRetirementAge.getText().toString());
            double moneyInSavings1 = Double.parseDouble(edtMoneyInSavings.getText().toString());
            double retiringIn1 = Double.parseDouble(edtRetiringIn.getText().toString());
            requiredIncome = requiredIncome1;
            socialSecurity = socialSecurity1;
            pension = pension1;
            partTimeIncome = partTimeIncome1;
            anticipatedRetirementAge = retirementAge1;
            moneyInSavings = moneyInSavings1;
            retiringIn = retiringIn1;

            retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

            if(anticipatedRetirementAge <= 55)
            {
                needToSave = retirementShortfall * 21;
            }
            else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
            {
                needToSave = retirementShortfall * 18.9;
            }
            else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
            {
                needToSave = retirementShortfall * 16.4;
            }
            else
            {
                needToSave = retirementShortfall * 13.6;
            }

            if(retiringIn <= 10)
            {
                tempResult = moneyInSavings * 1.3;
            }
            else if(retiringIn > 10 && retiringIn <= 15)
            {
                tempResult = moneyInSavings * 1.6;
            }
            else if(retiringIn > 15 && retiringIn <= 20)
            {
                tempResult = moneyInSavings * 1.8;
            }
            else if(retiringIn > 20 && retiringIn <= 25)
            {
                tempResult = moneyInSavings * 2.1;
            }
            else if(retiringIn > 25 && retiringIn <= 30)
            {
                tempResult = moneyInSavings * 2.4;
            }
            else if(retiringIn > 30 && retiringIn <= 35)
            {
                tempResult = moneyInSavings * 2.8;
            }
            else
            {
                tempResult = moneyInSavings * 3.3;
            }

            neededForRetirement = needToSave - tempResult;
            if(retiringIn <= 10)
            {
                neededPerYear = neededForRetirement * .085;
            }
            else if(retiringIn > 10 && retiringIn <= 15)
            {
                neededPerYear = neededForRetirement * .052;
            }
            else if(retiringIn > 15 && retiringIn <= 20)
            {
                neededPerYear = neededForRetirement * .036;
            }
            else if(retiringIn > 20 && retiringIn <= 25)
            {
                neededPerYear = neededForRetirement * .027;
            }
            else if(retiringIn > 25 && retiringIn <= 30)
            {
                neededPerYear = neededForRetirement * .020;
            }
            else if(retiringIn > 30 && retiringIn <= 35)
            {
                neededPerYear = neededForRetirement * .016;
            }
            else
            {
                neededPerYear = neededForRetirement * .013;
            }
        }
        printResults();
        dismissKeyboard();
    }

    public void clearLogic(View v)
    {
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);

        edtRequiredIncome.setText("");
        edtSocialSecurity.setText("");
        edtPension.setText("");
        edtPartTimeIncome.setText("");
        edtRetirementAge.setText("");
        edtMoneyInSavings.setText("");
        edtRetiringIn.setText("");

        textNeedToSave.setText("Need to Save: $");
        textNeededForRetirement.setText("Needed for Retirement: $");
        textNeededPerYear.setText("Needed per Year: $");
        dismissKeyboard();
    }

    private void printResults()
    {
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);

        String strNeedToSave = String.format("%1.2f", needToSave);
        String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
        String strNeededPerYear = String.format("%1.2f", neededPerYear);

        textNeedToSave.setText("Need to Save: $" + strNeedToSave);
        textNeededForRetirement.setText("Needed for Retirement: $" + strNeededForRetirement);
        textNeededPerYear.setText("Needed per Year: $" + strNeededPerYear);
    }

    private void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtRequiredIncome.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
        String text1 = edtRequiredIncome.getText().toString().trim();
        String text2 = edtSocialSecurity.getText().toString().trim();
        String text3 = edtPension.getText().toString().trim();
        String text4 = edtPartTimeIncome.getText().toString().trim();
        String text5 = edtRetirementAge.getText().toString().trim();
        String text6 = edtMoneyInSavings.getText().toString().trim();
        String text7 = edtRetiringIn.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter required income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter Social Security.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter pension.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter part-time income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter retirement age.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text6.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter $ in savings.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text7.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter years retiring in.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strReqiuiredIncome = String.format("%1.2f", requiredIncome);
            String strSocialSecurity = String.format("%1.2f", socialSecurity);
            String strPension = String.format("%1.2f", pension);
            String strPartTimeIncome= String.format("%1.2f", partTimeIncome);
            String strRetirementAge = String.format("%1.0f", anticipatedRetirementAge);
            String strMoneyInSavings = String.format("%1.2f", moneyInSavings);
            String strRetiringIn = String.format("%1.0f", retiringIn);
            String strNeedToSave = String.format("%1.2f", needToSave);
            String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
            String strNeededPerYear = String.format("%1.2f", neededPerYear);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Retirement Calculation Result");
            emailIntent.putExtra(Intent.EXTRA_TEXT,"Required Income: $" + strReqiuiredIncome
                    + "\nSocial Security: $" + strSocialSecurity + "\nPension: $" + strPension +
                    "\nPart-Time Income: $" + strPartTimeIncome + "\nRetirement Age: " +
                    strRetirementAge + " Years" + "\nMoney in Savings: $" + strMoneyInSavings
                    + "\nRetiring In: " + strRetiringIn + " Years" + "\nNeed to Save: $"
                    + strNeedToSave + "\nNeeded for Retirement: $" + strNeededForRetirement
                    + "\nNeeded per Year: $" + strNeededPerYear);
            startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
        }
    }
    public void saveInfo(View v)
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "Retirement.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strReqiuiredIncome = String.format("%1.2f", requiredIncome);
            String strSocialSecurity = String.format("%1.2f", socialSecurity);
            String strPension = String.format("%1.2f", pension);
            String strPartTimeIncome= String.format("%1.2f", partTimeIncome);
            String strRetirementAge = String.format("%1.0f", anticipatedRetirementAge);
            String strMoneyInSavings = String.format("%1.2f", moneyInSavings);
            String strRetiringIn = String.format("%1.0f", retiringIn);
            String strNeedToSave = String.format("%1.2f", needToSave);
            String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
            String strNeededPerYear = String.format("%1.2f", neededPerYear);
            writer.write(" " +  dateFormat.format(date) + " Required Income: $" + strReqiuiredIncome
                    + " Social Security: $" + strSocialSecurity + " Pension: $" + strPension +
                    " Part-Time Income: $" + strPartTimeIncome + " Retirement Age: " +
                    strRetirementAge + " Years" + "\nMoney in Savings: $" + strMoneyInSavings
                    + " Retiring In: " + strRetiringIn + " Years" + " Need to Save: $"
                    + strNeedToSave + " Needed for Retirement: $" + strNeededForRetirement
                    + " Needed per Year: $" + strNeededPerYear);
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context) (this),
                    new String[]{traceFile.toString()},
                    null,
                    null);

            Toast.makeText(getApplicationContext(), "Info has been saved.",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Log.e("com.mycompany.FileTest", "Unable to write to the Retirement.txt file.");
        }
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

