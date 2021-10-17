package com.savijan.financetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;
import com.savijan.financetest.Adapters.RecyclerAdapter;
import com.savijan.financetest.Adapters.RecyclerAdapterTransaction;
import com.savijan.financetest.Database.DatabaseManager;
import com.savijan.financetest.Database.DatabaseManagerTransaction;
import com.savijan.financetest.Database.MyCard;
import com.savijan.financetest.Database.MyTransaction;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends AppCompatActivity {

    private Button addTransaction;
    private Button goBackToCards;
    List<MyTransaction> transactionsList = new ArrayList<>();
    RecyclerView transactionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        String card_name = getIntent().getStringExtra("card_name");

        goBackToCards = (Button) findViewById(R.id.goBackToCards);
        goBackToCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Transaction.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addTransaction = (Button) findViewById(R.id.addTransactionToCard);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Transaction.this, AddTransactionToCard.class);
                intent.putExtra("card_name", card_name);
                startActivity(intent);
            }
        });


        transactionsList = getAllTransactions(card_name);

        transactionListView = (RecyclerView) findViewById(R.id.transactionListView);
        RecyclerAdapterTransaction mAdapter = new RecyclerAdapterTransaction(new ArrayList<>(transactionsList), getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        transactionListView.setLayoutManager(layoutManager);
        transactionListView.setAdapter(mAdapter);

    }


    private List<MyTransaction> getAllTransactions(String card) {
        return DatabaseManagerTransaction.getInstance(getApplicationContext()).getAllTransactionsOfCard(card);
    }
}