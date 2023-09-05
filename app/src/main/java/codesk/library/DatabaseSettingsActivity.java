package codesk.library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import codesk.library.Book.Book;
import codesk.library.Book.BookMainActivity;
import codesk.library.BookLanguage.BookLanguageSettingsActivity;
import codesk.library.BookType.BookTypeSettingsActivity;

public class DatabaseSettingsActivity extends AppCompatActivity {

	private static final String DB_NAME = "library";
	private static final String DB_PATH = "/data/data/codesk.library/databases/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

	if (Build.VERSION.SDK_INT >= 30) {
			if (!Environment.isExternalStorageManager()) {
				Intent getPermission = new Intent();
				getPermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				startActivity(getPermission);
			}
		}

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ImageView imgBack = findViewById(R.id.imgBack);
		imgBack.setOnClickListener(v -> onBackPressed());

		ImageView imgHome = findViewById(R.id.imgHome);
		imgHome.setOnClickListener(v -> {
			Intent intent = new Intent(getApplicationContext(), BookMainActivity.class);
			startActivity(intent);
		});

		Button btnResetDatabase = findViewById(R.id.btnResetDatabase);
		btnResetDatabase.setOnClickListener(v -> {
			Functions functions = new Functions(getApplicationContext());
			if (functions.DatabaseIsExist()) {
				getApplicationContext().deleteDatabase("library");
				Toast.makeText(getApplicationContext(), R.string.database_settings_activity_class_btnResetDatabase, Toast.LENGTH_LONG).show();
			}
		});

		Button btnExportDatabase = findViewById(R.id.btnDatabaseExport);
		btnExportDatabase.setOnClickListener(v -> {
			Cursor cursor=null;
			Functions functions = new Functions(getApplicationContext());
			if (!(functions.isExternalStorageAvailable() && functions.isExternalStorageReadOnly())) {
				try {

					SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
					cursor = db.rawQuery("SELECT * FROM tbl_Books ORDER BY BookID ASC", null);

					HSSFWorkbook workBook = new HSSFWorkbook();
					HSSFSheet sheet = workBook.createSheet("BookList");

					HSSFRow headerRow = sheet.createRow(0);
					for (int i = 0; i <= cursor.getColumnCount() - 1; i++) {
						HSSFCell fieldNames = headerRow.createCell(i);
						fieldNames.setCellValue(cursor.getColumnName(i));
					}

					int i = 1;
					while (cursor.moveToNext()) {
						HSSFRow row = sheet.createRow(i);
						for (int j = 0; j <= cursor.getColumnCount() - 1; j++) {

							HSSFCell cell = row.createCell(j);
							cell.setCellValue(cursor.getString(j));
						}
						i++;
					}
					File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/BookList.xls");
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					workBook.write(fileOutputStream);
					Toast.makeText(this, R.string.database_settings_activity_class_btnExportDatabase, Toast.LENGTH_SHORT).show();

					fileOutputStream.flush();
					fileOutputStream.close();

				} catch (Exception e) {
					Toast.makeText(this, R.string.database_settings_activity_class_btnDatabaseExportError,Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}finally {
					assert cursor != null;
					cursor.close();
				}
			}
		});

		Button btnDatabaseImport = findViewById(R.id.btnDatabaseImport);
		btnDatabaseImport.setOnClickListener(v -> {

					File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/BookList.xls");
					try (SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null)) {
						db.execSQL("DELETE FROM tbl_Books");

						FileInputStream fileInputStream = new FileInputStream(file);
						HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
						HSSFSheet sheet = workbook.getSheetAt(0);

						for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

							Book book = new Book(getApplicationContext());
							book.setBookID(Integer.parseInt(sheet.getRow(i).getCell(0).getStringCellValue()));

							book.setBookType(sheet.getRow(i).getCell(1).getStringCellValue());
							book.setBookLanguage(sheet.getRow(i).getCell(2).getStringCellValue());
							book.setBookEdition(Integer.parseInt(sheet.getRow(i).getCell(3).getStringCellValue()));
							book.setBookPublishYear(Integer.parseInt(sheet.getRow(i).getCell(4).getStringCellValue()));
							book.setBookPublishMonth(sheet.getRow(i).getCell(5).getStringCellValue());
							book.setBookPage(Integer.parseInt(sheet.getRow(i).getCell(6).getStringCellValue()));
							book.setBookISBN(sheet.getRow(i).getCell(7).getStringCellValue());
							book.setBookName(sheet.getRow(i).getCell(8).getStringCellValue());
							book.setBookAuthor(sheet.getRow(i).getCell(9).getStringCellValue());
							book.setBookPublisher(sheet.getRow(i).getCell(10).getStringCellValue());

							book.setBookInLibrary(sheet.getRow(i).getCell(11).getStringCellValue().equals("1"));
							book.setBookRead(sheet.getRow(i).getCell(12).getStringCellValue().equals("1"));

							book.setBookSummary(sheet.getRow(i).getCell(13).getStringCellValue());
							book.setBookExplanation(sheet.getRow(i).getCell(14).getStringCellValue());
							book.setBookImageURL(sheet.getRow(i).getCell(15).getStringCellValue());
							//System.out.println(cell.getStringCellValue());
							book.Insert(book);
						}
						//}
						fileInputStream.close();
						Toast.makeText(this, R.string.database_settings_activity_class_btnDatabaseImport,Toast.LENGTH_LONG).show();
					} catch (Exception exception) {
						Toast.makeText(this, R.string.database_settings_activity_class_btnDatabaseImportError,Toast.LENGTH_LONG).show();
						Log.e("Books Import Error", exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		Intent intent;

		if (id == R.id.bookLanguageSettings) {
			intent = new Intent(getApplicationContext(), BookLanguageSettingsActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.bookTypeSettings) {
			intent = new Intent(getApplicationContext(), BookTypeSettingsActivity.class);
			startActivity(intent);
			return true;
		} else if (id == R.id.databaseSettings) {
			intent = new Intent(getApplicationContext(), DatabaseSettingsActivity.class);
			startActivity(intent);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent=new Intent(getApplicationContext(), BookMainActivity.class);
		startActivity(intent);
		super.onBackPressed();
	}
}