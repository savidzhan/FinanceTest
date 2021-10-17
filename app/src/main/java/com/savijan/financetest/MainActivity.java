package com.savijan.financetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.savijan.financetest.Adapters.RecyclerAdapter;
import com.savijan.financetest.Database.DatabaseManager;
import com.savijan.financetest.Database.MyCard;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<MyCard> cardList = new ArrayList<>();
    RecyclerView cardListView;
    private Button addNewCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardList = getAllCards();

        cardListView = (RecyclerView) findViewById(R.id.cardListView);
        RecyclerAdapter mAdapter = new RecyclerAdapter(new ArrayList<>(cardList), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardListView.setLayoutManager(layoutManager);
        cardListView.setAdapter(mAdapter);
        addNewCard = (Button) findViewById(R.id.addNewCard);
        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCardActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<MyCard> getAllCards() {
        return DatabaseManager.getInstance(getApplicationContext()).getAllCards();
    }

}