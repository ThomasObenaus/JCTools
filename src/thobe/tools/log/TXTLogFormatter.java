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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Thomas Obenaus
 * @source TXTLogFormatter.java
 * @date 26.03.2012
 */
public class TXTLogFormatter extends SimpleFormatter
{
	private static Pattern		pattern		= Pattern.compile( "\\{[0-9]+\\}" );
	private static DateFormat	formatter	= new SimpleDateFormat( "HH:mm:ss:SSS" );

	public TXTLogFormatter( )
	{}

	@Override
	public String format( LogRecord record )
	{
		String message = record.getMessage( );

		Matcher matcher = pattern.matcher( message );
		while ( matcher.find( ) )
		{
			String gid = matcher.group( 0 ).replace( "{", "" );
			gid = gid.replace( "}", "" );
			int d = Integer.parseInt( gid ) + 1;
			message = message.replaceFirst( pattern.pattern( ), "%" + d + "\\$s" );
		}

		message = String.format( message, record.getParameters( ) );
		Date date = new Date( record.getMillis( ) );
		return formatter.format( date ).toString( ) + " [" + record.getLevel( ) + "] " + message + "\n";
	}

}
