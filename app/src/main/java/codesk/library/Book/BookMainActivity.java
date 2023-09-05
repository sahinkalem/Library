package codesk.library.Book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import codesk.library.BookLanguage.BookLanguageSettingsActivity;
import codesk.library.BookType.BookTypeSettingsActivity;
import codesk.library.DatabaseSettingsActivity;
import codesk.library.Functions;
import codesk.library.R;
import codesk.library.SQLiteHelper;

public class BookMainActivity extends AppCompatActivity {

	private Toolbar toolbar;
	RecyclerView recyclerView;
	BookAdapter bookAdapter;
	int bookCount;
	SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		//CREATE DATABASE
		Functions functions = new Functions(getApplicationContext());
		if (!functions.DatabaseIsExist()) {
			SQLiteHelper sqLiteHelper = new SQLiteHelper(BookMainActivity.this);
		}
		//SET LAYOUT DESIGN 9786052997444978
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ImageView imgBack = findViewById(R.id.imgBack);
		imgBack.setVisibility(View.GONE);

		ImageView imgHome = findViewById(R.id.imgHome);
		imgHome.setVisibility(View.GONE);

		FloatingActionButton btnInsert = findViewById(R.id.btnInsert);
		btnInsert.setOnClickListener(this::BtnInsert_OnClick);

		FloatingActionButton btnMenu = findViewById(R.id.btnBookMenu);
		btnMenu.setOnClickListener(this::BtnBookMenu_OnClick);

		swipeRefreshLayout = findViewById(R.id.swipeRefresh);
		swipeRefreshLayout.setOnRefreshListener(this::SwipeRefreshLayout_OnRefresh);

		SearchView txtSearch = findViewById(R.id.txtSearch);
		txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				String sqlQuery = "SELECT *  FROM tbl_Books WHERE " +
						"BookName LIKE '%" + txtSearch.getQuery() + "%' " +
						"OR BookAuthor LIKE '%" + txtSearch.getQuery() + "%' " +
						"OR BookPublisher LIKE '%" + txtSearch.getQuery() + "%' " +
						"OR BookType LIKE '%" + txtSearch.getQuery() + "%' " +
						"OR BookLanguage LIKE '%" + txtSearch.getQuery() + "%' " +
						"ORDER BY BookID DESC";
				Book book = new Book(getApplicationContext());
				BookAdapter bookAdapter = new BookAdapter(getApplicationContext(), book.GetBooks(sqlQuery));
				recyclerView = findViewById(R.id.recyclerview);
				recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
				recyclerView.setAdapter(bookAdapter);
				return true;
			}
		});

		GetBooks();
	}

	private void BtnBookMenu_OnClick(View view) {
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.inflate(R.menu.book_menu);
		popupMenu.show();
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			final Book book = new Book(getApplicationContext());
			BookAdapter bookAdapter;

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				if (item.getItemId() == R.id.AllBooks) {
					bookAdapter = new BookAdapter(getApplicationContext(), book.GetBooks("SELECT * FROM tbl_Books ORDER BY BookID DESC"));
					recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
					recyclerView.setAdapter(bookAdapter);
					return true;
				} else if (item.getItemId() == R.id.BooksWillBeRead) {
					bookAdapter = new BookAdapter(getApplicationContext(), book.GetBooks("SELECT * FROM tbl_Books WHERE BookRead=0 ORDER BY BookID DESC"));
					recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
					recyclerView.setAdapter(bookAdapter);
					return true;
				} else if (item.getItemId() == R.id.BooksNotInLibrary) {
					bookAdapter = new BookAdapter(getApplicationContext(), book.GetBooks("SELECT * FROM tbl_Books WHERE BookInLibrary=0 ORDER BY BookID DESC"));
					recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
					recyclerView.setAdapter(bookAdapter);
					return true;
				} else {
					return false;
				}
			}
		});
	}

	private void SwipeRefreshLayout_OnRefresh() {
		GetBooks();
		swipeRefreshLayout.setRefreshing(false);
	}

	private void BtnInsert_OnClick(View view) {
		Intent intent = new Intent(this, BookInsertActivity.class);
		startActivity(intent);
	}

	void GetBooks() {
		recyclerView = findViewById(R.id.recyclerview);
		try {
			Book book = new Book(this);
			recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
			bookAdapter = new BookAdapter(this, book.GetBooks("SELECT * FROM tbl_Books WHERE BookRead=0 ORDER BY BookID DESC"));
			if (bookAdapter.getItemCount() > 0)
				recyclerView.setAdapter(bookAdapter);
			else
				recyclerView.setAdapter(null);
		} catch (SQLiteException sqLiteException) {
			Log.e("BookMainActivity GetBooks Error", sqLiteException.getMessage());
		}
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