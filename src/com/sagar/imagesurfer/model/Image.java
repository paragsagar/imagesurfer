package com.sagar.imagesurfer.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Image implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 4749396513537118096L;

	public String thumbUrl,fullUrl,title;
	public int width,height;
	
	public Image(JSONObject json){
		try {
			this.thumbUrl = json.getString("tbUrl");
			this.fullUrl = json.getString("url");
			this.title = json.getString("title");
			this.height = json.getInt("height");
			this.width = json.getInt("width");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Image> fromJSONArray(JSONArray images)
	{
		ArrayList<Image> results = new ArrayList<Image>();
		Image image;
		
		for (int i = 0; i < images.length(); i++) {
			try {
				results.add(new Image(images.getJSONObject(i)));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return results;
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title.toString();
	}
	
	 
}
