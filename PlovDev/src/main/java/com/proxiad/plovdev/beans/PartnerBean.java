package com.proxiad.plovdev.beans;

import android.graphics.drawable.Drawable;

import com.proxiad.plovdev.utils.ImageUtils;

public class PartnerBean {
	private String partnerId;
	private String urlLink;

	public PartnerBean(String partnerId, String urlLink) {
		super();
		this.partnerId = partnerId;
		this.urlLink = urlLink;
	}

	public Drawable getImageDrawable() {
		return ImageUtils.getCachedDrawable(partnerId);
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

}