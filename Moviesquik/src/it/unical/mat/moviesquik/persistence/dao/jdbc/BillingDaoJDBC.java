/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.Billing;
import it.unical.mat.moviesquik.persistence.dao.BillingDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class BillingDaoJDBC implements BillingDao
{
	protected static final String INSERT_STATEMENT = "insert into billing (billing_id, start_date, plan, is_trial, family_id) values (?,?,?,?,?)";

	protected static void setDataToInsertStatement( final Billing billing, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong(1, billing.getId());
		statement.setTimestamp(2, DateUtil.toJDBC(billing.getStartDate()));
		statement.setString(3, billing.getPlan());
		statement.setBoolean(4, billing.isTrial());
		statement.setLong(5, billing.getFamily().getId());
	}
}
