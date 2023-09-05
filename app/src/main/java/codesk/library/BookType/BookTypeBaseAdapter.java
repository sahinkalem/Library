package codesk.library.BookType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import codesk.library.R;

public class BookTypeBaseAdapter extends BaseAdapter {
	private final ArrayList<BookType> bookTypeArrayList;
	private final Context context;

	public BookTypeBaseAdapter(Context _context, ArrayList<BookType> _bookTypeArrayList) {
		context = _context;
		bookTypeArrayList = _bookTypeArrayList;
	}

	@Override
	public int getCount() {
		return bookTypeArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return bookTypeArrayList.get(position).getBookTypeID();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booktype_spinner_item, null);

		//TextView textViewBookTypeID = view.findViewById(R.id.spinnerItemBookTypeID);
		TextView textViewBookType = view.findViewById(R.id.spinnerItemBookType);

		BookType bookType = bookTypeArrayList.get(position);

		//textViewBookTypeID.setText(Integer.toString(bookType.getBookTypeID()));
		textViewBookType.setText(bookType.getBookTypeName());
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booktype_spinner_item, null);

		//TextView textViewBookTypeID = view.findViewById(R.id.spinnerItemBookTypeID);
		TextView textViewBookType = view.findViewById(R.id.spinnerItemBookType);

		BookType bookType = bookTypeArrayList.get(position);

		//textViewBookTypeID.setText(Integer.toString(bookType.getBookTypeID()));
		textViewBookType.setText(bookType.getBookTypeName());
		return view;
	}
}
