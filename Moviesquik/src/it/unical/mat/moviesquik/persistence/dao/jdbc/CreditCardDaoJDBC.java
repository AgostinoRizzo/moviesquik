/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.CreditCard;
import it.unical.mat.moviesquik.persistence.dao.CreditCardDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class CreditCardDaoJDBC implements CreditCardDao
{
	protected static final String INSERT_STATEMENT          = "insert into credit_card(credit_card_id, number, full_name, expiration, cvv, balance) values (?,?,?,?,?,?)";
	protected static final String FIND_QUERY                = "select * from credit_card where number = ? and full_name = ? and expiration = ? and cvv = ?";
	protected static final String FIND_BY_PRIMARY_KEY_QUERY = "select * from credit_card where credit_card_id = ?";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public CreditCardDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(CreditCard card)
	{
		try
		{
			card.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			setDataToInsertStatement(card, statement);
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean existsMatch(CreditCard card)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_QUERY);
			
			statement.setString(1, card.getNumber());
			statement.setString(2, card.getName());
			statement.setTimestamp(3, DateUtil.toTimestampJDBC(card.getExpiration()));
			statement.setString(4, card.getCvv());
			
			ResultSet result = statement.executeQuery();
			if ( result.next() )
			{
				card.setId(createCreditCardFromResult(result).getId());
				return true;
			}
			
			return false;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public CreditCard findByPrimaryKey(Long key)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong(1, key);
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createCreditCardFromResult(result);
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	protected static void setDataToInsertStatement( final CreditCard card, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong(1, card.getId());
		statement.setString(2, card.getNumber());
		statement.setString(3, card.getName());
		statement.setTimestamp(4, DateUtil.toTimestampJDBC(card.getExpiration()));
		statement.setString(5, card.getCvv());
		statement.setFloat(6, card.getBalance());
	}
	
	private CreditCard createCreditCardFromResult( final ResultSet result ) throws SQLException
	{
		final CreditCard card = new CreditCard();
		
		card.setId(result.getLong("credit_card_id"));
		card.setName(result.getString("full_name"));
		card.setNumber(result.getString("number"));
		card.setExpiration(DateUtil.toJava(result.getTimestamp("expiration")));
		card.setCvv(result.getString("cvv"));
		card.setBalance(result.getFloat("balance"));
		
		return card;
	}

}
