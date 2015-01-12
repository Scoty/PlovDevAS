package com.proxiad.plovdev.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.beans.PartnerBean;

public class PartnerAdapter extends ArrayAdapter<PartnerBean> {

	private final Context context;
	private final List<PartnerBean> itemsArrayList;

	public PartnerAdapter(Context context, List<PartnerBean> itemsArrayList) {
		super(context, R.layout.row_partner, itemsArrayList);
		this.context = context;
		this.itemsArrayList = itemsArrayList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder viewHolder;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_partner, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.partnerImageView = (ImageView) rowView.findViewById(R.id.partnerImageView);
			rowView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		final PartnerBean partner = itemsArrayList.get(position);
		viewHolder.partnerImageView.setImageDrawable(partner.getImageDrawable());
		viewHolder.partnerImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(partner.getUrlLink()));
				context.startActivity(intent);
			}
		});

		return rowView;
	}

	static class ViewHolder {
		ImageView partnerImageView;
	}

}
