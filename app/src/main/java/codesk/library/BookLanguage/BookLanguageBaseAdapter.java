package codesk.library.BookLanguage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import codesk.library.R;

public class BookLanguageBaseAdapter extends BaseAdapter {
	private final ArrayList<BookLanguage> bookLanguageArrayList;
	private final Context context;

	public BookLanguageBaseAdapter(Context _context, ArrayList<BookLanguage> _bookLanguageArrayList) {
		context = _context;
		bookLanguageArrayList = _bookLanguageArrayList;
	}

	@Override
	public int getCount() {
		return bookLanguageArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return bookLanguageArrayList.get(position).getBookLanguageID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklanguage_spinner_item, null);

		//TextView textViewBookTypeID = view.findViewById(R.id.spinnerItemBookTypeID);
		TextView textViewBookLanguage = view.findViewById(R.id.spinnerItemBookLanguage);

		BookLanguage bookLanguage = bookLanguageArrayList.get(position);

		//textViewBookTypeID.setText(Integer.toString(bookType.getBookTypeID()));
		textViewBookLanguage.setText(bookLanguage.getBookLanguageName());
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklanguage_spinner_item, null);

		//TextView textViewBookTypeID = view.findViewById(R.id.spinnerItemBookTypeID);
		TextView textViewBookType = view.findViewById(R.id.spinnerItemBookLanguage);

		BookLanguage bookLanguage = bookLanguageArrayList.get(position);

		//textViewBookTypeID.setText(Integer.toString(bookType.getBookTypeID()));
		textViewBookType.setText(bookLanguage.getBookLanguageName());
		return view;
	}
}
