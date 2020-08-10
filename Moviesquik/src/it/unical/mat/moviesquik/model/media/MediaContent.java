/**
 * 
 */
package it.unical.mat.moviesquik.model.media;

import java.util.Date;

import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MediaContent
{
	protected Long id;
	protected String title;
	protected String type;
	protected short year;
	protected Date released;
	protected String runtime;
	protected String genre;
	protected String plot;
	protected String poster;
	protected String production;
	protected String director;
	protected String actors;
	protected float rating;
	protected Long views;
	protected Long likes;
	protected float imdbRating;
	protected Integer streamTime;
	protected MediaContentStatistics statistics;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public short getYear()
	{
		return year;
	}
	public void setYear(short year)
	{
		this.year = year;
	}
	public Date getReleased()
	{
		return released;
	}
	public void setReleased(Date releasedDate)
	{
		this.released = releasedDate;
	}
	public String getHumanReadableReleasedDateTime()
	{
		return DateUtil.toHumanReadable(released);
	}
	public String getRuntime()
	{
		return runtime;
	}
	public void setRuntime(String runtime)
	{
		this.runtime = runtime;
	}
	public String getGenre()
	{
		return genre;
	}
	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	public String getPlot()
	{
		return plot;
	}
	public void setPlot(String plot)
	{
		this.plot = plot;
	}
	public String getPoster()
	{
		return poster;
	}
	public void setPoster(String poster)
	{
		this.poster = poster;
	}
	public String getProduction()
	{
		return production;
	}
	public void setProduction(String production)
	{
		this.production = production;
	}
	public String getDirector()
	{
		return director;
	}
	public void setDirector(String director)
	{
		this.director = director;
	}
	public String getActors()
	{
		return actors;
	}
	public void setActors(String actors)
	{
		this.actors = actors;
	}
	
	public float getRating()
	{
		return rating;
	}
	public void setRating(float rating)
	{
		this.rating = rating;
	}
	public int getRatingStarsCount()
	{
		return (int) (rating/10.0*5.0);
	}
	
	public Long getViews()
	{
		return views;
	}
	public void setViews(Long views)
	{
		this.views = views;
	}
	
	public Long getLikes()
	{
		return likes;
	}
	public void setLikes(Long likes)
	{
		this.likes = likes;
	}
	
	public float getImdbRating()
	{
		return imdbRating;
	}
	public void setImdbRating(float imdbRating)
	{
		this.imdbRating = imdbRating;
	}
	public Integer getStreamTime()
	{
		if ( streamTime != null && streamTime > 0 )
			return streamTime;
		try { return Integer.parseInt(runtime.replaceAll("[^0-9]", "")) * 60; }
		catch (NumberFormatException e) { return 7200; }
	}
	public void setStreamTime(Integer streamTime)
	{
		this.streamTime = streamTime;
	}
	public Integer getMinStreamTime()
	{
		return getStreamTime() / 60;
	}
	public MediaContentStatistics getStatistics()
	{
		return statistics;
	}
	public void setStatistics(MediaContentStatistics statistics)
	{
		this.statistics = statistics;
	}
	
	@Override
	public int hashCode()
	{
		return Long.hashCode(getId());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
			return true;
		if ( obj instanceof MediaContent )
			return this.getId().equals(((MediaContent) obj).getId());
		return false;
	}
}
