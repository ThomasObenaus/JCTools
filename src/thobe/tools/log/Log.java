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

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple class that can be used for logging purposes. The logging will be done
 * at different log-levels to the console and into a given log-file.
 * @author Thomas Obenaus
 * @source Log.java
 * @date 27.03.2012
 */
public class Log
{
	private static Logger	loggerInstance	= null;

	private Log( )
	{}

	/**
	 * Access to the {@link Logger} instance.
	 * @return
	 */
	public static Logger LOG( )
	{
		if ( loggerInstance == null )
			throw new RuntimeException( "You can't use a Log that is not initiated! Call the method Log.initLog() at least one time before Log.LOG()." );
		return loggerInstance;
	}

	/**
	 * Method to initiate the log. This method has to be called before using the
	 * log (method {@link Log#LOG()}).
	 * @param applicationName - name of the application using the log
	 * @param resourceBundleName - name of the resource bundle
	 * @param logFile - full path to the log-file
	 * @param consoleLogLevel - log-level ({@link Level}) used for writing
	 *            log-messages into the console
	 * @param fileLogLevel - log-level ({@link Level}) used for writing
	 *            log-messages into the log-file
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void initLog( String applicationName, String resourceBundleName, File logFile, Level consoleLogLevel, Level fileLogLevel ) throws SecurityException, IOException
	{
		if ( resourceBundleName == null )
			loggerInstance = Logger.getLogger( applicationName );
		else loggerInstance = Logger.getLogger( applicationName, resourceBundleName );
		loggerInstance.setLevel( Level.ALL );
		loggerInstance.setUseParentHandlers( false );
		ConsoleHandler cHandler = new ConsoleHandler( );
		cHandler.setLevel( consoleLogLevel );
		cHandler.setFormatter( new TXTLogFormatter( ) );
		loggerInstance.addHandler( cHandler );

		if ( logFile != null )
		{
			FileHandler fHandler = new FileHandler( logFile.getAbsolutePath( ) );
			fHandler.setFormatter( new TXTLogFormatter( ) );
			fHandler.setLevel( fileLogLevel );
			loggerInstance.addHandler( fHandler );
		}
	}

	/**
	 * Method to initiate the log. This method has to be called before using the
	 * log (method {@link Log#LOG()}).
	 * @param applicationName - name of the application using the log
	 * @param logFile - full path to the log-file
	 * @param consoleLogLevel - log-level ({@link Level}) used for writing
	 *            log-messages into the console
	 * @param fileLogLevel - log-level ({@link Level}) used for writing
	 *            log-messages into the log-file
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void initLog( String applicationName, File logFile, Level consoleLogLevel, Level fileLogLevel ) throws SecurityException, IOException
	{
		initLog( applicationName, null, logFile, consoleLogLevel, fileLogLevel );
	}

	/**
	 * Method to initiate the log. This method has to be called before using the
	 * log (method {@link Log#LOG()}).
	 * @param applicationName - name of the application using the log
	 * @param consoleLogLevel - log-level ({@link Level}) used for writing
	 *            log-messages into the console
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void initLog( String applicationName, Level consoleLogLevel ) throws SecurityException, IOException
	{
		initLog( applicationName, null, null, consoleLogLevel, null );
	}
}
