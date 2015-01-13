package com.proxiad.plovdev.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.activities.LectureDetailsActivity;
import com.proxiad.plovdev.adapters.LectureAdapter;
import com.proxiad.plovdev.utils.parsers.DataParser;

public class MainFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LectureAdapter adapter = new LectureAdapter(getActivity(), DataParser.getLectures());
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.isHidden()) {
            getActivity().getActionBar().setTitle(R.string.first_day);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        Intent intent = new Intent(getActivity(), LectureDetailsActivity.class);
        intent.putExtra("position", pos);
        startActivity(intent);
    }

}