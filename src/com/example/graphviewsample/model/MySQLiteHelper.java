package com.example.graphviewsample.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "limetray.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TWEETSTABLE = "tweets";
	public static final String COLUMN_ID = "_id";
	public static final String MESSAGE = "message";
	public static final String TWEET_TIME = "created_at";

	private static final String DATABASE_CREATE = "create table " + TWEETSTABLE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ MESSAGE + " text not null, " + TWEET_TIME + " datetime " + ");";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TWEETSTABLE);
		onCreate(db);
	}

}
