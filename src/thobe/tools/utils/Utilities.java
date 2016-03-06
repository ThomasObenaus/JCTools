/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *      Project:    JavaComponents/Tools
 */
package thobe.tools.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Thomas Obenaus
 * @source Utilities.java
 * @date 26.08.2009
 */
public class Utilities
{
	/**
	 * Returns the current date and time.
	 * @return
	 */
	public static Date getCurrentDateTime( )
	{

		Calendar cal = Calendar.getInstance( TimeZone.getDefault( ) );
		return cal.getTime( );
	}

	/**
	 * Takes the given date and returns it in a formatted manner (yyyy-MM-dd
	 * HH:mm:ss).
	 * @param date
	 * @return
	 */
	public static String dateToString( Date date )
	{
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat( DATE_FORMAT );

		sdf.setTimeZone( TimeZone.getDefault( ) );
		return sdf.format( date );
	}

	/**
	 * Checks if the user has the needed permissions to be able to create/
	 * modify files in the given <i>directory</i> by trying to create a
	 * tempoary-file.
	 * @param directory - the directory that should be examined
	 * @return - false if the given File (<i>directory</i>) is null, is no
	 *         directory or if the needed permissions are missing, true
	 *         otherwise.
	 */
	public static boolean isDirectoryWritable( File directory )
	{
		if ( directory == null )
			return false;
		if ( !directory.isDirectory( ) )
			return false;

		File tmpFile = new File( directory.getAbsolutePath( ) + File.separator + "tmpWRT" );
		boolean canWriteFile = false;
		try
		{
			canWriteFile = tmpFile.createNewFile( );
			tmpFile.delete( );
		}
		catch ( IOException e )
		{
			return false;
		}
		return canWriteFile;
	}

	/**
	 * Creates a list of unique string by replacing doubles of the given list of
	 * strings (input). Multiple occurrences will be removed by adding an
	 * integer in the form of '_0' to the string. e.g. the input-list of
	 * {"a","b","b","a","c","a"} will be transformed to
	 * {"a","b","b_0","a_0","c","a_1"}
	 * @param listOfStrings
	 * @return
	 */
	public static List<String> makeUnique( List<String> listOfStrings )
	{
		List<String> uniqueStrings = new ArrayList<String>( );

		for ( String str : listOfStrings )
		{
			int index = 0;
			String newEntry = str;
			while ( uniqueStrings.contains( newEntry ) )
			{
				newEntry = str;
				newEntry = newEntry + "_" + index;
				index++;
			}
			uniqueStrings.add( newEntry );
		}

		return uniqueStrings;
	}

	/**
	 * This method can be used to extend the java.library.path by a given path.
	 * With this method it is possible to add a path at runtime wherein needed
	 * libraries/binaries (dll/so) are located.
	 * @param pathToAppend - the path that should extend that java.library.path
	 * @throws IOException
	 */
	public static void appendToJavaLibraryPath( String pathToAppend ) throws IOException
	{
		try
		{
			// This enables the java.library.path to be modified at runtime
			// From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
			//
			Field field = ClassLoader.class.getDeclaredField( "usr_paths" );
			field.setAccessible( true );
			String[] paths = ( String[] ) field.get( null );
			for ( int i = 0; i < paths.length; i++ )
			{

				if ( pathToAppend.equals( paths[i] ) )
				{
					return;
				}
			}
			String[] tmp = new String[paths.length + 1];
			System.arraycopy( paths, 0, tmp, 0, paths.length );
			tmp[paths.length] = pathToAppend;
			field.set( null, tmp );
			System.setProperty( "java.library.path", System.getProperty( "java.library.path" ) + File.pathSeparator + pathToAppend );
		}
		catch ( IllegalAccessException e )
		{
			throw new IOException( "Failed to get permissions to set library path" );
		}
		catch ( NoSuchFieldException e )
		{
			throw new IOException( "Failed to get field handle to set library path" );
		}
	}

}
