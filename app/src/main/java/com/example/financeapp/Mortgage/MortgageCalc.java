
package com.example.financeapp.Mortgage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class MortgageCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText edtHousePrice, edtDownPayment, edtInterestRate, edtTerm;
    TextView textBiMonthlyPayment, textMonthlyPayment, textAmtToInterest, textAmtToPrincipal;
    DrawerLayout drawerLayout;
    double housePrice = 0;
    double downPayment = 0;
    double interestRate = 0;
    double term = 0;
    double totalMortgage = 0;
    double biMonthlyPayment = 0;
    private String rate1;
    private String[] rates = {"INR","USD","AUS","EUR","JPY","RUB","SGD","LKR","BDT"};
    double monthlyPayment = 0;
    double amountToInterest = 0;
    double amountToPrincipal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mortgage_calc);

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

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                        double housePrice1 = Double.parseDouble(edtHousePrice.getText().toString());
                        double downPayment1 = Double.parseDouble(edtDownPayment.getText().toString());
                        double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
                        double term1 = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = housePrice1;
                        downPayment = downPayment1;
                        interestRate = interestRate1;
                        term = term1;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMonthly = String.format("%1.2f", biMonthlyPayment);
                        String strBiMonthly = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInterestAmt = String.format("%1.2f",amountToInterest);
                        String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("₹ " + strMonthly);
                        textMonthlyPayment.setText("₹ " + strBiMonthly);
                        textAmtToInterest.setText("₹ " + strInterestAmt);
                        textAmtToPrincipal.setText("₹ " + strPrincipalAmt);
                        break;
                    case "USD":
                        double housePrice2 = Double.parseDouble(edtHousePrice.getText().toString());
                        double downPayment2 = Double.parseDouble(edtDownPayment.getText().toString());
                        double interestRate2 = Double.parseDouble(edtInterestRate.getText().toString());
                        double term2 = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = housePrice2*0.013;
                        downPayment = downPayment2*0.013;
                        interestRate = interestRate2;
                        term = term2;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMonthly1 = String.format("%1.2f", biMonthlyPayment);
                        String strBiMonthly1 = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInterestAmt1 = String.format("%1.2f",amountToInterest);
                        String strPrincipalAmt1 = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("$ " + strMonthly1);
                        textMonthlyPayment.setText("$ " + strBiMonthly1);
                        textAmtToInterest.setText("$ " + strInterestAmt1);
                        textAmtToPrincipal.setText("$ " + strPrincipalAmt1);
                        break;
                    case "AUS":
                        double housePric = Double.parseDouble(edtHousePrice.getText().toString());
                        double downPaymen = Double.parseDouble(edtDownPayment.getText().toString());
                        double interestRat = Double.parseDouble(edtInterestRate.getText().toString());
                        double ter = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = housePric*0.019;
                        downPayment = downPaymen*0.019;
                        interestRate = interestRat;
                        term = ter;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMonth = String.format("%1.2f", biMonthlyPayment);
                        String strBiMon = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInterestAm = String.format("%1.2f",amountToInterest);
                        String strPrincipalA = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("A$ " + strMonth);
                        textMonthlyPayment.setText("A$ " + strBiMon);
                        textAmtToInterest.setText("A$ " + strInterestAm);
                        textAmtToPrincipal.setText("A$ " + strPrincipalA);
                        break;
                    case "EUR":
                        double housePr = Double.parseDouble(edtHousePrice.getText().toString());
                        double downPaym = Double.parseDouble(edtDownPayment.getText().toString());
                        double interestR = Double.parseDouble(edtInterestRate.getText().toString());
                        double terscsc = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = housePr*0.013;
                        downPayment = downPaym*0.013;
                        interestRate = interestR;
                        term = terscsc;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMon = String.format("%1.2f", biMonthlyPayment);
                        String strBiMo = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInterest = String.format("%1.2f",amountToInterest);
                        String strPrincip = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("€ " + strMon);
                        textMonthlyPayment.setText("€ " + strBiMo);
                        textAmtToInterest.setText("€ " + strInterest);
                        textAmtToPrincipal.setText("€ " + strPrincip);
                        break;
                    case "JPY":
                        double house = Double.parseDouble(edtHousePrice.getText().toString());
                        double downPa = Double.parseDouble(edtDownPayment.getText().toString());
                        double interes = Double.parseDouble(edtInterestRate.getText().toString());
                        double tersc = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = house*1.79;
                        downPayment = downPa*1.79;
                        interestRate = interes;
                        term = tersc;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMo = String.format("%1.2f", biMonthlyPayment);
                        String strBiM = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strIntere = String.format("%1.2f",amountToInterest);
                        String strPrinci = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("¥ " + strMo);
                        textMonthlyPayment.setText("¥ " + strBiM);
                        textAmtToInterest.setText("¥ " + strIntere);
                        textAmtToPrincipal.setText("¥ " + strPrinci);
                        break;
                    case "RUB":
                        double hous = Double.parseDouble(edtHousePrice.getText().toString());
                        double down = Double.parseDouble(edtDownPayment.getText().toString());
                        double inter = Double.parseDouble(edtInterestRate.getText().toString());
                        double tc = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = hous*0.76;
                        downPayment = down*0.76;
                        interestRate = inter;
                        term = tc;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strM = String.format("%1.2f", biMonthlyPayment);
                        String strBi = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInte = String.format("%1.2f",amountToInterest);
                        String strPrin = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("₽ " + strM);
                        textMonthlyPayment.setText("₽ " + strBi);
                        textAmtToInterest.setText("₽ " + strInte);
                        textAmtToPrincipal.setText("₽ " + strPrin);
                        break;
                    case "SGD":
                        double hou = Double.parseDouble(edtHousePrice.getText().toString());
                        double dow = Double.parseDouble(edtDownPayment.getText().toString());
                        double inte = Double.parseDouble(edtInterestRate.getText().toString());
                        double tckk = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = hou*0.018;
                        downPayment = dow*0.018;
                        interestRate = inte;
                        term = tckk;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMmmm = String.format("%1.2f", biMonthlyPayment);
                        String strBimmm = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strIntekk = String.format("%1.2f",amountToInterest);
                        String strPrinll = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("S$ " + strMmmm);
                        textMonthlyPayment.setText("S$ " + strBimmm);
                        textAmtToInterest.setText("S$ " + strIntekk);
                        textAmtToPrincipal.setText("S$ " + strPrinll);
                        break;
                    case "LKR":
                        double ho = Double.parseDouble(edtHousePrice.getText().toString());
                        double dowww = Double.parseDouble(edtDownPayment.getText().toString());
                        double in = Double.parseDouble(edtInterestRate.getText().toString());
                        double tck = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = ho*4.51;
                        downPayment = dowww*4.51;
                        interestRate = in;
                        term = tck;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMmm = String.format("%1.2f", biMonthlyPayment);
                        String strBimm = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strIntek = String.format("%1.2f",amountToInterest);
                        String strPrinl = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("රු, " + strMmm);
                        textMonthlyPayment.setText("රු, " + strBimm);
                        textAmtToInterest.setText("රු, " + strIntek);
                        textAmtToPrincipal.setText("රු, " + strPrinl);
                        break;
                    case "BDT":
                        double h = Double.parseDouble(edtHousePrice.getText().toString());
                        double doww = Double.parseDouble(edtDownPayment.getText().toString());
                        double innn = Double.parseDouble(edtInterestRate.getText().toString());
                        double tckkk = Double.parseDouble(edtTerm.getText().toString());
                        housePrice = h*1.31;
                        downPayment = doww*1.31;
                        interestRate = innn;
                        term = tckkk;
                        interestRate = interestRate * 0.01;
                        housePrice = housePrice - downPayment;
                        monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                        biMonthlyPayment = monthlyPayment / 2;
                        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        String strMm = String.format("%1.2f", biMonthlyPayment);
                        String strBim = String.format("%1.2f" , monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        String strInt = String.format("%1.2f",amountToInterest);
                        String strPri = String.format("%1.2f", amountToPrincipal);
                        textBiMonthlyPayment.setText("৳ " + strMm);
                        textMonthlyPayment.setText("৳ " + strBim);
                        textAmtToInterest.setText("৳ " + strInt);
                        textAmtToPrincipal.setText("৳ " + strPri);
                        break;


                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                double housePrice1 = Double.parseDouble(edtHousePrice.getText().toString());
                double downPayment1 = Double.parseDouble(edtDownPayment.getText().toString());
                double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
                double term1 = Double.parseDouble(edtTerm.getText().toString());
                housePrice = housePrice1;
                downPayment = downPayment1;
                interestRate = interestRate1;
                term = term1;
                interestRate = interestRate * 0.01;
                housePrice = housePrice - downPayment;
                monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
                biMonthlyPayment = monthlyPayment / 2;
                textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
                textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
                textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
                textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                String strMonthly = String.format("%1.2f", biMonthlyPayment);
                String strBiMonthly = String.format("%1.2f" , monthlyPayment);
                amountToInterest = monthlyPayment * interestRate;
                amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                String strInterestAmt = String.format("%1.2f",amountToInterest);
                String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
                textBiMonthlyPayment.setText("₹ " + strMonthly);
                textMonthlyPayment.setText("₹ " + strBiMonthly);
                textAmtToInterest.setText("₹ " + strInterestAmt);
                textAmtToPrincipal.setText("₹ " + strPrincipalAmt);


            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(MortgageCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(MortgageCalc.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(MortgageCalc.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(MortgageCalc.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateLogic(View v)
    {
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        spinnerInflater();
        String text1 = edtHousePrice.getText().toString().trim();
        String text2 = edtDownPayment.getText().toString().trim();
        String text3 = edtInterestRate.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter house price.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter down payment.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double housePrice1 = Double.parseDouble(edtHousePrice.getText().toString());
            double downPayment1 = Double.parseDouble(edtDownPayment.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            housePrice = housePrice1;
            downPayment = downPayment1;
            interestRate = interestRate1;
            term = term1;
            interestRate = interestRate * 0.01;
            housePrice = housePrice - downPayment;
            monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
            biMonthlyPayment = monthlyPayment / 2;
        }
        printResults();
        keyboardDismiss();
    }

    public void clearLogic(View v)
    {
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);

        edtHousePrice.setText("");
        edtDownPayment.setText("");
        edtInterestRate.setText("");
        edtTerm.setText("");

        textBiMonthlyPayment.setText("");
        textMonthlyPayment.setText("");
        textAmtToInterest.setText("");
        textAmtToPrincipal.setText("");

        keyboardDismiss();
    }

    private void printResults()
    {
        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        String strMonthly = String.format("%1.2f", biMonthlyPayment);
        String strBiMonthly = String.format("%1.2f" , monthlyPayment);
        amountToInterest = monthlyPayment * interestRate;
        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
        String strInterestAmt = String.format("%1.2f",amountToInterest);
        String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
        textBiMonthlyPayment.setText("Rs. " + strMonthly);
        textMonthlyPayment.setText("Rs. " + strBiMonthly);
        textAmtToInterest.setText("Rs. " + strInterestAmt);
        textAmtToPrincipal.setText("Rs. " + strPrincipalAmt);

    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDownPayment.getWindowToken(), 0);
    }

    public void send(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        String text1 = edtHousePrice.getText().toString().trim();
        String text2 = edtDownPayment.getText().toString().trim();
        String text3 = edtInterestRate.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter house price.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter down payment.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strDownPayment = String.format("%1.2f", downPayment);
            String strHousePrice = String.format("%1.2f", housePrice);
            String strMonthly = String.format("%1.2f", biMonthlyPayment);
            String strBiMonthly = String.format("%1.2f" , monthlyPayment);
            String strInterestAmt = String.format("%1.2f",amountToInterest);
            String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Mortgage Calculation Results");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "House Price: $" + strHousePrice +
                    "\nDown Payment: $" + strDownPayment + "\nInterest Rate: " + interestRate * 100 + "% "
                    + "\nTerm: " + term + " years " + "\nBiMonthly Payment: $" + strBiMonthly + "\nMonthly Payment: $"
                    + strMonthly + "\nAmount to Interest: $" + strInterestAmt
                    + "\nAmount to Principal: $" + strPrincipalAmt);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "Mortgage.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strDownPayment = String.format("%1.2f", downPayment);
            String strHousePrice = String.format("%1.2f", housePrice);
            String strMonthly = String.format("%1.2f", biMonthlyPayment);
            String strBiMonthly = String.format("%1.2f" , monthlyPayment);
            String strInterestAmt = String.format("%1.2f",amountToInterest);
            String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
            writer.write( " " + dateFormat.format(date) + " House Price: $" + strHousePrice +
                    " Down Payment: $" + strDownPayment + " Interest Rate: " + interestRate * 100 + "% "
                    + " Term: " + term + " years " + " BiMonthly Payment: $" + strBiMonthly + " Monthly Payment: $"
                    + strMonthly + " Amount to Interest: $" + strInterestAmt
                    + " Amount to Principal: $" + strPrincipalAmt);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the Mortgage.txt file.");
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

