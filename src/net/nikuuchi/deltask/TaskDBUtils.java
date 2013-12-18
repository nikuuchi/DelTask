package net.nikuuchi.deltask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaskDBUtils {
	final static String[] projection = {
		Task.COLUMN_ID,
		Task.COLUMN_TITLE,
		Task.COLUMN_CREATED_AT,
		Task.COLUMN_START_AT,
		Task.COLUMN_END_AT,
		Task.COLUMN_DELETE_FLAG
	};
	
	private static long getLong(Cursor c, String columnName) {
		return c.getLong(c.getColumnIndexOrThrow(columnName));
	}

	private static String getString(Cursor c, String columnName) {
		return c.getString(c.getColumnIndexOrThrow(columnName));
	}

	private static boolean getBoolean(Cursor c, String columnName) {
		return c.getLong(c.getColumnIndexOrThrow(columnName)) > 0;
	}

	public static List<Task> select(TaskDBHelper helper) {
		List<Task> list = new ArrayList<Task>();
		SQLiteDatabase db = helper.getReadableDatabase();
		String selection = Task.COLUMN_DELETE_FLAG + " = 0";
		Cursor cursor = db.query(Task.TABLE_NAME, projection, selection, null, null, null, null);
		boolean isEOF = cursor.moveToFirst();
		while(isEOF) {
			long id            = getLong(cursor,    Task.COLUMN_ID);
			String title       = getString(cursor,  Task.COLUMN_TITLE);
			long createdAt     = getLong(cursor,    Task.COLUMN_CREATED_AT);
			long startAt       = getLong(cursor,    Task.COLUMN_START_AT);
			long endAt         = getLong(cursor,    Task.COLUMN_END_AT);
			boolean deleteFlag = getBoolean(cursor, Task.COLUMN_DELETE_FLAG);
			
			list.add(new Task(title, id, createdAt, startAt, endAt, deleteFlag));

			isEOF = cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	public static Task insert(TaskDBHelper helper, String title) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		long time = new Date().getTime();
		long time_null = 0;
		values.put(Task.COLUMN_TITLE,      title);
		values.put(Task.COLUMN_CREATED_AT, time);
		values.put(Task.COLUMN_START_AT,   time_null);
		values.put(Task.COLUMN_END_AT,     time_null);
		values.put(Task.COLUMN_DELETE_FLAG, 0);
		
		long id = db.insert(Task.TABLE_NAME, null, values);
		db.close();
		return new Task(title, id, time, time_null, time_null, false);
	}

	public static int update_startTime(TaskDBHelper helper, Task item) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		long time = new Date().getTime();

		values.put(Task.COLUMN_START_AT, time);
		String whereClause = Task.COLUMN_ID + " = ?";
		String[] whereArgs = {
				"" + item.getId()
		};
		int ret = db.update(Task.TABLE_NAME, values, whereClause, whereArgs);
		db.close();
		return ret;
	}

	public static int update_endTime(TaskDBHelper helper, Task item) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		long time = new Date().getTime();

		values.put(Task.COLUMN_END_AT, time);
		String whereClause = Task.COLUMN_ID + " = ?";
		String[] whereArgs = {
				"" + item.getId()
		};
		int ret = db.update(Task.TABLE_NAME, values, whereClause, whereArgs);
		db.close();
		return ret;
	}

	public static int delete_logical(TaskDBHelper helper, Task item) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(Task.COLUMN_DELETE_FLAG, 1);
		String whereClause = Task.COLUMN_ID + " = ?";
		String[] whereArgs = {
				"" + item.getId()
		};
		int ret = db.update(Task.TABLE_NAME, values, whereClause, whereArgs);
		db.close();
		return ret;
	}

	public static long countCreatedTaskBetween(TaskDBHelper helper, long start_time, long end_time) {
		String sql = "select count(id) from Task where created_at > ? and created_at < ?"; //FIXME
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] selectionArgs = { Long.toString(start_time), Long.toString(end_time)};
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		boolean isEOF = cursor.moveToFirst();
		long count = 0;
		while(isEOF) {
			count = cursor.getLong(0);
			isEOF = cursor.moveToNext();
		}
		db.close();
		return count;
	}

	public static long countDeletedTaskBetween(TaskDBHelper helper, long start_time, long end_time) {
		String sql = "select count(id) from Task where start_at > ? and start_at < ? and end_at != 0"; //FIXME
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] selectionArgs = { Long.toString(start_time), Long.toString(end_time)};
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		boolean isEOF = cursor.moveToFirst();
		long count = 0;
		while(isEOF) {
			count = cursor.getLong(0);
			isEOF = cursor.moveToNext();
		}
		db.close();
		return count;
	}

	public static String countDeletedTaskTotalTimeBetween(TaskDBHelper helper,
			long start_time, long end_time) {
		// TODO Auto-generated method stub
		return String.format("%02d:%02d:%02d", 2,4,5);
	}

	public static List<Task> selectCompletedList(TaskDBHelper helper,
			long start_time, long end_time) {
		// TODO Auto-generated method stub
		return new ArrayList<Task>();
	}

}
