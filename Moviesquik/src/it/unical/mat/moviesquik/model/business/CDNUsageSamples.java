/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

import java.util.Arrays;

/**
 * @author Agostino
 *
 */
public class CDNUsageSamples
{
	public static final short CDN_USAGE_CHART_SAMPLES_WINDOW = 50;
	public static final short CDN_USAGE_CHART_SAMPLES_INTERVAL = 1000;  // expressed in milliseconds
	
	private Float[] samples;
	private long timestamp;
	
	public CDNUsageSamples()
	{
		timestamp = System.currentTimeMillis();
		samples = createNewSamplesArray();
	}
	
	public CDNUsageSamples( final Float[] samples )
	{
		timestamp = System.currentTimeMillis();
		this.samples = samples != null && samples.length == CDN_USAGE_CHART_SAMPLES_WINDOW 
						? Arrays.copyOf(samples, samples.length)
						: createNewSamplesArray();
	}
	
	public Float[] getSamples()
	{
		return samples;
	}
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public static Float[] createNewSamplesArray()
	{
		final Float[] sample = new Float[CDN_USAGE_CHART_SAMPLES_WINDOW];
		Arrays.fill(sample, 0.0f);
		return sample;
	}
	
}
