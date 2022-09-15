
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

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        textBiMonthlyPayment.setText("$" + strMonthly);
        textMonthlyPayment.setText("$" + strBiMonthly);
        textAmtToInterest.setText("$" + strInterestAmt);
        textAmtToPrincipal.setText("$" + strPrincipalAmt);

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

