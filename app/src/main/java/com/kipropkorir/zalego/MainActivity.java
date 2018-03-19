package com.kipropkorir.zalego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private ArrayList<String> galleryImageUrls;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init(){
		findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this,MultiImagePick.class);
				startActivity(intent);
			}
		});
	}


}
