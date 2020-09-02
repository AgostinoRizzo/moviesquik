/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

/**
 * @author Agostino
 *
 */
public class ClientGeolocation
{
	private Float latitude;
	private Float longitude;
	
	public ClientGeolocation( final Float latitude, final Float longitude )
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Float getLatitude()
	{
		return latitude;
	}
	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}
	public Float getLongitude()
	{
		return longitude;
	}
	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}
	
}
