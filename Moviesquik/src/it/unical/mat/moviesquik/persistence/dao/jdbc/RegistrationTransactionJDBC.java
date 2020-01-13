/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.dao.RegistrationTransaction;

/**
 * @author Agostino
 *
 */
public class RegistrationTransactionJDBC implements RegistrationTransaction
{
	private final StatementPrompterJDBC statementPrompter;
	
	public RegistrationTransactionJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean execute(Family family)
	{
		final List<User> members = family.getMembers();
		if ( members.size() != 1 )
			return false;
		
		final User user = members.get(0);
		Connection connection = null;
		try
		{
			connection = statementPrompter.getDataSource().getConnection();
			connection.setAutoCommit(false);
			
			final PreparedStatement familyInsertStatement = connection.prepareStatement(FamilyDaoJDBC.INSERT_STATEMENT);
			family.setId(IdBroker.getNextId(statementPrompter));
			FamilyDaoJDBC.setDataToInsertStatement(family, familyInsertStatement);
			familyInsertStatement.executeUpdate();
			
			final PreparedStatement userInsertStatement = connection.prepareStatement(UserDaoJDBC.INSERT_STATEMENT);
			user.setId(IdBroker.getNextId(statementPrompter));
			UserDaoJDBC.setDataToInsertStatement(user, userInsertStatement);
			userInsertStatement.executeUpdate();
			
			final PreparedStatement billingInsertStatement = connection.prepareStatement(BillingDaoJDBC.INSERT_STATEMENT);
			family.getCurrentBilling().setId(IdBroker.getNextId(statementPrompter));
			BillingDaoJDBC.setDataToInsertStatement(family.getCurrentBilling(), billingInsertStatement);
			billingInsertStatement.executeUpdate();
			
			connection.commit();
			return true;
		} 
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}


}
