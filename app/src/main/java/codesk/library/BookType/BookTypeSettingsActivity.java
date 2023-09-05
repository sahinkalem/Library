package codesk.library.BookType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import codesk.library.BookLanguage.BookLanguageSettingsActivity;
import codesk.library.DatabaseSettingsActivity;
import codesk.library.Book.BookMainActivity;
import codesk.library.R;

public class BookTypeSettingsActivity extends AppCompatActivity {

	private Toolbar toolbar;
	BookType bookType;
	Spinner spinnerBookType;
	ArrayList<BookType> bookTypeArrayList;
	TextView txtBookTypeID;
	EditText txtBookType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_type_settings);

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

		FloatingActionButton btnClear = findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this::btnClear_OnClick);

		txtBookTypeID = findViewById(R.id.txtBookTypeID);
		txtBookType = findViewById(R.id.txtBookType);

		spinnerBookType = findViewById(R.id.spinnerBookType);
		GetBookTypes();
		spinnerBookType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int bookTypeID = bookTypeArrayList.get(position).getBookTypeID();
				String bookType = bookTypeArrayList.get(position).getBookTypeName();
				txtBookTypeID.setText(String.valueOf(bookTypeID));
				txtBookType.setText(bookType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void btnClear_OnClick(View view) {
		txtBookType.setText("");
		txtBookTypeID.setText("");
	}

	private void btnInsert_OnClick(View view) {
		if (txtBookType.getText().length() > 0) {
			try {
				bookType = new BookType(this);
				bookType.setBookTypeID(spinnerBookType.getAdapter().getCount()+1);
				bookType.setBookTypeName(txtBookType.getText().toString());
				boolean isInserted = bookType.Insert(bookType);
				if (isInserted) {
					Toast.makeText(this, R.string.book_type_settings_class_btnInsert, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.book_type_settings_class_btnInsertError, Toast.LENGTH_SHORT).show();
				}
				GetBookTypes();
			} catch (Exception exception) {
				Log.e("BookType Insert Error: ", exception.getMessage());
			}
		}else {
			Toast.makeText(this, R.string.book_type_settings_class_btnInsertError2, Toast.LENGTH_SHORT).show();
		}
	}

	private void btnUpdate_OnClick(View view) {
		if (txtBookTypeID.getText().length() > 0 && txtBookType.getText().length() > 0) {
			bookType = new BookType(this);
			bookType.setBookTypeID(Integer.parseInt(txtBookTypeID.getText().toString()));
			bookType.setBookTypeName(txtBookType.getText().toString());

			try {
				bookType = new BookType(this);
				boolean isUpdated = bookType.Update(bookType);
				if (isUpdated) {
					Toast.makeText(this, R.string.book_type_settings_class_btnUpdate, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.book_type_settings_class_btnUpdateError, Toast.LENGTH_SHORT).show();
				}
				GetBookTypes();
			} catch (Exception exception) {
				Log.e("BookType Insert Error: ", exception.getMessage());
			}
		}else {
			Toast.makeText(this, R.string.book_type_settings_class_btnUpdateError2, Toast.LENGTH_SHORT).show();
		}
	}

	private void btnDelete_OnClick(View view) {
		try {
			DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						bookType = new BookType(BookTypeSettingsActivity.this);
						boolean isDeleted = bookType.Delete(Integer.parseInt(txtBookTypeID.getText().toString()));
						if (isDeleted) {
							Toast.makeText(getApplicationContext(), R.string.book_type_settings_class_btnDelete, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), R.string.book_type_settings_class_btnDeleteError, Toast.LENGTH_SHORT).show();
						}
						GetBookTypes();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						break;
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeSettingsActivity.this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
			builder.setMessage(R.string.book_type_settings_class_btnDeleteAlert).setPositiveButton(R.string.book_type_settings_class_btnDeleteAlertYes, dialogClickListener)
					.setNegativeButton(R.string.book_type_settings_class_btnDeleteAlertNo, dialogClickListener).show();

		} catch (SQLiteException exception) {
			Toast.makeText(this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private void GetBookTypes() {
		spinnerBookType = findViewById(R.id.spinnerBookType);
		bookType = new BookType(this);
		bookTypeArrayList = bookType.GetBookTypes();
		BookTypeBaseAdapter bookTypeBaseAdapter = new BookTypeBaseAdapter(this, bookTypeArrayList);
		spinnerBookType.setAdapter(bookTypeBaseAdapter);
		spinnerBookType.setSelection(0);
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