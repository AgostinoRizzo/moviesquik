/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Agostino
 *
 */
public class MediaContentsSearchFilter implements SearchFilter
{
	public static final MediaContentsSearchFilter EMPTY = new MediaContentsSearchFilter();
	
	private List<String> genres;
	private DurationFilter duration;
	private FeaturesFilter features;
	
	public MediaContentsSearchFilter()
	{
		genres = new ArrayList<String>();
		duration = DurationFilter.ANY;
		features = FeaturesFilter.ANY;
	}
	public MediaContentsSearchFilter( final JsonObject json )
	{
		final JsonArray jsonGenres = json.getAsJsonArray("genres");
		genres = new ArrayList<String>();
		
		for ( final JsonElement el : jsonGenres )
			genres.add( el.getAsString() );
		
		duration = DurationFilter.getFromJson(json);
		features = FeaturesFilter.getFromJson(json);
	}
	public List<String> getGenres()
	{
		return genres;
	}
	public void setGenres(List<String> genres)
	{
		this.genres = genres;
	}
	public DurationFilter getDuration()
	{
		return duration;
	}
	public void setDuration(DurationFilter duration)
	{
		this.duration = duration;
	}
	public FeaturesFilter getFeatures()
	{
		return features;
	}
	public void setFeatures(FeaturesFilter features)
	{
		this.features = features;
	}
}
