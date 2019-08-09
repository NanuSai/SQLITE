package com.saiproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHandler extends SQLiteOpenHelper {

//Database Version
private static final int DATABASE_VERSION = 1;
//Database name
private static final String DATABASE_NAME = "computer.db";
//Computer Table name
private static final String TABLE_NAME = "computers";


//Values of Columns

    private static final String  COLUMN_ID = "id";
    private static final String COLUMN_COMPUTER_NAME = "computerName";
    private static final String COLUMN_COMPUTER_TYPE = "computerType";


/* CREATE TABLE computers (id INTEGER PRIMARY KEY, computerName TEXT, computer Type TEXT) */

    String CREATE_COMPUTER_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_COMPUTER_NAME + " TEXT, "+
            COLUMN_COMPUTER_TYPE+" TEXT"+")";


    public MySQLiteHandler(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION); //Create database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_COMPUTER_TABLE); //CREATE_COMPUTER_TABLE is the command that is passed

    }


//To update/change database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //Drop older version
        onCreate(db);

    }


    //Database operations: create,read,update,delete




//Add data to database
    public void addComputer(Computer computer){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME,computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE,computer.getComputerType());

        db.insert(TABLE_NAME,null,contentValues); // Put the contentValues inside TABLE_NAME
        db.close();



    }


//Getting a single computer - read
     public Computer getComputer(int id){

        SQLiteDatabase db = this.getReadableDatabase(); //Create readable database
        Cursor cursor = db.query(TABLE_NAME,new String[]{COLUMN_ID,COLUMN_COMPUTER_NAME,COLUMN_COMPUTER_TYPE},
                COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Computer computer = new Computer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));

        return computer;


    }


//Get all computer objects

public List<Computer> getAllComputers() {


    List<Computer> computerList = new ArrayList<>();
    String query = "SELECT * FROM " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, null); //WHERE clause is null


    if (cursor.moveToFirst()) {     //cursor.moveToFirst() returns a boolean


        while (cursor.moveToNext()) {        //Cursors selects different rows one by one

            Computer computer = new Computer();
            computer.setId(Integer.parseInt(cursor.getString(0)));
            computer.setComputerName((cursor.getString(1)));
            computer.setComputerType((cursor.getString(1)));

            computerList.add(computer);


        }


    }

    return computerList;

}


//Updating a single computer


    public int updateComputer(Computer computer) {


        SQLiteDatabase db = this.getWritableDatabase(); // Since some data is being added
        ContentValues contentValues = new ContentValues(); //To extract values
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());     //Pull out computer name and type from the argument object
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        return db.update(TABLE_NAME, contentValues,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(computer.getId())}); // WHERE COLUMN_ID = computer.getId()
    }



    //Deleting a single computer

    public void deleteComputer(Computer computer){


        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{ String.valueOf(computer.getId())});

        db.close();

    }


    //Get total number of computers

    public int getComputerCount(){

        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }



























        }












