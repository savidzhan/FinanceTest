package com.savijan.financetest.Database;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

    private final String TAG = DatabaseManager.this.getClass().getSimpleName();
    private final Context mContext;
    public static DatabaseManager INSTANCE;
    private DatabaseHelper databaseHelper;

    private Dao<MyCard, Long> cardItemDao;
    private static String INDEX = "index";
    private static String NAME = "name";
    private static String CASH = "cash";

    public DatabaseManager(Context mContext) {
        Log.i(TAG, "DatabaseManager");
        this.mContext = mContext;
        OpenHelperManager.setOpenHelperClass(DatabaseHelper.class);
        databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);

        try{
            cardItemDao = databaseHelper.getCardItemDao();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new DatabaseManager(context);
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
            databaseHelper.clearTable1();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isUserExisting(String index){
        QueryBuilder queryBuilder = cardItemDao.queryBuilder();
        boolean flag = false;

        try{
            if(queryBuilder.where().eq(NAME, index).countOf()>0){
                flag = true;
            }else {
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int insertUserItam(MyCard myCard, boolean isEdit){
        int count = 0;
        try {
            UpdateBuilder updateBuilder = cardItemDao.updateBuilder();

            String name = myCard.getName() != null ? myCard.getName() : "";
            String cash = myCard.getCash();

            if(cardItemDao == null) return -1;

            if(isUserExisting(name)){
                Log.i(TAG, "this card exist");
                count = 1;
                if(isEdit){
                    deleteCard(name);
                    cardItemDao.create(myCard);
                }
            }else {
                count = 0;
                cardItemDao.create(myCard);
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int deleteCard(String index) {

        try{
            if(cardItemDao == null) return -1;
            DeleteBuilder deleteBuilder = cardItemDao.deleteBuilder();
            if(index != null || !index.isEmpty()) deleteBuilder.where().eq(INDEX, index);

            deleteBuilder.delete();
            Log.i(TAG, "user deleted");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<MyCard> getAllCards(){
        try{
            if(cardItemDao == null) return null;
            return cardItemDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCashRow(String cardName, String type, float cashAmount) throws SQLException {
        float result;
        float oldRes;

        UpdateBuilder updateBuilder = cardItemDao.updateBuilder();

        List<MyCard> cashOldList = cardItemDao.queryForEq("name", cardName);
        MyCard newCash = cashOldList.get(0);
        String cashOld = newCash.getCash();
        oldRes = Float.parseFloat(cashOld);
        if(type.equals("up")){
            result = oldRes + cashAmount;
        } else {
            result = oldRes - cashAmount;
        }

        String resStr = String.valueOf(result);


        updateBuilder.where().eq("name", cardName);
        updateBuilder.updateColumnValue("cash", resStr);
        updateBuilder.update();

    }


}
