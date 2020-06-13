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
	public static final Float BASIC_PLAN_PRICE    =  8.99f;
	public static final Float STANDARD_PLAN_PRICE = 12.99f;
	public static final Float PREMIUM_PLAN_PRICE  = 15.99f;
	
	private static PlanPricesManager instance = null;
	private final Map<BillingPlan, Float> prices = new HashMap<BillingPlan, Float>();
	
	public static PlanPricesManager getInstance()
	{
		if ( instance == null )
			instance = new PlanPricesManager();
		return instance;
	}
	
	public Float getPlanPrice( final BillingPlan plan )
	{
		return prices.get(plan);
	}
	
	public Float getBillingPrice( final Billing billing )
	{
		if ( billing.isTrial() )
			return 0.0f;
		return prices.get( BillingPlan.parseBillingPlan(billing.getPlan()) );
	}
	
	private PlanPricesManager()
	{
		prices.put(BillingPlan.BASIC, BASIC_PLAN_PRICE);
		prices.put(BillingPlan.STANDARD, STANDARD_PLAN_PRICE);
		prices.put(BillingPlan.PREMIUM, PREMIUM_PLAN_PRICE);
	}
}
