package com.example.financeapp.Crypto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.financeapp.R;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    public MyAdapter(List<CardItems> cardItemsList, Context context) {

        this.cardItemsList = cardItemsList;
        this.context = context;
    }

    private List<CardItems> cardItemsList;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_items, parent, false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CardItems cardItem = cardItemsList.get(position);

        final String curr = cardItem.getCurrency();
        final double btcVal = cardItem.getBtc_value();
        final double EthVal = cardItem.getEth_value();

        holder.currency.setText(curr);
        holder.btc_value.setText(String.format("%1$,.2f",btcVal));
        holder.eth_value.setText(String.format("%1$,.2f",EthVal));

        holder.cardLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ConvertActivity.class);
                intent.putExtra(com.example.financeapp.Crypto.Constants.EXTRA_CURRENCY, curr);
                intent.putExtra(com.example.financeapp.Crypto.Constants.EXTRA_BTC_RATE, btcVal);
                intent.putExtra(com.example.financeapp.Crypto.Constants.EXTRA_ETH_RATE, EthVal);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cardItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView btc_value, eth_value, currency;
        LinearLayout cardLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            btc_value = itemView.findViewById(R.id.btc_value);
            eth_value = itemView.findViewById(R.id.eth_value);
            currency = itemView.findViewById(R.id.textViewCurrency);
            cardLinearLayout = itemView.findViewById(R.id.card_linear_layout);
        }
    }
}
