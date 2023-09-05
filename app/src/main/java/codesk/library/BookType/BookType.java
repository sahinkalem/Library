package codesk.library.BookType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;


public class BookType {
	private int bookTypeID;
	private String bookTypeName;
	Context context;

	public int getBookTypeID() {
		return bookTypeID;
	}

	public void setBookTypeID(int _bookTypeID) {
		this.bookTypeID = _bookTypeID;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String _bookTypeName) {
		this.bookTypeName = _bookTypeName;
	}

	public BookType(Context _context) {
		this.context=_context;
	}
	private static final String DB_NAME = "library";
	private static final String DB_PATH = "/data/data/codesk.library/databases/";
	private SQLiteDatabase db;

	public boolean Insert(BookType _bookType) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			values.put("BookTypeName", _bookType.getBookTypeName());
			long result = db.insert("tbl_BookTypes", null, values);
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("BookException Insert Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean Update(BookType _bookType) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			int bookTypeID = _bookType.getBookTypeID();
			values.put("BookTypeName", _bookType.getBookTypeName());
			long result = db.update("tbl_BookTypes", values, "BookTypeID=?", new String[]{String.valueOf(bookTypeID)});
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("Book Update Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public boolean Delete(int bookTypeID) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM tbl_BookTypes WHERE BookTypeID=" + bookTypeID);
			db.setTransactionSuccessful();
			return true;
		} catch (SQLiteException exception) {
			Log.e("BookType Delete Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
		}
	}

	public ArrayList<BookType> GetBookTypes() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookTypes ORDER BY BookTypeID ASC", null);
		ArrayList<BookType> bookTypeList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookType bookType = new BookType(context);
				bookType.setBookTypeID(cursor.getInt(0));
				bookType.setBookTypeName(cursor.getString(1));
				bookTypeList.add(bookType);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookTypes Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookTypeList;
	}

	public ArrayList<BookType> GetBookType(int bookTypeID) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookTypes WHERE BookTypeID=" + bookTypeID, null);
		ArrayList<BookType> bookTypeList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookType bookType = new BookType(context);
				bookType.setBookTypeID(cursor.getInt(0));
				bookType.setBookTypeName(cursor.getString(1));
				bookTypeList.add(bookType);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookTypes Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookTypeList;
	}

	public ArrayList<BookType> GetBookType(String bookTypeName) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = db.rawQuery("SELECT * FROM tbl_BookTypes WHERE BookTypeName='" + bookTypeName + "'", null);
		ArrayList<BookType> bookTypeList = new ArrayList<>();
		try {
			while (cursor.moveToNext()) {
				BookType bookType = new BookType(context);
				bookType.setBookTypeID(cursor.getInt(0));
				bookType.setBookTypeName(cursor.getString(1));
				bookTypeList.add(bookType);
			}
		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception", "GetBookTypes Error:" + exception.getMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookTypeList;
	}
}
