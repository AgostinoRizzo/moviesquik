/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author Agostino
 *
 */
public class JSONUtil
{
	public static final FieldNamingPolicy FIELD_NAMING_POLICY = FieldNamingPolicy.UPPER_CAMEL_CASE;
	public static final String DATE_FORMAT_PATTERN = "dd MMM yyyy";
	
	public static <T> T readFromFile( final String path, final Class<T> ans_class )
	{
		final GsonBuilder builder = new GsonBuilder();
		builder.setFieldNamingPolicy(FIELD_NAMING_POLICY).create();
		builder.setDateFormat(DATE_FORMAT_PATTERN);
		
		final Gson gson = builder.create();
		
		try
		{
			BufferedReader br = new BufferedReader( new FileReader(path) );
			return gson.fromJson(br, ans_class);
		}
		catch (FileNotFoundException e)
		{ e.printStackTrace(); return null; }
	}
	
	public static String fromListToString( final List<?> lst )
	{
		return new Gson().toJson(lst);
	}
	
	public static String toJson( final Object obj )
	{
		return new Gson().toJson(obj, obj.getClass());
	}
	
	public static JsonObject readFromReader( final BufferedReader reader )
	{
		String line;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Gson().fromJson(reader, JsonObject.class);
	}
	
	public static JsonArray fromStringToArray( final String json )
	{
		return (JsonArray) JsonParser.parseString(json);
	}
}
