package com.savijan.financetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.savijan.financetest.Database.DatabaseManager;
import com.savijan.financetest.Database.MyCard;

import java.util.ArrayList;
import java.util.List;

public class AddCardActivity extends AppCompatActivity {

    private EditText nameEdText;
    private Button submitAdding;
    List<MyCard> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        nameEdText = (EditText) findViewById(R.id.newCardName);
        submitAdding = (Button) findViewById(R.id.submitAdding);
        submitAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCard();
            }
        });
    }

    private void addNewCard() {
        String cardName = nameEdText.getText().toString();

        if(cardName != null && !cardName.isEmpty()){
            MyCard newCard = new MyCard();
            newCard.setName(cardName);
            newCard.setCash("0.00");
            addNewCardToDB(newCard);
            cardList = getAllCards();
        }

    }

    private List<MyCard> getAllCards() {
        return DatabaseManager.getInstance(getApplicationContext()).getAllCards();
    }

    private int addNewCardToDB(MyCard newCard) {

        int isSuccess;
        isSuccess = DatabaseManager.getInstance(getApplicationContext()).insertUserItam(newCard, false);
        if(isSuccess == 0){
            Toast.makeText(getApplicationContext(), "Save card", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(isSuccess == 1){
            Toast.makeText(getApplicationContext(), "card with this name exist", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Card adding failed", Toast.LENGTH_SHORT).show();
        }
        return isSuccess;

    }
}