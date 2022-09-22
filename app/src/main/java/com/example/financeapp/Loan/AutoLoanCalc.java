package com.example.financeapp.Loan;

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


public class AutoLoanCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText edtLoanAmount, edtTerm, edtRate;
    DrawerLayout drawerLayout;
    private String rate1;
    private String[] rates = {"INR","USD","AUS","EUR","JPY","RUB","SGD","LKR","BDT"};
    TextView textMonthlyPayment, textAmountTowardPrincipal, textAmountTowardInterest,
            textTotalForLoan, textTotalToInterest;

    double principal = 0, interestRate = 0, term = 0, monthlyPayment = 0,
            totalForLoan = 0, totalToInterest = 0, amountToInterest = 0, amountToPrincipal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auto_loan_calc);
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
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal1 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term1 = Double.parseDouble(edtTerm.getText().toString());
                        double rate1 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal1;
                        term = term1;
                        interestRate = rate1;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: ₹ " + strMonthly);
                        textAmountTowardInterest.setText("Amount to Interest: ₹ " + strAmountToInterest);
                        textAmountTowardPrincipal.setText("Amount to Principal: ₹ " + strAmountToPrincipal);
                        textTotalToInterest.setText("Total to Interest: ₹" + strTotalToInterest);
                        textTotalForLoan.setText("Total for Loan: ₹ " + strTotalForLoan);
                        break;
                    case "USD":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal2 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term2 = Double.parseDouble(edtTerm.getText().toString());
                        double rate2 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal2*0.013;
                        term = term2;
                        interestRate = rate2;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly1 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest1 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal1 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest1 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan1 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: $ " + strMonthly1);
                        textAmountTowardInterest.setText("Amount to Interest: $ " + strAmountToInterest1);
                        textAmountTowardPrincipal.setText("Amount to Principal: $ " + strAmountToPrincipal1);
                        textTotalToInterest.setText("Total to Interest: $" + strTotalToInterest1);
                        textTotalForLoan.setText("Total for Loan: $ " + strTotalForLoan1);
                        break;
                    case "AUS":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal3 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term3 = Double.parseDouble(edtTerm.getText().toString());
                        double rate3 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal3*0.019;
                        term = term3;
                        interestRate = rate3;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly2 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest2 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal2 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest2 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan2 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: A$ " + strMonthly2);
                        textAmountTowardInterest.setText("Amount to Interest: A$ " + strAmountToInterest2);
                        textAmountTowardPrincipal.setText("Amount to Principal: A$ " + strAmountToPrincipal2);
                        textTotalToInterest.setText("Total to Interest: A$" + strTotalToInterest2);
                        textTotalForLoan.setText("Total for Loan: A$ " + strTotalForLoan2);
                        break;
                    case "EUR":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal4 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term4 = Double.parseDouble(edtTerm.getText().toString());
                        double rate4 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal4*0.013;
                        term = term4;
                        interestRate = rate4;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly3 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest3 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal3 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest3 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan3 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: € " + strMonthly3);
                        textAmountTowardInterest.setText("Amount to Interest: € " + strAmountToInterest3);
                        textAmountTowardPrincipal.setText("Amount to Principal: € " + strAmountToPrincipal3);
                        textTotalToInterest.setText("Total to Interest: €" + strTotalToInterest3);
                        textTotalForLoan.setText("Total for Loan: € " + strTotalForLoan3);
                        break;
                    case "JPY":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal5 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term5 = Double.parseDouble(edtTerm.getText().toString());
                        double rate5 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal5*1.79;
                        term = term5;
                        interestRate = rate5;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly4 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest4 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal4 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest4 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan4 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: ¥ " + strMonthly4);
                        textAmountTowardInterest.setText("Amount to Interest: ¥ " + strAmountToInterest4);
                        textAmountTowardPrincipal.setText("Amount to Principal: ¥ " + strAmountToPrincipal4);
                        textTotalToInterest.setText("Total to Interest: ¥" + strTotalToInterest4);
                        textTotalForLoan.setText("Total for Loan: ¥ " + strTotalForLoan4);
                        break;
                    case "RUB":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal6 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term6 = Double.parseDouble(edtTerm.getText().toString());
                        double rate6 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal6*0.76;
                        term = term6;
                        interestRate = rate6;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly5 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest5 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal5 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest5 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan5 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: ₽ " + strMonthly5);
                        textAmountTowardInterest.setText("Amount to Interest: ₽ " + strAmountToInterest5);
                        textAmountTowardPrincipal.setText("Amount to Principal: ₽ " + strAmountToPrincipal5);
                        textTotalToInterest.setText("Total to Interest: ₽ " + strTotalToInterest5);
                        textTotalForLoan.setText("Total for Loan: ₽ " + strTotalForLoan5);
                        break;
                    case "SGD":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal7 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term7 = Double.parseDouble(edtTerm.getText().toString());
                        double rate7 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal7*0.018;
                        term = term7;
                        interestRate = rate7;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly6 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest6 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal6 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest6 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan6 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: S$ " + strMonthly6);
                        textAmountTowardInterest.setText("Amount to Interest: S$ " + strAmountToInterest6);
                        textAmountTowardPrincipal.setText("Amount to Principal: S$ " + strAmountToPrincipal6);
                        textTotalToInterest.setText("Total to Interest: S$ " + strTotalToInterest6);
                        textTotalForLoan.setText("Total for Loan: S$ " + strTotalForLoan6);
                        break;
                    case "LKR":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal8 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term8 = Double.parseDouble(edtTerm.getText().toString());
                        double rate8 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal8*4.51;
                        term = term8;
                        interestRate = rate8;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly7 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest7 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal7 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest7 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan7 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: රු, " + strMonthly7);
                        textAmountTowardInterest.setText("Amount to Interest: රු, " + strAmountToInterest7);
                        textAmountTowardPrincipal.setText("Amount to Principal: රු, " + strAmountToPrincipal7);
                        textTotalToInterest.setText("Total to Interest: රු, " + strTotalToInterest7);
                        textTotalForLoan.setText("Total for Loan: රු, " + strTotalForLoan7);
                        break;
                    case "BDT":
                        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                        double principal9 = Double.parseDouble(edtLoanAmount.getText().toString());
                        double term9 = Double.parseDouble(edtTerm.getText().toString());
                        double rate9 = Double.parseDouble(edtRate.getText().toString());
                        principal = principal9*1.31;
                        term = term9;
                        interestRate = rate9;
                        interestRate = interestRate * 0.01;
                        monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                        String strMonthly8 = String.format("%1.2f", monthlyPayment);
                        amountToInterest = monthlyPayment * interestRate;
                        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                        totalToInterest = monthlyPayment * term * 12 - principal;
                        totalForLoan = totalToInterest + principal;

                        String strAmountToInterest8 = String.format("%1.2f", amountToInterest);
                        String strAmountToPrincipal8 = String.format("%1.2f", amountToPrincipal);
                        String strTotalToInterest8 = String.format("%1.2f", totalToInterest);
                        String strTotalForLoan8 = String.format("%1.2f", totalForLoan);
                        textMonthlyPayment.setText("Monthly Payment: ৳ " + strMonthly8);
                        textAmountTowardInterest.setText("Amount to Interest: ৳ " + strAmountToInterest8);
                        textAmountTowardPrincipal.setText("Amount to Principal: ৳ " + strAmountToPrincipal8);
                        textTotalToInterest.setText("Total to Interest: ৳ " + strTotalToInterest8);
                        textTotalForLoan.setText("Total for Loan: ৳ " + strTotalForLoan8);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
                textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
                textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
                textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
                textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

                double principal1 = Double.parseDouble(edtLoanAmount.getText().toString());
                double term1 = Double.parseDouble(edtTerm.getText().toString());
                double rate1 = Double.parseDouble(edtRate.getText().toString());
                principal = principal1;
                term = term1;
                interestRate = rate1;
                interestRate = interestRate * 0.01;
                monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));

                String strMonthly = String.format("%1.2f", monthlyPayment);
                amountToInterest = monthlyPayment * interestRate;
                amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
                totalToInterest = monthlyPayment * term * 12 - principal;
                totalForLoan = totalToInterest + principal;

                String strAmountToInterest = String.format("%1.2f", amountToInterest);
                String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
                String strTotalToInterest = String.format("%1.2f", totalToInterest);
                String strTotalForLoan = String.format("%1.2f", totalForLoan);
                textMonthlyPayment.setText("Monthly Payment: ₹ " + strMonthly);
                textAmountTowardInterest.setText("Amount to Interest: ₹ " + strAmountToInterest);
                textAmountTowardPrincipal.setText("Amount to Principal: ₹ " + strAmountToPrincipal);
                textTotalToInterest.setText("Total to Interest: ₹" + strTotalToInterest);
                textTotalForLoan.setText("Total for Loan: ₹ " + strTotalForLoan);

            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(AutoLoanCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(AutoLoanCalc.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(AutoLoanCalc.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(AutoLoanCalc.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateLogic(View v)
    {
        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        spinnerInflater();
        String text1 = edtLoanAmount.getText().toString().trim();
        String text2 = edtTerm.getText().toString().trim();
        String text3 = edtRate.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter loan amount.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double principal1 = Double.parseDouble(edtLoanAmount.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            double rate1 = Double.parseDouble(edtRate.getText().toString());
            principal = principal1;
            term = term1;
            interestRate = rate1;
            interestRate = interestRate * 0.01;
            monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));
            //totalForLoan = (principal * Math.pow((1 + (interestRate / 12)),(12 * term)));
            //totalToInterest = totalForLoan - principal;
        }
        printResults();
        keyboardDismiss();

    }

    public void clearLogic(View v)
    {
        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

        edtLoanAmount.setText("");
        edtTerm.setText("");
        edtRate.setText("");
        textMonthlyPayment.setText("Monthly Payment: $");
        textTotalForLoan.setText("Total for Loan: $");
        textTotalToInterest.setText("Total to Interest: $");
        textAmountTowardPrincipal.setText("Amount toward Principal: $");
        textAmountTowardInterest.setText("Amount toward Interest: $");
        principal = 0;
        interestRate = 0;
        term = 0;
        monthlyPayment = 0;
        totalForLoan = 0;
        totalToInterest = 0;
        keyboardDismiss();
    }

    private void printResults()
    {
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

        String strMonthly = String.format("%1.2f", monthlyPayment);
        amountToInterest = monthlyPayment * interestRate;
        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
        totalToInterest = monthlyPayment * term * 12 - principal;
        totalForLoan = totalToInterest + principal;

        String strAmountToInterest = String.format("%1.2f", amountToInterest);
        String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
        String strTotalToInterest = String.format("%1.2f", totalToInterest);
        String strTotalForLoan = String.format("%1.2f", totalForLoan);
        textMonthlyPayment.setText("Monthly Payment: $" + strMonthly);
        textAmountTowardInterest.setText("Amount to Interest: $" + strAmountToInterest);
        textAmountTowardPrincipal.setText("Amount to Principal: $" + strAmountToPrincipal);
        textTotalToInterest.setText("Total to Interest: $" + strTotalToInterest);
        textTotalForLoan.setText("Total for Loan: $" + strTotalForLoan);
    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtTerm.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);
        String text1 = edtLoanAmount.getText().toString().trim();
        String text2 = edtTerm.getText().toString().trim();
        String text3 = edtRate.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter loan amount.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double tempInterest = interestRate * 100;
            emailIntent.setType("plain/text");
            String strAmountToInterest = String.format("%1.2f", amountToInterest);
            String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
            String strTotalToInterest = String.format("%1.2f", totalToInterest);
            String strTotalForLoan = String.format("%1.2f", totalForLoan);
            String strLoanAmount = String.format("%1.2f", principal);
            String strTerm = String.format("%1.0f", term);
            String strInterestRate = String.format("%1.2f", tempInterest);
            String strMonthlyPayment = String.format("%1.2f", monthlyPayment);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Auto Loan Calculation Results");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Loan Amount: $" + strLoanAmount +
                    "\nTerm: " + strTerm + " Years" + "\nInterest Rate: " + strInterestRate
                    + "%" + "\nMonthly Payment: $" + strMonthlyPayment + "\nAmount to Principal: $"
                    + strAmountToPrincipal + "\nAmount to Interest: $" + strAmountToInterest +
                    "\nTotal for Loan: $" + strTotalForLoan + "\nTotal to Interest: $"
                    + strTotalToInterest);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "AutoLoan.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strAmountToInterest = String.format("%1.2f", amountToInterest);
            String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
            String strTotalToInterest = String.format("%1.2f", totalToInterest);
            String strTotalForLoan = String.format("%1.2f", totalForLoan);
            String strLoanAmount = String.format("%1.2f", principal);
            String strTerm = String.format("%1.0f", term);
            String strInterestRate = String.format("%1.2f", interestRate * 100);
            String strMonthlyPayment = String.format("%1.2f", monthlyPayment);
            writer.write(" " +  dateFormat.format(date) + " Loan Amount: $" + strLoanAmount +
                    "Term: " + strTerm + " Years" + " Interest Rate: " + strInterestRate
                    + "%" + " Monthly Payment: $" + strMonthlyPayment + " Amount to Principal: $"
                    + strAmountToPrincipal + " Amount to Interest: $" + strAmountToInterest +
                    " Total for Loan: $" + strTotalForLoan + " Total to Interest: $"
                    + strTotalToInterest);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the AutoLoan.txt file.");
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

