/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.List;

/**
 * @author Agostino
 *
 */
public class Showcase
{
	private List<MediaContent> topRated;
	private List<MediaContent> mostPopular;
	private List<MediaContent> mostFavorites;
	
	public List<MediaContent> getTopRated()
	{
		return topRated;
	}
	public void setTopRated(List<MediaContent> topRated)
	{
		this.topRated = topRated;
	}
	public List<MediaContent> getMostPopular()
	{
		return mostPopular;
	}
	public void setMostPopular(List<MediaContent> mostPopular)
	{
		this.mostPopular = mostPopular;
	}
	public List<MediaContent> getMostFavorites()
	{
		return mostFavorites;
	}
	public void setMostFavorites(List<MediaContent> mostFavorites)
	{
		this.mostFavorites = mostFavorites;
	}
}
