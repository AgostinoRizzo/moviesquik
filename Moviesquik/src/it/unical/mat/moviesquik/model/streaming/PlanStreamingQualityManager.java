/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

import java.util.HashMap;
import java.util.Map;

import it.unical.mat.moviesquik.model.accounting.Billing;
import it.unical.mat.moviesquik.model.accounting.BillingPlan;

/**
 * @author Agostino
 *
 */
public class PlanStreamingQualityManager
{
	public static final StreamingQuality BASIC_PLAN_QUALITY    = StreamingQuality.LD;
	public static final StreamingQuality STANDARD_PLAN_QUALITY = StreamingQuality.HD;
	public static final StreamingQuality PREMIUM_PLAN_QUALITY  = StreamingQuality._4K;
	
	private static PlanStreamingQualityManager instance = null;
	private final Map<BillingPlan, StreamingQuality> streamingQualityMap = new HashMap<BillingPlan, StreamingQuality>();
	
	public static PlanStreamingQualityManager getInstance()
	{
		if ( instance == null )
			instance = new PlanStreamingQualityManager();
		return instance;
	}
	
	public StreamingQuality getBillingStreamingQuality( final Billing billing )
	{
		return streamingQualityMap.get( BillingPlan.parseBillingPlan(billing.getPlan()) );
	}
	
	private PlanStreamingQualityManager()
	{
		streamingQualityMap.put(BillingPlan.BASIC, BASIC_PLAN_QUALITY);
		streamingQualityMap.put(BillingPlan.STANDARD, STANDARD_PLAN_QUALITY);
		streamingQualityMap.put(BillingPlan.PREMIUM, PREMIUM_PLAN_QUALITY);
	}
}
