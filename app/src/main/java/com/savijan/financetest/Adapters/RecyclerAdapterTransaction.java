package com.savijan.financetest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savijan.financetest.Database.MyCard;
import com.savijan.financetest.Database.MyTransaction;
import com.savijan.financetest.R;
import com.savijan.financetest.Transaction;

import java.util.List;

public class RecyclerAdapterTransaction extends RecyclerView.Adapter<RecyclerAdapterTransaction.viewHolder> {

    private List<MyTransaction>myCards;
    public Context context;

    public RecyclerAdapterTransaction(List<MyTransaction> myCards, Context context) {
        this.myCards = myCards;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final MyTransaction card = myCards.get(position);

        String type = card.getType_transaction();

        holder.name.setText(card.getName_transaction());
        holder.cash.setText(card.getCheck_transaction() + " т");

        if(type.equals("up")){
            holder.cash.setTextColor(Color.parseColor("#62d44d"));
        }else {
            holder.cash.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return myCards.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView name, cash;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.transaction_name);
            cash = (TextView) itemView.findViewById(R.id.transaction_amount);
        }
    }
}
