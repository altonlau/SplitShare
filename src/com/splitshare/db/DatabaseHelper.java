package com.splitshare.db;

import java.util.ArrayList;
import java.util.List;

import com.splitshare.db.model.FinanceModel;
import com.splitshare.db.model.PeopleModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// All Static variables
	private static final String TAG = "alton:DB";
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "splitshareDB";
 
    // Table names
    private static final String TABLE_PEOPLE = "people";
    private static final String TABLE_FINANCE = "finance";
    
    // Common column names
    private static final String COLUMN_ID = "id";
    
    // PEOPLE Table - column names
    private static final String COLUMN_FIRSTNAME = "first_name";
    private static final String COLUMN_LASTNAME = "last_name";
    private static final String COLUMN_ISUSER = "is_user";
    
    // FINANCE Table - column names
    private static final String COLUMN_AMOUNTOWED = "amount_owed";
    private static final String COLUMN_REASON = "reason";
    private static final String COLUMN_FROMID = "from_id";
    private static final String COLUMN_TOID = "to_id";
    
    // CREATE statements
	private static final String CREATE_TABLE_PEOPLE = "CREATE TABLE "
			+ TABLE_PEOPLE + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY,"
			+ COLUMN_FIRSTNAME + " TEXT,"
			+ COLUMN_LASTNAME + " TEXT,"
			+ COLUMN_ISUSER + " INTEGER" + ")";
	private static final String CREATE_TABLE_FINANCE = "CREATE TABLE "
			+ TABLE_FINANCE + "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY,"
			+ COLUMN_AMOUNTOWED + " FLOAT,"
			+ COLUMN_REASON + " TEXT,"
			+ COLUMN_FROMID + " INTEGER,"
			+ COLUMN_TOID + " INTEGER" + ")";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_FINANCE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINANCE);
 
        // Create tables again
        onCreate(db);
    }
    
    /** PEOPLE methods **/
    public long insertPeople(PeopleModel people) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_FIRSTNAME, people.getFirstName());
    	values.put(COLUMN_LASTNAME, people.getLastName());
    	values.put(COLUMN_ISUSER, people.getIsUser());
    	
    	// Insert row
    	return db.insert(TABLE_PEOPLE, null, values);
    }
    
    public PeopleModel getPeople(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String query = "SELECT  * FROM " + TABLE_PEOPLE + " WHERE "
                + COLUMN_ID + " = " + id;
     
        Log.i(TAG, query);
     
        Cursor c = db.rawQuery(query, null);
     
        if (c != null) c.moveToFirst();
     
        PeopleModel person = new PeopleModel();
        person.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
        person.setFirstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
        person.setLastName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
        person.setIsUser(c.getInt(c.getColumnIndex(COLUMN_ISUSER)));
     
        return person;
    }
    
    public PeopleModel getPeople(String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();
     
		String query = "SELECT  * FROM " + TABLE_PEOPLE + " WHERE "
				+ COLUMN_FIRSTNAME + " = " + firstName + " AND "
				+ COLUMN_LASTNAME + " = " + lastName;
     
        Log.i(TAG, query);
     
        Cursor c = db.rawQuery(query, null);
     
        if (c != null) c.moveToFirst();
     
        PeopleModel person = new PeopleModel();
        person.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
        person.setFirstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
        person.setLastName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
        person.setIsUser(c.getInt(c.getColumnIndex(COLUMN_ISUSER)));
     
        return person;
    }
    
    public List<PeopleModel> getAllPeople() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	List<PeopleModel> people = new ArrayList<PeopleModel>();
    	String query = "SELECT * FROM " + TABLE_PEOPLE;
    	
    	Log.i(TAG, query);
    	
    	Cursor c = db.rawQuery(query, null);
    	
        // Looping through all rows and adding to list
    	if (c.moveToFirst()) {
            do {
                PeopleModel person = new PeopleModel();
                person.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
                person.setFirstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
                person.setLastName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
                person.setIsUser(c.getInt(c.getColumnIndex(COLUMN_ISUSER)));
     
                // Adding to people list
                people.add(person);
            } while (c.moveToNext());
        }
    	
    	return people;
    }
    
    public PeopleModel getUser() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String query = "SELECT * FROM " + TABLE_PEOPLE + " WHERE " + COLUMN_ISUSER + " = 1";
    	
    	Cursor c = db.rawQuery(query, null);
    	
    	if (c != null) c.moveToFirst();

        PeopleModel person = new PeopleModel();
        person.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
        person.setFirstName(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)));
        person.setLastName(c.getString(c.getColumnIndex(COLUMN_LASTNAME)));
        person.setIsUser(c.getInt(c.getColumnIndex(COLUMN_ISUSER)));
        
        return person;
    }
    
    public int updatePeople(PeopleModel people) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_FIRSTNAME, people.getFirstName());
    	values.put(COLUMN_LASTNAME, people.getLastName());
    	values.put(COLUMN_ISUSER, people.getIsUser());
    	
        // Updating row
        return db.update(TABLE_PEOPLE, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(people.getID()) });
    }
    
    public void deletePeople(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Deleting row
        db.delete(TABLE_PEOPLE, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    
    /** FINANCE methods **/
    public long insertFinance(FinanceModel finance) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_AMOUNTOWED, finance.getAmountOwed());
    	values.put(COLUMN_REASON, finance.getReason());
    	values.put(COLUMN_FROMID, finance.getFromID());
    	values.put(COLUMN_TOID, finance.getToID());
    	
    	// Insert row
    	return db.insert(TABLE_FINANCE, null, values);
    }
    
    public FinanceModel getFinance(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String query = "SELECT  * FROM " + TABLE_FINANCE + " WHERE "
                + COLUMN_ID + " = " + id;
     
        Log.i(TAG, query);
     
        Cursor c = db.rawQuery(query, null);
     
        if (c != null) c.moveToFirst();
        
        FinanceModel finance = new FinanceModel();
        finance.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
        finance.setAmountOwed(c.getFloat(c.getColumnIndex(COLUMN_AMOUNTOWED)));
        finance.setReason(c.getString(c.getColumnIndex(COLUMN_REASON)));
        finance.setFromID(c.getInt(c.getColumnIndex(COLUMN_FROMID)));
        finance.setToID(c.getInt(c.getColumnIndex(COLUMN_TOID)));
     
        return finance;
    }
    
    public List<FinanceModel> getAllFinances() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	List<FinanceModel> finances = new ArrayList<FinanceModel>();
    	String query = "SELECT * FROM " + TABLE_FINANCE;
    	
    	Log.i(TAG, query);
    	
    	Cursor c = db.rawQuery(query, null);
    	
        // Looping through all rows and adding to list
    	if (c.moveToFirst()) {
            do {
                FinanceModel finance = new FinanceModel();
                finance.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
                finance.setAmountOwed(c.getFloat(c.getColumnIndex(COLUMN_AMOUNTOWED)));
                finance.setReason(c.getString(c.getColumnIndex(COLUMN_REASON)));
                finance.setFromID(c.getInt(c.getColumnIndex(COLUMN_FROMID)));
                finance.setToID(c.getInt(c.getColumnIndex(COLUMN_TOID)));
     
                // Adding to people list
                finances.add(finance);
            } while (c.moveToNext());
        }
    	
    	return finances;
    }
    
    public List<FinanceModel> getFinancesFromID(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	List<FinanceModel> finances = new ArrayList<FinanceModel>();
    	String query = "SELECT * FROM " + TABLE_FINANCE + " WHERE " + COLUMN_FROMID + " = " + id;
    	
    	Log.i(TAG, query);
    	
    	Cursor c = db.rawQuery(query, null);
    	
        // Looping through all rows and adding to list
    	if (c.moveToFirst()) {
            do {
                FinanceModel finance = new FinanceModel();
                finance.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
                finance.setAmountOwed(c.getFloat(c.getColumnIndex(COLUMN_AMOUNTOWED)));
                finance.setReason(c.getString(c.getColumnIndex(COLUMN_REASON)));
                finance.setFromID(c.getInt(c.getColumnIndex(COLUMN_FROMID)));
                finance.setToID(c.getInt(c.getColumnIndex(COLUMN_TOID)));
     
                // Adding to people list
                finances.add(finance);
            } while (c.moveToNext());
        }
    	
    	return finances;
    }

    public List<FinanceModel> getFinancesToID(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	List<FinanceModel> finances = new ArrayList<FinanceModel>();
    	String query = "SELECT * FROM " + TABLE_FINANCE + " WHERE " + COLUMN_TOID + " = " + id;
    	
    	Log.i(TAG, query);
    	
    	Cursor c = db.rawQuery(query, null);
    	
        // Looping through all rows and adding to list
    	if (c.moveToFirst()) {
            do {
                FinanceModel finance = new FinanceModel();
                finance.setID(c.getInt(c.getColumnIndex(COLUMN_ID)));
                finance.setAmountOwed(c.getFloat(c.getColumnIndex(COLUMN_AMOUNTOWED)));
                finance.setReason(c.getString(c.getColumnIndex(COLUMN_REASON)));
                finance.setFromID(c.getInt(c.getColumnIndex(COLUMN_FROMID)));
                finance.setToID(c.getInt(c.getColumnIndex(COLUMN_TOID)));
     
                // Adding to people list
                finances.add(finance);
            } while (c.moveToNext());
        }
    	
    	return finances;
    }
    
    public int updateFinance(PeopleModel people) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_FIRSTNAME, people.getFirstName());
    	values.put(COLUMN_LASTNAME, people.getLastName());
    	values.put(COLUMN_ISUSER, people.getIsUser());
    	
        // Updating row
        return db.update(TABLE_PEOPLE, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(people.getID()) });
    }
    
    public void deleteFinance(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Deleting row
        db.delete(TABLE_FINANCE, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}