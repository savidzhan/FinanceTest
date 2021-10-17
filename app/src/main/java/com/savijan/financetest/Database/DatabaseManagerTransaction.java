package com.savijan.financetest.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

public class DatabaseManagerTransaction {

    private final String TAG = DatabaseManagerTransaction.this.getClass().getSimpleName();
    private final Context mContext;
    public static DatabaseManagerTransaction INSTANCE;
    private DatabaseHelper databaseHelper;

    private Dao<MyTransaction, Long> tranItemDao;
    private static String INDEX = "index";
    private static String CARD_NAME = "card_name";
    private static String NAME_TRANSACTION = "name_transaction";
    private static String CHECK_TRANSACTION = "check_transaction";

    public DatabaseManagerTransaction(Context mContext) {
        Log.i(TAG, "DatabaseManager");
        this.mContext = mContext;
        databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);

        try{
            tranItemDao = databaseHelper.getTranItemDao();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static DatabaseManagerTransaction getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new DatabaseManagerTransaction(context);
        return INSTANCE;
    }

    public void releaseDB(){
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
            INSTANCE = null;
        }
    }

    public int clearAllData(){
        try{
            if(databaseHelper == null) return -1;
            databaseHelper.clearTable2();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int insertItem(MyTransaction myCheck, boolean isEdit){
        int count = 0;
        try {
            UpdateBuilder updateBuilder = tranItemDao.updateBuilder();

            String card_name = myCheck.getCard_name();
            String name_transaction = myCheck.getName_transaction();
            String checkTr = myCheck.getCheck_transaction();
            String type_transaction = myCheck.getType_transaction();

            if(tranItemDao == null) {return -1; }


            count = 0;
            tranItemDao.create(myCheck);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<MyTransaction> getAllTransactionsOfCard(String card_name){
        try{
            if(tranItemDao == null) return null;
            return tranItemDao.queryForEq("card_name", card_name);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<MyTransaction> getAllTransactions() {
        try{
            if(tranItemDao == null) return null;
            return tranItemDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
