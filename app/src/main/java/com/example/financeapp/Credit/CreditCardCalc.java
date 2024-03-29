package com.example.financeapp.Credit;


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


public class CreditCardCalc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    EditText edtPrincipal, edtInterestRate, edtPaymentPerMonth, edtExtraPayment;
    TextView textTimeToPayoff, textTimeToPayoffExtra;
    double principal = 0, interestRate = 0, paymentPerMonth = 0, extraPayment = 0,
            timeToPayoff = 0, timeWithExtra = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_credit_card_calc);
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
                Intent intent = new Intent(CreditCardCalc.this, More_Tools.class);
                startActivity(intent);
                break;
            case R.id.gst:
                Intent intent1 = new Intent(CreditCardCalc.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(CreditCardCalc.this, Appinfo.class);
                startActivity(intent2);
                break;
            case R.id.help:
                Intent intent4 = new Intent(CreditCardCalc.this, Help.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void creditCalculateLogic(View v)
    {
        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtPaymentPerMonth = (EditText)findViewById(R.id.edtPaymentAmount);
        edtExtraPayment = (EditText)findViewById(R.id.edtExtraPayment);

        String text1 = edtPrincipal.getText().toString().trim();
        String text2 = edtInterestRate.getText().toString().trim();
        String text3 = edtPaymentPerMonth.getText().toString().trim();
        String text4 = edtExtraPayment.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter credit card principal.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter payment per month.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter extra payment or 0 if none.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double principal1 = Double.parseDouble(edtPrincipal.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double paymentPerMonth1 = Double.parseDouble(edtPaymentPerMonth.getText().toString());
            double extraPayment1 = Double.parseDouble(edtExtraPayment.getText().toString());
            principal = principal1;
            interestRate = interestRate1;
            paymentPerMonth  = paymentPerMonth1;
            extraPayment = extraPayment1;
            interestRate = interestRate / 12;
            interestRate *= 0.01;
            timeToPayoff = -(Math.log(1-(interestRate) * (principal/paymentPerMonth)))
                    / Math.log(1 + (interestRate));
            timeWithExtra = -(Math.log(1-(interestRate) * (principal/(paymentPerMonth + extraPayment))))
                    / Math.log(1 + (interestRate));

        }
        printResults();
        keyboardDismiss();
    }

    public void creditClearLogic(View v)
    {
        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtPaymentPerMonth = (EditText)findViewById(R.id.edtPaymentAmount);
        edtExtraPayment = (EditText)findViewById(R.id.edtExtraPayment);
        textTimeToPayoff = (TextView)findViewById(R.id.textTimeToPayoff);
        textTimeToPayoffExtra = (TextView)findViewById(R.id.textTimeToPayoffExtra);

        edtPrincipal.setText("");
        edtInterestRate.setText("");
        edtPaymentPerMonth.setText("");
        edtExtraPayment.setText("0.00");
        textTimeToPayoff.setText("Credit card payoff in: ");
        textTimeToPayoffExtra.setText("With extra payment, paid off in: ");
    }

    private void printResults()
    {
        textTimeToPayoff = (TextView)findViewById(R.id.textTimeToPayoff);
        textTimeToPayoffExtra = (TextView)findViewById(R.id.textTimeToPayoffExtra);
        String strTimeToPayoff = String.format("%1.0f", timeToPayoff);
        String strTimeToPayoffExtra = String.format("%1.0f", timeWithExtra);
        textTimeToPayoff.setText("Credit card payoff in: " + strTimeToPayoff + " months");
        textTimeToPayoffExtra.setText("With extra payment, paid off in: " + strTimeToPayoffExtra + " months");
    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtPrincipal.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        edtPrincipal = (EditText)findViewById(R.id.edtPrincipal);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtPaymentPerMonth = (EditText)findViewById(R.id.edtPaymentAmount);
        edtExtraPayment = (EditText)findViewById(R.id.edtExtraPayment);
        textTimeToPayoff = (TextView)findViewById(R.id.textTimeToPayoff);
        textTimeToPayoffExtra = (TextView)findViewById(R.id.textTimeToPayoffExtra);
        String text1 = edtPrincipal.getText().toString().trim();
        String text2 = edtInterestRate.getText().toString().trim();
        String text3 = edtPaymentPerMonth.getText().toString().trim();
        String text4 = edtExtraPayment.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter credit card principal.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter payment per month.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter extra payment or 0 if none.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strTimeToPayoff = String.format("%1.0f", timeToPayoff);
            String strTimeToPayoffExtra = String.format("%1.0f", timeWithExtra);
            String strPrincipal = String.format("%1.2f", principal);
            String strInterestRate = String.format("%1.1f", interestRate * 100 * 12);
            String strPaymentPerMonth = String.format("%1.2f", paymentPerMonth);
            String strExtraPayment = String.format("%1.2f", extraPayment);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Credit Card Calculation Result");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Principal: $" + strPrincipal
                    + "\nInterest Rate: " + strInterestRate + "%" + "\nPayment Per Month: $"
                    + strPaymentPerMonth + "\nExtra Payment: $" + strExtraPayment +
                    "\nTime to Payoff: " + strTimeToPayoff + " Months" + "\nTime to Payoff with Extra: "
                    + strTimeToPayoffExtra + " Months");
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "CreditCard.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strTimeToPayoff = String.format("%1.0f", timeToPayoff);
            String strTimeToPayoffExtra = String.format("%1.0f", timeWithExtra);
            String strPrincipal = String.format("%1.2f", principal);
            String strInterestRate = String.format("%1.1f", interestRate * 100 * 12);
            String strPaymentPerMonth = String.format("%1.2f", paymentPerMonth);
            String strExtraPayment = String.format("%1.2f", extraPayment);
            writer.write(" " +  dateFormat.format(date) + " Principal: $" + strPrincipal
                    + " Interest Rate: " + strInterestRate + "%" + " Payment Per Month: $"
                    + strPaymentPerMonth + " Extra Payment: $" + strExtraPayment +
                    " Time to Payoff: " + strTimeToPayoff + " Months" + " Time to Payoff with Extra: "
                    + strTimeToPayoffExtra + " Months");
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
            Log.e("com.mycompany.FileTest", "Unable to write to the CreditCard.txt file.");
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
