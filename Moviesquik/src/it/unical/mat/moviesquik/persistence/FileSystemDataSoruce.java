/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.io.File;

/**
 * @author Agostino
 *
 */
public class FileSystemDataSoruce
{
	private static final String USER_PROFILE_IMAGE_DIR = "res" + File.separator + "user";
	
	private String srcPath;
	
	public String getSrcPath()
	{
		return srcPath;
	}
	
	public void setSrcPath(String srcPath)
	{
		this.srcPath = srcPath;
	}
	
	public String getUserProfileImagePath()
	{
		return (srcPath.endsWith(File.separator)) 
				? srcPath + USER_PROFILE_IMAGE_DIR
				: srcPath + File.separator + USER_PROFILE_IMAGE_DIR;
	}
	
	public String fetchAbsUserProfileImagePath( final String imgFileName )
	{
		return ( imgFileName != null && imgFileName.length() > 0 ) ? USER_PROFILE_IMAGE_DIR + File.separator + imgFileName : null;
	}
}
