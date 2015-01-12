package com.proxiad.plovdev.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class LectureBean implements Serializable {

	private static final long serialVersionUID = 4772024428525844855L;
	
	private String startTimeAsString;
	private Timestamp startTime;
	private String name;
	private String description;
	private String idSpeaker;
	private SpeakerBean speaker;
	
	public LectureBean(String startTimeAsString, String name, String idSpeaker) {
		super();
		this.startTimeAsString = startTimeAsString;
		this.name = name;
		this.idSpeaker = idSpeaker;
	}

	public LectureBean() {
		super();
	}

	public String getStartTimeAsString() {
		return startTimeAsString;
	}

	public void setStartTimeAsString(String startTimeAsString) {
		this.startTimeAsString = startTimeAsString;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdSpeaker() {
		return idSpeaker;
	}

	public void setIdSpeaker(String idSpeaker) {
		this.idSpeaker = idSpeaker;
	}

	public SpeakerBean getSpeaker() {
		return speaker;
	}

	public void setSpeaker(SpeakerBean speaker) {
		this.speaker = speaker;
	}


	public String getTimePeriodName() {
		String timePeriodName = "(" + ") " + this.name;
		return timePeriodName;
	}

}
