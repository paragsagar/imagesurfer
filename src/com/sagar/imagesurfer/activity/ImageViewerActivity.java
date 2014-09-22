package com.sagar.imagesurfer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.sagar.imagesurfer.R;
import com.sagar.imagesurfer.model.Image;
import com.squareup.picasso.Picasso;

public class ImageViewerActivity extends Activity {
	
	ImageView ivFullImage;
	TextView tvFullText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		getActionBar().hide();
		Image image = (Image) getIntent().getSerializableExtra("image");
		
		ivFullImage = (ImageView)findViewById(R.id.ivFullImage);
		tvFullText = (TextView) findViewById(R.id.tvFullTitle);
		
		Picasso.with(this).load(image.fullUrl).resize(200,200).centerCrop().into(ivFullImage);
		tvFullText.setText(Html.fromHtml(image.title));
	}
	
}
