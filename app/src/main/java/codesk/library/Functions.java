package codesk.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.File;
import java.util.ArrayList;

public class Functions {

	Context context;
	private static final String DB_NAME = "library";
	private static final String DB_PATH = "/data/data/codesk.library/databases/";

	public Functions(Context context) {
		this.context = context;
	}

	public void AutoCompleteTextView(Context context, AutoCompleteTextView autoCompleteTextView, String sqlQuery) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery(sqlQuery, null);
		ArrayList<String> stringArrayList = new ArrayList<>();
		if (cursor.getCount() > 0) {
			try {
				while (cursor.moveToNext()) {
					stringArrayList.add(cursor.getString(0));
				}
			} catch (Exception exception) {
				Log.e("Sqlite Exception",  exception.getLocalizedMessage());
			} finally {
				cursor.close();
				db.close();
			}
			ArrayAdapter<String> bookNameArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, stringArrayList);
			autoCompleteTextView.setAdapter(bookNameArrayAdapter);
			autoCompleteTextView.setThreshold(3);
		}
	}

	public  void CreateDatabase() {
		try {
			SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context.getApplicationContext());
			SQLiteDatabase db =context.openOrCreateDatabase("library", Context.MODE_PRIVATE,null);
			//sqliteHelper.onUpgrade(db, 1, 1);
			sqliteHelper.onCreate(db);
		} catch (SQLiteException sqLiteException) {
			Log.e("Main Activity Error:", sqLiteException.getMessage());
		}
	}
	public  boolean DatabaseIsExist() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	public  boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
	}

	public  boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(extStorageState);
	}
}
