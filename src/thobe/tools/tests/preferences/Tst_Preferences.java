/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.tests.preferences;

import java.io.File;

import thobe.tools.preferences.PreferenceManager;
import thobe.tools.preferences.PrefsException;

/**
 * @author Thomas Obenaus
 * @source Tst_Preferences.java
 * @date 25.06.2010
 */
public class Tst_Preferences
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		/* my implementation of the application specific PreferenceObject */
		MyPrefObj prefs = new MyPrefObj( );

		/* create the PreferenceManager and load the prefs for this application */
		PreferenceManager.createPrefs( prefs );
		System.out.println( "Loaded preferences...." );
		System.out.println( "User: " + prefs.getUserName( ) );
		System.out.println( "HomeDir: " + prefs.getHomeDir( ).getAbsolutePath( ) );
		System.out.println( "WorkDir: " + prefs.getWorkDir( ).getAbsolutePath( ) );
		System.out.println( "MailDir: " + prefs.getMailDir( ).getAbsolutePath( ) );

		prefs.setUserName( "MyName" );
		prefs.setHomeDir( new File( "dirs" + File.separator + "home" ) );
		prefs.setMailDir( new File( "dirs" + File.separator + "mail" ) );
		prefs.setWorkDir( new File( "dirs" + File.separator + "work" ) );

		/* save the preferences (WINDOWS: into registry under HKEY_CURRENT_USER/Software/JavaSoft/Prefs/MyApplication) 
		 * (UNIX: into file ~/.MyApplication ) */
		try
		{
			PreferenceManager.get( ).save( );
		}
		catch ( PrefsException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

	}

}
