/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

/**
 * @author Agostino
 *
 */
public class MediaContentStatistics
{
	private Integer rate;
	private Integer[] ratingBreakdown;
	private Float actualRate;
	private Long likes;
	private Long nolikes;
	private Long views;
	
	public Integer getRate()
	{
		return rate;
	}
	public void setRate(Integer rate)
	{
		this.rate = rate;
	}
	public Integer[] getRatingBreakdown()
	{
		return ratingBreakdown;
	}
	public void setRatingBreakdown(Integer[] ratingBreakdown)
	{
		this.ratingBreakdown = ratingBreakdown;
	}
	public Float getActualRate()
	{
		return actualRate;
	}
	public void setActualRate(Float actualRate)
	{
		this.actualRate = actualRate;
	}
	public Long getLikes()
	{
		return likes;
	}
	public void setLikes(Long likes)
	{
		this.likes = likes;
	}
	public Long getNolikes()
	{
		return nolikes;
	}
	public void setNolikes(Long notlikes)
	{
		this.nolikes = notlikes;
	}
	public Long getViews()
	{
		return views;
	}
	public void setViews(Long views)
	{
		this.views = views;
	}
	
}
