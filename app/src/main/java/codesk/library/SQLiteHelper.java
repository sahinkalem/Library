package codesk.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static final String CREATE_TBl_BOOK_LANGUAGES = "CREATE TABLE IF NOT EXISTS tbl_BookLanguages (" +
			"BookLanguageID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			"BookLanguageName TEXT NOT NULL)";

	public static final String CREATE_TBL_BOOKS = "CREATE TABLE IF NOT EXISTS tbl_Books (" +
			"BookID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
			"BookType TEXT NOT NULL," +
			"BookLanguage TEXT NOT NULL," +
			"BookEdition INTEGER NOT NULL," +
			"BookPublishYear INTEGER NOT NULL," +
			"BookPublishMonth Text NOT NULL," +
			"BookPage INTEGER NOT NULL," +
			"BookISBN TEXT NOT NULL," +
			"BookName TEXT NOT NULL," +
			"BookAuthor TEXT NOT NULL," +
			"BookPublisher TEXT NOT NULL," +
			"BookInLibrary INTEGER NOT NULL," +
			"BookRead INTEGER NOT NULL," +
			"BookSummary TEXT," +
			"BookExplanation TEXT," +
			"BookImageURL TEXT) ";

	public static final String CREATE_TBl_BOOK_TYPES = "CREATE TABLE IF NOT EXISTS tbl_BookTypes(" +
			"BookTypeID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			"BookTypeName TEXT NOT NULL)";

	private static final String DB_NAME = "library";
	private static final int DB_VERSION = 1;
	Context context;
	private final SQLiteDatabase db = this.getWritableDatabase();
	@SuppressLint ("StaticFieldLeak")
	static SQLiteHelper sInstance;

	public SQLiteHelper(Context _context) {
		super(_context, DB_NAME, null, DB_VERSION);
		context = _context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TBL_BOOKS);
		db.execSQL(CREATE_TBl_BOOK_LANGUAGES);
		db.execSQL(CREATE_TBl_BOOK_TYPES);

		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Turkish')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Arabic')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Chinese')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('English')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('French')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('German')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Hindi')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Italian')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Japanese')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Korean')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Persian')");
		db.execSQL("INSERT INTO tbl_BookLanguages (BookLanguageName) VALUES('Spanish')");

		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Art')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Autobiography')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Biography')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Diary')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Ecomoics')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Fairytale')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Health')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('History')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Memoir')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Action')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Advanture')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Comedy')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Crime')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Drama')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Fantasy')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Mystery')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Romance')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Science Fiction')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Suspense')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Novel - Thriller')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Philosophy')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Poetry')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Politic')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Psychology')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Religion')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Review')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Science')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Short Story')");
		db.execSQL("INSERT INTO tbl_BookTypes (BookTypeName) VALUES('Sociology')");


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tbl_Books");
		db.execSQL("DROP TABLE IF EXISTS tbl_BookLanguages");
		db.execSQL("DROP TABLE IF EXISTS tbl_BookSettings");
		onCreate(db);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		super.onConfigure(db);
		db.setForeignKeyConstraintsEnabled(true);
	}

	@Override
	public synchronized void close() {
		if (db != null) {
			db.close();
			super.close();
		}
	}

	public static synchronized SQLiteHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new SQLiteHelper(context.getApplicationContext());
		}
		return sInstance;
	}
}