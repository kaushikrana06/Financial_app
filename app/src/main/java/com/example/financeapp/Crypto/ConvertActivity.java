package com.example.financeapp.Crypto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.financeapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ConvertActivity extends AppCompatActivity {

    String currencyCode;
    String currencyFullName;
    EditText btcValueEdit, ethValueEdit, flatValueEdit, flatValueEditETH;
    Button btcConvertButton, ethConvertButton;

    double btcRate;
    double ethRate;

    TextView moneyCodeView;
    TextView moneyCodeViewETH;
    TextView fullNameView;
    TextView fullNameViewETH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        moneyCodeView = (TextView) findViewById(R.id.money_code_view);
        moneyCodeViewETH = (TextView) findViewById(R.id.money_code_view_eth);
        fullNameView = (TextView) findViewById(R.id.full_name_view);
        fullNameViewETH = (TextView) findViewById(R.id.full_name_view_eth);

        btcValueEdit = (EditText) findViewById(R.id.btc_value_edit);

        ethValueEdit = (EditText) findViewById(R.id.eth_value_edit);

        flatValueEdit = (EditText) findViewById(R.id.flat_value_edit);
        flatValueEditETH = (EditText) findViewById(R.id.flat_value_editETH);

        btcConvertButton = (Button) findViewById(R.id.btc_convert_button);
        ethConvertButton = (Button) findViewById(R.id.eth_convert_button);


        Intent intent = getIntent();
        currencyCode = intent.getStringExtra(com.example.financeapp.Crypto.Constants.EXTRA_CURRENCY);
        btcRate = intent.getDoubleExtra(com.example.financeapp.Crypto.Constants.EXTRA_BTC_RATE, 0);
        ethRate = intent.getDoubleExtra(com.example.financeapp.Crypto.Constants.EXTRA_ETH_RATE, 0);
        currencyFullName = getFullName(currencyCode);
        String conversionMessage = currencyFullName + " Conversion";
        moneyCodeView.setText(currencyCode);
        fullNameView.setText(conversionMessage);

        moneyCodeViewETH.setText(currencyCode);
        fullNameViewETH.setText(conversionMessage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Get full money name from curency code
    public String getFullName(String moneyCode) {
        switch (moneyCode) {
            case "NGN": return "Naira";
            case "USD": return "US Dollar";
            case "EUR": return "Euro";
            case "JPY": return "Yen";
            case "GBP": return "Pound Sterling";
            case "AUD": return "Australian Dollar";
            case "CAD": return "Canadian Dollar";
            case "CHF": return "Swiss Franc";
            case "CNY": return "Yuan";
            case "KES": return "Kenyan Shilling";
            case "GHS": return "Cedi";
            case "UGX": return "Ugandan Shilling";
            case "ZAR": return "Rand";
            case "XAF": return "CFA Franc BCEAO";
            case "NZD": return "New Zealand Dollar";
            case "MYR": return "Malaysian Ringgit";
            case "BND": return "Brunei Dollar";
            case "GEL": return "Lari";
            case "RUB": return "Russian Ruble";
            case "INR": return "Indian Rupee";
            default: return "";
        }
    }

    @SuppressLint("DefaultLocale")
    //Method to do the conversion from one currency to the 2 others
    public void doConvert(View view) {
        if (view == btcConvertButton) {
            try {
                double btcAmount = Double.parseDouble(btcValueEdit.getText().toString());
                flatValueEdit.setText(String.format("%1$,.2f", (btcAmount * btcRate)));
                
            } catch (NumberFormatException e) {
             
            }

        } else if (view == ethConvertButton) {
            try {
                double ethAmount = Double.parseDouble(ethValueEdit.getText().toString());
                flatValueEditETH.setText(String.format("%1$,.2f", (ethAmount * ethRate)));
               
            } catch (NumberFormatException e) {
               
            }

        }

    }}

