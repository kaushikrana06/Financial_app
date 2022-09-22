package com.example.financeapp.Lease;


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


public class AutoLeaseCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    final double CASALESTAX = 0.0975;
    private String rate1;
    private String[] rates = {"INR","USD","AUS","EUR","JPY","RUB","SGD","LKR","BDT"};
    DrawerLayout drawerLayout;
    EditText edtMsrp, edtTermOfLease, edtInterestRate, edtInvoicePrice, edtResidualValue;
    TextView textMonthlyPayment, textMonthlyPaymentInCa, textAmtToPrincipal,
            textAmountToInterest, textTotalForLease;
    double msrp = 0, moneyFactor = 0, termOfLease = 0, residualValue = 0,
            monthlyPayment = 0, monthlyPaymentInCa = 0, amtToPrincipal = 0,
            amtToInterest = 0, totalForLease = 0, invoicePrice = 0, interestRate = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_lease_calc);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spin=findViewById(R.id.spinn);
        spin.setVisibility(View.VISIBLE);
        TextView tx= findViewById(R.id.ttt);
        tx.setVisibility(View.VISIBLE);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, rates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

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
                switch (rate1){
                    case "INR":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);
                        double msrp1 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue1 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease1 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice1 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp1;
                        residualValue = residualValue1;
                        termOfLease = termOfLease1;
                        interestRate = interestRate1;
                        invoicePrice = invoicePrice1;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar = msrp * residualValue;
                        double depreciation = invoicePrice - residualValueOfCar;
                        double monthlyDepreciationPayment = depreciation / termOfLease;
                        double numTimesMoneyFactor = invoicePrice + residualValueOfCar;
                        double interestPayment = numTimesMoneyFactor * moneyFactor;
                        monthlyPayment = interestPayment + monthlyDepreciationPayment;
                        monthlyPaymentInCa = monthlyPayment + (monthlyPayment * CASALESTAX);
                        amtToInterest = monthlyPayment * (interestRate * 100) / 12;
                        amtToPrincipal = monthlyPayment - amtToInterest;
                        totalForLease = monthlyPayment * termOfLease;
                        String strMonthly = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest = String.format("%1.2f", amtToInterest);
                        String strTotalForLease = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: ₹ " + strMonthly);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: ₹" + strMonthlyInCa);
                        textAmtToPrincipal.setText("Amount to Principal: ₹" + strAmtToPrincipal);
                        textAmountToInterest.setText("Amount to Interest: ₹" + strAmtToInterest);
                        textTotalForLease.setText("Total for Lease: ₹" + strTotalForLease);

                        break;
                    case "USD":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp2 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue2 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease2 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate2= Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice2 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp2;
                        residualValue = residualValue2;
                        termOfLease = termOfLease2 ;
                        interestRate = interestRate2;
                        invoicePrice = invoicePrice2;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar1 = msrp * residualValue;
                        double depreciation1 = invoicePrice - residualValueOfCar1;
                        double monthlyDepreciationPayment1 = depreciation1 / termOfLease;
                        double numTimesMoneyFactor1 = invoicePrice + residualValueOfCar1;
                        double interestPayment1 = numTimesMoneyFactor1 * moneyFactor;
                        monthlyPayment = (interestPayment1 + monthlyDepreciationPayment1)*0.013;
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX))*0.013;
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12)*0.013;
                        amtToPrincipal = (monthlyPayment - amtToInterest)*0.013;
                        totalForLease = (monthlyPayment * termOfLease)*0.013;
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly1 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa1 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal1= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest1 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease1 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: $" + strMonthly1);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: $" + strMonthlyInCa1);
                        textAmtToPrincipal.setText("Amount to Principal: $" + strAmtToPrincipal1);
                        textAmountToInterest.setText("Amount to Interest: $" + strAmtToInterest1);
                        textTotalForLease.setText("Total for Lease: $" + strTotalForLease1);
                        break;
                    case "AUS":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp3 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue3 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease3 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate3 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice3 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp3;
                        residualValue = residualValue3;
                        termOfLease = termOfLease3 ;
                        interestRate = interestRate3;
                        invoicePrice = invoicePrice3;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar2 = (msrp * residualValue)*0.019;
                        double depreciation2 = (invoicePrice - residualValueOfCar2)*0.019;
                        double monthlyDepreciationPayment2 = (depreciation2 / termOfLease)*0.019;
                        double numTimesMoneyFactor2 = (invoicePrice + residualValueOfCar2)*0.019;
                        double interestPayment2 = (numTimesMoneyFactor2 * moneyFactor)*0.019;
                        monthlyPayment = (interestPayment2 + monthlyDepreciationPayment2);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly2 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa2 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal2= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest2 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease2 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: A$" + strMonthly2);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: A$" + strMonthlyInCa2);
                        textAmtToPrincipal.setText("Amount to Principal: A$" + strAmtToPrincipal2);
                        textAmountToInterest.setText("Amount to Interest: A$" + strAmtToInterest2);
                        textTotalForLease.setText("Total for Lease: A$" + strTotalForLease2);
                        break;
                    case "EUR":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp4 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue4 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease4 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate4 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice4 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp4;
                        residualValue = residualValue4;
                        termOfLease = termOfLease4 ;
                        interestRate = interestRate4;
                        invoicePrice = invoicePrice4;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar3 = (msrp * residualValue)*0.013;
                        double depreciation3 = (invoicePrice - residualValueOfCar3)*0.013;
                        double monthlyDepreciationPayment3 = (depreciation3 / termOfLease)*0.013;
                        double numTimesMoneyFactor3 = (invoicePrice + residualValueOfCar3)*0.013;
                        double interestPayment3 = (numTimesMoneyFactor3 * moneyFactor)*0.013;
                        monthlyPayment = (interestPayment3 + monthlyDepreciationPayment3);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly3 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa3 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal3= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest3 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease3 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: €" + strMonthly3);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: €" + strMonthlyInCa3);
                        textAmtToPrincipal.setText("Amount to Principal: €" + strAmtToPrincipal3);
                        textAmountToInterest.setText("Amount to Interest: €" + strAmtToInterest3);
                        textTotalForLease.setText("Total for Lease: €" + strTotalForLease3);
                        break;
                    case "JPY":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp5 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue5 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease5 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate5 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice5 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp5;
                        residualValue = residualValue5;
                        termOfLease = termOfLease5 ;
                        interestRate = interestRate5;
                        invoicePrice = invoicePrice5;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar4 = (msrp * residualValue)*1.79;
                        double depreciation4 = (invoicePrice - residualValueOfCar4)*1.79;
                        double monthlyDepreciationPayment4 = (depreciation4 / termOfLease)*1.79;
                        double numTimesMoneyFactor4 = (invoicePrice + residualValueOfCar4)*1.79;
                        double interestPayment4 = (numTimesMoneyFactor4 * moneyFactor)*1.79;
                        monthlyPayment = (interestPayment4 + monthlyDepreciationPayment4);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly4 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa4 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal4= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest4 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease4 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: ¥" + strMonthly4);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: ¥" + strMonthlyInCa4);
                        textAmtToPrincipal.setText("Amount to Principal: ¥" + strAmtToPrincipal4);
                        textAmountToInterest.setText("Amount to Interest: ¥" + strAmtToInterest4);
                        textTotalForLease.setText("Total for Lease: ¥" + strTotalForLease4);
                        break;
                    case "RUB":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp6 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue6 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease6 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate6 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice6 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp6;
                        residualValue = residualValue6;
                        termOfLease = termOfLease6 ;
                        interestRate = interestRate6;
                        invoicePrice = invoicePrice6;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar5 = (msrp * residualValue)*0.76;
                        double depreciation5 = (invoicePrice - residualValueOfCar5)*0.76;
                        double monthlyDepreciationPayment5 = (depreciation5 / termOfLease)*0.76;
                        double numTimesMoneyFactor5 = (invoicePrice + residualValueOfCar5)*0.76;
                        double interestPayment5 = (numTimesMoneyFactor5 * moneyFactor)*0.76;
                        monthlyPayment = (interestPayment5 + monthlyDepreciationPayment5);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly5 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa5 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal5= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest5 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease5 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: ₽ " + strMonthly5);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: ₽ " + strMonthlyInCa5);
                        textAmtToPrincipal.setText("Amount to Principal: ₽ " + strAmtToPrincipal5);
                        textAmountToInterest.setText("Amount to Interest: ₽ " + strAmtToInterest5);
                        textTotalForLease.setText("Total for Lease: ₽ " + strTotalForLease5);
                        break;
                    case "SGD":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp7 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue7 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease7 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate7 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice7 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp7;
                        residualValue = residualValue7;
                        termOfLease = termOfLease7 ;
                        interestRate = interestRate7;
                        invoicePrice = invoicePrice7;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar6 = (msrp * residualValue)*0.018;
                        double depreciation6 = (invoicePrice - residualValueOfCar6)*0.018;
                        double monthlyDepreciationPayment6 = (depreciation6 / termOfLease)*0.018;
                        double numTimesMoneyFactor6 = (invoicePrice + residualValueOfCar6)*0.018;
                        double interestPayment6 = (numTimesMoneyFactor6 * moneyFactor)*0.018;
                        monthlyPayment = (interestPayment6 + monthlyDepreciationPayment6);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly6 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa6 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal6= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest6 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease6 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: S$" + strMonthly6);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: S$" + strMonthlyInCa6);
                        textAmtToPrincipal.setText("Amount to Principal: S$" + strAmtToPrincipal6);
                        textAmountToInterest.setText("Amount to Interest: S$" + strAmtToInterest6);
                        textTotalForLease.setText("Total for Lease: S$" + strTotalForLease6);
                        break;
                    case "LKR":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp8 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue8 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease8 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate8 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice8 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp8;
                        residualValue = residualValue8;
                        termOfLease = termOfLease8 ;
                        interestRate = interestRate8;
                        invoicePrice = invoicePrice8;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar7 = (msrp * residualValue)*4.51;
                        double depreciation7 = (invoicePrice - residualValueOfCar7)*4.51;
                        double monthlyDepreciationPayment7 = (depreciation7 / termOfLease)*4.51;
                        double numTimesMoneyFactor7 = (invoicePrice + residualValueOfCar7)*4.51;
                        double interestPayment7 = (numTimesMoneyFactor7 * moneyFactor)*4.51;
                        monthlyPayment = (interestPayment7 + monthlyDepreciationPayment7);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly7 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa7 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal7= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest7 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease7 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: රු," + strMonthly7);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: රු," + strMonthlyInCa7);
                        textAmtToPrincipal.setText("Amount to Principal: රු," + strAmtToPrincipal7);
                        textAmountToInterest.setText("Amount to Interest: රු," + strAmtToInterest7);
                        textTotalForLease.setText("Total for Lease: රු," + strTotalForLease7);
                        break;
                    case "BDT":
                        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
                        double msrp9 = Double.parseDouble(edtMsrp.getText().toString());
                        double residualValue9 = Double.parseDouble(edtResidualValue.getText().toString());
                        double termOfLease9 = Double.parseDouble(edtTermOfLease.getText().toString());
                        double interestRate9 = Double.parseDouble(edtInterestRate.getText().toString());
                        double invoicePrice9 = Double.parseDouble(edtInvoicePrice.getText().toString());

                        msrp = msrp9;
                        residualValue = residualValue9;
                        termOfLease = termOfLease9 ;
                        interestRate = interestRate9;
                        invoicePrice = invoicePrice9;

                        residualValue *= 0.01;
                        moneyFactor = interestRate / 2400;
                        interestRate *= 0.01;
                        double residualValueOfCar8 = (msrp * residualValue)*1.31;
                        double depreciation8 = (invoicePrice - residualValueOfCar8)*1.31;
                        double monthlyDepreciationPayment8 = (depreciation8 / termOfLease)*1.31;
                        double numTimesMoneyFactor8 = (invoicePrice + residualValueOfCar8)*1.31;
                        double interestPayment8 = (numTimesMoneyFactor8 * moneyFactor)*1.31;
                        monthlyPayment = (interestPayment8 + monthlyDepreciationPayment8);
                        monthlyPaymentInCa = (monthlyPayment + (monthlyPayment * CASALESTAX));
                        amtToInterest = (monthlyPayment * (interestRate * 100) / 12);
                        amtToPrincipal = (monthlyPayment - amtToInterest);
                        totalForLease = (monthlyPayment * termOfLease);
                        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

                        String strMonthly8 = String.format("%1.2f", monthlyPayment);
                        String strMonthlyInCa8 = String.format("%1.2f", monthlyPaymentInCa);
                        String strAmtToPrincipal8= String.format("%1.2f", amtToPrincipal);
                        String strAmtToInterest8 = String.format("%1.2f", amtToInterest);
                        String strTotalForLease8 = String.format("%1.2f", totalForLease);
                        textMonthlyPayment.setText("Monthly Payment: ৳" + strMonthly8);
                        textMonthlyPaymentInCa.setText("Monthly Payment in CA: ৳" + strMonthlyInCa8);
                        textAmtToPrincipal.setText("Amount to Principal: ৳" + strAmtToPrincipal8);
                        textAmountToInterest.setText("Amount to Interest: ৳" + strAmtToInterest8);
                        textTotalForLease.setText("Total for Lease: ৳" + strTotalForLease8);
                        break;



                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                rate1 = adapterView.getItemAtPosition(0).toString().trim();
                edtMsrp = (EditText)findViewById(R.id.edtMsrp);
                edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
                edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
                edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
                edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

                textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
                textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
                textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
                textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
                textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);
                double msrp1 = Double.parseDouble(edtMsrp.getText().toString());
                double residualValue1 = Double.parseDouble(edtResidualValue.getText().toString());
                double termOfLease1 = Double.parseDouble(edtTermOfLease.getText().toString());
                double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
                double invoicePrice1 = Double.parseDouble(edtInvoicePrice.getText().toString());

                msrp = msrp1;
                residualValue = residualValue1;
                termOfLease = termOfLease1;
                interestRate = interestRate1;
                invoicePrice = invoicePrice1;

                residualValue *= 0.01;
                moneyFactor = interestRate / 2400;
                interestRate *= 0.01;
                double residualValueOfCar = msrp * residualValue;
                double depreciation = invoicePrice - residualValueOfCar;
                double monthlyDepreciationPayment = depreciation / termOfLease;
                double numTimesMoneyFactor = invoicePrice + residualValueOfCar;
                double interestPayment = numTimesMoneyFactor * moneyFactor;
                monthlyPayment = interestPayment + monthlyDepreciationPayment;
                monthlyPaymentInCa = monthlyPayment + (monthlyPayment * CASALESTAX);
                amtToInterest = monthlyPayment * (interestRate * 100) / 12;
                amtToPrincipal = monthlyPayment - amtToInterest;
                totalForLease = monthlyPayment * termOfLease;
                String strMonthly = String.format("%1.2f", monthlyPayment);
                String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
                String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
                String strAmtToInterest = String.format("%1.2f", amtToInterest);
                String strTotalForLease = String.format("%1.2f", totalForLease);
                textMonthlyPayment.setText("Monthly Payment: ₹" + strMonthly);
                textMonthlyPaymentInCa.setText("Monthly Payment in CA: ₹" + strMonthlyInCa);
                textAmtToPrincipal.setText("Amount to Principal: ₹" + strAmtToPrincipal);
                textAmountToInterest.setText("Amount to Interest: ₹" + strAmtToInterest);
                textTotalForLease.setText("Total for Lease: ₹" + strTotalForLease);

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(AutoLeaseCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(AutoLeaseCalc.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(AutoLeaseCalc.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(AutoLeaseCalc.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateLogic(View v)
    {
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);

        String text1 = edtMsrp.getText().toString().trim();
        String text2 = edtResidualValue.getText().toString().trim();
        String text3 = edtTermOfLease.getText().toString().trim();
        String text4 = edtInterestRate.getText().toString().trim();
        String text5 = edtInvoicePrice.getText().toString().trim();
        spinnerInflater();
        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter MSRP.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter residual value.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term of lease.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter invoice price.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double msrp1 = Double.parseDouble(edtMsrp.getText().toString());
            double residualValue1 = Double.parseDouble(edtResidualValue.getText().toString());
            double termOfLease1 = Double.parseDouble(edtTermOfLease.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double invoicePrice1 = Double.parseDouble(edtInvoicePrice.getText().toString());

            msrp = msrp1;
            residualValue = residualValue1;
            termOfLease = termOfLease1;
            interestRate = interestRate1;
            invoicePrice = invoicePrice1;

            residualValue *= 0.01;
            moneyFactor = interestRate / 2400;
            interestRate *= 0.01;
            double residualValueOfCar = msrp * residualValue;
            double depreciation = invoicePrice - residualValueOfCar;
            double monthlyDepreciationPayment = depreciation / termOfLease;
            double numTimesMoneyFactor = invoicePrice + residualValueOfCar;
            double interestPayment = numTimesMoneyFactor * moneyFactor;
            monthlyPayment = interestPayment + monthlyDepreciationPayment;
            monthlyPaymentInCa = monthlyPayment + (monthlyPayment * CASALESTAX);
            amtToInterest = monthlyPayment * (interestRate * 100) / 12;
            amtToPrincipal = monthlyPayment - amtToInterest;
            totalForLease = monthlyPayment * termOfLease;
        }
        printResults();
        dismissKeyboard();
    }

    public void clearLogic(View v)
    {
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease = (TextView)findViewById(R.id.textTotalForLease);

        edtMsrp.setText("");
        edtInvoicePrice.setText("");
        edtTermOfLease.setText("");
        edtInterestRate.setText("");
        edtResidualValue.setText("");

        textMonthlyPayment.setText("Monthly Payment: $");
        textMonthlyPaymentInCa.setText("Monthly Payment in CA: $");
        textAmtToPrincipal.setText("Amount to Principal: $");
        textAmountToInterest.setText("Amount to Interest: $");
        textTotalForLease.setText("Total For Lease: $");

        dismissKeyboard();

    }

    public void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtMsrp.getWindowToken(), 0);
    }
    public void printResults()
    {
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

        String strMonthly = String.format("%1.2f", monthlyPayment);
        String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
        String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
        String strAmtToInterest = String.format("%1.2f", amtToInterest);
        String strTotalForLease = String.format("%1.2f", totalForLease);
        textMonthlyPayment.setText("Monthly Payment: $" + strMonthly);
        textMonthlyPaymentInCa.setText("Monthly Payment in CA: $" + strMonthlyInCa);
        textAmtToPrincipal.setText("Amount to Principal: $" + strAmtToPrincipal);
        textAmountToInterest.setText("Amount to Interest: $" + strAmtToInterest);
        textTotalForLease.setText("Total for Lease: $" + strTotalForLease);

    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);



        String text1 = edtMsrp.getText().toString().trim();
        String text2 = edtResidualValue.getText().toString().trim();
        String text3 = edtTermOfLease.getText().toString().trim();
        String text4 = edtInterestRate.getText().toString().trim();
        String text5 = edtInvoicePrice.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter MSRP.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter residual value.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term of lease.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter invoice price.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Auto Lease Calculation Info");
            String strMsrp = String.format("%1.2f", msrp);
            String strResidualValue = String.format("%1.1f", residualValue * 100);
            String strTerm = String.format("%1.0f", termOfLease);
            String strInterestRate = String.format("%1.1f", interestRate * 100);
            String strInvoicePrice = String.format("%1.2f", invoicePrice);
            String strMonthly = String.format("%1.2f", monthlyPayment);
            String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
            String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
            String strAmtToInterest = String.format("%1.2f", amtToInterest);
            String strTotalForLease = String.format("%1.2f", totalForLease);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "MSRP: $" + strMsrp +
                    "\n Residual Value: " + strResidualValue + "%"+ "\nTerm: " + strTerm + " months"
                    + "\n Interest Rate: " + strInterestRate + "%" + "\n Invoice Price: $" + strInvoicePrice
                    + "\n Monthly Payment: $" + strMonthly + "\n Monthly Payment in CA: $" + strMonthlyInCa
                    + "\n Amount to Principal: $" + strAmtToPrincipal + "\n Amount to Interest: $"
                    + strAmtToInterest + " \n Total for Lease: $" + strTotalForLease);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "AutoLease.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strMsrp = String.format("%1.2f", msrp);
            String strResidualValue = String.format("%1.1f", residualValue * 100);
            String strTerm = String.format("%1.0f", termOfLease);
            String strInterestRate = String.format("%1.1f", interestRate * 100);
            String strInvoicePrice = String.format("%1.2f", invoicePrice);
            String strMonthly = String.format("%1.2f", monthlyPayment);
            String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
            String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
            String strAmtToInterest = String.format("%1.2f", amtToInterest);
            String strTotalForLease = String.format("%1.2f", totalForLease);
            writer.write(" " +  dateFormat.format(date) + " MSRP: $" + strMsrp +
                    " Residual Value: " + strResidualValue + "%"+ " Term: " + strTerm + " months"
                    + " Interest Rate: " + strInterestRate + "%" + " Invoice Price: $" + strInvoicePrice
                    + " Monthly Payment: $" + strMonthly + " Monthly Payment in CA: $" + strMonthlyInCa
                    + " Amount to Principal: $" + strAmtToPrincipal + " Amount to Interest: $"
                    + strAmtToInterest + "  Total for Lease: $" + strTotalForLease);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the AutoLease.txt file.");
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

