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
import java.util.prefs.Preferences;

import thobe.tools.preferences.PreferenceObject;

/**
 * @author Thomas Obenaus
 * @source MyPrefObj.java
 * @date 25.06.2010
 */
public class MyPrefObj extends PreferenceObject
{
	private static String	NODE_DIRS		= "directories";
	private static String	KEY_HOME_DIR	= "HomeDirectory";
	private static String	KEY_WORK_DIR	= "WorkDirectory";
	private static String	KEY_MAIL_DIR	= "MailDirectory";

	private static String	KEY_USER_NAME	= "UserName";

	private File			homeDir;
	private File			mailDir;
	private File			workDir;

	private String			userName;

	public MyPrefObj( )
	{
		super( "MyApplication" );
		this.userName = "user1";
		this.homeDir = new File( "home" );
		this.workDir = new File( "work" );
		this.mailDir = new File( "mail" );
	}

	@Override
	public void load( Preferences applicationRoot )
	{
		this.userName = applicationRoot.get( KEY_USER_NAME, "" );

		/* the dirs */
		Preferences nDirs = applicationRoot.node( NODE_DIRS );
		this.homeDir = new File( nDirs.get( KEY_HOME_DIR, "home" ) );
		this.workDir = new File( nDirs.get( KEY_WORK_DIR, "work" ) );
		this.mailDir = new File( nDirs.get( KEY_MAIL_DIR, "mail" ) );
	}

	@Override
	public void save( Preferences applicationRoot )
	{
		applicationRoot.put( KEY_USER_NAME, this.userName );

		/* the dirs */
		Preferences nDirs = applicationRoot.node( NODE_DIRS );
		nDirs.put( KEY_HOME_DIR, this.homeDir.getAbsolutePath( ) );
		nDirs.put( KEY_WORK_DIR, this.workDir.getAbsolutePath( ) );
		nDirs.put( KEY_MAIL_DIR, this.mailDir.getAbsolutePath( ) );
	}

	public File getHomeDir( )
	{
		return homeDir;
	}

	public File getMailDir( )
	{
		return mailDir;
	}

	public String getUserName( )
	{
		return userName;
	}

	public File getWorkDir( )
	{
		return workDir;
	}

	public void setHomeDir( File homeDir )
	{
		this.homeDir = homeDir;
	}

	public void setMailDir( File mailDir )
	{
		this.mailDir = mailDir;
	}

	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	public void setWorkDir( File workDir )
	{
		this.workDir = workDir;
	}

}
