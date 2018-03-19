package com.kipropkorir.zalego;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class CustomAdapter extends PagerAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	int[] mResources;


	public CustomAdapter(Context context, int[] mResources) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mResources = mResources;

	}

	@Override
	public int getCount() {
		return mResources.length;
	}


	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mLayoutInflater.inflate(R.layout.view_pager, container, false);
		ImageView ivWelcomeImages = (ImageView) itemView.findViewById(R.id.ivView);
		ivWelcomeImages.setImageResource(mResources[position]);
		container.addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}
}
