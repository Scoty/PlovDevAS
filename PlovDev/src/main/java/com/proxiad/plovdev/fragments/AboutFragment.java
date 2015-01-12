package com.proxiad.plovdev.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.proxiad.plovdev.R;

public class AboutFragment extends Fragment {

	private final String calendarUrl = "http://www.google.com/calendar/event?action=TEMPLATE&text=PlovDev&dates=20141018/20141018&details=&location=%D0%9F%D0%BB%D0%BE%D0%B2%D0%B4%D0%B8%D0%B2%2C%20%D0%9F%D0%BB%D0%BE%D0%B2%D0%B4%D0%B8%D0%B2%D1%81%D0%BA%D0%B8%20%D0%BF%D0%B0%D0%BD%D0%B0%D0%B8%D1%80%2C%20%D0%9F%D0%B0%D0%BB%D0%B0%D1%82%D0%B0%20%D0%9A%D0%B8%D1%80%D0%BE%D0%B2&trp=false&sprop=&sprop=name:";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_about, container, false);
		ImageButton buttonCalendar = (ImageButton) view.findViewById(R.id.button_gooogle_calendar);
		buttonCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent startCalendar = new Intent(Intent.ACTION_VIEW);
				startCalendar.setData(Uri.parse(calendarUrl));
				startActivity(startCalendar);
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!this.isHidden()) {
			getActivity().getActionBar().setTitle(R.string.about);
		}
	}
}
