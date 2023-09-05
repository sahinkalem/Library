package codesk.library.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

public class Book {
	private int bookID;
	private String bookType;
	private String bookLanguage;
	private int bookEdition;
	private int bookPublishYear;
	private String bookPublishMonth;
	private int bookPage;
	private String bookISBN;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private boolean bookInLibrary;
	private boolean bookRead;
	private String bookSummary;
	private String bookExplanation;
	private String bookImageURL;
	private String bookAuthorImageURL;

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int _bookID) {
		bookID = _bookID;
	}

	public String  getBookType() {
		return bookType;
	}

	public void setBookType(String _bookType) {
		bookType = _bookType;
	}

	public String getBookLanguage() {
		return bookLanguage;
	}

	public void setBookLanguage(String _bookLanguage) {
		bookLanguage = _bookLanguage;
	}

	public int getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(int _bookEdition) {
		bookEdition = _bookEdition;
	}

	public int getBookPublishYear() {
		return bookPublishYear;
	}

	public void setBookPublishYear(int _bookPublishYear) {
		bookPublishYear = _bookPublishYear;
	}

	public String getBookPublishMonth() {
		return bookPublishMonth;
	}

	public void setBookPublishMonth(String _bookPublishMonth) {
		bookPublishMonth = _bookPublishMonth;
	}

	public int getBookPage() {
		return bookPage;
	}

	public void setBookPage(int _bookPage) {
		bookPage = _bookPage;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String _bookISBN) {
		if (!(_bookISBN == null) && !(_bookISBN.trim().equals("")))
			bookISBN = _bookISBN;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String _bookName) {
		if (!(_bookName == null) && !(_bookName.trim().equals("")))
			bookName = _bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String _bookAuthor) {
		if (!(_bookAuthor == null) && !(_bookAuthor.trim().equals("")))
			bookAuthor = _bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String _bookPublisher) {
		if (!(_bookPublisher == null) && !(_bookPublisher.trim().equals("")))
			bookPublisher = _bookPublisher;
	}

	public boolean getBookInLibrary() {
		return bookInLibrary;
	}

	public void setBookInLibrary(boolean _bookInLibrary) {
		bookInLibrary = _bookInLibrary;
	}

	public boolean getBookRead() {
		return bookRead;
	}

	public void setBookRead(boolean _bookRead) {
		bookRead = _bookRead;
	}

	public String getBookSummary() {
		return bookSummary;
	}

	public void setBookSummary(String _bookSummary) {
		bookSummary = _bookSummary;
	}

	public String getBookExplanation() {
		return bookExplanation;
	}

	public void setBookExplanation(String _bookExplanation) {
		bookExplanation = _bookExplanation;
	}

	public String getBookImageURL() {
		return bookImageURL;
	}

	public void setBookImageURL(String bookImageURL) {
		this.bookImageURL = bookImageURL;
	}

	public String getBookAuthorImageURL() {
		return bookAuthorImageURL;
	}

	public void setBookAuthorImageURL(String bookAuthorImageURL) {
		this.bookAuthorImageURL = bookAuthorImageURL;
	}

	Context context;
	private static final String DB_NAME = "library";
	private static final String DB_PATH = "/data/data/codesk.library/databases/";
	private SQLiteDatabase db;

	public Book(Context context) {
		this.context = context;
	}

	public boolean Insert(Book _book) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			values.put("BookType", _book.getBookType());
			values.put("BookLanguage", _book.getBookLanguage());
			values.put("BookEdition", _book.getBookEdition());
			values.put("BookPublishYear", _book.getBookPublishYear());
			values.put("BookPublishMonth", _book.getBookPublishMonth());
			values.put("BookPage", _book.getBookPage());
			values.put("BookISBN", _book.getBookISBN());
			values.put("BookName", _book.getBookName().replace("'", "´"));
			values.put("BookAuthor", _book.getBookAuthor().replace("'", "´"));
			values.put("BookPublisher", _book.getBookPublisher().replace("'", "´"));
			values.put("BookInLibrary", _book.getBookInLibrary());
			values.put("BookRead", _book.getBookRead());
			values.put("BookSummary", _book.getBookSummary().replace("'", "´"));
			values.put("BookExplanation", _book.getBookExplanation().replace("'", "´"));
			values.put("BookImageURL", _book.getBookImageURL());

			long result = db.insert("tbl_Books", null, values);
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("Book Insert Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	public boolean Update(Book _book) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try {
			int BookID = _book.getBookID();
			values.put("BookType", _book.getBookType());
			values.put("BookLanguage", _book.getBookLanguage());
			values.put("BookEdition", _book.getBookEdition());
			values.put("BookPublishYear", _book.getBookPublishYear());
			values.put("BookPublishMonth", _book.getBookPublishMonth());
			values.put("BookPage", _book.getBookPage());
			values.put("BookISBN", _book.getBookISBN());
			values.put("BookName", _book.getBookName().replace("'", "´"));
			values.put("BookAuthor", _book.getBookAuthor().replace("'", "´"));
			values.put("BookPublisher", _book.getBookPublisher().replace("'", "´"));
			values.put("BookInLibrary", _book.getBookInLibrary());
			values.put("BookRead", _book.getBookRead());
			values.put("BookSummary", _book.getBookSummary().replace("'", "´"));
			values.put("BookExplanation", _book.getBookExplanation().replace("'", "´"));
			values.put("BookImageURL", _book.getBookImageURL());

			long result = db.update("tbl_Books", values, "BookID=?", new String[]{String.valueOf(BookID)});
			db.setTransactionSuccessful();
			return result != -1;
		} catch (SQLiteException exception) {
			Log.e("Book Update Error", exception.getMessage());
			return false;
		} finally {
			db.endTransaction();
			db.close();
		}
	}

	public ArrayList<Book> GetBooks(String sqlQuery) {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		if (sqlQuery == null || sqlQuery.equals(""))
			sqlQuery = "SELECT * FROM tbl_Books ORDER BY BookID ASC";
		Cursor cursor = db.rawQuery(sqlQuery, null);
		ArrayList<Book> bookList = new ArrayList<>();

		try {
			while (cursor.moveToNext()) {
				Book book = new Book(context);
				book.setBookID(cursor.getInt(0));
				book.setBookType(cursor.getString(1));
				book.setBookLanguage(cursor.getString(2));
				book.setBookEdition(cursor.getInt(3));
				book.setBookPublishYear(cursor.getInt(4));
				book.setBookPublishMonth(cursor.getString(5));
				book.setBookPage(cursor.getInt(6));
				book.setBookISBN(cursor.getString(7));
				book.setBookName(cursor.getString(8));
				book.setBookAuthor(cursor.getString(9));
				book.setBookPublisher(cursor.getString(10));
				book.setBookInLibrary((cursor.getInt(11) == 1));
				book.setBookRead((cursor.getInt(12)) == 1);
				book.setBookSummary(cursor.getString(13));
				book.setBookExplanation(cursor.getString(14));
				book.setBookImageURL(cursor.getString(15));
				bookList.add(book);
			}
		} catch (SQLiteException sqLiteException) {
			Log.e("Sqlite Exception", sqLiteException.getLocalizedMessage());
		} finally {
			cursor.close();
			db.close();
		}
		return bookList;
	}

	public ArrayList<Book> GetBookDetails(int BookID) {
		ArrayList<Book> bookList = new ArrayList<>();
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		String sql = "SELECT * FROM tbl_Books WHERE BookID=" + BookID;
		try (Cursor cursor = db.rawQuery(sql, null)) {
			while (cursor.moveToNext()) {
				Book book = new Book(context);
				book.setBookID(cursor.getInt(0));
				book.setBookType(cursor.getString(1));
				book.setBookLanguage(cursor.getString(2));
				book.setBookEdition(cursor.getInt(3));
				book.setBookPublishYear(cursor.getInt(4));
				book.setBookPublishMonth(cursor.getString(5));
				book.setBookPage(cursor.getInt(6));
				book.setBookISBN(cursor.getString(7));
				book.setBookName(cursor.getString(8));
				book.setBookAuthor(cursor.getString(9));
				book.setBookPublisher(cursor.getString(10));
				book.setBookInLibrary((cursor.getInt(11) == 1));
				book.setBookRead((cursor.getInt(12)) == 1);
				book.setBookSummary(cursor.getString(13));
				book.setBookExplanation(cursor.getString(14));
				book.setBookImageURL(cursor.getString(15));
				bookList.add(book);
			}

		} catch (SQLiteException exception) {
			Log.e("Sqlite Exception",  exception.getLocalizedMessage());
		}
		return bookList;
	}

}
