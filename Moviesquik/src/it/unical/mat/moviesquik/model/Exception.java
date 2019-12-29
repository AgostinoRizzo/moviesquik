/**
 * 
 */
package it.unical.mat.moviesquik.model;

/**
 * @author Agostino
 *
 */
public class Exception
{
	private String type;
	
	public Exception()
	{}

	public Exception(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
