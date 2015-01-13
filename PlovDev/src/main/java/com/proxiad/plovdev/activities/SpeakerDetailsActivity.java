package com.proxiad.plovdev.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.adapters.LectureForSpeakerAdapter;
import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.beans.SpeakerBean;
import com.proxiad.plovdev.utils.parsers.DataParser;
import com.proxiad.plovdev.utils.ImageUtils;

public class SpeakerDetailsActivity extends ListActivity {
    private int position;
    private SpeakerBean speaker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt("position");
        } else {
            this.position = getIntent().getIntExtra("position", 0);
        }
        speaker = DataParser.getSpeaker(position);
        setContentView(R.layout.activity_speaker_details);
        @SuppressLint("InflateParams")
        View header = getLayoutInflater().inflate(R.layout.header_lecture_for_speaker, null);
        // no need for parent view! FCK LINT!

        ImageView speakerImageView = (ImageView) header.findViewById(R.id.imageSpeakerPortrait);
        TextView nameSpeakerView = (TextView) header.findViewById(R.id.nameSpeaker);
        TextView bioSpeakerView = (TextView) header.findViewById(R.id.bioSpeaker);

        speakerImageView.setImageDrawable(speaker.getPortraitDrawable());
        nameSpeakerView.setText(speaker.getName());
        bioSpeakerView.setText(Html.fromHtml(speaker.getBio()));

        ListView listView = getListView();
        listView.addHeaderView(header);
        LectureForSpeakerAdapter adapter = new LectureForSpeakerAdapter(this, speaker.getLectures());
        setListAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", position);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(R.string.title_activity_speaker_details);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtils.cleanupCache();
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        if (pos != 0) {
            pos = pos - 1;
            Intent intent = new Intent(this, LectureDetailsActivity.class);
            intent.putExtra("position", pos);
            intent.putExtra("lectureList", (ArrayList<LectureBean>) speaker.getLectures());
            startActivity(intent);
        }
    }
}
