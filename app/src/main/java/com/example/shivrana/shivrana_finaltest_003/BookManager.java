package com.example.shivrana.shivrana_finaltest_003;
//
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vinay on 08/14/2018.
 */
//
public class BookManager extends SQLiteOpenHelper {
    //database name and version
    private static final String DATABASE_NAME = "BookDB";
    private static final int DATABASE_VERSION = 2;

    // table name and table creator string (SQL statement to create the table)
    // should be set from within Book activity
    public static String tableName;
    public static String tableCreatorString;

    public void setTableName(String tablename){
        this.tableName = tablename;
    }

    public void setTableCreatorString(String tableCreatorString){
        this.tableCreatorString = tableCreatorString;
    }
    //
    // no-argument constructor
    public BookManager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    // Called when the database is created for the first time.
    // This is where the creation of tables and the initial population
    // of the tables should happen.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        db.execSQL(tableCreatorString);
    }
    //
    // Called when the database needs to be upgraded.
    // The implementation should use this method to drop tables,
    // add tables, or do anything else it needs to upgrade
    // to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if existed
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        //recreate the table
        onCreate(db);
    }
    //
    // initialize table names and CREATE TABLE statement
    // called by activity to create a table in the database
    // The following arguments should be passed:
    // tableName - a String variable which holds the table name
    // tableCreatorString - a String variable which holds the CREATE Table statement
    //
    public void dbInitialize(String tableName, String tableCreatorString)
    {
        this.tableName=tableName;
        this.tableCreatorString=tableCreatorString;
    }
    //
    // CRUD Operations
    //
    //This method is called by the activity to add a row in the table
    // The following arguments should be passed:
    // values - a ContentValues object that holds row values

    public boolean addRecord (ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the row
        long nr= db.insert(tableName, null, values);
        db.close(); //close database connection
        return nr> -1;
    }
    // This method returns an account object which holds the table row with the given id
    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field

    public Book getBookById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + tableName + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );

        Book book = new Book(); //create a new book object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row

            //initialize the instance variables of book object
            book.setBookISBN(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setAuthorName(cursor.getString(2));
            book.setPrice(cursor.getDouble(3));
            cursor.close();

        } else {
            book = null;
        }
        db.close();
        return book;
    }
    //
    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field
    // values - a ContentValues object that holds row values
    //
    public boolean editRow (Object id, String fieldName, ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        int nr = db.update(tableName, values, fieldName + " = ? ", new String[]{String.valueOf(id)});
        return nr > 0;
    }

    public void addBooks(){
        ContentValues contentValues = new ContentValues();
        for (int i=0; i<5;i++){
            contentValues.put("bookISBN",i+809809812);
            contentValues.put("bookName","book volume "+i);
            contentValues.put("authorName","Author MR. "+i);
            contentValues.put("price",i+100);
            try{
                addRecord(contentValues);
            }
            catch (Exception exception){
                Log.i("Error message",exception.getMessage());
            }
        }
    }

    public ArrayList<Book> getBooks(){
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<Book> getBooks = new ArrayList<>();
        getBooks.clear();

            Cursor cursor = db.rawQuery("select * from Book ",null);
            if(cursor.moveToFirst()){
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()){
                    getBooks.add(new Book(
                            cursor.getInt(cursor.getColumnIndex("bookISBN")),
                            cursor.getString(cursor.getColumnIndex("bookName")),
                            cursor.getString(cursor.getColumnIndex("authorName")),
                            cursor.getDouble(cursor.getColumnIndex("price"))
                    ));
                }
            }
            cursor.close();
            return  getBooks;
        }
    }

