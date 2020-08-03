/**
 * 
 */
package it.unical.mat.moviesquik.model.media;

import java.util.List;

/**
 * @author Agostino
 *
 */
public class MediaContentSearchResult
{
	private String type;
	private String viewTemplate;
	private List<MediaContent> fullContents;
	private List<MediaContent> topRatedContents;
	private List<MediaContent> mostPopularContents;
	private List<MediaContent> mostFavoritesContents;
	private List<MediaContent> suggestedContents;
	private List<MediaContent> recentlyWatchedContents;
	
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getViewTemplate()
	{
		return viewTemplate;
	}
	public void setViewTemplate(String viewTemplate)
	{
		this.viewTemplate = viewTemplate;
	}
	public List<MediaContent> getFullContents()
	{
		return fullContents;
	}
	public void setFullContents(List<MediaContent> fullContents)
	{
		this.fullContents = fullContents;
	}
	public List<MediaContent> getTopRatedContents()
	{
		return topRatedContents;
	}
	public void setTopRatedContents(List<MediaContent> topRatedContents)
	{
		this.topRatedContents = topRatedContents;
	}
	public List<MediaContent> getMostPopularContents()
	{
		return mostPopularContents;
	}
	public void setMostPopularContents(List<MediaContent> mostPopularContents)
	{
		this.mostPopularContents = mostPopularContents;
	}
	public List<MediaContent> getMostFavoritesContents()
	{
		return mostFavoritesContents;
	}
	public void setMostFavoritesContents(List<MediaContent> mostFavoritesContents)
	{
		this.mostFavoritesContents = mostFavoritesContents;
	}
	public List<MediaContent> getSuggestedContents()
	{
		return suggestedContents;
	}
	public void setSuggestedContents(List<MediaContent> suggestedContents)
	{
		this.suggestedContents = suggestedContents;
	}
	public List<MediaContent> getRecentlyWatchedContents()
	{
		return recentlyWatchedContents;
	}
	public void setRecentlyWatchedContents(List<MediaContent> recentlyWatchedContents)
	{
		this.recentlyWatchedContents = recentlyWatchedContents;
	}
}
