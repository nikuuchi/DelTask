package net.nikuuchi.deltask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskModelDBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "Task.db";

	public static final String CREATE_TABLE_TASK =
			"CREATE TABLE " + Task.TABLE_NAME + " (" +
                    Task.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    Task.COLUMN_TITLE + " TEXT NOT NULL, " +
                    Task.COLUMN_CREATED_AT + " INTEGER, " +
                    Task.COLUMN_START_AT+ " INTEGER, " +
                    Task.COLUMN_END_AT+ " INTEGER "+
                    Task.COLUMN_DELETE_FLAG + " INTEGER );";
;
	private static final String DELETE_TABLE_TASK = "DROP TABLE IF EXISTS " + Task.TABLE_NAME;

	public TaskModelDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion != newVersion) {
			db.execSQL(DELETE_TABLE_TASK);
			onCreate(db);
		}
	}

}
