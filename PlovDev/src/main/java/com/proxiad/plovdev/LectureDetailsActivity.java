package com.proxiad.plovdev;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.proxiad.plovdev.beans.LectureBean;
import com.proxiad.plovdev.utils.DataParser;
import com.proxiad.plovdev.utils.ImageUtils;

public class LectureDetailsActivity extends Activity {

	private int position;
	private float rating;
	private RatingBar ratingBar;
	private boolean isRateClicked;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			this.position = savedInstanceState.getInt("position");
			this.rating = savedInstanceState.getFloat("rating");
		} else {
			this.position = getIntent().getIntExtra("position", 0);
			this.rating = 0f;
		}
		List<LectureBean> lectureList = (ArrayList<LectureBean>) getIntent().getSerializableExtra("lectureList");
		LectureBean lecture;
		if (lectureList != null) {
			lecture = lectureList.get(position);
		} else {
			lecture = DataParser.getLecture(position);
		}

		setContentView(R.layout.activity_lecture_details);

		TextView timeTextView = (TextView) findViewById(R.id.time);
		ImageView speakerImageView = (ImageView) findViewById(R.id.imageSpeakerPortrait);
		TextView nameLectureView = (TextView) findViewById(R.id.nameLecture);
		ratingBar = ((RatingBar) findViewById(R.id.rating));
		final Button rateButton = (Button) findViewById(R.id.buttonSubmitRating);

		timeTextView.setText(lecture.getStartTimeAsString());
		speakerImageView.setImageDrawable(lecture.getSpeaker().getPortraitDrawable());
		nameLectureView.setText(lecture.getName());
		ratingBar.setRating(rating);
		final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		rateButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isRateClicked) {
					v.startAnimation(animAlpha);
					isRateClicked = true;
					Toast.makeText(v.getContext(), R.string.toast_rating_not_yet_avaible, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(v.getContext(), R.string.toast_already_voted, Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("position", position);
		rating = ratingBar.getRating();
		outState.putFloat("rating", rating);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActionBar().setTitle(R.string.title_activity_lecture_details);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageUtils.cleanupCache();
	}
}
