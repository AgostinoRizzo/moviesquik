/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.accounting.Billing;
import it.unical.mat.moviesquik.model.accounting.BillingReport;
import it.unical.mat.moviesquik.model.accounting.Family;
import it.unical.mat.moviesquik.persistence.DBManager;
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
		Connection connection = null;
		try
		{
			connection = statementPrompter.getDataSource().getNewConnection();
			connection.setAutoCommit(false);
			
			if ( !DBManager.getInstance().canRegister(family) )
			{
				connection.rollback();
				return false;
			}
			
			final PreparedStatement familyInsertStatement = connection.prepareStatement(FamilyDaoJDBC.INSERT_STATEMENT);
			family.setId(IdBroker.getNextId(statementPrompter));
			FamilyDaoJDBC.setDataToInsertStatement(family, familyInsertStatement);
			familyInsertStatement.executeUpdate();
			
			/*
			final PreparedStatement userInsertStatement = connection.prepareStatement(UserDaoJDBC.INSERT_STATEMENT);
			user.setId(IdBroker.getNextId(statementPrompter));
			UserDaoJDBC.setDataToInsertStatement(user, userInsertStatement);
			userInsertStatement.executeUpdate();
			*/
			
			
			/* billing report */
			
			final BillingReport billingReport = family.getBillingReport();
			final Billing currentTrialBilling = billingReport.getCurrent();
			final Billing nextBillingUpdate = billingReport.getNextUpdate();
			
			// current trial billing
			final PreparedStatement currentTrialBillingInsertStatement = connection.prepareStatement(BillingDaoJDBC.INSERT_STATEMENT);
			currentTrialBilling.setId(IdBroker.getNextId(statementPrompter));
			BillingDaoJDBC.setDataToInsertStatement(currentTrialBilling, currentTrialBillingInsertStatement);
			currentTrialBillingInsertStatement.executeUpdate();
			
			// next billing update
			final PreparedStatement nextBillingInsertStatement = connection.prepareStatement(BillingDaoJDBC.INSERT_STATEMENT);
			nextBillingUpdate.setId(IdBroker.getNextId(statementPrompter));
			BillingDaoJDBC.setDataToInsertStatement(nextBillingUpdate, nextBillingInsertStatement);
			nextBillingInsertStatement.executeUpdate();
			
			connection.commit();
			return true;
		} 
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}


}
