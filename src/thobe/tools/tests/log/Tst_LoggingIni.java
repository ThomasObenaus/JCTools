/*
 *  Copyright (C) 2014, j.umbel. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    j.umbel
 */

package thobe.tools.tests.log;

import java.util.logging.Logger;

import thobe.tools.log.Logging;
import thobe.tools.log.LoggingException;

/**
 * @author Thomas Obenaus
 * @source Tst_LoggingIni.java
 * @date May 2, 2014
 */
public class Tst_LoggingIni
{

	public static void main( String[] args )
	{
		try
		{
			Logging.init( "logging.ini" );

			Logger log = Logger.getLogger( "log.channel.one" );
			log.info( "That should be visible on stdout bot not in any file" );
			log.fine( "That should NOT be visible" );

			log = Logger.getLogger( "log.channel.two" );
			log.severe( "That should be visible on stdout and in file test1.log" );
			log.warning( "That should be visible in file test.log but not in stdout" );

			log = Logger.getLogger( "log.channel.three" );
			log.severe( "That should be visible on stdout and in file test2.log" );
			log.info( "That should be visible on stdout and in file test2.log" );

			for ( Logger l : Logging.getRegisteredLoggers( ) )
				Logging.printLogger( l );

		}
		catch ( LoggingException e )
		{
			System.err.println( "Error initializing logging: " + e.getLocalizedMessage( ) );
		}
	}
}
