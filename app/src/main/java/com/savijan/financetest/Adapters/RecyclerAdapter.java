package com.savijan.financetest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savijan.financetest.Database.MyCard;
import com.savijan.financetest.R;
import com.savijan.financetest.Transaction;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    private List<MyCard>myCards;
    public Context context;

    public RecyclerAdapter(List<MyCard> myCards, Context context) {
        this.myCards = myCards;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final MyCard card = myCards.get(position);

        holder.name.setText(card.getName());
        holder.cash.setText(card.getCash() + " т");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = holder.name.getText().toString();
                Intent intent = new Intent(context, Transaction.class);
                intent.putExtra("card_name", name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCards.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView name, cash;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cardName);
            cash = (TextView) itemView.findViewById(R.id.cardCash);
        }
    }
}
