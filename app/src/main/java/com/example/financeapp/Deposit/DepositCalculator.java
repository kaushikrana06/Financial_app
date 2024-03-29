package com.example.financeapp.Deposit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class DepositCalculator extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText edtPrincipal, edtInterest, edtCompoundPeriod, edtTerm;
    TextView txtTotalAfterMaturity, txtInterestEarned;
    DrawerLayout drawerLayout;
    double principal = 0, interest = 0, compound = 0, term = 0,
            totalAfterMaturity = 0, interestEarned = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deposit_calculator);
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

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.more:
                Intent intent = new Intent(DepositCalculator.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(DepositCalculator.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(DepositCalculator.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(DepositCalculator.this, Help.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void calculateLogic(View v)
    {
        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterest = (EditText)findViewById(R.id.edtInterest);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompound);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        txtInterestEarned = (TextView)findViewById(R.id.textInterestEarned);
        txtTotalAfterMaturity = (TextView)findViewById(R.id.textTotalAfterMaturity);

        String text1 = edtPrincipal.getText().toString().trim();
        String text2 = edtInterest.getText().toString().trim();
        String text3 = edtCompoundPeriod.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter principal.",
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
        else
        {
            double principal1 = Double.parseDouble(edtPrincipal.getText().toString());
            double interest1 = Double.parseDouble(edtInterest.getText().toString());
            double compound1 = Double.parseDouble(edtCompoundPeriod.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            principal = principal1;
            interest = interest1;
            compound = compound1;
            term = term1;
            interest = interest * 0.01;
            totalAfterMaturity = (principal * Math.pow((1 + (interest / compound)),(compound * term)));
            interestEarned = totalAfterMaturity - principal;

        }
        printResults();
        keyboardDismiss();

    }

    public void clearLogic(View v)
    {
        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterest = (EditText)findViewById(R.id.edtInterest);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompound);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        txtInterestEarned = (TextView)findViewById(R.id.textInterestEarned);
        txtTotalAfterMaturity = (TextView)findViewById(R.id.textTotalAfterMaturity);

        edtPrincipal.setText("");
        edtInterest.setText("");
        edtCompoundPeriod.setText("");
        edtTerm.setText("");
        txtInterestEarned.setText("$");
        txtTotalAfterMaturity.setText("$");
        keyboardDismiss();
    }

    private void printResults()
    {
        txtTotalAfterMaturity = (TextView)findViewById(R.id.textTotalAfterMaturity);
        txtInterestEarned = (TextView)findViewById(R.id.textInterestEarned);
        String strTotalAfterMaturity = String.format("%1.2f", totalAfterMaturity);
        String strInterestEarned = String.format("%1.2f", interestEarned);
        txtTotalAfterMaturity.setText("$" + strTotalAfterMaturity);
        txtInterestEarned.setText("$" + strInterestEarned);
    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtTerm.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterest = (EditText)findViewById(R.id.edtInterest);
        edtCompoundPeriod = (EditText)findViewById(R.id.edtCompound);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        txtInterestEarned = (TextView)findViewById(R.id.textInterestEarned);
        txtTotalAfterMaturity = (TextView)findViewById(R.id.textTotalAfterMaturity);
        String text1 = edtPrincipal.getText().toString().trim();
        String text2 = edtInterest.getText().toString().trim();
        String text3 = edtCompoundPeriod.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter principal.",
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
        else
        {
            emailIntent.setType("plain/text");
            String strTotalAfterMaturity = String.format("%1.2f", totalAfterMaturity);
            String strInterestEarned = String.format("%1.2f", interestEarned);
            String strPrincipal = String.format("%1.2f", principal);
            String strInterestRate = String.format("%1.1f", interest*100);
            String strCompoundPeriod = String.format("%1.0f", compound);
            String strTerm = String.format("%1.0f", term);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Certificate of Deposit Calculation Result");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Principal: $" + strPrincipal
                    + "\nInterest Rate: " + strInterestRate + "%" + "\nCompound Period: " + strCompoundPeriod
                    + " Times/Year" + "\nTerm: " + strTerm + " Years" + "\nInterest Earned: $" + strInterestEarned
                    + "\nTotal After Maturity: $" + strTotalAfterMaturity);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "CertificateOfDeposit.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strTotalAfterMaturity = String.format("%1.2f", totalAfterMaturity);
            String strInterestEarned = String.format("%1.2f", interestEarned);
            String strPrincipal = String.format("%1.2f", principal);
            String strInterestRate = String.format("%1.1f", interest*100);
            String strCompoundPeriod = String.format("%1.0f", compound);
            String strTerm = String.format("%1.0f", term);
            writer.write(" " + dateFormat.format(date) + " Principal: $" + strPrincipal
                    + " Interest Rate: " + strInterestRate + "%" + " Compound Period: " + strCompoundPeriod
                    + " Times/Year" + " Term: " + strTerm + " Years" + " Interest Earned: $" + strInterestEarned
                    + " Total After Maturity: $" + strTotalAfterMaturity);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the TraceFile.txt file.");
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