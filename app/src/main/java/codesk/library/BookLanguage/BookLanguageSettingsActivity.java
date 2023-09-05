package codesk.library.BookLanguage;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import codesk.library.Book.BookMainActivity;
import codesk.library.BookType.BookTypeSettingsActivity;
import codesk.library.DatabaseSettingsActivity;
import codesk.library.R;

public class BookLanguageSettingsActivity extends AppCompatActivity {
	private Toolbar toolbar;
	BookLanguage bookLanguage;
	Spinner spinnerBookLanguage;
	ArrayList<BookLanguage> bookLanguageArrayList;
	TextView txtBookLanguageID;
	EditText txtBookLanguage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_language_settings);

		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ImageView imgBack = findViewById(R.id.imgBack);
		imgBack.setOnClickListener(v -> onBackPressed());

		ImageView imgHome = findViewById(R.id.imgHome);
		imgHome.setOnClickListener(v -> {
			Intent intent = new Intent(getApplicationContext(), BookMainActivity.class);
			startActivity(intent);
		});

		FloatingActionButton btnInsert = findViewById(R.id.btnInsert);
		btnInsert.setOnClickListener(this::btnInsert_OnClick);

		FloatingActionButton btnUpdate = findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this::btnUpdate_OnClick);

		FloatingActionButton btnDelete = findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(this::btnDelete_OnClick);

		FloatingActionButton btnClear=findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this::btnClear_OnClick);

		txtBookLanguageID = findViewById(R.id.txtBookLanguageID);
		txtBookLanguage = findViewById(R.id.txtBookLanguage);

		spinnerBookLanguage=findViewById(R.id.spinnerBookLanguage);
		GetBookLanguages();
		spinnerBookLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int bookLanguageID = bookLanguageArrayList.get(position).getBookLanguageID();
				String bookLanguage = bookLanguageArrayList.get(position).getBookLanguageName();
				txtBookLanguageID.setText(String.valueOf(bookLanguageID));
				txtBookLanguage.setText(bookLanguage);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void btnClear_OnClick(View view) {
		txtBookLanguage.setText("");
		txtBookLanguageID.setText("");
	}

	private void btnInsert_OnClick(View view) {
		if (txtBookLanguage.getText().length() > 0) {
			try {
				bookLanguage = new BookLanguage(this);
				bookLanguage.setBookLanguageID(Integer.parseInt(txtBookLanguageID.getText().toString()));
				bookLanguage.setBookLanguageName(txtBookLanguage.getText().toString());
				boolean isInserted = bookLanguage.Insert(bookLanguage);
				if (isInserted) {
					Toast.makeText(this, R.string.book_language_settings_class_btnInsert, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.book_language_settings_class_btnInsertError, Toast.LENGTH_SHORT).show();
				}
				GetBookLanguages();
			} catch (Exception exception) {
				Log.e("BookLanguage Insert Error: ", exception.getMessage());
			}
		} else {
			Toast.makeText(this, R.string.book_language_settings_class_btnInsertError2, Toast.LENGTH_SHORT).show();
		}
	}

	private void btnUpdate_OnClick(View view) {
		if (txtBookLanguageID.getText().length() > 0 && txtBookLanguage.getText().length() > 0) {
			try {
				bookLanguage = new BookLanguage(this);
				bookLanguage.setBookLanguageID(Integer.parseInt(txtBookLanguageID.getText().toString()));
				bookLanguage.setBookLanguageName(txtBookLanguage.getText().toString());
				boolean isUpdated = bookLanguage.Update(bookLanguage);
				if (isUpdated) {
					Toast.makeText(this, R.string.book_language_settings_class_btnUpdate, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.book_language_settings_class_btnUpdateError, Toast.LENGTH_SHORT).show();
				}
				GetBookLanguages();
			} catch (Exception exception) {
				Log.e("BookLanguage Insert Error: ", exception.getMessage());
			}
		}else {
			Toast.makeText(this, R.string.book_language_settings_class_btnUpdate2, Toast.LENGTH_SHORT).show();
		}
	}

	private void btnDelete_OnClick(View view) {
		try {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							bookLanguage = new BookLanguage(BookLanguageSettingsActivity.this);
							boolean isDeleted = bookLanguage.Delete(Integer.parseInt(txtBookLanguageID.getText().toString()));
							if (isDeleted) {
								Toast.makeText(getApplicationContext(), R.string.book_language_settings_class_btnDelete, Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(), R.string.book_language_settings_class_btnDeleteError, Toast.LENGTH_SHORT).show();
							}
							GetBookLanguages();
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(BookLanguageSettingsActivity.this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
			builder.setMessage(R.string.book_language_settings_class_btnDeleteAlert).setPositiveButton(R.string.book_language_settings_class_btnDeleteAlertYes, dialogClickListener)
					.setNegativeButton(R.string.book_language_settings_class_btnDeleteAlertNo, dialogClickListener).show();

		} catch (SQLiteException exception) {
			Toast.makeText(this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private void GetBookLanguages() {
		spinnerBookLanguage = findViewById(R.id.spinnerBookLanguage);
		bookLanguage = new BookLanguage(this);
		bookLanguageArrayList = bookLanguage.GetBookLanguages();
		BookLanguageBaseAdapter bookLanguageBaseAdapter = new BookLanguageBaseAdapter(this, bookLanguageArrayList);
		spinnerBookLanguage.setAdapter(bookLanguageBaseAdapter);
		spinnerBookLanguage.setSelection(0);
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
}