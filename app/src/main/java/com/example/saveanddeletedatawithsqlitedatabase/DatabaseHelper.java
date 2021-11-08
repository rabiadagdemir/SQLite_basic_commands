package com.example.saveanddeletedatawithsqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID"; // column zero
    private static final String COL2 = "name";// column one

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //the method below is used for adding data to database
    public boolean addData(String item){
        //created an SQLite database object
        // to decleare the SQLite database object getWritableDatabase() method is used
        // .this show that object is created on the context
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item);
        Log.d(TAG, "addData: Adding" + item + "to"+TABLE_NAME);

        // result will be -1 if data is not inserted correctly
        long result = db.insert(TABLE_NAME, null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }
// returns all the data from database
    public Cursor getData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
// returns only the id that matches the name passed in
    public Cursor getItemID(String name){
      SQLiteDatabase db =this.getReadableDatabase();
      String query="SELECT " + COL1 + " FROM " +  TABLE_NAME + " WHERE " + COL2 + " = '" + name +"'";
      Cursor data=db.rawQuery(query,null);
      return data;
    }
    //update method
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db= this.getWritableDatabase();
        String query= " UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newName + " ' WHERE "+ COL1 +
                " = '" + id + "'" + " AND " + COL2+ " = '"+ oldName + "'";
        Log.d(TAG,"updateName: query"+ query);
        Log.d(TAG,"updateName: Setting name to " +newName);
        db.execSQL(query);
    }
    //delete method
    public void deleteName(int id, String name){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= " DELETE FROM " + TABLE_NAME +" WHERE " + COL1+ " = '" + id + "'" +
                " AND " + COL2 +  " = '" + name+ "'";
        Log.d(TAG,"deleteName: query"+ query);
        Log.d(TAG,"deleteName: Deleting "+ name + "from database");
        db.execSQL(query);
    }
}
