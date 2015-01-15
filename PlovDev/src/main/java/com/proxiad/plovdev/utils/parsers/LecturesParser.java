package com.proxiad.plovdev.utils.parsers;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.adapters.database.structure.TableLectures;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.utils.tasks.ReadJsonTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LecturesParser extends JsonToListParser {

    private static final String LOG_TAG = "LecturesParser";
    private Context context;
    private DatabaseAdapter dbAdapter;
    private List<LectureBean> lectures;

    public LecturesParser(Context context, DatabaseAdapter dbAdapter) {
        super(context, dbAdapter);
        this.context = context;
        this.dbAdapter = dbAdapter;
    }

    @Override
    public List getList() {
        if (dbAdapter.getLecturesCount() <= 0) {
            parseJson();
        }

        parseDataFromDb();
        //TODO this can return null
        return lectures;
    }

    @Override
    public boolean parseDataFromDb() {
        Cursor lecturesCursor = dbAdapter.getAllLectures();
        if (lecturesCursor.moveToFirst()) {
            lectures = new ArrayList<LectureBean>();
            do {
                lectures.add(new LectureBean(
                        lecturesCursor.getString(TableLectures.KEY_START_TIME_STR.getIndex()),
                        lecturesCursor.getString(TableLectures.KEY_NAME.getIndex()),
                        lecturesCursor.getString(TableLectures.KEY_SPEAKER_USID.getIndex())
                ));
            } while (lecturesCursor.moveToNext());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean parseJson() {
        String lecturesJsonString = null;
        try {
            lecturesJsonString = new ReadJsonTask().execute(DataParser.URL_LECTURES).get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Task interrupted!", e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Execution failed!", e);
        }
        JSONArray lecturesJsonArray;
        if (lecturesJsonString != null) {
            try {
                lecturesJsonArray = new JSONArray(lecturesJsonString);
                dbAdapter.deleteAllLectures();

                for (int i = 0; i < lecturesJsonArray.length(); i++) {
                    JSONObject lectureJson = lecturesJsonArray.getJSONObject(i);
                    String startTimeAsString = getStringFromJson(lectureJson, "start");
                    String name = getStringFromJson(lectureJson, "title");

                    String speakerId;
                    if (lectureJson.has("speaker")) {
                        speakerId = getStringFromJson(lectureJson.getJSONObject("speaker"), "id");
                    } else {
                        speakerId = getStringFromJson(lectureJson, "icon");
//                        // add new placeholder speaker for the image
//                        SpeakerBean speaker = new SpeakerBean(speakerId, new ArrayList<LectureBean>());
//                        // TODO may be use Set here instead. Adding unnecessary speakers here, just for the image! BAD BAD BAD!
//                        Log.wtf(LOG_TAG, "BEFORE Created new speaker: " + speakerId);
//                        if (!speakers.contains(speaker)) {
//                            speakers.add(new SpeakerBean(speakerId, new ArrayList<LectureBean>()));
//                            Log.wtf(LOG_TAG, "Created new speaker: " + speakerId);
//                        }
                    }
                    dbAdapter.insertLecture(name, startTimeAsString, "desc", speakerId);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception!", e);
            }
        }
        return false;
    }


}
