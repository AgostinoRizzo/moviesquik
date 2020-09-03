/**
 * 
 */
package it.unical.mat.moviesquik.model.accounting;

/**
 * @author Agostino
 *
 */
public enum BillingPlan
{
	BASIC, STANDARD, PREMIUM;
	
	private static final String BASIC_STRING    = "basic";
	private static final String STANDARD_STRING = "standard";
	private static final String PREMIUM_STRING  = "premium";
	
	public static BillingPlan parseBillingPlan( final String plan )
	{
		if ( plan == null )
			return null;
		plan.trim();
		if ( plan.equals(BASIC_STRING) )    return BASIC;
		if ( plan.equals(STANDARD_STRING) ) return STANDARD;
		if ( plan.equals(PREMIUM_STRING) )  return PREMIUM;
		return null;
	}
	
	@Override
	public String toString()
	{
		switch ( this ) 
		{
		case BASIC:    return BASIC_STRING;
		case STANDARD: return STANDARD_STRING;
		case PREMIUM:  return PREMIUM_STRING;
		default:       return BASIC_STRING;
		}
	}
}
