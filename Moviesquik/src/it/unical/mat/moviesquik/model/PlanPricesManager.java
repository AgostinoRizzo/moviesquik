/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Agostino
 *
 */
public class PlanPricesManager
{
	public static final Double BASIC_PLAN_PRICE    =  8.99;
	public static final Double STANDARD_PLAN_PRICE = 12.99;
	public static final Double PREMIUM_PLAN_PRICE  = 15.99;
	
	private static PlanPricesManager instance = null;
	private final Map<BillingPlan, Double> prices = new HashMap<BillingPlan, Double>();
	
	public static PlanPricesManager getInstance()
	{
		if ( instance == null )
			instance = new PlanPricesManager();
		return instance;
	}
	
	public Double getPlanPrice( final BillingPlan plan )
	{
		return prices.get(plan);
	}
	
	public Double getBillingPrice( final Billing billing )
	{
		if ( billing.isTrial() )
			return 0.0;
		return prices.get( BillingPlan.parseBillingPlan(billing.getPlan()) );
	}
	
	private PlanPricesManager()
	{
		prices.put(BillingPlan.BASIC, BASIC_PLAN_PRICE);
		prices.put(BillingPlan.STANDARD, STANDARD_PLAN_PRICE);
		prices.put(BillingPlan.PREMIUM, PREMIUM_PLAN_PRICE);
	}
}
