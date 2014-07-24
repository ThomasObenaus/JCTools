/*
 *  Copyright (C) 2014, j.umbel. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    j.umbel
 */

package thobe.tools.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas Obenaus
 * @source Logging.java
 * @date May 2, 2014
 */
public class Logging
{
	public static final String	GROUP_SEPARATOR		= ";";
	public static final String	ELEMENT_SEPARATOR	= ",";

	/**
	 * List of loggers that where registered via ini-file resulting in calling {@link Logging#init(String)}
	 */
	private static Set<Logger>	registeredLoggers	= new HashSet<>( );

	/**
	 * Use this method to initialize logging per ini-file. Within the given ini-file you can specify log-target (a file or stdout) for each
	 * log-channel separately.</br>
	 * <b>Format of inifile:</b></br>
	 * NameOfLogChannel = Group[;Group;Group;...]</br>
	 * group := LogTarget,LogLevel</br>
	 * LogTarget := stdout|Filename</br>
	 * LogLevel := OFF|SEVERE|WARNING|INFO|FINE|FINER|FINEST|ALL</br>
	 * </br>
	 * <b>Example: file logging.ini</b></br>
	 * log.channel.one = stdout,INFO</br>
	 * log.channel.two = log/test.log,ALL;stdout,SEVERE</br>
	 * log.channel.three = log/test.log,SEVERE;stdout,INFO</br>
	 * </br>
	 * <b>Example: Usage in code</b></br> <code>
	 * Logging.init("logging.ini");</br>
	 * Logger.getLogger( "log.channel.one" ).info("Hello world (should be visible only on stdout");</br>
	 * Logger.getLogger( "log.channel.two" ).info("Hello world (should be visible only on log/test.log");</br>
	 * Logger.getLogger( "log.channel.two" ).severe("Hello world (should be visible on stdout and in log/test.log");</br>
	 * </code>
	 * @param filename - name of the inifile
	 * @throws LoggingException
	 */
	public static void init( String filename ) throws LoggingException
	{
		Map<String, FileHandler> fileHandlers = new HashMap<>( );

		if ( filename == null || filename.trim( ).isEmpty( ) )
			throw new LoggingException( "Filename '" + filename + "' is invalid" );

		File iniFile = new File( filename );
		if ( !iniFile.exists( ) )
			throw new LoggingException( "Given file '" + iniFile.getAbsolutePath( ) + "' does not exist" );

		if ( !iniFile.canRead( ) )
			throw new LoggingException( "Given file '" + iniFile.getAbsolutePath( ) + "' is not readable" );

		// read the inifile
		Properties props = new Properties( );
		try
		{
			props.load( new FileInputStream( iniFile ) );

			// for each log-channel
			for ( Map.Entry<Object, Object> entry : props.entrySet( ) )
			{
				String logChannel = ( String ) entry.getKey( );

				// Values are pairs of output-target followed by log-level separated by; and _
				// e.g. log/all.log,All;stdout,Info
				String values = ( String ) entry.getValue( );
				String groups[] = values.split( GROUP_SEPARATOR );

				// for each group
				for ( String group : groups )
				{
					String elements[] = group.split( ELEMENT_SEPARATOR );
					if ( elements.length < 2 )
					{
						System.err.println( "Group " + group + " for logChannel '" + logChannel + "' is invalid and will be ignored: Not enough elements (2 expected)." );
						continue;
					}

					try
					{
						// 1. logtarget type
						LogTargetType type = LogTargetType.parse( elements[0] );

						// 2. LogLevel
						Level level = Level.parse( elements[1] );

						// 3. configure the log-channel
						Logger log = Logger.getLogger( logChannel );

						// don't overwrite log-level that is higher (less verbose) than a previously set log-level
						if ( registeredLoggers.contains( log ) )
						{
							if ( log.getLevel( ).intValue( ) > level.intValue( ) )
								log.setLevel( level );
						}// if ( registeredLoggers.contains( log ) )
						else
						{
							log.setLevel( level );
						}

						log.setUseParentHandlers( false );

						if ( type == LogTargetType.STDOUT )
						{
							ConsoleHandler cHandler = new ConsoleHandler( );
							cHandler.setLevel( level );
							cHandler.setFormatter( new TXTLogFormatter( ) );
							log.addHandler( cHandler );
						}// if ( type == LogTargetType.STDOUT ).

						if ( type == LogTargetType.FILE )
						{
							File file = new File( elements[0] );
							if ( !file.exists( ) )
							{
								// create directories?
								if ( !file.isDirectory( ) && ( !file.getParentFile( ).exists( ) ) )
								{
									// try to create missing directories
									if ( !file.getParentFile( ).mkdirs( ) )
									{
										System.err.println( "Unable to create directory '" + file.getParentFile( ).getAbsolutePath( ) + "'" );
										System.err.println( "Group " + group + " for logChannel '" + logChannel + " will be ignored" );
										continue;
									}// if ( !file.getParentFile( ).mkdirs( ) ).
								}// if ( !file.isDirectory( ) && ( !file.getParentFile( ).exists( ) ) ).
							}// if ( !file.exists( ) ).

							// Create only new filehandlers for different log-files
							FileHandler fHandler = fileHandlers.get( file.getAbsolutePath( ) );
							if ( fHandler == null )
							{
								fHandler = new FileHandler( file.getAbsolutePath( ) );
								fHandler.setFormatter( new TXTLogFormatter( ) );
								fileHandlers.put( file.getAbsolutePath( ), fHandler );
								fHandler.setLevel( level );
							}

							// increase log-level if needed
							if ( fHandler.getLevel( ).intValue( ) > level.intValue( ) )
								fHandler.setLevel( level );
							log.addHandler( fHandler );
						}// if ( type == LogTargetType.FILE ).

						registeredLoggers.add( log );
					}
					catch ( IllegalArgumentException e )
					{
						System.err.println( "Group " + group + " for logChannel '" + logChannel + "' is invalid and will be ignored: " + e.getLocalizedMessage( ) );
						continue;
					}

				}// for ( String group : groups ).

			}// for ( Map.Entry<Object, Object> entry : props.entrySet( ) ).

		}
		catch ( IOException e )
		{
			throw new LoggingException( "Unable to read given inifile (" + iniFile.getAbsolutePath( ) + "): " + e.getLocalizedMessage( ) );
		}
	}

	private enum LogTargetType
	{
		STDOUT, FILE;

		/**
		 * @param logTargetTypeStr
		 * @return
		 * @throws IllegalArgumentException - If the given logTargetTypeStr is null or empty
		 */
		public static LogTargetType parse( String logTargetTypeStr ) throws IllegalArgumentException
		{
			if ( logTargetTypeStr == null || logTargetTypeStr.trim( ).isEmpty( ) )
				throw new IllegalArgumentException( "The given logtarget-type '" + logTargetTypeStr + "' is invalid." );
			if ( logTargetTypeStr.toUpperCase( ).equals( STDOUT.toString( ) ) )
				return STDOUT;
			return FILE;
		}
	}

	/**
	 * Returns the list of registered {@link Logger}'s.
	 * @return
	 */
	public static Set<Logger> getRegisteredLoggers( )
	{
		return registeredLoggers;
	}

	public static void printLogger( Logger logger )
	{
		System.out.println( logger.getName( ) + " {lvl=" + logger.getLevel( ) + "}" );
		for ( Handler handler : logger.getHandlers( ) )
		{
			System.out.println( "\t" + handler.toString( ) + " {lvl=" + handler.getLevel( ) + ", formatter=" + handler.getFormatter( ) + "}" );
		}
	}
}
