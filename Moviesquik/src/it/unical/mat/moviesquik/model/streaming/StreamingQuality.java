/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

/**
 * @author Agostino
 *
 */
public enum StreamingQuality
{
	_4K, HD, LD;
	
	public static String toString( final StreamingQuality q )
	{
		switch (q) 
		{
		case _4K: return "4k";
		case HD:  return "hd";
		case LD:  return "ld";
		default:  return "ld";
		}
	}
	
}
