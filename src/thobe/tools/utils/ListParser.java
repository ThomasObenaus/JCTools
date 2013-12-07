/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Obenaus
 * @source ListParser.java
 * @date 09.07.2012
 */
public abstract class ListParser<T>
{
	public abstract T parse( String str ) throws ParseException;

	static class DoubleParser extends ListParser<Double>
	{
		@Override
		public Double parse( String str ) throws ParseException
		{
			try
			{
				return Double.parseDouble( str );
			}
			catch ( NumberFormatException e )
			{
				throw new ParseException( str, "DoubleParser", e.getLocalizedMessage( ) );
			}
		}
	}

	static class IntegerParser extends ListParser<Integer>
	{
		@Override
		public Integer parse( String str ) throws ParseException
		{
			try
			{
				return Integer.parseInt( str );
			}
			catch ( NumberFormatException e )
			{
				throw new ParseException( str, "IntegerParser", e.getLocalizedMessage( ) );
			}
		}
	}

	static class BooleanParser extends ListParser<Boolean>
	{
		@Override
		public Boolean parse( String str ) throws ParseException
		{
			try
			{
				try
				{
					int value = Integer.parseInt( str );
					if ( value <= 0 )
						return false;
					return true;
				}
				catch ( Exception e )
				{}

				return Boolean.parseBoolean( str );
			}
			catch ( NumberFormatException e )
			{
				throw new ParseException( str, "BooleanParser", e.getLocalizedMessage( ) );
			}
		}
	}

	static class StringParser extends ListParser<String>
	{
		@Override
		public String parse( String str ) throws ParseException
		{
			return str;
		}
	}

	static class LongParser extends ListParser<Long>
	{
		@Override
		public Long parse( String str ) throws ParseException
		{
			try
			{
				return Long.parseLong( str );
			}
			catch ( NumberFormatException e )
			{
				throw new ParseException( str, "LongParser", e.getLocalizedMessage( ) );
			}
		}
	}

	/**
	 * Parses the given string, trying to break that string into a list of values of the given type (clazz) using the given delimiter.
	 * @param str - string to parse
	 * @param clazz - class/ type of elements
	 * @param delimiter - delimiter used to separate the element
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings ( "unchecked")
	public static <D> List<D> parseList( String str, Class<D> clazz, String delimiter ) throws ParseException
	{
		List<D> result = new ArrayList<>( );

		ListParser<D> parser = null;

		// find the correct parser
		if ( clazz == Double.class )
			parser = ( thobe.tools.utils.ListParser<D> ) new DoubleParser( );
		else if ( clazz == Long.class )
			parser = ( thobe.tools.utils.ListParser<D> ) new LongParser( );
		else if ( clazz == String.class )
			parser = ( thobe.tools.utils.ListParser<D> ) new StringParser( );
		else if ( clazz == Boolean.class )
			parser = ( thobe.tools.utils.ListParser<D> ) new BooleanParser( );
		else if ( clazz == Integer.class )
			parser = ( thobe.tools.utils.ListParser<D> ) new IntegerParser( );

		if ( parser == null )
			throw new ParseException( str, "no-parser found", "No parser found that is able to parse instances of " + clazz );

		String[] elements = str.split( delimiter );
		for ( String element : elements )
			result.add( parser.parse( element ) );
		return result;
	}

	/**
	 * Parses the given string, trying to break that string into a list of values of the given type (clazz) using ',' as delimiter.
	 * @param str - string to parse
	 * @param clazz - class/ type of elements
	 * @return
	 * @throws ParseException
	 */
	public static <D> List<D> parseList( String str, Class<D> clazz ) throws ParseException
	{
		return parseList( str, clazz, "," );
	}
}
