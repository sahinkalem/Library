package codesk.library.Book;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codesk.library.BookLanguage.BookLanguage;
import codesk.library.BookLanguage.BookLanguageBaseAdapter;
import codesk.library.BookLanguage.BookLanguageSettingsActivity;
import codesk.library.BookType.BookType;
import codesk.library.BookType.BookTypeBaseAdapter;
import codesk.library.BookType.BookTypeSettingsActivity;
import codesk.library.DatabaseSettingsActivity;
import codesk.library.Functions;
import codesk.library.Months;
import codesk.library.R;

public class BookUpdateActivity extends AppCompatActivity {
	EditText txtBookEdition;
	EditText txtBookPublishYear;
	EditText txtBookPage;
	EditText txtBookISBN;
	AutoCompleteTextView txtBookName;
	AutoCompleteTextView txtBookAuthor;
	AutoCompleteTextView txtBookPublisher;
	CheckBox checkBoxBookInLibrary;
	CheckBox checkBoxBookIsRead;
	EditText txtBookSummary;
	EditText txtBookExplanation;
	Spinner spinnerBookLanguage;
	Spinner spinnerBookType;
	Spinner spinnerBookPublishMonth;
	ImageView imgBook;
	Button btnGetBookInfo;
	private String bookImageURL;

	public String getBookImageURL() {
		return bookImageURL;
	}

	public void setBookImageURL(String bookImageURL) {
		this.bookImageURL = bookImageURL;
	}

	private Toolbar toolbar;
	Book book;
	ArrayList<BookLanguage> bookLanguageList;
	ArrayList<BookType> bookTypeList;
	ActivityResultLauncher<ScanOptions> barLauncher;
	String bookLanguageName, bookMonthName, bookTypeName;

