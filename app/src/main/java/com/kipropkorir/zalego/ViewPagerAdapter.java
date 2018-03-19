package com.kipropkorir.zalego;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by manasvi on 9/27/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

	Context context;
	LayoutInflater mLayoutInflater;
	ArrayList<String> mImagesList;

	int[] mResources;

	public ViewPagerAdapter(Context ctx, ArrayList<String> mImagesList) {
		this.context = ctx;
		this.mImagesList = mImagesList;
		this.mResources = mResources;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mImagesList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final View itemView = mLayoutInflater.inflate(R.layout.view_pager, container, false);
		ImageView ivView = (ImageView) itemView.findViewById(R.id.ivView);
		final ImageView ivCross = (ImageView) itemView.findViewById(R.id.cross);
		final EditText editText = (EditText) itemView.findViewById(R.id.edtView);
		final TextView txtView = (TextView) itemView.findViewById(R.id.txtView);
		final LinearLayout ll = (LinearLayout) itemView.findViewById(R.id.ll);
		txtView.setVisibility(View.GONE);
		ivCross.setVisibility(View.GONE);
		editText.setVisibility(View.VISIBLE);
		editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			                                   @Override
			                                   public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				                                   if (i == EditorInfo.IME_ACTION_DONE) {
					                                   txtView.setVisibility(View.VISIBLE);
					                                   txtView.setText(editText.getText());
					                                   ivCross.setVisibility(View.VISIBLE);
					                                   editText.setVisibility(View.INVISIBLE);
					                                   InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
					                                   imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
					                                   return true;
				                                   }
				                                   return false;
			                                   }
		                                   });
		ivCross.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				txtView.setVisibility(View.GONE);
				ivCross.setVisibility(View.GONE);
				editText.setVisibility(View.VISIBLE);
				editText.setText("");
			}
		});
				Glide.with(context)
						.load(mImagesList.get(position).toString())
						.centerCrop()
						.error(R.drawable.ic_arrow_down)
						.into(ivView);
		container.addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((RelativeLayout) object);
	}
}
