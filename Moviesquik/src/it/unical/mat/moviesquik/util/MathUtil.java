/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Agostino
 *
 */
public class MathUtil
{
	public static float roundRateValue( final float rate )
	{
		return new BigDecimal(rate).setScale(1, RoundingMode.HALF_EVEN).floatValue();
	}
}
