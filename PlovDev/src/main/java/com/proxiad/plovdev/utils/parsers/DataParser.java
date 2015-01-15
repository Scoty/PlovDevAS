package com.proxiad.plovdev.utils.parsers;

import android.content.Context;

import com.proxiad.plovdev.adapters.database.DatabaseAdapter;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.beans.PartnerBean;
import com.proxiad.plovdev.beans.SpeakerBean;
import com.proxiad.plovdev.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class DataParser {

    public static final String URL_WEB_PAGE = "http://2014.plovdev.com/";
    public static final String URL_SPEAKERS = URL_WEB_PAGE + "data/speakers.json";
    public static final String URL_LECTURES = URL_WEB_PAGE + "data/program.json";
    public static final String URL_PARTNERS = URL_WEB_PAGE + "data/partners.json";
    private static final String LOG_TAG = "DataParser";
    public static Context context;
    private static boolean isDataParsed;

    private static List<LectureBean> lectures;
    private static List<SpeakerBean> speakers;
    private static List<PartnerBean> partners;

    private static void parseData() {
        //adapter to connect to the database
        DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
        dbAdapter.open();
        speakers = new SpeakersParser(context, dbAdapter).getList();
        //put a placeholder images for the lectures without real speakers in the image cache and create the fake speakers
        List<SpeakerBean> speakersWithAddedPlaceholderSpeakers = new ArrayList<SpeakerBean>(speakers);
        String[] imgIds = ImageUtils.putPlaceHolderDrawablesInCache(context);
        for (int i = 0; i < imgIds.length; i++) {
            speakersWithAddedPlaceholderSpeakers.add(new SpeakerBean(imgIds[i], new ArrayList<LectureBean>()));
        }
        lectures = new LecturesParser(context, dbAdapter).getList();
        partners = new PartnersParser(context, dbAdapter).getList();
        //add speaker to each lecture and populate the list of lectures for each speaker
        for (LectureBean lecture : lectures) {
            for (SpeakerBean speaker : speakersWithAddedPlaceholderSpeakers) {
                if (lecture.getIdSpeaker().equals(speaker.getSpeakerId())) {
                    lecture.setSpeaker(speaker);
                    speaker.getLectures().add(lecture);
                }
            }
        }
       /* for each lecture without speaker put error speaker to prevent nulls when getting the image of the lecture,
        this should never happen!*/
        for (LectureBean lecture : lectures) {
            if (lecture.getSpeaker() == null) {
                SpeakerBean speakerError = new SpeakerBean("ersror", new ArrayList<LectureBean>());
                lecture.setSpeaker(speakerError);
            }
        }
        //close the database
        dbAdapter.close();
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
        return speakers;
    }

    public static List<PartnerBean> getPartners() {
        if (!isDataParsed) {
            parseData();
        }
        return partners;
    }

    public static LectureBean getLecture(int location) {
        return lectures.get(location);
    }

    public static SpeakerBean getSpeaker(int location) {
        return speakers.get(location);
    }

    public static void refreshData() {
        ImageUtils.invalidateCache();
        DatabaseAdapter dbAdapter = new DatabaseAdapter(context);
        dbAdapter.open();
        dbAdapter.clearDatabase();
        dbAdapter.close();
        parseData();
    }
}
