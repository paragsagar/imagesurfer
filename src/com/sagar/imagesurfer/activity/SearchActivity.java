package com.sagar.imagesurfer.activity;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sagar.imagesurfer.R;
import com.sagar.imagesurfer.activity.AdvanceSearchFragment.AdvanceSearchFragmentListener;
import com.sagar.imagesurfer.adapter.ImageResultAdapter;
import com.sagar.imagesurfer.model.Image;
import com.sagar.imagesurfer.model.SearchQuery;

public class SearchActivity extends FragmentActivity implements AdvanceSearchFragmentListener {

	public static final String ADV_SEARCH_FRAGMENT = "ADV_SEARCH_FRAGMENT";
	SearchView searchView; //Search icon on Action Bar
	ArrayList<Image> imageResults ; //List of images to be displayed on Grid
	GridView gridView ;
	ImageResultAdapter imageAdapter;
	public SearchQuery search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		gridView = (GridView)findViewById(R.id.imageGridView);
		imageResults = new ArrayList<Image>();
		imageAdapter = new ImageResultAdapter(this,imageResults);
		gridView.setAdapter(imageAdapter);
		search = new SearchQuery();
		setGridListeners();
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
	}
	
	/**
	 * Setting Grid listeners for 
	 */
	private void setGridListeners() {
		
		//One Click of a Photo
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				Intent i = new Intent(SearchActivity.this,ImageViewerActivity.class);
				i.putExtra("image",imageResults.get(position) );
				startActivity(i);
			}
		});
		
		//Endless Scrolling
		
		gridView.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				loadAsyncData(page);
				
			}
		});
		
	}

/**
 * Method to load Async Data 
 * @param page
 */
	private void loadAsyncData(final int page) {
		// perform your query here
		AsyncHttpClient client = new AsyncHttpClient();

//		googleImageAPIUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+search.queryString+"&rsz=8&start="+page;

		Log.d("DEBUG","Query String = "+search.toString());
		
		client.get(search.toString(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
				JSONArray imageResultJson ;
				Log.d("DEBUG",response.toString());

				try {
					imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
					if(page <1)
						imageResults.clear();
					imageResults.addAll(Image.fromJSONArray(imageResultJson));
					imageAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// 
					e.printStackTrace();
				}
			}
			

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Could not fetch data from internet! "+ errorResponse, Toast.LENGTH_SHORT).show();
			}

		});
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()) {
		case R.id.miAdvSearch:

//			Intent i = new Intent(this, .class);
//			i.putExtra("search_query",search);
//			startActivityForResult(i, 50);
			
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction txn = fm.beginTransaction();
			AdvanceSearchFragment advSearch = new AdvanceSearchFragment();
			advSearch.setSearch(search);
			txn.add(R.id.activity_main_search,advSearch,ADV_SEARCH_FRAGMENT);
			txn.commit();
			
			
			return true;

		default:
			return false;
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);
		MenuItem menuItem = menu.findItem(R.id.action_search);
		searchView = (SearchView)menuItem.getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				search.queryString = query;
				loadAsyncData(0);
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public void onFinishAdvanceSearch(SearchQuery search) {
		this.search = search;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction txn = fm.beginTransaction();fm.findFragmentByTag("");
		AdvanceSearchFragment advSearch = (AdvanceSearchFragment)fm.findFragmentByTag(ADV_SEARCH_FRAGMENT);
		txn.hide(advSearch);
		txn.commit();
		//if there is any query string then do another async load.
		if(search!= null && search.queryString != null && !search.queryString.equalsIgnoreCase("null") && search.queryString.length()>0)
			loadAsyncData(0);
	}
}
