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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Obenaus
 * @source Complex.java
 * @date 01.07.2009
 */
public class Complex
{
	private double	real;
	private double	img;

	public Complex( double real, double img )
	{
		this.real = real;
		this.img = img;
	}

	public double getReal( )
	{
		return real;
	}

	public void setReal( double real )
	{
		this.real = real;
	}

	public double getImg( )
	{
		return img;
	}

	public void setImg( double img )
	{
		this.img = img;
	}

	public boolean isComplex( )
	{
		if ( this.img != 0 )
			return true;
		return false;
	}

	public static Complex parseComplex( String complex ) throws ParseComplexException
	{
		String parts[] = null;

		complex = complex.trim( );
		if ( complex.length( ) == 0 )
			return new Complex( 0, 0 );

		boolean add = true;
		if ( complex.contains( "-" ) && !complex.contains( "+" ) )
		{
			parts = complex.trim( ).split( "\\-" );
			add = false;
		}
		else parts = complex.trim( ).split( "\\+" );

		List<String> tmp = new ArrayList<String>( );
		for ( int i = 0; i < parts.length; i++ )
		{
			String part = parts[i];
			if ( part.trim( ).equals( "" ) )
			{
				if ( !add )
					tmp.add( "-" + parts[i + 1] );
				else tmp.add( "+" + parts[i + 1] );
				i++;
			}
			else tmp.add( parts[i] );

		}

		parts = new String[tmp.size( )];
		for ( int i = 0; i < tmp.size( ); i++ )
			parts[i] = tmp.get( i );

		/* add 0+ or 0- to the given string if we have no real but an imaginary part */
		if ( parts.length == 1 && parts[0].contains( "i" ) )
		{
			String tmpPart = parts[0];
			parts = new String[2];
			parts[0] = "0";
			parts[1] = tmpPart;
		}

		if ( parts.length > 2 )
			throw new ParseComplexException( "At most one '+' or '-' is allowed." );

		if ( parts.length == 0 )
			throw new ParseComplexException( "String to parse was empty!" );

		/* the real part */
		double real = 0;
		try
		{
			real = Double.parseDouble( parts[0] );
		}
		catch ( NumberFormatException e1 )
		{
			throw new ParseComplexException( "The real part (" + parts[0] + ") of the complex number is not valid." );
		}

		/* the complex part */
		double img = 0;
		if ( parts.length == 2 )
		{
			if ( !parts[1].trim( ).matches( "\\-*(\\d+\\.)?\\d*(\\d+e(\\-?)\\d+)?i" ) )
				throw new ParseComplexException( "The imaginary part (" + parts[1] + ") of the complex number is not valid." );

			if ( parts[1].trim( ).equals( "i" ) || parts[1].trim( ).matches( "-(\\s)*i" ) )
				img = 1d;
			else
			{
				/* split the value from i */
				String imgParts[] = parts[1].trim( ).split( "i" );
				try
				{
					img = Double.parseDouble( imgParts[0].trim( ) );
				}
				catch ( NumberFormatException e )
				{
					throw new ParseComplexException( "The imaginary part (" + parts[1] + ") of the complex number is not valid." );
				}
			}
		}

		if ( !add )
			img = Math.abs( img ) * -1;
		return new Complex( real, img );
	}

	public String toString( )
	{
		String result = "";

		if ( this.real != 0 || ( this.real == 0 && this.img == 0 ) )
			result = this.real + "";

		if ( real != 0 && img != 0 )
		{
			if ( img > 0 )
				result += "+";
		}

		if ( this.img != 0 )
			result += this.img + "i";
		return result;
	}

	public boolean equals( Object obj )
	{
		if ( !( obj instanceof Complex ) )
			return false;

		Complex cplx = ( Complex ) obj;
		if ( this.real != cplx.real )
			return false;
		if ( this.img != cplx.img )
			return false;
		return true;
	}
}
