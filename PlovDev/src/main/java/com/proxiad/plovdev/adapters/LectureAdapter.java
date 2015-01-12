package com.proxiad.plovdev.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.beans.LectureBean;

public class LectureAdapter extends ArrayAdapter<LectureBean> {

	private final Context context;
	private final List<LectureBean> itemsArrayList;

	public LectureAdapter(Context context, List<LectureBean> itemsArrayList) {
		super(context, R.layout.row_lecture, itemsArrayList);
		this.context = context;
		this.itemsArrayList = itemsArrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder viewHolder;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_lecture, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageSpeakerPortraitView = (ImageView) rowView.findViewById(R.id.imageSpeakerPortrait);
			viewHolder.timeView = (TextView) rowView.findViewById(R.id.time);
			viewHolder.nameView = (TextView) rowView.findViewById(R.id.name);
			viewHolder.descriptionView = (TextView) rowView.findViewById(R.id.description);
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		LectureBean lecture = itemsArrayList.get(position);
		viewHolder.imageSpeakerPortraitView.setImageDrawable(lecture.getSpeaker().getPortraitDrawable());
		viewHolder.timeView.setText(lecture.getStartTimeAsString());
		viewHolder.nameView.setText(lecture.getName());
		if (lecture.getSpeaker().getBio() != null && !lecture.getSpeaker().getBio().isEmpty()) {
			viewHolder.descriptionView.setText(R.string.rating_desc);
			// TODO Why this works backwards?
			rowView.setClickable(false);
		} else {
			viewHolder.descriptionView.setText("");
			// TODO Why this works backwards?
			rowView.setClickable(true);
		}

		return rowView;
	}

	static class ViewHolder {
		ImageView imageSpeakerPortraitView;
		TextView timeView;
		TextView nameView;
		TextView descriptionView;
	}
}