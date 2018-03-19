package com.kipropkorir.zalego;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class ShowImageAdapter extends RecyclerView.Adapter<ShowImageAdapter.ViewHolder> {

	private ArrayList<String> mImagesList;
	private Context mContext;

	public ShowImageAdapter(Context ctx, ArrayList<String> i) {
		this.mContext = ctx;
		this.mImagesList = i;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_multiphoto_item, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		String imageUrl = mImagesList.get(position);

		Glide.with(mContext)
				.load("file://"+imageUrl)
				.centerCrop()
				.error(R.drawable.ic_arrow_down)
				.into(holder.imageView);
	}

	@Override
	public int getItemCount() {
		return mImagesList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView imageView;
		public ViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.imageView1);
		}
	}
}
