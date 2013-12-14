package net.nikuuchi.deltask;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskModel {
	public static final String TABLE_NAME = "Task";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CREATED_AT = "created_at";
	public static final String COLUMN_START_AT = "start_at";
	public static final String COLUMN_END_AT = "end_at";

	private long id;
	private String title;
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

		json.put(TaskModel.COLUMN_TITLE     , this.title);
		json.put(TaskModel.COLUMN_ID        , this.id);
		json.put(TaskModel.COLUMN_CREATED_AT, this.createdAt);
		json.put(TaskModel.COLUMN_START_AT  , this.startAt);
		json.put(TaskModel.COLUMN_END_AT    , this.endAt);
		return json;
	}
}