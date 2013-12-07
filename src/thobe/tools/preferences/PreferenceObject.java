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
 * @source PreferenceObject.java
 * @date 25.08.2009
 */
public abstract class PreferenceObject
{
	private String	applicationName;

	/**
	 * Ctor
	 * @param applicationName - is used to identify/ find the preferences of this application within
	 *            a crowd of saved preferences of other applications.
	 */
	public PreferenceObject( String applicationName )
	{
		this.applicationName = applicationName;
	}

	public String getApplicationName( )
	{
		return this.applicationName;
	}

	/**
	 * Place your code to load the preferences into this method. This method will be called when
	 * calling <code>{@link PreferenceManager}.createPrefs()</code>.
	 * @param applicationRoot - the preference root-node for this application
	 */
	public abstract void load( Preferences applicationRoot );

	/**
	 * Place your code to save the preferences into this method. This method will be called when
	 * calling <code>{@link PreferenceManager}.save()</code>.
	 * @param applicationRoot - the preference root-node for this application
	 */
	public abstract void save( Preferences applicationRoot );
}
