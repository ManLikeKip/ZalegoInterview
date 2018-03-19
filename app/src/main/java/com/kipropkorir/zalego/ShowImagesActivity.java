package com.kipropkorir.zalego;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ShowImagesActivity extends AppCompatActivity {

	private RecyclerView rvShowImage;
	private ViewPager pager;
	private ShowImageAdapter adapter;
	private ArrayList<String> i = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		i = getIntent().getStringArrayListExtra("images");
		Log.d("mana", "onCreate: " + i);
		setViewPager();

	}

	private void setViewPager() {
		pager = (ViewPager) findViewById(R.id.view_pager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this, i);
		pager.setAdapter(adapter);
		pager.setCurrentItem(adapter.getCount() - 1);
	}
}
