/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.math;

/**
 * @author Thomas Obenaus
 * @source Math.java
 * @date 02.09.2010
 */
public class MathLib
{

	/**
	 * Returns the maximum value of the given 2D array.
	 * @param vals
	 * @return
	 */
	public static double max( double[][] vals )
	{
		double max = -Double.MAX_VALUE;
		for ( double[] v : vals )
			max = Math.max( max( v ), max );
		return max;
	}

	/**
	 * Returns the maximum value of the given array.
	 * @param vals
	 * @return
	 */
	public static double max( double[] vals )
	{
		double max = -Double.MAX_VALUE;
		for ( double v : vals )
			max = Math.max( v, max );
		return max;
	}

	/**
	 * Returns the minimum and maximum value of the given 2D array.
	 * @param vals
	 * @return - a double array with two elements. the first one is the minimum and the second one
	 *         is the maximum value.
	 */
	public static double[] range( double[][] vals )
	{
		double max = -Double.MAX_VALUE;
		double min = Double.MAX_VALUE;
		for ( double[] v : vals )
		{
			double range[] = range( v );
			min = Math.min( range[0], min );
			max = Math.max( range[1], max );
		}

		return new double[]
		{ min, max };
	}

	/**
	 * Returns the minimum and maximum value of the given array.
	 * @param vals
	 * @return - a double array with two elements. the first one is the minimum and the second one
	 *         is the maximum value.
	 */
	public static double[] range( double[] vals )
	{
		double max = -Double.MAX_VALUE;
		double min = Double.MAX_VALUE;

		for ( double v : vals )
		{
			max = Math.max( v, max );
			min = Math.min( v, min );
		}

		return new double[]
		{ min, max };
	}
}
