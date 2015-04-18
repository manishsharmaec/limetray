package com.example.graphviewsample.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TweetDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.MESSAGE, MySQLiteHelper.TWEET_TIME };

	public TweetDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public TweetModel createTweetModel(String message, String dateTime) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.MESSAGE, message);
		values.put(MySQLiteHelper.TWEET_TIME, dateTime);
		long insertId = database.insert(MySQLiteHelper.TWEETSTABLE, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TWEETSTABLE, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		TweetModel tweetModel = cursorToTweetModel(cursor);
		cursor.close();
		return tweetModel;
	}

	public List<TweetModel> getAllComments() {
		List<TweetModel> tweetModelList = new ArrayList<TweetModel>();

		Cursor cursor = database.query(MySQLiteHelper.TWEETSTABLE, allColumns,
				null, null, null, null, null);
		

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			TweetModel tweetModel = cursorToTweetModel(cursor);
			tweetModelList.add(tweetModel);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return tweetModelList;
	}

	private TweetModel cursorToTweetModel(Cursor cursor) {
		TweetModel tweetModel = new TweetModel();
		tweetModel.setId(cursor.getLong(0));
		tweetModel.setTweetMessage(cursor.getString(1));
		tweetModel.setTweetDateTime(cursor.getString(2));
		return tweetModel;
	}

}
