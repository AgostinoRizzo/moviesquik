/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.nio.charset.StandardCharsets;

/**
 * @author Agostino
 *
 */
public class EncodingUtil
{
	public static String decodeString( final String from )
	{
		final byte[] source_bytes = from.getBytes();
		final StringBuilder decoded_string = new StringBuilder();
		
		for ( int i=0; i<source_bytes.length; ++i )
		{
			if ( source_bytes[i] == -16 && source_bytes.length - i > 4 )
			{
				byte[] symbol_bytes = {source_bytes[i], source_bytes[i+1], source_bytes[i+2], source_bytes[i+3]};
				symbol_bytes = new String(symbol_bytes, StandardCharsets.UTF_8).getBytes();
			}
			else
				decoded_string.append(source_bytes[i]);
		}
		return "";
	}
}
