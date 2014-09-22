package com.sagar.imagesurfer.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagar.imagesurfer.R;
import com.sagar.imagesurfer.model.Image;
import com.squareup.picasso.Picasso;

public class ImageResultAdapter extends ArrayAdapter<Image> {

	public ImageResultAdapter(Context context, List<Image> objects) {
		super(context, R.layout.image_list, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Image image = (Image)getItem(position);
		ViewHolder viewHolder;
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_list, parent,false);
			
			convertView.setTag(viewHolder);
			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivThumbImage);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.ivImage.setImageResource(0);
		}
			
		Picasso.with(getContext()).load(image.thumbUrl).into(viewHolder.ivImage);
		viewHolder.tvTitle.setText(Html.fromHtml(image.title));
		
		return convertView;
	}

	
	public class ViewHolder {
		ImageView ivImage;
		TextView  tvTitle ;
	}
}
