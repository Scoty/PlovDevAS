package com.proxiad.plovdev.beans;

import java.io.Serializable;
import java.util.List;

import com.proxiad.plovdev.utils.ImageUtils;

import android.graphics.drawable.Drawable;

public class SpeakerBean implements Serializable {

	private static final long serialVersionUID = -3559477671554703845L;

	private String speakerId;
	private String name;
	private String imgUrl;
	private String personalPageUrl;
	private String companyName;
	private String companyUrl;
	private String bio;
	private List<LectureBean> lectures;

	public SpeakerBean(String speakerId, List<LectureBean> lectures) {
		super();
		this.speakerId = speakerId;
		this.lectures = lectures;
	}

	public SpeakerBean(String speakerId, String name, String imgUrl, String personalPageUrl, String companyName, String companyUrl, String bio,
			List<LectureBean> lectures) {
		super();
		this.speakerId = speakerId;
		this.name = name;
		this.imgUrl = imgUrl;
		this.personalPageUrl = personalPageUrl;
		this.companyName = companyName;
		this.companyUrl = companyUrl;
		this.bio = bio;
		this.lectures = lectures;
	}

	public SpeakerBean() {
		super();
	}

	public Drawable getPortraitDrawable() {
		return ImageUtils.getCachedDrawable(speakerId);
	}

	public String getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(String speakerId) {
		this.speakerId = speakerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPersonalPageUrl() {
		return personalPageUrl;
	}

	public void setPersonalPageUrl(String personalPageUrl) {
		this.personalPageUrl = personalPageUrl;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<LectureBean> getLectures() {
		return lectures;
	}

	public void setLectures(List<LectureBean> lectures) {
		this.lectures = lectures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((speakerId == null) ? 0 : speakerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpeakerBean other = (SpeakerBean) obj;
		if (speakerId == null) {
			if (other.speakerId != null)
				return false;
		} else if (!speakerId.equals(other.speakerId))
			return false;
		return true;
	}

}