	int bookID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_update);
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		txtBookEdition = findViewById(R.id.txtBookEdition);
		txtBookPublishYear = findViewById(R.id.txtBookPublishYear);
		txtBookPage = findViewById(R.id.txtBookPage);
		txtBookISBN = findViewById(R.id.txtBookISBN);
		txtBookName = findViewById(R.id.txtBookName);
		txtBookAuthor = findViewById(R.id.txtBookAuthor);
		txtBookPublisher = findViewById(R.id.txtBookPublisher);
		checkBoxBookInLibrary = findViewById(R.id.checkboxBookInLibrary);
		checkBoxBookIsRead = findViewById(R.id.checkboxBookIsRead);
		txtBookSummary = findViewById(R.id.txtBookSummary);
		txtBookExplanation = findViewById(R.id.txtBookExplanation);
		spinnerBookLanguage = findViewById(R.id.spinnerBookLanguage);
		spinnerBookType = findViewById(R.id.spinnerBookType);
		spinnerBookPublishMonth = findViewById(R.id.spinnerBookPublishMonth);
		imgBook = findViewById(R.id.imgBook);
		toolbar = findViewById(R.id.toolbar);

		// Get BookLanguageList From Sqlite
		BookLanguage bookLanguage = new BookLanguage(this);
		bookLanguageList = bookLanguage.GetBookLanguages();
		BookLanguageBaseAdapter bookLanguageBaseAdapter = new BookLanguageBaseAdapter(this, bookLanguageList);
		spinnerBookLanguage.setAdapter(bookLanguageBaseAdapter);

		// Get BookTypeList from Sqlite
		BookType bookType = new BookType(this);
		bookTypeList = bookType.GetBookTypes();
		BookTypeBaseAdapter bookTypeBaseAdapter = new BookTypeBaseAdapter(this, bookTypeList);
		spinnerBookType.setAdapter(bookTypeBaseAdapter);

		// Get Months
		spinnerBookPublishMonth = findViewById(R.id.spinnerBookPublishMonth);
		spinnerBookPublishMonth.setAdapter(new ArrayAdapter<>(this, R.layout.month_spinner_item, R.id.spinnerItemMonth, Months.values()));

		//Get Book Information
		bookID = Integer.parseInt(getIntent().getStringExtra("BookID"));
		book = new Book(getApplicationContext());
		List<Book> books = book.GetBookDetails(bookID);

		bookLanguage = new BookLanguage(this);
		bookLanguageList = bookLanguage.GetBookLanguage(books.get(0).getBookLanguage());
		spinnerBookLanguage.setSelection(bookLanguageList.get(0).getBookLanguageID() - 1);

		bookType = new BookType(this);
		bookTypeList = bookType.GetBookType(books.get(0).getBookType());
		spinnerBookType.setSelection(bookTypeList.get(0).getBookTypeID() - 1);

		for (int i = 0; i < spinnerBookPublishMonth.getAdapter().getCount(); i++) {
			if (spinnerBookPublishMonth.getAdapter().getItem(i).toString().equals(books.get(0).getBookPublishMonth())) {
				spinnerBookPublishMonth.setSelection(i);
			}
		}

		txtBookEdition.setText(String.valueOf(books.get(0).getBookEdition()));
		txtBookPublishYear.setText(String.valueOf(books.get(0).getBookPublishYear()));
		txtBookPage.setText(String.valueOf(books.get(0).getBookPage()));
		txtBookISBN.setText(books.get(0).getBookISBN());
		txtBookName.setText(books.get(0).getBookName());
		txtBookAuthor.setText(books.get(0).getBookAuthor());
		txtBookPublisher.setText(books.get(0).getBookPublisher());
		checkBoxBookInLibrary.setChecked(books.get(0).getBookInLibrary());
		checkBoxBookIsRead.setChecked(books.get(0).getBookRead());
		txtBookSummary.setText(books.get(0).getBookSummary());
		txtBookExplanation.setText(books.get(0).getBookExplanation());

		if (books.get(0).getBookImageURL() == null || books.get(0).getBookImageURL().equals("")) {
			imgBook.setImageResource(R.drawable.img_no_image);
		} else {
			setBookImageURL(books.get(0).getBookImageURL());
			Glide.with(this).load(getBookImageURL()).error(R.drawable.img_no_image).into(imgBook);
		}
		//-----------------------------------------------------

		Functions functions = new Functions(this);
		functions.AutoCompleteTextView(this, txtBookName, "SELECT DISTINCT BookName FROM tbl_Books WHERE BookName LIKE '" + txtBookName.getText() + "%'");
		functions.AutoCompleteTextView(this, txtBookAuthor, "SELECT DISTINCT BookAuthor FROM tbl_Books WHERE BookAuthor LIKE '" + txtBookAuthor.getText() + "%'");
		functions.AutoCompleteTextView(this, txtBookPublisher, "SELECT DISTINCT BookPublisher FROM tbl_Books WHERE BookPublisher LIKE '" + txtBookPublisher.getText() + "%'");
		ImageView imgBack = findViewById(R.id.imgBack);
		imgBack.setOnClickListener(v -> onBackPressed());

		ImageView imgHome = findViewById(R.id.imgHome);
		imgHome.setOnClickListener(v -> {
			Intent intent = new Intent(getApplicationContext(), BookMainActivity.class);
			startActivity(intent);
		});

		FloatingActionButton btnBack = findViewById(R.id.btnBack);
		btnBack.setOnClickListener(v -> onBackPressed());

		FloatingActionButton btnUpdate = findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this::btnUpdate_Click);

		FloatingActionButton btnScanner = findViewById(R.id.btnScanner);
		btnScanner.setOnClickListener(this::btnScanner_OnClick);

		FloatingActionButton btnClear = findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this::btnClear_OnCLick);

		btnGetBookInfo = findViewById(R.id.btnGetBookInfo);
		btnGetBookInfo.setOnClickListener(this::btnGetBookInfo_Click);

		barLauncher = registerForActivityResult(new ScanContract(), this::onActivityResult);

		spinnerBookLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				BookLanguage bookLanguage = new BookLanguage(getApplicationContext());
				List<BookLanguage> bookLanguages = bookLanguage.GetBookLanguages();
				bookLanguageName = bookLanguages.get(position).getBookLanguageName();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		spinnerBookType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				BookType booktype = new BookType(getApplicationContext());
				List<BookType> bookTypes = booktype.GetBookTypes();
				bookTypeName = bookTypes.get(position).getBookTypeName();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinnerBookPublishMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				bookMonthName = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void btnClear_OnCLick(View view) {
		txtBookEdition.setText("");
		txtBookPublishYear.setText("");
		txtBookPage.setText("");
		txtBookISBN.setText("");
		txtBookName.setText("");
		txtBookAuthor.setText("");
		txtBookPublisher.setText("");
		checkBoxBookInLibrary.setChecked(false);
		checkBoxBookIsRead.setChecked(false);
		txtBookSummary.setText("");
		txtBookExplanation.setText("");
		spinnerBookLanguage.setSelection(0);
		spinnerBookType.setSelection(0);
		spinnerBookPublishMonth.setSelection(0);
	}

	private void btnUpdate_Click(View view) {
		if (TextUtils.isEmpty(txtBookEdition.getText().toString())) {
			txtBookEdition.setError("Baskı alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookPublishYear.getText().toString())) {
			txtBookPublishYear.setError("Yıl alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookPage.getText().toString())) {
			txtBookPage.setError("Sayfa sayısı alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookISBN.getText().toString())) {
			txtBookISBN.setError("ISBN alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookName.getText().toString())) {
			txtBookName.setError("Kitap adı alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookAuthor.getText().toString())) {
			txtBookAuthor.setError("Yazar alanı boş olamaz");
		} else if (TextUtils.isEmpty(txtBookPublisher.getText().toString())) {
			txtBookPublisher.setError("Yayınevi alanı boş olamaz");
		} else {

			try {
				book = new Book(this);
				book.setBookID(bookID);
				book.setBookType(bookTypeName);
				book.setBookLanguage(bookLanguageName);
				book.setBookEdition(Integer.parseInt(txtBookEdition.getText().toString()));
				book.setBookPublishYear(Integer.parseInt(txtBookPublishYear.getText().toString()));
				book.setBookPublishMonth(bookMonthName);
				book.setBookPage(Integer.parseInt(txtBookPage.getText().toString()));
				book.setBookISBN(txtBookISBN.getText().toString());
				book.setBookName(txtBookName.getText().toString());
				book.setBookAuthor(txtBookAuthor.getText().toString());
				book.setBookPublisher(txtBookPublisher.getText().toString());
				book.setBookInLibrary(checkBoxBookInLibrary.isChecked());
				book.setBookRead(checkBoxBookIsRead.isChecked());
				book.setBookSummary(txtBookSummary.getText().toString());
				book.setBookExplanation(txtBookExplanation.getText().toString());
				book.setBookImageURL(getBookImageURL());

				boolean isUpdated = book.Update(book);
				if (isUpdated) {
					Toast.makeText(this, R.string.book_insert_update_class_btnUpdate, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.book_insert_update_class_btnUpdateError, Toast.LENGTH_SHORT).show();
				}
			} catch (SQLiteException sqLiteException) {
				Log.e("Database Error: ", sqLiteException.getMessage());
			}
		}
	}

	private void btnGetBookInfo_Click(View view) {
		if (txtBookISBN.getText().length() == 13) {
			GetBooksInfo(getApplicationContext());
		} else {
			Toast.makeText(this, R.string.book_insert_update_class_btnGetBookInfoClick, Toast.LENGTH_LONG).show();
		}
	}

	private void btnScanner_OnClick(View view) {
		ScanOptions options = new ScanOptions();
		options.setPrompt("Volume up to flash on");
		options.setBeepEnabled(true);
		options.setOrientationLocked(true);
		options.setCaptureActivity(CaptureActivity.class);
		barLauncher.launch(options);
		GetBooksInfo(this);
	}

	private void onActivityResult(ScanIntentResult result) {
		if (result.getContents() != null) {
			txtBookISBN.setText(result.getContents());
			GetBooksInfo(this);

		}
	}

	private void GetBooksInfo(Context context) {

		String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + txtBookISBN.getText().toString().trim();
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

			try {
				JSONArray itemsArray = response.getJSONArray("items");
				JSONObject item = itemsArray.getJSONObject(0);
				JSONObject volumeObj = item.getJSONObject("volumeInfo");

				// Get Book Publish Year
				if (volumeObj.has("publishedDate"))
					txtBookPublishYear.setText(volumeObj.optString("publishedDate").substring(0, 4));
				else
					txtBookPublishYear.setText("");

				// Get Book Publish Month

				if (volumeObj.has("publishedDate")) {

					if (volumeObj.optString("publishedDate").length() > 5) {
						spinnerBookPublishMonth.setSelection(Integer.parseInt(volumeObj.optString("publishedDate").substring(5, 7)) - 1);
					} else {
						spinnerBookPublishMonth.setSelection(1);
					}
				} else {
					spinnerBookPublishMonth.setSelection(1);
				}

				// Get Book Page Count
				if (volumeObj.has("pageCount"))
					txtBookPage.setText(volumeObj.optString("pageCount"));
				else
					txtBookPage.setText(0);

				// Get Book Name
				if (volumeObj.has("title"))
					txtBookName.setText(volumeObj.optString("title"));
				else
					txtBookName.setText("");

				// Get Book Author
				if (volumeObj.has("authors")) {
					JSONArray authorsArray = volumeObj.getJSONArray("authors");
					txtBookAuthor.setText(authorsArray.optString(0));
				}

				// Get Book Publisher
				if (volumeObj.has("publisher"))
					txtBookPublisher.setText(volumeObj.optString("publisher"));
				else
					txtBookPublisher.setText("");

				checkBoxBookInLibrary.setChecked(true);
				checkBoxBookIsRead.setChecked(false);

				// Get Book Summary<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				if (volumeObj.has("description"))
					txtBookSummary.setText(volumeObj.optString("description"));
				else
					txtBookSummary.setText("");

				setBookImageURL("https://covers.openlibrary.org/b/isbn/" + txtBookISBN.getText() + "-M.jpg");
				Glide.with(getApplicationContext()).load(getBookImageURL()).error(R.drawable.icon_no_image).into(imgBook);
			} catch (JSONException e) {
				Log.e("Developer Error", e.getLocalizedMessage());
				Toast.makeText(context, R.string.get_book_info_ErrorMessage, Toast.LENGTH_SHORT).show();
			}
		}, BookUpdateActivity::onErrorResponse);
		requestQueue.add(jsonObjectRequest);
	}

	private static void onErrorResponse(VolleyError error) {
		Log.e("Developer Error", error.getLocalizedMessage());
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
		Intent intent = new Intent(getApplicationContext(), BookMainActivity.class);
		startActivity(intent);
		super.onBackPressed();
	}

}