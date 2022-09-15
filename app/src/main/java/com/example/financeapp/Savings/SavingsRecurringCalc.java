package com.example.financeapp.Savings;


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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


public class SavingsRecurringCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText edtDepositAmount, edtInterestRate, edtCompoundPeriod, edtTerm, edtInitialDeposit;
    TextView textAccumulatedBalance;
    DrawerLayout drawerLayout;
    double depositAmount = 0, interestRate = 0, compoundPeriod = 0, term = 0,
            accumulatedBalance = 0, initialDeposit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_recurring_calc);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener( this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null)
            navigationView.setCheckedItem(R.id.gst);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(SavingsRecurringCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(SavingsRecurringCalc.this, MainActivity.class);
                startActivity(intent1);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void calculateLogic(View v)
    {
        edtDepositAmount = (EditText)findViewById(R.id.edtDepositAmount);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompoundPeriod);
        edtTerm = (EditText)findViewById(R.id.edtTerm);
        edtInitialDeposit = (EditText)findViewById(R.id.edtInitialDeposit);

        String text1 = edtDepositAmount.getText().toString().trim();
        String text2 = edtInterestRate.getText().toString().trim();
        String text3 = edtCompoundPeriod.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();
        String text5 = edtInitialDeposit.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter deposit amount",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter compound period.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter initial value.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double depositAmount1 = Double.parseDouble(edtDepositAmount.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double compoundPeriod1 = Double.parseDouble(edtCompoundPeriod.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            double initialDeposit1 = Double.parseDouble(edtInitialDeposit.getText().toString());

            depositAmount = depositAmount1;
            interestRate = interestRate1;
            compoundPeriod = compoundPeriod1;
            term = term1;
            initialDeposit = initialDeposit1;
            interestRate *= 0.01;
            interestRate = interestRate / compoundPeriod;
            double numOfCompoundingPeriods = compoundPeriod * term;
            accumulatedBalance = initialDeposit * Math.pow((1 + interestRate), numOfCompoundingPeriods)
                    + depositAmount * ((Math.pow((1 + interestRate), numOfCompoundingPeriods) - 1)
                    / interestRate) * (1 + interestRate);

        }
        printResults();
        dismissKeyboard();
    }
    public void clearLogic(View v)
    {
        edtDepositAmount = (EditText)findViewById(R.id.edtDepositAmount);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompoundPeriod);
        edtTerm = (EditText)findViewById(R.id.edtTerm);
        edtInitialDeposit = (EditText)findViewById(R.id.edtInitialDeposit);
        textAccumulatedBalance = (TextView)findViewById(R.id.textAccumulatedBalance);
        edtDepositAmount.setText("");
        edtInterestRate.setText("");
        edtCompoundPeriod.setText("");
        edtTerm.setText("");
        edtInitialDeposit.setText("");
        textAccumulatedBalance.setText("Accumulated Balance: $");
        dismissKeyboard();
    }
    private void printResults()
    {
        textAccumulatedBalance = (TextView)findViewById(R.id.textAccumulatedBalance);
        String strAccumulatedBalance = String.format("%1.2f", accumulatedBalance);
        textAccumulatedBalance.setText("Accumulated Balance: $" + strAccumulatedBalance);
    }
    private void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtTerm.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        edtDepositAmount = (EditText)findViewById(R.id.edtDepositAmount);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompoundPeriod);
        edtTerm = (EditText)findViewById(R.id.edtTerm);
        edtDepositAmount = (EditText)findViewById(R.id.edtDepositAmount);
        textAccumulatedBalance = (TextView)findViewById(R.id.textAccumulatedBalance);
        String text1 = edtDepositAmount.getText().toString().trim();
        String text2 = edtInterestRate.getText().toString().trim();
        String text3 = edtCompoundPeriod.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();
        String text5 = edtDepositAmount.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter deposit amount",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter compound period.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strAccumulatedBalance = String.format("%1.2f", accumulatedBalance);
            String strDepositAmount = String.format("%1.2f",depositAmount);
            String strInterestRate = String.format("%1.1f",interestRate * 100 * compoundPeriod);
            String strCompoundPeriod = String.format("%1.0f",compoundPeriod);
            String strTerm = String.format("%1.0f",term);
            String strInitialDeposit = String.format("%1.2f", initialDeposit);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Recurring" +
                    " Savings Calculation Results");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Recurring Monthly Deposit: $" + strDepositAmount
                    + "\nInterest Rate: " + strInterestRate + "%" + "\nCompound Period: " + strCompoundPeriod
                    + " Times/Year" + "\nTerm: " + strTerm + " Years"
                    + "\nInitial Deposit: $" + strInitialDeposit + "\nAccumulated Balance: $"
                    + strAccumulatedBalance);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "Savings.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strAccumulatedBalance = String.format("%1.2f", accumulatedBalance);
            String strDepositAmount = String.format("%1.2f",depositAmount);
            String strInterestRate = String.format("%1.1f",interestRate * 100 * compoundPeriod);
            String strCompoundPeriod = String.format("%1.0f",compoundPeriod);
            String strTerm = String.format("%1.0f",term);
            String strInitialDeposit = String.format("%1.2f", initialDeposit);
            writer.write(" " +  dateFormat.format(date) + " Recurring Monthly Deposit: $" + strDepositAmount
                    + " Interest Rate: " + strInterestRate + "%" + " Compound Period: " + strCompoundPeriod
                    + " Times/Year" + " Term: " + strTerm + " Years"
                    + " Initial Deposit: $" + strInitialDeposit + " Accumulated Balance: $"
                    + strAccumulatedBalance);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the Savings.txt file.");
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
