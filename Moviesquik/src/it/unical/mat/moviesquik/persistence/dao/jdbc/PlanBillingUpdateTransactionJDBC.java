/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.accounting.Billing;
import it.unical.mat.moviesquik.model.accounting.BillingReport;
import it.unical.mat.moviesquik.model.accounting.CreditCard;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.PlanBillingUpdateTransaction;

/**
 * @author Agostino
 *
 */
public class PlanBillingUpdateTransactionJDBC implements PlanBillingUpdateTransaction
{
	private final StatementPrompterJDBC statementPrompter;
	
	public PlanBillingUpdateTransactionJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean execute(BillingReport data)
	{
		Connection connection = null;
		try
		{
			connection = statementPrompter.getDataSource().getNewConnection();
			connection.setAutoCommit(false);
			
			/* check available money in credit card */
			
			final CreditCard creditCard = DBManager.getInstance().getDaoFactory().getCreditCardDao()
														.findByPrimaryKey( data.getAccount().getCreditCard().getId() );
			if ( creditCard.getBalance() < data.getCurrent().getBalance() )
			{
				connection.rollback();
				return false;
			}
			
			/* update nextBillingUpdate to newCurrentBilling */
			
			final Billing newCurrentBilling = data.getCurrent();
			final PreparedStatement newCurrentBillingStatement = connection.prepareStatement(BillingDaoJDBC.UPDATE_STATEMENT);
			
			BillingDaoJDBC.setDataToUpdateStatement(newCurrentBilling, newCurrentBillingStatement);
			newCurrentBillingStatement.executeUpdate();
			
			/* insert newNextBillingUpdate */
			
			final Billing newNextBillingUpdate = data.getNextUpdate();
			final PreparedStatement newNextBillingUpdateInsertStatement = connection.prepareStatement(BillingDaoJDBC.INSERT_STATEMENT);
			
			newNextBillingUpdate.setId(IdBroker.getNextId(statementPrompter));
			BillingDaoJDBC.setDataToInsertStatement(newNextBillingUpdate, newNextBillingUpdateInsertStatement);
			newNextBillingUpdateInsertStatement.executeUpdate();
			
			/* update credit card balance */
			
			creditCard.setBalance( creditCard.getBalance() - data.getCurrent().getBalance() );
			// TODO: add money to system credit card.
			
			connection.commit();
			return true;
		} 
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

}
