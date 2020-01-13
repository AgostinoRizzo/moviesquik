/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Agostino
 *
 */
public class FileUtil
{
	public static String read( final String filename )
	{
		try
		{ return new String(Files.readAllBytes(Paths.get(filename))); } 
		
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
