/**
 * 
 */
package it.unical.mat.moviesquik.model.accounting;

import java.util.Calendar;
import java.util.List;

import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class BillingReport
{
	private static final DaoFactory DAO_FACTORY = DBManager.getInstance().getDaoFactory();
	private static final BillingPlan TRIAL_PLAN = BillingPlan.PREMIUM;
	
	private Billing current = null;
	private Billing nextUpdate = null;
	private List<Billing> history = null;
	private Family account = null;
	
	public static BillingReport createNewBillingReport( final Family account )
	{
		final BillingReport report = new BillingReport();
		report.setAccount(account);
		return report;
	}
	
	public Billing getCurrent()
	{
		if ( current == null )
			current = DAO_FACTORY.getBillingDao().findCurrent(account);
		return current;
	}
	public void setCurrent(Billing current)
	{
		this.current = current;
	}
	public Billing getNextUpdate()
	{
		if ( nextUpdate == null )
			nextUpdate = DAO_FACTORY.getBillingDao().findNextUpdate(account);
		return nextUpdate;
	}
	public void setNextUpdate(Billing nextUpdate)
	{
		this.nextUpdate = nextUpdate;
	}
	public List<Billing> getHistory()
	{
		if ( history == null )
			history = DAO_FACTORY.getBillingDao().findHistory(account);
		return history;
	}
	public void setHistory(List<Billing> history)
	{
		this.history = history;
	}
	public Family getAccount()
	{
		return account;
	}
	public void setAccount(Family account)
	{
		this.account = account;
	}
	
	public void initNextBillingUpdate( final BillingPlan choosenPlan )
	{
		final Billing nextBillingUpdate = new Billing();
		
		nextBillingUpdate.setStartDate(null);
		nextBillingUpdate.setPlan(choosenPlan.toString());
		nextBillingUpdate.setTrial(false);
		nextBillingUpdate.setFamily(account);
		
		setNextUpdate(nextBillingUpdate);
	}
	
	public void initCurrentTrialBilling()
	{
		final Billing currentTrialBilling = new Billing();
		
		currentTrialBilling.setPlan(TRIAL_PLAN.toString());
		currentTrialBilling.setTrial(true);
		currentTrialBilling.setFamily(account);
		currentTrialBilling.setStartDate(DateUtil.getCurrent());
		
		setCurrent(currentTrialBilling);
	}
	
	public void reload()
	{
		current = null;
		nextUpdate = null;
		history = null;
	}
	
	public boolean isActive()
	{
		final Calendar now = Calendar.getInstance();
		now.setTime( DateUtil.getCurrent() );
		DateUtil.setDaysHorizon(now);
		
		final Calendar billingEnd = Calendar.getInstance();
		billingEnd.setTime( getCurrent().getEndDate() );
		DateUtil.setDaysHorizon(billingEnd);
		
		if ( billingEnd.after(now) )
			return true;
		return tryToActivate();
	}
	
	private boolean tryToActivate()
	{
		if ( !getAccount().getAutoUpdate() )
			return false;
		
		final Billing newCurrent = getNextUpdate();		
		newCurrent.setFamily(account);
		newCurrent.setStartDate(getCurrent().getEndDate());
		
		final Billing newNextUpdate = new Billing();
		newNextUpdate.setFamily(account);
		newNextUpdate.setStartDate(null);
		newNextUpdate.setPlan(getNextUpdate().getPlan());
		newNextUpdate.setTrial(getNextUpdate().isTrial());
		
		final BillingReport newBillingReport = new BillingReport();
		newBillingReport.setAccount(account);
		newBillingReport.setCurrent(newCurrent);
		newBillingReport.setNextUpdate(newNextUpdate);
		
		if ( DBManager.getInstance().updatePlanBilling(newBillingReport) )
		{
			reload();
			return true;
		}
		return false;
	}
	
}
