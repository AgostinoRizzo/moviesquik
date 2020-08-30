/**
 * 
 */
package it.unical.mat.moviesquik.controller.developer;

import java.util.Random;

import javax.servlet.ServletContext;

import it.unical.mat.moviesquik.util.ConfigUtil;

/**
 * @author Agostino
 *
 */
public class APIKeyManager
{	
	private static final short API_KEY_LENGTH = 10;
	private static final Random RANDOM = new Random();
	private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int CHARACTER_SET_LENGTH = CHARACTER_SET.length();
	
	public static String generateNewAPIKey()
	{
		final StringBuilder keyBuilder = new StringBuilder();
		
		for ( int i=0; i<API_KEY_LENGTH; ++i )
			keyBuilder.append(getRandomCharacter());
		
		return keyBuilder.toString();
	}
	
	public static String getAssistantServiceAPI( final ServletContext context )
	{
		return ConfigUtil.loadConfigFile(ConfigUtil.MOVIESQUIK_API_CONFIG_FILENAME, context)
				.getProperty("assistant_service_key");
	}
	
	private static char getRandomCharacter()
	{
		return CHARACTER_SET.charAt(RANDOM.nextInt(CHARACTER_SET_LENGTH));
	}
}
