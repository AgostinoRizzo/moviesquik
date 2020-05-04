/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Billing;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.persistence.dao.BillingDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class BillingDaoJDBC extends AbstractDaoJDBC<Billing> implements BillingDao
{
	protected static final String INSERT_STATEMENT = "insert into billing (billing_id, start_date, plan, is_trial, family_id) values (?,?,?,?,?)";
	protected static final String FIND_BY_PRIMARY_KEY_QUERY = "select * from billing where billing_id = ?";
	protected static final String FIND_BY_FAMILY_QUERY = "select * from billing where family_id = ? AND start_date IS NOT NULL order by start_date desc";
	protected static final String FIND_CURRENT_QUERY = FIND_BY_FAMILY_QUERY + " limit 1";
	protected static final String FIND_NEXT_UPDATE_QUERY = "SELECT * FROM billing WHERE family_id = ? AND start_date IS NULL";
	protected static final String FIND_HISTORY_QUERY = FIND_BY_FAMILY_QUERY + " OFFSET 1";
	protected static final String UPDATE_STATEMENT = "UPDATE billing SET start_date = ?, plan = ?, is_trial = ? WHERE billing_id = ?";
	
	protected BillingDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}
	
	@Override
	public Billing findByPrimaryKey(Long key)
	{
		Billing billing = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong  (1, key);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				billing = createFromResult(result);
			return billing;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public Billing findCurrent(Family family)
	{
		Billing billing = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_CURRENT_QUERY);
			statement.setLong  (1, family.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				billing = createFromResult(result) ;
			return billing;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public Billing findNextUpdate(Family family)
	{
		Billing billing = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_NEXT_UPDATE_QUERY);
			statement.setLong  (1, family.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				billing = createFromResult(result) ;
			return billing;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<Billing> findHistory(Family family)
	{
		final List<Billing> billings = new ArrayList<Billing>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_HISTORY_QUERY);
			statement.setLong  (1, family.getId());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				billings.add( createFromResult(result) );
			return billings;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return billings; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean update(Billing billing)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setTimestamp(1, DateUtil.toJDBC(billing.getStartDate()));
			statement.setString   (2, billing.getPlan());
			statement.setBoolean  (3, billing.isTrial());
			
			statement.setLong     (4, billing.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	protected Billing createFromResult(ResultSet result) throws SQLException
	{
		final Billing billing = new Billing();
		
		billing.setId(result.getLong("billing_id"));
		billing.setStartDate(DateUtil.toJava(result.getTimestamp("start_date")));
		billing.setPlan(result.getString("plan"));
		billing.setTrial(result.getBoolean("is_trial"));
		
		return billing;
	}
	
	protected static void setDataToInsertStatement( final Billing billing, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong(1, billing.getId());
		statement.setTimestamp(2, DateUtil.toJDBC(billing.getStartDate()));
		statement.setString(3, billing.getPlan());
		statement.setBoolean(4, billing.isTrial());
		statement.setLong(5, billing.getFamily().getId());
	}
	
	protected static void setDataToUpdateStatement( final Billing billing, final PreparedStatement statement ) throws SQLException
	{
		statement.setTimestamp(1, DateUtil.toJDBC(billing.getStartDate()));
		statement.setString(2, billing.getPlan());
		statement.setBoolean(3, billing.isTrial());
		statement.setLong(4, billing.getId());
	}
}
