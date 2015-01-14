package com.proxiad.plovdev.adapters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proxiad.plovdev.adapters.database.structure.DbPlovDev;
import com.proxiad.plovdev.adapters.database.structure.TableLectures;
import com.proxiad.plovdev.adapters.database.structure.TablePartners;
import com.proxiad.plovdev.adapters.database.structure.TableSpeakers;

/**
 * Includes inner DataBase Helper class
 */
public class DatabaseAdapter {

    private static final String LOG_TAG = "DatabaseAdapter";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
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
            speakerValues.put(TableSpeakers.KEY_NAME.toString(), name);
            speakerValues.put(TableSpeakers.KEY_USID.toString(), usid);
            speakerValues.put(TableSpeakers.KEY_IMG_URL.toString(), imgUrl);
            speakerValues.put(TableSpeakers.KEY_PAGE_URL.toString(), pageUrl);
            speakerValues.put(TableSpeakers.KEY_COMP_NAME.toString(), compName);
            speakerValues.put(TableSpeakers.KEY_COMP_URL.toString(), compUrl);
            speakerValues.put(TableSpeakers.KEY_BIO.toString(), bio);
            return db.insert(DbPlovDev.TABLE_SPEAKERS.toString(), null, speakerValues);
        }
    }

    public boolean updateSpeaker() {
        return true;
    }

    public boolean deleteSpeaker(long rowId) {
        return db.delete(DbPlovDev.TABLE_SPEAKERS.toString(), TableSpeakers.KEY_ID_PK + "=" + rowId, null) > 0;
    }

    public boolean deleteAllSpeakers() {
        return db.delete(DbPlovDev.TABLE_SPEAKERS.toString(), null, null) > 0;
    }

    public Cursor getAllSpeakers() {
        return db.query(DbPlovDev.TABLE_SPEAKERS.toString(), TableSpeakers.KEY_ID_PK.getColumns(), null, null, null, null, null);
    }

    public Cursor getSpeaker(long rowId) {
        Cursor cursor = db.query(true, DbPlovDev.TABLE_SPEAKERS.toString(), TableSpeakers.KEY_ID_PK.getColumns(), TableSpeakers.KEY_ID_PK + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long getSpeakersCount() {
        long count = 0;
        Cursor cursor = db.query(DbPlovDev.TABLE_SPEAKERS.toString(), new String[]{TableSpeakers.KEY_ID_PK.toString()}, null, null, null, null, null);
        count = cursor.getCount();
        return count;
    }

    //Queries for the LECTURES table
    public long insertLecture(String name, String startTimeAsString, String desc, String speakerUsid) {
        if (name == null) {
            return -1;
        } else {
            ContentValues lectureValues = new ContentValues();
            lectureValues.put(TableLectures.KEY_NAME.toString(), name);
            lectureValues.put(TableLectures.KEY_START_TIME_STR.toString(), startTimeAsString);
            if (startTimeAsString != null) {
                //TODO Use date to store the DateTime as ms in KEY_START_TIME
            }
            lectureValues.put(TableLectures.KEY_DESC.toString(), desc);
            lectureValues.put(TableLectures.KEY_SPEAKER_USID.toString(), speakerUsid);
            return db.insert(DbPlovDev.TABLE_LECTURES.toString(), null, lectureValues);
        }
    }

    public boolean updateLecture() {
        return true;
    }

    public boolean deleteLecture(long rowId) {
        return db.delete(DbPlovDev.TABLE_LECTURES.toString(), TableLectures.KEY_ID_PK.toString() + "=" + rowId, null) > 0;
    }

    public boolean deleteAllLectures() {
        return db.delete(DbPlovDev.TABLE_LECTURES.toString(), null, null) > 0;
    }

    public Cursor getAllLectures() {
        return db.query(DbPlovDev.TABLE_LECTURES.toString(), TableLectures.KEY_ID_PK.getColumns(), null, null, null, null, null);
    }

    public Cursor getLecture(long rowId) {
        Cursor cursor = db.query(true, DbPlovDev.TABLE_LECTURES.toString(), TableLectures.KEY_ID_PK.getColumns(), TableLectures.KEY_ID_PK + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long getLecturesCount() {
        long count = 0;
        Cursor cursor = db.query(DbPlovDev.TABLE_LECTURES.toString(), new String[]{TableLectures.KEY_ID_PK.toString()}, null, null, null, null, null);
        count = cursor.getCount();
        return count;
    }

    //Queries for the PARTNERS table
    public long insertPartner(String title, String pageUrl, String logo) {
        if (pageUrl == null) {
            return -1;
        } else {
            ContentValues partnerValues = new ContentValues();
            partnerValues.put(TablePartners.KEY_TITLE.toString(), title);
            partnerValues.put(TablePartners.KEY_PAGE_URL.toString(), pageUrl);
            partnerValues.put(TablePartners.KEY_LOGO.toString(), logo);
            return db.insert(DbPlovDev.TABLE_PARTNERS.toString(), null, partnerValues);
        }
    }

    public boolean updatePartner() {
        return true;
    }

    public boolean deletePartner(long rowId) {
        return db.delete(DbPlovDev.TABLE_PARTNERS.toString(), TablePartners.KEY_ID_PK.toString() + "=" + rowId, null) > 0;
    }

    public boolean deleteAllPartners() {
        return db.delete(DbPlovDev.TABLE_PARTNERS.toString(), null, null) > 0;
    }

    public Cursor getAllPartners() {
        return db.query(DbPlovDev.TABLE_PARTNERS.toString(), TablePartners.KEY_ID_PK.getColumns(), null, null, null, null, null);
    }

    public Cursor getPartner(long rowId) {
        Cursor cursor = db.query(true, DbPlovDev.TABLE_PARTNERS.toString(), TablePartners.KEY_ID_PK.getColumns(), TablePartners.KEY_ID_PK.toString() + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public long getPartnersCount() {
        long count = 0;
        Cursor cursor = db.query(DbPlovDev.TABLE_PARTNERS.toString(), new String[]{TablePartners.KEY_ID_PK.toString()}, null, null, null, null, null);
        count = cursor.getCount();
        return count;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DbPlovDev.DATABASE_NAME, null, DbPlovDev.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TableSpeakers.CREATE_TABLE_SPEAKERS);
            db.execSQL(TableLectures.CREATE_TABLE_LECTURES);
            db.execSQL(TablePartners.CREATE_TABLE_PARTNERS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE + DbPlovDev.TABLE_SPEAKERS);
            db.execSQL(DROP_TABLE + DbPlovDev.TABLE_LECTURES);
            db.execSQL(DROP_TABLE + DbPlovDev.TABLE_PARTNERS);
            onCreate(db);
        }
    }

}
