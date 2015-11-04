package com.dimit.bean;
public class AlbumsDynamic {

	private String title;
	private DatasetDynamic[] dataset;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDataset(DatasetDynamic[] dataset) {
		this.dataset = dataset;
	}

	public String getTitle() {
		return title;
	}

	public DatasetDynamic[] getDataset() {
		return dataset;
	}
}