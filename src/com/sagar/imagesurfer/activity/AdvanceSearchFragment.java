package com.sagar.imagesurfer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sagar.imagesurfer.R;
import com.sagar.imagesurfer.model.SearchQuery;

public class AdvanceSearchFragment extends Fragment {
	
	private SearchQuery search;
	
	private EditText etImgSize,etColor,etImgType,etSite;
	private Button btnSave;

	public AdvanceSearchFragment()
	{
		
	}
	

	 @Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			
			View view = inflater.inflate(R.layout.fragment_advance_search, container,false);
			etImgSize = (EditText) view.findViewById(R.id.etImageSize);
			
			etColor = (EditText) view.findViewById(R.id.etColor);
			etImgType = (EditText) view.findViewById(R.id.etImageType);
			etSite = (EditText) view.findViewById(R.id.etSiteFilter);
			btnSave = (Button)view.findViewById(R.id.btnSave);
			
			 etColor.setText(getSearch().color);
			 etImgSize.setText(getSearch().size);
			 etImgType.setText(getSearch().type);
			 etSite.setText(getSearch().site);
			 
			 
			
			btnSave.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					 getSearch().color = etColor.getText().toString();
					 getSearch().size = etImgSize.getText().toString();
					 getSearch().type = etImgType.getText().toString();
					 getSearch().site = etSite.getText().toString();
					 
					 AdvanceSearchFragmentListener listner = (AdvanceSearchFragmentListener)getActivity();
					 listner.onFinishAdvanceSearch(getSearch());
				}
			});
			
			//view.setBackgroundResource(android.R.color.transparent);
			
			return  view;
		}
		 
	 public SearchQuery getSearch() {
		return search;
	}


	public void setSearch(SearchQuery search) {
		this.search = search;
	}

		public interface AdvanceSearchFragmentListener{
			 public void onFinishAdvanceSearch(SearchQuery search);
		 }
	 

}
