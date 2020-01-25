/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.List;

/**
 * @author Agostino
 *
 */
public class SearchResult
{
	private String query;
	private List<MediaContent> contents;
	
	public String getQuery()
	{
		return query;
	}
	public void setQuery(String query)
	{
		this.query = query;
	}
	
	public List<MediaContent> getContents()
	{
		return contents;
	}
	public void setContents(List<MediaContent> contents)
	{
		this.contents = contents;
	}
}
