package com.proxiad.plovdev.adapters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.database.DatabaseUtilsCompat;

/**
 * Includes inner DataBase Helper class
 */
public class DatabaseAdapter {
    private static final String LOG_TAG = "DatabaseAdapter";
    //database info
    private static final String DATABASE_NAME = "plovDev.db";
    private static final int DATABASE_VERSION = 1;
    //tables:
    private static final String DATABASE_TABLE_SPEAKERS = "speakers";
    private static final String DATABASE_TABLE_LECTURES = "lectures";
    private static final String DATABASE_TABLE_PARTNERS = "partners";

    //the primary key for all the tables that use ContentProvider should be called _id
    private static final String KEY_ID_PK = "_id";
    //speakers table:
    //KEY_ID_PK
    private static final String KEY_USID = "usid";
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
    private static final String KEY_SPEAKER_USID = "speaker_usid";
    //partners table:
    //KEY_ID_PK
    private static final String KEY_TITLE = "title";
    //KEY_PAGE_URL
    private static final String KEY_LOGO = "logo";

    private static final String CREATE_TABLE_SPEAKERS = "CREATE TABLE " +
            DATABASE_TABLE_SPEAKERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_USID + " TEXT NOT NULL, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_IMG_URL + " TEXT NOT NULL, " +
            KEY_PAGE_URL + " TEXT NOT NULL, " +
            KEY_COMP_NAME + " TEXT NOT NULL, " +
            KEY_COMP_URL + " TEXT NOT NULL, " +
            KEY_BIO + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_LECTURES = "CREATE TABLE " +
            DATABASE_TABLE_LECTURES + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_START_TIME_STR + " TEXT NOT NULL, " +
            KEY_START_TIME + " DATETIME, " +
            KEY_DESC + " TEXT NOT NULL, " +
            KEY_SPEAKER_USID + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PARTNERS = "CREATE TABLE " +
            DATABASE_TABLE_PARTNERS + "(" +
            KEY_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TITLE + " TEXT NOT NULL, " +
            KEY_PAGE_URL + " TEXT NOT NULL, " +
            KEY_LOGO + " TEXT NOT NULL);";

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
            onCreate(db);
        }
    }

    public DatabaseAdapter open() {
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    //Queries for the SPEAKERS table
    public long insertSpeaker(String name, String usid, String imgUrl, String pageUrl, String compName, String compUrl, String bio) {
        if (name == null || usid == null) {
            return -1;
        } else {
            ContentValues speakerValues = new ContentValues();
            speakerValues.put(KEY_NAME, name);
            speakerValues.put(KEY_USID, usid);
            speakerValues.put(KEY_IMG_URL, imgUrl);
            speakerValues.put(KEY_PAGE_URL, pageUrl);
            speakerValues.put(KEY_COMP_NAME, compName);
            speakerValues.put(KEY_COMP_URL, compUrl);
            speakerValues.put(KEY_BIO, bio);
            return db.insert(DATABASE_TABLE_SPEAKERS, null, speakerValues);
        }
    }

    public boolean updateSpeaker() {
        return true;
    }

    public boolean deleteSpeaker(long rowId) {
        return db.delete(DATABASE_TABLE_SPEAKERS, KEY_ID_PK + "=" + rowId, null) > 0;
    }

    String[] columnsSpeakers = {KEY_ID_PK, KEY_USID, KEY_NAME, KEY_IMG_URL, KEY_PAGE_URL, KEY_COMP_NAME, KEY_COMP_URL, KEY_BIO};

    public Cursor getAllSpeakers() {
        return db.query(DATABASE_TABLE_SPEAKERS, columnsSpeakers, null, null, null, null, null);
    }

    public Cursor getSpeaker(long rowId) {
        Cursor cursor = db.query(true, DATABASE_TABLE_SPEAKERS, columnsSpeakers, KEY_ID_PK + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Queries for the LECTURES table
    public long insertLecture(String name, String startTimeAsString, String desc, String speakerUsid) {
        if (name == null) {
            return -1;
        } else {
            ContentValues lectureValues = new ContentValues();
            lectureValues.put(KEY_NAME, name);
            lectureValues.put(KEY_START_TIME_STR, startTimeAsString);
            if (startTimeAsString != null) {
                //TODO Use date to store the DateTime as ms in KEY_START_TIME
            }
            lectureValues.put(KEY_DESC, desc);
            lectureValues.put(KEY_SPEAKER_USID, speakerUsid);
            return db.insert(DATABASE_TABLE_LECTURES, null, lectureValues);
        }
    }

    public boolean updateLecture() {
        return true;
    }

    public boolean deleteLecture(long rowId) {
        return db.delete(DATABASE_TABLE_LECTURES, KEY_ID_PK + "=" + rowId, null) > 0;
    }

    String[] columnsLectures = {KEY_ID_PK, KEY_NAME, KEY_START_TIME_STR, KEY_START_TIME, KEY_DESC, KEY_SPEAKER_USID};

    public Cursor getAllLectures() {
        return db.query(DATABASE_TABLE_LECTURES, columnsLectures, null, null, null, null, null);
    }

    public Cursor getLecture(long rowId) {
        Cursor cursor = db.query(true, DATABASE_TABLE_LECTURES, columnsLectures, KEY_ID_PK + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Queries for the PARTNERS table
    public long insertPartner(String title, String pageUrl, String logo) {
        if (pageUrl == null) {
            return -1;
        } else {
            ContentValues partnerValues = new ContentValues();
            partnerValues.put(KEY_TITLE, title);
            partnerValues.put(KEY_PAGE_URL, pageUrl);
            partnerValues.put(KEY_LOGO, logo);
            return db.insert(DATABASE_TABLE_PARTNERS, null, partnerValues);
        }
    }

    public boolean updatePartner() {
        return true;
    }

    public boolean deletePartner(long rowId) {
        return db.delete(DATABASE_TABLE_PARTNERS, KEY_ID_PK + "=" + rowId, null) > 0;
    }

    public boolean deleteAllPartners() {
        return db.delete(DATABASE_TABLE_PARTNERS, null, null) > 0;
    }

    String[] columnsPartners = {KEY_ID_PK, KEY_TITLE, KEY_PAGE_URL, KEY_LOGO};

    public Cursor getAllPartners() {
        return db.query(DATABASE_TABLE_PARTNERS, columnsPartners, null, null, null, null, null);
    }

    public Cursor getPartner(long rowId) {
        Cursor cursor = db.query(true, DATABASE_TABLE_PARTNERS, columnsPartners, KEY_ID_PK + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long getPartnersCount() {
        long count = 0;
        Cursor cursor = db.query(DATABASE_TABLE_PARTNERS, new String[]{KEY_ID_PK}, null, null, null, null, null);
        count = cursor.getCount();
        return count;
    }

}
