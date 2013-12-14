package net.nikuuchi.deltask;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskModel {
	private String title;
	private long id;
	private long createdAt;
	private long startAt;
	private long endAt;
	
	TaskModel(String title, long id, long createdAt, long startAt, long endAt) {
		this.setTitle(title);
		this.setId(id);
		this.setCreatedAt(createdAt);
		this.setStartAt(startAt);
		this.setEndAt(endAt);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getStartAt() {
		return startAt;
	}

	public void setStartAt(long startAt) {
		this.startAt = startAt;
	}

	public long getEndAt() {
		return endAt;
	}

	public void setEndAt(long endAt) {
		this.endAt = endAt;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();

		json.put("title"    , this.title);
		json.put("id"       , this.id);
		json.put("createdAt", this.createdAt);
		json.put("startAt"  , this.startAt);
		json.put("endAt"    , this.endAt);
		return json;
	}
}
