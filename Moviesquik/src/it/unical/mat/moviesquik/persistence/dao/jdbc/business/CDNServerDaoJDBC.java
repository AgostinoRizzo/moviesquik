/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.business.CDNServer;
import it.unical.mat.moviesquik.model.business.CDNServerLocation;
import it.unical.mat.moviesquik.model.streaming.StreamServiceTable;
import it.unical.mat.moviesquik.persistence.dao.business.CDNServerDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class CDNServerDaoJDBC extends AbstractDaoJDBC<CDNServer> implements CDNServerDao
{
	protected static final String FIND_ALL_QUERY = "select * from cdn_server";
	
	public CDNServerDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public List<CDNServer> findAll()
	{
		final List<CDNServer> servers = new ArrayList<CDNServer>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				servers.add( createFromResult(result) );
			return servers;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return servers; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected CDNServer createFromResult(ResultSet result) throws SQLException
	{
		final CDNServer server = new CDNServer();
		final CDNServerLocation location = new CDNServerLocation();
		
		server.setId(result.getLong("cdn_server_id"));
		server.setKey(result.getString("server_key"));
		server.setName(result.getString("name"));
		
		location.setLatitude(result.getFloat("latitude"));
		location.setLongitude(result.getFloat("longitude"));
		
		server.setLocation(location);
		
		String description = result.getString("description");
		if ( result.wasNull() )
			description = null;
		
		server.setDescription(description);
		server.setStatus( StreamServiceTable.getInstance().isStreamServiceOn(server.getKey()) );
		
		return server;
	}
	
}
