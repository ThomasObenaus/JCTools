/*
 *  Copyright (C) 2014, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    LogFileViewer
 */

package testing.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import testing.plugin.api.IPlugin;
import testing.plugin.impl.Plugin;
import thobe.tools.plugin.PluginClassLoader;

/**
 * @author Thomas Obenaus
 * @source MainClass.java
 * @date Apr 9, 2015
 */
public class MainClass
{
	public static void main( String[] args ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, URISyntaxException
	{
		

		List<File> dirs = new ArrayList<File>( );
		dirs.add( new File( "src/" ) );

		ClassLoader appClassLoader = IPlugin.class.getClassLoader( );
		PluginClassLoader specClassLoader = new MyPluginClassLoader( appClassLoader, dirs );

		System.out.println( "AppClassLoader: " + appClassLoader );
		System.out.println( "SpecialClassLoader: " + specClassLoader );


		IPlugin objFromAppClassLoader = new Plugin( );
		IPlugin objFromSpecialClassLoader = ( IPlugin ) specClassLoader.loadClass( "testing.plugin.impl.Plugin" ).newInstance( );

		System.out.println( "objFromAppClassLoader=" + objFromAppClassLoader.getVersion( ) );
		System.out.println( "objFromSpecialClassLoader=" + objFromSpecialClassLoader.getVersion( ) );

	}

	private final static class MyPluginClassLoader extends PluginClassLoader
	{

		public MyPluginClassLoader( ClassLoader parent, List<File> dir ) throws ZipException, IOException, URISyntaxException
		{
			super( parent, dir );
		}

		public MyPluginClassLoader( ClassLoader parent, ZipFile jarFile ) throws ZipException, IOException, URISyntaxException
		{
			super( parent, jarFile );
		}

		@Override
		protected boolean useParentClassLoader( String name )
		{
			return name.equals( "testing.plugin.IPlugin" );
		}
	};

}
