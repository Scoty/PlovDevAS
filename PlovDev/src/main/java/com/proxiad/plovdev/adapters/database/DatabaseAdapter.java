package com.proxiad.plovdev.adapters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Includes inner DataBase Helper class
 */
public class DatabaseAdapter {
    //database info
    private static final String DATABASE_NAME = "PlovDevDatabase";
    private static final int DATABASE_VERSION = 1;
    //tables:
    private static final String DATABASE_TABLE_SPEAKERS = "speakers";
    private static final String DATABASE_TABLE_LECTURES = "lectures";
    private static final String DATABASE_TABLE_PARTNERS = "partners";

    //the primary key for all the tables that use ContentProvider should be called _id
    private static final String KEY_ID_PK = "_id";
    //speakers table:
    //KEY_ID_PK
    private static final String KEY_NAME = "name";
    private static final String KEY_IMG_URL = "img_url";
    private static final String KEY_PAGE_URL = "page_url";
    private static final String KEY_COMP_NAME = "comp_name";
    private static final String KEY_COMP_URL = "comp_url";
    private static final String KEY_BIO = "bio";
    //lectures table:
    //KEY_ID_PK
    //KEY_NAME
    private static final String KEY_START_TIME_STR = "start_time_str";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_DESC = "desc";
    private static final String KEY_SPEAKER_ALIAS = "speaker_alias";
    //partners table:
    //KEY_ID_PK
    //KEY_PAGE_URL

    private static final String CREATE_TABLE_SPEAKERS = "CREATE TABLE" +
            DATABASE_TABLE_SPEAKERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_IMG_URL + " TEXT, " +
            KEY_PAGE_URL + " TEXT, " +
            KEY_COMP_NAME + " TEXT, " +
            KEY_COMP_URL + " TEXT, " +
            KEY_BIO + " TEXT);";

    private static final String CREATE_TABLE_LECTURES = "CREATE TABLE" +
            DATABASE_TABLE_LECTURES + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_START_TIME_STR + " TEXT NOT NULL, " +
            KEY_START_TIME + " DATETIME NOT NULL, " +
            KEY_DESC + " TEXT, " +
            KEY_SPEAKER_ALIAS + " TEXT);";

    private static final String CREATE_TABLE_PARTNERS = "CREATE TABLE" +
            DATABASE_TABLE_PARTNERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_PAGE_URL + " TEXT);";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SPEAKERS);
            db.execSQL(CREATE_TABLE_LECTURES);
            db.execSQL(CREATE_TABLE_PARTNERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE + DATABASE_TABLE_SPEAKERS);
            db.execSQL(DROP_TABLE + DATABASE_TABLE_LECTURES);
            db.execSQL(DROP_TABLE + DATABASE_TABLE_PARTNERS);
        }
    }

    public DatabaseAdapter open() {
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertSpeaker(String name, String imgUrl, String pageUrl, String compName, String compUrl, String bio) {
        if (name == null) {
            return -1;
        } else {
            ContentValues speakerValues = new ContentValues();
            speakerValues.put(KEY_NAME, name);
            speakerValues.put(KEY_IMG_URL, imgUrl);
            speakerValues.put(KEY_PAGE_URL, pageUrl);
            speakerValues.put(KEY_COMP_NAME, compName);
            speakerValues.put(KEY_COMP_URL, compUrl);
            speakerValues.put(KEY_BIO, bio);
            return db.insert(DATABASE_TABLE_SPEAKERS, null, speakerValues);
        }
    }
}
