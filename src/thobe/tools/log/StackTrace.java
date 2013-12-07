/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.log;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Thomas Obenaus
 * @source StackTracer.java
 * @date May 28, 2012
 */
public class StackTrace
{
	private static final Throwable		tracer	= new Throwable( );
	private static final StringWriter	sw		= new StringWriter( 1024 );
	private static final PrintWriter	out		= new PrintWriter( sw, false );

	private StackTrace( )
	{}

	/**
	 * Returns the line of code that has invoked/called the method we are currently in.
	 * @return
	 */
	public static String getCaller( )
	{
		return getCallStack( 2 );
	}

	/**
	 * Returns the n'th line of the stack-trace. --> getCallStack(0) returns the current line of code, getCallStack(1) returns the line of
	 * code that has invoked the method we are
	 * currently in (caller), getCallStack(2) returns the callers caller, ...
	 * @param depth - a value greater 0
	 * @return
	 */
	public static String getCallStack( int depth )
	{
		synchronized ( tracer )
		{
			if ( depth < 0 )
				throw new IllegalArgumentException( "Only values >= 0 are permitted" );
			// skip the first 2 lines
			int lineOfInterest = depth + 3;
			// set the buffer back to zero
			sw.getBuffer( ).setLength( 0 );
			tracer.fillInStackTrace( );
			tracer.printStackTrace( out );
			out.flush( );
			LineNumberReader in = new LineNumberReader( new StringReader( sw.toString( ) ) );
			try
			{
				String result;
				while ( ( result = in.readLine( ) ) != null )
				{
					if ( in.getLineNumber( ) == lineOfInterest )
						return beautify( result );
				}
			}
			catch ( IOException ex )
			{} // we'll just return null
			return null;
		}
	}

	private static String beautify( String raw )
	{
		raw = raw.trim( ); // we don't want any whitespace
		if ( raw.startsWith( "at " ) ) // we also cut off the "at "
			return raw.substring( 3 );
		return raw;
	}
}
