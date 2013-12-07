/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.utils;

/**
 * @author Thomas Obenaus
 * @source ZipSupport.java
 * @date 23.03.2010
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipSupport
{

	/**
	 * Extracts all files located in the given file (*.jar or *.zip) into the given directory.
	 * @param zipSrcStr - name and path to the file that should be extracted
	 * @param unzipDstStr - name of the directory wherein the files should be extracted to
	 */
	public static void unzip( String zipSrcStr, String unzipDstStr )
	{
		ZipSupport.unzip( zipSrcStr, unzipDstStr, null );
	}

	/**
	 * Extracts all files, starting at the given subfolder (entryName), located in the given file
	 * (*.jar or *.zip) into the given directory.
	 * @param zipSrcStr - name and path to the file that should be extracted
	 * @param unzipDstStr - name of the directory wherein the files should be extracted to
	 * @param entryName - the subfolder whoose contents should be extracted
	 */
	public static void unzip( String zipSrcStr, String unzipDstStr, String entryName )
	{
		FileInputStream fin = null;
		InputStream is = null;
		boolean unzipAll = false;

		if ( entryName == null || entryName == "" )
			unzipAll = true;

		String zipExtension = zipSrcStr.substring( zipSrcStr.lastIndexOf( "." ) );

		try
		{
			File zipFile = new File( zipSrcStr );
			zipFile = zipFile.getAbsoluteFile( );
			fin = new FileInputStream( zipFile );
			if ( zipExtension.equalsIgnoreCase( ".zip" ) || zipExtension.equalsIgnoreCase( ".jar" ) )
			{

				is = new ZipInputStream( fin );
				ZipEntry entry = ( ( ZipInputStream ) is ).getNextEntry( );
				while ( entry != null )
				{
					String name = entry.getName( );
					if ( unzipAll || name.equals( entryName ) || name.startsWith( entryName ) )
					{
						if ( entry.isDirectory( ) )
						{
							File dir = new File( unzipDstStr + File.separator + name );
							dir.mkdirs( );
						}
						else
						{
							if ( name.contains( "/" ) || !unzipDstStr.isEmpty( ) )
							{
								File file = new File( unzipDstStr + File.separator + name );
								File dir = file.getParentFile( );
								dir.mkdirs( );
							}
							unzipFile( entry, is, unzipDstStr + File.separator + name );
						}
					}
					entry = ( ( ZipInputStream ) is ).getNextEntry( );
				}
			}
		}
		catch ( FileNotFoundException fnfe )
		{
			fnfe.printStackTrace( );
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace( );
		}
		finally
		{
			try
			{
				if ( is != null )
					is.close( );
				if ( fin != null )
					fin.close( );
			}
			catch ( IOException e )
			{
				e.printStackTrace( );
			}
		}
	}

	private static int unzipFile( ZipEntry entry, InputStream in, String name ) throws IOException
	{

		int numRead = 0, numReadSum = 0;
		byte[] data = new byte[1024];
		FileOutputStream out = null;
		switch ( entry.getMethod( ) )
		{
		case ZipEntry.STORED:
			break;
		case ZipEntry.DEFLATED:
			break;
		}
		out = new FileOutputStream( name );
		while ( ( numRead = in.read( data, 0, 1024 ) ) != -1 )
		{
			out.write( data, 0, numRead );
			numReadSum += numRead;
		}
		out.close( );
		return numReadSum;
	}

}
