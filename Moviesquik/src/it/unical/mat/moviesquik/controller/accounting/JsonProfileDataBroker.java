/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author Agostino
 *
 */
public class JsonProfileDataBroker implements SubmittedProfileDataBroker
{
	private final JsonObject json;
	
	public JsonProfileDataBroker( final Reader source )
	{
		final Gson gson = new Gson();
		this.json = gson.fromJson(source, JsonObject.class);
	}
	
	@Override
	public String getParameter(String param)
	{
		return json.get(param).getAsString();
	}
	
}
