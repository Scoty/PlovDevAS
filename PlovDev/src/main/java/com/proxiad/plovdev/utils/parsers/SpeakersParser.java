package com.proxiad.plovdev.utils.parsers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.adapters.database.structure.TableSpeakers;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.beans.SpeakerBean;
import com.proxiad.plovdev.utils.ImageUtils;
import com.proxiad.plovdev.utils.ImportUtils;
import com.proxiad.plovdev.utils.tasks.ReadJsonTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpeakersParser extends JsonToListParser {

    private static final String LOG_TAG = "SpeakersParser";
    private Context context;
    private DatabaseAdapter dbAdapter;
    private List<SpeakerBean> speakers;

    public SpeakersParser(Context context, DatabaseAdapter dbAdapter) {
        super(context, dbAdapter);
        this.context = context;
        this.dbAdapter = dbAdapter;
    }

    @Override
    public List getList() {
        if (dbAdapter.getSpeakersCount() <= 0) {
            parseJson();
        }

        parseDataFromDb();
        //TODO this can return null
        return speakers;
    }

    @Override
    public boolean parseDataFromDb() {
        Cursor speakersCursor = dbAdapter.getAllSpeakers();
        if (speakersCursor.moveToFirst()) {
            speakers = new ArrayList<SpeakerBean>();
            do {
                speakers.add(new SpeakerBean(
                        speakersCursor.getString(TableSpeakers.KEY_USID.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_NAME.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_IMG_URL.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_PAGE_URL.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_COMP_NAME.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_COMP_URL.getIndex()),
                        speakersCursor.getString(TableSpeakers.KEY_BIO.getIndex()),
                        new ArrayList<LectureBean>()
                ));
            } while (speakersCursor.moveToNext());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean parseJson() {
        String speakersJsonString = null;
        try {
            speakersJsonString = new ReadJsonTask().execute(DataParser.URL_SPEAKERS).get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Task interrupted!", e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Execution failed!", e);
        }
        JSONArray speakersJsonArray;

        if (speakersJsonString != null) {
            try {
                speakersJsonArray = new JSONArray(speakersJsonString);
                dbAdapter.deleteAllSpeakers();

                for (int i = 0; i < speakersJsonArray.length(); i++) {
                    JSONObject speakerJson = speakersJsonArray.getJSONObject(i);

                    String speakerId = getStringFromJson(speakerJson, "id");
                    String name = getStringFromJson(speakerJson, "name");
                    String imgUrl = getStringFromJson(speakerJson, "img");
                    String personalPageUrl = getStringFromJson(speakerJson, "personalPage");
                    String companyName = "";
                    String companyUrl = "";
                    if (speakerJson.has("company")) {
                        JSONObject company = speakerJson.getJSONObject("company");
                        companyName = getStringFromJson(company, "name");
                        companyUrl = getStringFromJson(company, "url");
                    }
                    String bio = getStringFromJson(speakerJson, "resume");

                    Drawable speakerDrawable = ImageUtils.getCachedDrawable(speakerId);

                    if (speakerDrawable == null) {
                        Bitmap bitmap = ImageUtils.load(DataParser.URL_WEB_PAGE + imgUrl).bitmap;
                        if (bitmap != null) {
                            ImportUtils.addSpeakerPortraitToCache(speakerId, bitmap);
                            speakerDrawable = ImageUtils.getCachedDrawable(speakerId);
                        } else {
                            speakerDrawable = ImageUtils.getDefaultDrawable(context);
                        }
                    }
                    dbAdapter.insertSpeaker(name, speakerId, imgUrl, personalPageUrl, companyName, companyUrl, bio);
                }
                return true;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception!", e);
            }
        }
        return false;
    }
}
