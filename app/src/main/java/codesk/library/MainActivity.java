package codesk.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesk.library.Book.BookMainActivity;

public class MainActivity extends AppCompatActivity {

	ImageView imageView;
	Button btnNext;
	Button btnSkip;
	TextView lblMessage;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Functions functions = new Functions(this);
		if (functions.DatabaseIsExist()) {
			Intent intent = new Intent(this, BookMainActivity.class);
			startActivity(intent);
			finish();
		} else {
			imageView = findViewById(R.id.imageView);
			btnNext = findViewById(R.id.btnNext);
			btnSkip=findViewById(R.id.btnSkip);
			lblMessage = findViewById(R.id.lblMessage);
			imageView.setVisibility(View.GONE);

			btnNext.setOnClickListener(v -> {
				lblMessage.setVisibility(View.GONE);
				imageView.setVisibility(View.VISIBLE);
				btnNext.setText("NEXT");
				i++;
				switch (i) {
					case 1:
						imageView.setImageResource(R.drawable.info_page1);
						break;
					case 2:
						imageView.setImageResource(R.drawable.info_page2);
						break;
					case 3:
						imageView.setImageResource(R.drawable.info_page3);
						break;
					case 4:
						imageView.setImageResource(R.drawable.info_page4);
						break;
					case 5:
						imageView.setImageResource(R.drawable.info_page5);
						break;
					case 6:
						imageView.setImageResource(R.drawable.info_page6);
						break;
					case 7:
						imageView.setImageResource(R.drawable.info_page7);
						break;
					case 8:
						imageView.setImageResource(R.drawable.info_page8);
						btnNext.setText("FINISH");
						break;
					case 9:
						Intent intent = new Intent(MainActivity.this, BookMainActivity.class);
						startActivity(intent);
						finish();
				}
			});

			btnSkip.setOnClickListener(v -> {
				Intent intent = new Intent(MainActivity.this, BookMainActivity.class);
				startActivity(intent);
				finish();
			});
		}
	}
}