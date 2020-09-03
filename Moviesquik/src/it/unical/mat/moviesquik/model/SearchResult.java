/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public class SearchResult
{
	private String query;
	private List<MediaContent> contents;
	private List<User> users;
	
	public SearchResult()
	{
		contents = new ArrayList<MediaContent>();
		users = new ArrayList<User>();
	}
	
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
	
	public List<User> getUsers()
	{
		return users;
	}
	public void setUsers(List<User> users)
	{
		this.users = users;
	}
	
}
