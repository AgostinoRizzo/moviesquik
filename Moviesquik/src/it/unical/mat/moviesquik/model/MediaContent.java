/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

/**
 * @author Agostino
 *
 */
public class MediaContent
{
	private Long id;
	private String title;
	private String type;
	private short year;
	private Date released;
	private String runtime;
	private String genre;
	private String plot;
	private String poster;
	private String production;
	private String director;
	private String actors;
	
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
	
}