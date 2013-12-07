/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.preferences;

import java.util.prefs.Preferences;

/**
 * @author Thomas Obenaus
 * @source Prefs.java
 * @date 25.08.2009
 */
public class PreferenceManager
{

	private static PreferenceManager	instance	= null;
	private PreferenceObject			preferenceObject;

	/**
	 * Ctor
	 */
	private PreferenceManager( PreferenceObject preferenceObject )
	{
		this.preferenceObject = preferenceObject;
		this.load( );
	}

	/**
	 * Use this method to access the sole instance of the class {@link PreferenceManager} (implemented as singleton).
	 * @return
	 * @throws PrefsException
	 */
	public static PreferenceManager get( ) throws PrefsException
	{
		if ( instance == null )
			throw new PrefsException( "The preference-objcet is not available. Use Prefs.createPrefs() to create it!" );
		return instance;
	}

	/**
	 * Creates the {@link PreferenceManager} and loads the preferences of the given {@link PreferenceObject}.
	 * @param preferenceObject
	 */
	public static void createPrefs( PreferenceObject preferenceObject )
	{
		instance = new PreferenceManager( preferenceObject );
	}

	private void load( )
	{
		Preferences root = Preferences.userRoot( );
		this.preferenceObject.load( root.node( this.preferenceObject.getApplicationName( ) ) );
	}

	/**
	 * Save the preferences.
	 * <ul>
	 * <li>Windows: The preferences will be stored into registry under HKEY_CURRENT_USER/Software/JavaSoft/Prefs/
	 * <code>PreferenceObject.getApplicationName()</code></li>
	 * <li>UNIX: The preferences will be stored into ~/.java/.userPrefs/ <code>PreferenceObject.getApplicationName()</code></li>
	 * </ul>
	 */
	public void save( )
	{
		Preferences root = Preferences.userRoot( );
		Preferences applicationRoot = root.node( this.preferenceObject.getApplicationName( ) );
		this.preferenceObject.save( applicationRoot );

	}
}
