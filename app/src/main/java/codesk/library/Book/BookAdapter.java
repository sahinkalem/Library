package codesk.library.Book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import codesk.library.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
	LayoutInflater layoutInflater;
	List<Book> bookList;
	Context context;

	public BookAdapter(Context context, List<Book> bookList) {
		this.layoutInflater = LayoutInflater.from(context);
		this.bookList = bookList;
		this.context = context;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = layoutInflater.inflate(R.layout.book_list_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Book book = bookList.get(position);
		holder.textViewBookID.setText(String.valueOf(book.getBookID()));
		holder.textViewBookName.setText(book.getBookName());
		holder.textViewBookAuthor.setText(book.getBookAuthor());
		holder.textViewBookPublisher.setText(book.getBookPublisher());
		holder.txtBookISBN.setText(book.getBookISBN());
		holder.txtBookLanguage.setText(book.getBookLanguage());
		holder.txtBookType.setText(book.getBookType());
		holder.cboxBookRead.setChecked(book.getBookRead());
		holder.cboxBookInLibray.setChecked(book.getBookInLibrary());

		if (book.getBookImageURL() == null || book.getBookImageURL().equals("")) {
			Glide.with(layoutInflater.getContext()).load(R.drawable.img_no_image).into(holder.imageView);
		} else {
			Glide.with(layoutInflater.getContext()).load(book.getBookImageURL()).into(holder.imageView);
		}

		//
		// List Item Click
		holder.itemView.setOnClickListener(v -> {
			Intent intent = new Intent(layoutInflater.getContext(), BookUpdateActivity.class);
			intent.putExtra("BookID", Integer.toString(book.getBookID()));
			v.getContext().startActivity(intent);
		});
	}

	@Override
	public int getItemCount() {
		return bookList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView textViewBookID;
		private final TextView textViewBookName;
		private final TextView textViewBookAuthor;
		private final TextView textViewBookPublisher;
		private final TextView txtBookISBN;
		private final TextView txtBookLanguage;
		private final TextView txtBookType;
		private final CheckBox cboxBookRead;
		private final CheckBox cboxBookInLibray;
		private final ImageView imageView;

		public ViewHolder(View view) {
			super(view);

			textViewBookID = view.findViewById(R.id.textBookID);
			textViewBookName = view.findViewById(R.id.textBookName);
			textViewBookAuthor = view.findViewById(R.id.textBookAuthor);
			textViewBookPublisher = view.findViewById(R.id.textBookPublisher);
			imageView = itemView.findViewById(R.id.imageView);

			txtBookISBN = itemView.findViewById(R.id.txtBookISBN);
			txtBookLanguage = itemView.findViewById(R.id.txtBookLanguage);
			cboxBookRead = itemView.findViewById(R.id.cboxBookRead);
			cboxBookInLibray = itemView.findViewById(R.id.cboxBookInLibray);
			txtBookType = itemView.findViewById(R.id.txtBookType);
		}
	}

}


