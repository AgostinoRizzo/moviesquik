/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Agostino
 *
 */
public class RequestProfileDataBroker implements SubmittedProfileDataBroker
{
	private final HttpServletRequest req;
	
	public RequestProfileDataBroker( final HttpServletRequest req )
	{
		this.req = req;
	}
	
	@Override
	public String getParameter(String param)
	{
		return req.getParameter(param);
	}
	
}
