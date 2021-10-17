package com.savijan.financetest.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    private Dao<MyCard, Long> cardItemDBS;
    private Dao<MyTransaction, Long> tranItemDBS;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, MyCard.class);
            TableUtils.createTable(connectionSource, MyTransaction.class);
        }catch (SQLException | java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

//    @Override
//    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//
//        try {
//            if(checkTableExist(database, "item_card")){
//                TableUtils.dropTable(connectionSource, MyCard.class, false);
//                onCreate(database, connectionSource);
//            }
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    private boolean checkTableExist(SQLiteDatabase database, String tableName){
        Cursor c = null;
        boolean tableExist = false;
        try{
            c = database.query(tableName, null, null, null, null, null, null, null );
            tableExist = true;
        } catch (Exception e){

        }
        return tableExist;
    }

    public Dao<MyCard, Long> getCardItemDao() throws SQLException, java.sql.SQLException {
        if(cardItemDBS == null){
            cardItemDBS = getDao(MyCard.class);
        }

        return cardItemDBS;
    }

    public Dao<MyTransaction, Long> getTranItemDao() throws SQLException, java.sql.SQLException {
        if(tranItemDBS == null){
            tranItemDBS = getDao(MyTransaction.class);
        }

        return tranItemDBS;
    }

    public void close(){
        cardItemDBS = null;
        tranItemDBS = null;
        super.close();
    }


    public void clearTable1() throws SQLException, java.sql.SQLException {
        TableUtils.clearTable(getConnectionSource(), MyCard.class);
    }

    public void clearTable2() throws SQLException, java.sql.SQLException {
        TableUtils.clearTable(getConnectionSource(), MyCard.class);
    }

}
