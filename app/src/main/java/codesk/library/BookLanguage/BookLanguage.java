package codesk.library.BookLanguage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

public class BookLanguage {
	private int bookLanguageID;
	private String bookLanguageName;
	Context context;

	public int getBookLanguageID() {
		return bookLanguageID;
	}

	public void setBookLanguageID(int _bookLanguageID) {
		this.bookLanguageID = _bookLanguageID;
	}

	public String getBookLanguageName() {
		return bookLanguageName;
	}

	public void setBookLanguageName(String _bookLanguage) {
		this.bookLanguageName = _bookLanguage;
	}

	public BookLanguage(Context _context) {
		this.context = _context;
	}

	private static final String DB_NAME = "library";
	private static final String DB_PATH = "/data/data/codesk.library/databases/";
	private SQLiteDatabase db;

	public boolean Insert(BookLanguage _bookLanguage) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			values.put("BookLanguageName", _bookLanguage.getBookLanguageName());
			long result = db.insert("tbl_BookLanguages", null, values);
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("BookLanguage Insert Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean Update(BookLanguage _bookLanguage) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			int bookLanguageID = _bookLanguage.getBookLanguageID();
			//String name=.replace("'", "´");
			values.put("BookLanguageName", _bookLanguage.getBookLanguageName());
			long result = db.update("tbl_BookLanguages", values, "BookLanguageID=?", new String[]{String.valueOf(bookLanguageID)});
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("BookLanguage Update Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean Delete(int bookLanguageID){
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		//ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM tbl_BookLanguages WHERE BookLanguageID=" + bookLanguageID);
			db.setTransactionSuccessful();
			return true;
		} catch (SQLiteException exception) {
			Log.e("BookLanguage Delete Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public ArrayList<BookLanguage> GetBookLanguages() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookLanguages ORDER BY BookLanguageID ASC", null);
		ArrayList<BookLanguage> bookLanguageList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookLanguage bookLanguage = new BookLanguage(context);
				bookLanguage.setBookLanguageID(cursor.getInt(0));
				bookLanguage.setBookLanguageName(cursor.getString(1));
				bookLanguageList.add(bookLanguage);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookLanguage Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookLanguageList;
	}

	public ArrayList<BookLanguage> GetBookLanguage(int bookLanguageID) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookLanguages WHERE BooklanguageID=" + bookLanguageID, null);
		ArrayList<BookLanguage> bookLanguageList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookLanguage bookLanguage = new BookLanguage(context);
				bookLanguage.setBookLanguageID(cursor.getInt(0));
				bookLanguage.setBookLanguageName(cursor.getString(1));
				bookLanguageList.add(bookLanguage);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookLanguage Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookLanguageList;
	}

	public ArrayList<BookLanguage> GetBookLanguage(String bookLanguageName) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookLanguages WHERE BooklanguageName='" + bookLanguageName + "'", null);
		ArrayList<BookLanguage> bookLanguageList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookLanguage bookLanguage = new BookLanguage(context);
				bookLanguage.setBookLanguageID(cursor.getInt(0));
				bookLanguage.setBookLanguageName(cursor.getString(1));
				bookLanguageList.add(bookLanguage);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookLanguage Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookLanguageList;
	}
}
