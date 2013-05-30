/**
 * 
 */
package com.marakana.android.doittomorrow.provider;

import com.marakana.android.doittomorrow.BuildConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ToDoDatabaseOpenHandler extends SQLiteOpenHelper {

	private static final String TAG = ToDoDatabaseOpenHandler.class.getName();

	// Database constants
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "todoDatabase.db";

	// Table constants
	public static final String TODO_TABLE = "todoItemTable";
	public static final String KEY_ID = BaseColumns._ID;
	public static final String KEY_CONTENT = "content";
	public static final String KEY_PRIORITY = "priority";
	public static final String KEY_COMPLETED = "completed";
	public static final String KEY_CREATED_AT = "createdAt";

	private static final String TODO_TABLE_CREATE = "CREATE TABLE "
			+ TODO_TABLE + " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY, " // Aliases to unique SQLite rowid column
			+ KEY_CONTENT + " TEXT NOT NULL, " + KEY_PRIORITY
			+ " INTEGER NOT NULL, " + KEY_COMPLETED + " INTEGER NOT NULL, "
			+ KEY_CREATED_AT + " INTEGER NOT NULL" + ");";

	private static ToDoDatabaseOpenHandler sDbInstance = null;

	public ToDoDatabaseOpenHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Creating schema: " + TODO_TABLE_CREATE);
		db.execSQL(TODO_TABLE_CREATE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Upgrading schema from " + oldVersion + " to "
					+ newVersion);
		// In real life, migrate data to new schema
		db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
		onCreate(db);
	}

	public static synchronized ToDoDatabaseOpenHandler getInstance(Context context) {
		if (null == sDbInstance) {
			sDbInstance = new ToDoDatabaseOpenHandler(context);
		}
		return sDbInstance;
	}

}
