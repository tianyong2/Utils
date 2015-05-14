package com.eden.gif.two;

import java.util.List;

public class GifObj {

	private String gifID;
	private List<GifUtil> drawable; 
	
	public GifObj(String gifID,List<GifUtil>  drawableList)
	{
		this.gifID = gifID;
		this.drawable = drawableList;
	}
	public String getGifId()
	{
		return this.gifID;
	}
	public List<GifUtil> getGifTextDrawableList()
	{
		return this.drawable;
	}	
}
