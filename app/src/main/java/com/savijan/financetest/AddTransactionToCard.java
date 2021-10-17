package com.savijan.financetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.savijan.financetest.Database.DatabaseManager;
import com.savijan.financetest.Database.DatabaseManagerTransaction;
import com.savijan.financetest.Database.MyCard;
import com.savijan.financetest.Database.MyTransaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class AddTransactionToCard extends AppCompatActivity {

    private TextView headText;
    private EditText newTransactionName, cashNum;
    RadioGroup radioGroup;
    private Button submitAdding;
    static String card_name;
    List<MyTransaction> transactionList = new ArrayList<>();
    public String type = "up";
    public String amountOfTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_to_card);

        newTransactionName = (EditText) findViewById(R.id.newTransactionName);
        cashNum = (EditText) findViewById(R.id.cashNum);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_profit:
                        type = "up";
                        break;
                    case R.id.radio_spend:
                        type = "down";
                        break;
                    default:
                        break;
                }
            }
        });

        submitAdding = (Button) findViewById(R.id.submitTransactionAdding);
        submitAdding.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                addNewTransaction();
            }
        });

        headText = (TextView) findViewById(R.id.head);
        card_name = getIntent().getStringExtra("card_name");
        headText.setText("Adding transaction to " + card_name);
    }


    private void addNewTransaction() throws SQLException {
        String transactionName = newTransactionName.getText().toString();
        amountOfTr = cashNum.getText().toString();

        if(transactionName != null && !transactionName.isEmpty()){
            MyTransaction newTransaction = new MyTransaction();
            newTransaction.setCard_name(card_name);
            newTransaction.setName_transaction(transactionName);
            newTransaction.setCheck_transaction(amountOfTr);
            newTransaction.setType_transaction(type);
            addNewTransactionToDB(newTransaction);
            transactionList = getAllTransaction();
        }

    }

    private List<MyTransaction> getAllTransaction() {
        return DatabaseManagerTransaction.getInstance(getApplicationContext()).getAllTransactions();
    }

    private int addNewTransactionToDB(MyTransaction newCard) throws SQLException {

        int isSuccess;
        isSuccess = DatabaseManagerTransaction.getInstance(getApplicationContext()).insertItem(newCard, false);
        if(isSuccess == 0){
            DatabaseManager.getInstance(getApplicationContext()).updateCashRow(card_name, type, Float.parseFloat(amountOfTr));
            Toast.makeText(getApplicationContext(), "Save transaction", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Transaction.class);
            intent.putExtra("card_name", card_name);
            startActivity(intent);
        }else if(isSuccess == 1){
            Toast.makeText(getApplicationContext(), "Transaction with this name exist", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Transaction adding failed", Toast.LENGTH_SHORT).show();
        }
        return isSuccess;

    }

}