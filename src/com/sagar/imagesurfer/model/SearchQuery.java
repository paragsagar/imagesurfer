package com.sagar.imagesurfer.model;

import java.io.Serializable;

public class SearchQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6245752827820802554L;

	public static String googleImageAPIUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	
	String rszStr = "rsz=";
	String rsize = "8";
	String amp = "&";
	public String queryString,size,color,type,site;
	
	@Override
	public String toString() {
		// 
		String url = googleImageAPIUrl+queryString+amp+rszStr+rsize;
		
		if(color!=null && color.length() >0  )
			url = url +amp+ "imgcolor="+color;
		if(size!=null  && size.length() >0  )
			url = url +amp+ "imgsz="+size;
		if(type!=null  && type.length() >0  )
			url = url +amp+ "imgtype="+type;
		if(site!=null  && site.length() >0  )
			url = url +amp+ "as_sitesearch="+site;

		return url;
	}
}
