package net.nikuuchi.deltask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskModelDBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Task.db";

	public static final String CREATE_TABLE_TASK =
			"CREATE TABLE " + TaskModel.TABLE_NAME + " (" +
                    TaskModel.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TaskModel.COLUMN_TITLE + " TEXT NOT NULL, " +
                    TaskModel.COLUMN_CREATED_AT + " INTEGER, " +
                    TaskModel.COLUMN_START_AT+ " INTEGER, " +
                    TaskModel.COLUMN_END_AT+ " INTEGER );";
;
	private static final String DELETE_TABLE_TASK = "DROP TABLE IF EXISTS " + TaskModel.TABLE_NAME;

	public TaskModelDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TaskModelDBHelper.CREATE_TABLE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
