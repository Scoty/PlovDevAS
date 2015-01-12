package com.proxiad.plovdev.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.beans.LectureBean;

public class LectureForSpeakerAdapter extends ArrayAdapter<LectureBean> {

	private final Context context;
	private final List<LectureBean> itemsArrayList;

	public LectureForSpeakerAdapter(Context context, List<LectureBean> itemsArrayList) {
		super(context, R.layout.row_lecture_for_speaker, itemsArrayList);
		this.context = context;
		this.itemsArrayList = itemsArrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder viewHolder;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_lecture_for_speaker, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.timeNameView = (TextView) rowView.findViewById(R.id.time_name);
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		LectureBean lecture = itemsArrayList.get(position);
		viewHolder.timeNameView.setText(lecture.getStartTimeAsString() + " " + lecture.getName());
	
		return rowView;
	}

	static class ViewHolder {
		TextView timeNameView;
	}
}