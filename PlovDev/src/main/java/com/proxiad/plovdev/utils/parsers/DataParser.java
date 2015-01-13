package com.proxiad.plovdev.utils.parsers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.beans.PartnerBean;
import com.proxiad.plovdev.beans.SpeakerBean;
import com.proxiad.plovdev.utils.ImageUtils;
import com.proxiad.plovdev.utils.ImportUtils;
import com.proxiad.plovdev.utils.tasks.ReadJsonTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class DataParser {

    private static final String LOG_TAG = "DataParser";

    public static Context context;

    public static final String URL_WEB_PAGE = "http://2014.plovdev.com/";
    public static final String URL_SPEAKERS = URL_WEB_PAGE + "data/speakers.json";
    public static final String URL_LECTURES = URL_WEB_PAGE + "data/program.json";
    public static final String URL_PARTNERS = URL_WEB_PAGE + "data/partners.json";

    private static boolean isDataParsed;
    private static boolean isDatabaseFull;

    private static List<LectureBean> lectures;
    private static List<SpeakerBean> speakers;
    private static List<SpeakerBean> speakersClean;
    private static List<PartnerBean> partners;

    private static void parseData() {
        //adapter to connect to the database
        DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
        dbAdapter.open();
        // parse speakers json
        String speakersJsonString = null;
        try {
            speakersJsonString = new ReadJsonTask().execute(URL_SPEAKERS).get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Task interrupted!", e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Execution failed!", e);
        }
        JSONArray speakersJsonArray;

        if (speakersJsonString != null) {
            try {
                speakersJsonArray = new JSONArray(speakersJsonString);
                speakers = new ArrayList<SpeakerBean>();
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
                        Bitmap bitmap = ImageUtils.load(URL_WEB_PAGE + imgUrl).bitmap;
                        if (bitmap != null) {
                            ImportUtils.addSpeakerPortraitToCache(speakerId, bitmap);
                            speakerDrawable = ImageUtils.getCachedDrawable(speakerId);
                        } else {
                            speakerDrawable = ImageUtils.getDefaultDrawable(context);
                        }

                    }
                    SpeakerBean speaker = new SpeakerBean(speakerId, name, imgUrl, personalPageUrl, companyName, companyUrl, bio,
                            new ArrayList<LectureBean>());
                    speakers.add(speaker);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception!", e);
            }
        }

        // parse lectures json
        String lecturesJsonString = null;
        try {
            lecturesJsonString = new ReadJsonTask().execute(URL_LECTURES).get();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Task interupted!", e);
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Execution failed!", e);
        }
        JSONArray lecturesJsonArray;
        if (lecturesJsonString != null) {
            try {
                lecturesJsonArray = new JSONArray(lecturesJsonString);
                lectures = new ArrayList<LectureBean>();
                for (int i = 0; i < lecturesJsonArray.length(); i++) {
                    JSONObject lectureJson = lecturesJsonArray.getJSONObject(i);
                    String startTimeAsString = getStringFromJson(lectureJson, "start");
                    String name = getStringFromJson(lectureJson, "title");

                    String speakerId;
                    if (lectureJson.has("speaker")) {
                        speakerId = getStringFromJson(lectureJson.getJSONObject("speaker"), "id");
                    } else {
                        speakerId = getStringFromJson(lectureJson, "icon");
                        // add new placeholder speaker for the image
                        SpeakerBean speaker = new SpeakerBean(speakerId, new ArrayList<LectureBean>());
                        // TODO may be use Set here instead
                        if (!speakers.contains(speaker)) {
                            speakers.add(new SpeakerBean(speakerId, new ArrayList<LectureBean>()));
                        }
                    }
                    LectureBean lecture = new LectureBean(startTimeAsString, name, speakerId);
                    lectures.add(lecture);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON Exception!", e);
            }
        }

        for (LectureBean lecture : lectures) {
            for (SpeakerBean speaker : speakers) {
                if (lecture.getIdSpeaker().equals(speaker.getSpeakerId())) {
                    lecture.setSpeaker(speaker);
                    speaker.getLectures().add(lecture);
                }
            }
        }
        // TODO Clear this logic, it is bad design
        speakersClean = new ArrayList<SpeakerBean>(speakers);

        ListIterator<SpeakerBean> listIterator = speakersClean.listIterator();

        while (listIterator.hasNext()) {
            SpeakerBean speaker = listIterator.next();
            if (speaker.getImgUrl() == null) {
                listIterator.remove();
            }
        }

        ImageUtils.putPlaceHolderDrawablesInCache(context);

        // parse partners json
        partners = new PartnersParser(context, dbAdapter).getList();
        isDataParsed = true;
    }

    private static void parseDataFromDataBase() {

    }

    public static void getDataFromJSONs() {

    }


    public static List<LectureBean> getLectures() {
        if (!isDataParsed) {
            parseData();
        }
        return lectures;
    }

    public static List<SpeakerBean> getSpeakers() {
        if (!isDataParsed) {
            parseData();
        }
        return speakersClean;
    }

    public static List<PartnerBean> getPartners() {
        return partners;
    }

    public static LectureBean getLecture(int location) {
        return lectures.get(location);
    }

    public static SpeakerBean getSpeaker(int location) {
        return speakersClean.get(location);
    }

    public static void refreshData() {
//        ImageUtils.invalidateCache();
//        isDataParsed = false;
    }

    private static String getStringFromJson(JSONObject json, String name) throws JSONException {
        if (json.has(name)) {
            return json.getString(name);
        }
        return "";
    }
}
