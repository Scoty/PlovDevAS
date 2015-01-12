package com.proxiad.plovdev.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.beans.SpeakerBean;

public class SpeakerAdapter extends ArrayAdapter<SpeakerBean> {

	private final Context context;
	private final List<SpeakerBean> itemsArrayList;

	public SpeakerAdapter(Context context, List<SpeakerBean> itemsArrayList) {
		super(context, R.layout.row_speaker, itemsArrayList);
		this.context = context;
		this.itemsArrayList = itemsArrayList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder viewHolder;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_speaker, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageSpeakerPortraitView = (ImageView) rowView.findViewById(R.id.imageSpeakerPortrait);
			viewHolder.nameSpeakerView = (TextView) rowView.findViewById(R.id.nameSpeaker);
			viewHolder.bioSpeakerView = (TextView) rowView.findViewById(R.id.bioSpeaker);
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}
		SpeakerBean speaker = itemsArrayList.get(position);
		viewHolder.imageSpeakerPortraitView.setImageDrawable(speaker.getPortraitDrawable());
		viewHolder.nameSpeakerView.setText(speaker.getName());
		viewHolder.bioSpeakerView.setText(Html.fromHtml(speaker.getBio()));

		return rowView;
	}

	static class ViewHolder {
		ImageView imageSpeakerPortraitView;
		TextView nameSpeakerView;
		TextView bioSpeakerView;
	}

}
