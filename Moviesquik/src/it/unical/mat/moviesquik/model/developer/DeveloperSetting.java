/**
 * 
 */
package it.unical.mat.moviesquik.model.developer;

/**
 * @author Agostino
 *
 */
public class DeveloperSetting
{
	private Long id;
	private String apiKey;
	private Boolean active;
	private String assistantServiceKey;
	private Boolean playAction;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getApiKey()
	{
		return apiKey;
	}
	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}
	public Boolean getActive()
	{
		return active;
	}
	public void setActive(Boolean active)
	{
		this.active = active;
	}
	public String getAssistantServiceKey()
	{
		return assistantServiceKey;
	}
	public void setAssistantServiceKey(String assistantServiceKey)
	{
		this.assistantServiceKey = assistantServiceKey;
	}
	public Boolean getPlayAction()
	{
		return playAction;
	}
	public void setPlayAction(Boolean playAction)
	{
		this.playAction = playAction;
	}
	
}
