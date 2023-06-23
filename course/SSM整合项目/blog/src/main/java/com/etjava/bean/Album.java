package com.etjava.bean;

import java.util.Date;

public class Album {

	private Integer id;
	private String albumName;
	private Date releaseDate;
	private Integer state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Album [id=" + id + ", albumName=" + albumName + ", releaseDate=" + releaseDate + ", state=" + state
				+ "]";
	}
}
