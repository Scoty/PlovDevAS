package com.proxiad.plovdev.utils.parsers;

import android.content.Context;
import android.util.Log;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.beans.PartnerBean;
import com.proxiad.plovdev.beans.SpeakerBean;
import com.proxiad.plovdev.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DataParser {

    public static final String URL_WEB_PAGE = "http://2014.plovdev.com/";
    public static final String URL_SPEAKERS = URL_WEB_PAGE + "data/speakers.json";
    public static final String URL_LECTURES = URL_WEB_PAGE + "data/program.json";
    public static final String URL_PARTNERS = URL_WEB_PAGE + "data/partners.json";
    private static final String LOG_TAG = "DataParser";
    public static Context context;
    private static boolean isDataParsed;
    private static boolean isDatabaseFull;

    private static List<LectureBean> lectures;
    private static List<SpeakerBean> speakers;
    private static List<SpeakerBean> speakersClean;
    private static List<PartnerBean> partners;

    private static void parseData() {

        ImageUtils.putPlaceHolderDrawablesInCache(context);
        //adapter to connect to the database
        DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
        dbAdapter.open();

        speakers = new SpeakersParser(context, dbAdapter).getList();
        //TODO FIX ME: Awful design, the dataParser below touches the speakers ArrayList
        speakers.add(new SpeakerBean("signin", new ArrayList<LectureBean>()));
        speakers.add(new SpeakerBean("food", new ArrayList<LectureBean>()));
        speakers.add(new SpeakerBean("coffee", new ArrayList<LectureBean>()));
        speakers.add(new SpeakerBean("comments-alt", new ArrayList<LectureBean>()));
        speakers.add(new SpeakerBean("signout", new ArrayList<LectureBean>()));


        lectures = new LecturesParser(context, dbAdapter).getList();
        partners = new PartnersParser(context, dbAdapter).getList();
        //additional crap to match speakers with lectures

        for (LectureBean lecture : lectures) {
            for (SpeakerBean speaker : speakers) {
                if (lecture.getIdSpeaker().equals(speaker.getSpeakerId())) {
                    Log.wtf(LOG_TAG, "Match: " + lecture.getIdSpeaker() + " = " + speaker.getSpeakerId());
                    lecture.setSpeaker(speaker);
                    speaker.getLectures().add(lecture);
                }
            }
        }
        // TODO FIX ME: Clear this logic, it is bad design
        speakersClean = new ArrayList<SpeakerBean>(speakers);

        ListIterator<SpeakerBean> listIterator = speakersClean.listIterator();

        while (listIterator.hasNext()) {
            SpeakerBean speaker = listIterator.next();
            if (speaker.getImgUrl() == null) {
                listIterator.remove();
            }
        }

        isDataParsed = true;
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
}
