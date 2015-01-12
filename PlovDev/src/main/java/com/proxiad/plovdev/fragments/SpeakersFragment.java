package com.proxiad.plovdev.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.activities.SpeakerDetailsActivity;
import com.proxiad.plovdev.adapters.SpeakerAdapter;
import com.proxiad.plovdev.utils.DataParser;

public class SpeakersFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SpeakerAdapter adapter = new SpeakerAdapter(getActivity(), DataParser.getSpeakers());
		setListAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!this.isHidden()) {
			getActivity().getActionBar().setTitle(R.string.speakers);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		super.onListItemClick(l, v, pos, id);
		Intent intent = new Intent(getActivity(), SpeakerDetailsActivity.class);
		intent.putExtra("position", pos);
		startActivity(intent);
	}

}
