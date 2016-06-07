package com.androidapps.swathi.communityhelpapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swathi on 5/26/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    final private String TABLE_NAME = "USER_REQUEST";
    public final String GROUPBY_ASCENDING = "ASC";
    public final String GROUPBY_DESCENDING = "DESC";
    public final String REQUEST_NUMBER = "requestNumber INTEGER PRIMARY KEY,";
    public final String REQUESTOR_NAME = "requestorName TEXT NOT NULL,";
    public final String ACTIVITY_NAME = "activity TEXT NOT NULL,";
    public final String MESSAGE_TEXT = "message TEXT NOT NULL,";
    public final String REQUEST_STATUS = "requestStatus TEXT NOT NULL,";
    public final String NUMBER_OF_REQUESTS = "numberOfRequests INTEGER NOT NULL";
    public final String REQUEST_NUMBER_COLUMN = "requestNumber";
    public final String REQUESTOR_NAME_COLUMN = "requestorName";
    public final String ACTIVITY_NAME_COLUMN = "activity";
    public final String MESSAGE_TEXT_COLUMN = "message";
    public final String REQUEST_STATUS_COLUMN = "requestStatus";
    public final String NUMBER_OF_REQUESTS_COLUMN = "numberOfRequests";
    public final String SPACE = " ";
    public String column_names[] = {
            REQUEST_NUMBER,
            REQUESTOR_NAME,
            ACTIVITY_NAME,
            MESSAGE_TEXT,
            REQUEST_STATUS,
            NUMBER_OF_REQUESTS
    };
    public DataBaseHelper(final Context context){
        super(context,"community_app.db",null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final StringBuffer createTable = new StringBuffer();
        createTable.append("CREATE TABLE "+TABLE_NAME+" ( ");
        for(int i=0;i<column_names.length;i++){
                createTable.append(column_names[i]);
        }
        createTable.append(SPACE);
        createTable.append(")");
        db.execSQL(createTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public void insertRequestDetails(final UserRequest requestItem) {

        final SQLiteDatabase database = this.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(REQUESTOR_NAME_COLUMN, requestItem.getRequestorName());
        values.put(ACTIVITY_NAME_COLUMN, requestItem.getActivity());
        values.put(MESSAGE_TEXT_COLUMN,requestItem.getMessage());
        values.put(REQUEST_STATUS_COLUMN,requestItem.getRequestStatus());
        values.put(NUMBER_OF_REQUESTS_COLUMN,requestItem.getNumberOfRequests());
        database.insert(TABLE_NAME, null, values);
        database.close();

    }
    public List<UserRequest> getAllRequests(){
        List<UserRequest> requests = new ArrayList<UserRequest>();
        String selectQuery = "Select * FROM "+TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UserRequest req = new UserRequest();
                req.setRequestorName(cursor.getString(1));
                req.setActivity(cursor.getString(2));
                req.setNumberOfRequests(cursor.getInt(5));
                req.setMessage(cursor.getString(3));
                req.setRequestStatus(cursor.getString(4));
                requests.add(req);
            }while(cursor.moveToNext());
        }
        return requests;
    }

}
