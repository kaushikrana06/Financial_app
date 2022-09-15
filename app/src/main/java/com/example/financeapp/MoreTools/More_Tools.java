package com.example.financeapp.MoreTools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.financeapp.Credit.CreditCardCalc;
import com.example.financeapp.Crypto.CryptoActivity;
import com.example.financeapp.Deposit.DepositCalculator;
import com.example.financeapp.EmiCalculator.EmiCal;
import com.example.financeapp.Income.IncomeTax;
import com.example.financeapp.Interest.Simple;
import com.example.financeapp.Invoice.BillsActivity;
import com.example.financeapp.Lease.AutoLeaseCalc;
import com.example.financeapp.Loan.AutoLoanCalc;
import com.example.financeapp.MainActivity;
import com.example.financeapp.Mortgage.MortgageCalc;
import com.example.financeapp.News.NewsActivity;
import com.example.financeapp.R;
import com.example.financeapp.Reminder.RemindActivity;
import com.example.financeapp.Retirement.RetirementCalc;
import com.example.financeapp.Savings.SavingsRecurringCalc;
import com.google.android.material.navigation.NavigationView;

public class More_Tools extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
 DrawerLayout drawerLayout;
 @Override
    protected void onCreate(Bundle savedInstanceState){
     super.onCreate(savedInstanceState);
  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

  setContentView(R.layout.activity_more_tools);

  Toolbar toolbar = findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  drawerLayout=findViewById(R.id.drawer_layout);
  NavigationView navigationView=findViewById(R.id.nav);
  navigationView.setNavigationItemSelectedListener( this);
  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
  drawerLayout.addDrawerListener(toggle);
  toggle.syncState();
  if(savedInstanceState == null)
   navigationView.setCheckedItem(R.id.more);

 }
 @Override
 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        gst = (Button) findViewById(R.id.gst);

  switch (item.getItemId()){

   case R.id.more:
    recreate();
    break;
   case R.id.gst:
    Intent intent = new Intent(More_Tools.this, MainActivity.class);
    startActivity(intent);
    break;

  }
  drawerLayout.closeDrawer(GravityCompat.START);
  return true;
 }

 public void certOfDep(View v)
 {
  Intent intent = new Intent(More_Tools.this, DepositCalculator.class);
  startActivity(intent);
 }
 public void gstcal(View v)
 {
  Intent intent = new Intent(More_Tools.this, MainActivity.class);
  startActivity(intent);
 }

 public void mortgageCalc(View v)
 {
  Intent intent = new Intent(More_Tools.this, MortgageCalc.class);
  startActivity(intent);
 }

 public void autoLoan(View v)
 {
  Intent intent = new Intent(More_Tools.this, AutoLoanCalc.class);
  startActivity(intent);
 }

 public void creditCard(View v)
 {
  Intent intent = new Intent(More_Tools.this, CreditCardCalc.class);
  startActivity(intent);
 }
 public void autoLease(View v)
 {
  Intent intent = new Intent(More_Tools.this, AutoLeaseCalc.class);
  startActivity(intent);
 }

 public void retirement(View v)
 {
  Intent intent = new Intent(More_Tools.this, RetirementCalc.class);
  startActivity(intent);
 }

 public void savingsRecurring(View v)
 {
  Intent intent = new Intent(More_Tools.this, SavingsRecurringCalc.class);
  startActivity(intent);
 }

 public void emi(View v)
 {
  Intent intent = new Intent(More_Tools.this, EmiCal.class);
  startActivity(intent);
 }

 public void income(View view) {
  Intent intent = new Intent(More_Tools.this, IncomeTax.class);
  startActivity(intent);
 }
 public void interest(View view) {
  Intent intent = new Intent(More_Tools.this, Simple.class);
  startActivity(intent);
 }
 public void invoice(View view) {
  Intent intent = new Intent(More_Tools.this, BillsActivity.class);
  startActivity(intent);
 }
 public void news(View view) {
  Intent intent = new Intent(More_Tools.this, NewsActivity.class);
  startActivity(intent);
 }
 public void remind(View view) {
  Intent intent = new Intent(More_Tools.this, RemindActivity.class);
  startActivity(intent);
 }
 public void crypto(View view) {
  Intent intent = new Intent(More_Tools.this, CryptoActivity.class);
  startActivity(intent);
 }
 @Override
 public void onBackPressed() {
  if(drawerLayout.isDrawerOpen(GravityCompat.START))
   drawerLayout.closeDrawer(GravityCompat.START);
        else
        {

         Intent in=new Intent(More_Tools.this,MainActivity.class);
         startActivity(in);
            super.onBackPressed();
        }
 }


}