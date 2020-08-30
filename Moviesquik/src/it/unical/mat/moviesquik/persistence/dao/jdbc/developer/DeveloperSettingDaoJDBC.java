/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.developer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.developer.DeveloperSetting;
import it.unical.mat.moviesquik.persistence.dao.developer.DeveloperSettingDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.IdBroker;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class DeveloperSettingDaoJDBC extends AbstractDaoJDBC<DeveloperSetting> implements DeveloperSettingDao
{
	protected static final String INSERT_STATEMENT  = "INSERT INTO developer_setting(developer_setting_id, user_id) VALUES (?,?)";
	protected static final String UPDATE_STATEMENT  = "UPDATE developer_setting SET api_key = ?, active = ?, assistant_service_key = ?, play_action = ?";
	protected static final String FIND_BY_USER_QUERY = "SELECT * FROM developer_setting WHERE user_id = ?";
	
	public DeveloperSettingDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}
	
	@Override
	public boolean insert(DeveloperSetting setting, User owner)
	{
		try
		{
			setting.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, setting.getId());
			statement.setLong        (2, owner.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean update(DeveloperSetting setting)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			final String apiKey = setting.getApiKey();
			if ( apiKey == null ) statement.setNull(1, Types.NULL);
			else statement.setString   (1, apiKey);
			
			statement.setBoolean  (2, setting.getActive());
			
			final String assistantServiceKey = setting.getAssistantServiceKey();
			if ( assistantServiceKey == null ) statement.setNull(3, Types.NULL);
			else statement.setString   (3, assistantServiceKey);
			
			statement.setBoolean  (4, setting.getPlayAction());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public DeveloperSetting findByUser(User owner)
	{
		final DeveloperSetting setting = managefindByUser(owner);
		if ( setting != null )
			return setting;
		insert(new DeveloperSetting(), owner);
		return managefindByUser(owner);
	}
	
	@Override
	protected DeveloperSetting createFromResult(ResultSet result) throws SQLException
	{
		final DeveloperSetting setting = new DeveloperSetting();
		
		setting.setId(result.getLong("developer_setting_id"));
		
		final String apiKey = result.getString("api_key");
		if ( result.wasNull() ) setting.setApiKey(null);
		else setting.setApiKey(apiKey);
		
		setting.setActive(result.getBoolean("active"));
		
		final String assistantServiceKey = result.getString("assistant_service_key");
		if ( result.wasNull() ) setting.setAssistantServiceKey(null);
		else setting.setAssistantServiceKey(assistantServiceKey);
		
		setting.setPlayAction(result.getBoolean("play_action"));
		
		return setting;
	}
	
	private DeveloperSetting managefindByUser(User owner)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_QUERY);
			
			statement.setLong(1, owner.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
}
