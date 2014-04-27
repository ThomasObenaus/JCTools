/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import thobe.tools.utils.CopyFileException;

/**
 * @author Thomas Obenaus
 * @source FileTools.java 
 * @date 26.06.2009
 */
public class FileTools
{

	/**
	 * Creates a {@link FileFilter} that can be used within the {@link JFileChooser}.
	 * @param extensions - all file-extension that are allowed
	 * @param description - a short description for the filter
	 * @return
	 */
	public static FileFilter createFCFileFilter( final String[] extensions, final String description )
	{
		FileFilter result = new FileFilter( )
		{
			@Override
			public String getDescription( )
			{
				return description;
			}

			@Override
			public boolean accept( File f )
			{
				if ( f.isDirectory( ) )
					return true;

				for ( String ext : extensions )
				{
					if ( !ext.startsWith( "." ) )
						ext += ".";
					if ( f.getName( ).endsWith( ext ) )
						return true;
				}
				return false;
			}
		};
		return result;
	}

	public static boolean isDirectoryWritable( File directory )
	{
		try
		{
			File tempFile = new File( directory + File.separator + "_writeCheck.tempFile" );
			tempFile.createNewFile( );
			tempFile.delete( );
		}
		catch ( IOException e )
		{
			return false;
		}
		return true;
	}

	/**
	 * A function that counts the number of files in the given directory
	 * recursively. Each directory will be interpreted as file and will be
	 * counted too. So the function will return 1 at least if the given
	 * directory exists.
	 * @param directory
	 * @return - the number of files found in the given directory or 0 if the
	 *         directory does not exist.
	 */
	public static int numFilesInDirectory( File directory )
	{
		if ( !directory.exists( ) )
			return 0;

		int numFiles = 1;

		if ( directory.isDirectory( ) )
		{
			File files[] = directory.listFiles( );
			for ( File file : files )
				numFiles += numFilesInDirectory( file );
		}

		return numFiles;
	}

	/**
	 * Copies the given file 'source' to the location of 'target'
	 * @param source
	 * @param target
	 * @throws CopyFileException
	 */
	public static void copyFile( File source, File target ) throws CopyFileException
	{
		try
		{
			FileInputStream src = new FileInputStream( source );
			FileInputStream dst = new FileInputStream( source );

			FileChannel sourceChannel = src.getChannel( );
			FileChannel destinationChannel = dst.getChannel( );

			sourceChannel.transferTo( 0, sourceChannel.size( ), destinationChannel );
			sourceChannel.close( );
			destinationChannel.close( );
			src.close( );
			dst.close( );
		}
		catch ( Exception ex )
		{
			throw new CopyFileException( ex.getLocalizedMessage( ) );
		}
	}

	/**
	 * Copies a directory recursively.
	 * @param source_dir
	 * @param target_dir
	 */
	public static void copyDirectory( File source_dir, File target_dir ) throws CopyFileException
	{
		if ( !target_dir.exists( ) )
		{
			if ( !target_dir.mkdir( ) )
				throw new CopyFileException( "Error creating target-directory [" + target_dir.getAbsolutePath( ) + "], access denied" ); //$NON-NLS-1$ //$NON-NLS-2$
		}

		/* source is no directory */
		if ( !source_dir.isDirectory( ) )
			throw new CopyFileException( "The given source [" + source_dir.getAbsolutePath( ) + "] is no directory" ); //$NON-NLS-1$ //$NON-NLS-2$

		/* target is no directory */
		if ( !target_dir.isDirectory( ) )
			throw new CopyFileException( "The given target [" + target_dir.getAbsolutePath( ) + "] is no directory" ); //$NON-NLS-1$ //$NON-NLS-2$

		/* target is not writable */
		if ( !target_dir.isDirectory( ) )
			throw new CopyFileException( "The given target [" + target_dir.getAbsolutePath( ) + "] is not writable" ); //$NON-NLS-1$ //$NON-NLS-2$

		/* target-dir is not writable */
		try
		{
			File tmp = File.createTempFile( "writetest", null, target_dir ); //$NON-NLS-1$
			tmp.delete( );
		}
		catch ( IOException e )
		{
			throw new CopyFileException( "The given target [" + target_dir.getAbsolutePath( ) + "] is not writable" ); //$NON-NLS-1$ //$NON-NLS-2$
		}

		File[] source_files = source_dir.listFiles( );
		if ( source_files.length == 0 )
			return;

		for ( int i = 0; i < source_files.length; i++ )
		{
			File sf = source_files[i];
			if ( sf.isDirectory( ) )
				copyDirectory( sf, new File( target_dir.getAbsoluteFile( ) + "/" + sf.getName( ) ) ); //$NON-NLS-1$
			else copyFile( sf, new File( target_dir.getAbsoluteFile( ) + "/" + sf.getName( ) ) ); //$NON-NLS-1$

		}
	}

	/**
	 * Returns true if the file exists.
	 * @param filename
	 * @return
	 */
	public static boolean fileExists( String filename )
	{
		if ( filename == null )
			return false;
		Path path = FileSystems.getDefault( ).getPath( filename );
		if ( !Files.exists( path ) )
			return false;
		return false;
	}

	/**
	 * Returns true if the file (no directory) is readable. The method returns
	 * false if:</b>
	 * <ul>
	 * <li>the given reference parameter is null</li>
	 * <li>the given file does not exist</li>
	 * <li>the given file is an directory</li>
	 * <li>the given file is not readable</li>
	 * </ul>
	 * @param file
	 * @return
	 */
	public static boolean fileIsReadable( String filename )
	{
		if ( filename == null )
			return false;
		if ( !fileIsReadable( new File( filename ) ) )
			return false;
		return true;
	}

	/**
	 * Returns true if the file (no directory) is readable. The method returns
	 * false if:</b>
	 * <ul>
	 * <li>the given reference parameter is null</li>
	 * <li>the given file does not exist</li>
	 * <li>the given file is an directory</li>
	 * <li>the given file is not readable</li>
	 * </ul>
	 * @param file
	 * @return
	 */
	public static boolean fileIsReadable( File file )
	{
		if ( file == null )
			return false;
		Path path = FileSystems.getDefault( ).getPath( file.getAbsolutePath( ) );

		if ( !Files.exists( path ) )
			return false;
		if ( !Files.isReadable( path ) )
			return false;
		if ( Files.isDirectory( path ) )
			return false;
		return true;
	}

	/**
	 * Returns true if the given file is writable. Returns false if:</br>
	 * <ul>
	 * <li>the given parameter file is null</li>
	 * <li>the given file is not writable</li>
	 * <li>the given file is an directory</li>
	 * </ul>
	 * @param file
	 * @return
	 */
	public static boolean fileIsWritable( File file )
	{
		if ( file == null )
			return false;
		Path path = FileSystems.getDefault( ).getPath( file.getAbsolutePath( ) );

		if ( Files.notExists( path ) )
		{
			// file does not exist...check if the file can be created in files parent-directory
			File parentFile = file.getParentFile( );
			if ( parentFile == null )
				parentFile = new File( "" );
			Path parent = FileSystems.getDefault( ).getPath( parentFile.getAbsolutePath( ) );
			if ( !Files.isWritable( parent ) || !Files.isDirectory( parent ) )
				return false;
			return true;
		}

		if ( !Files.isWritable( path ) )
			return false;
		if ( Files.isDirectory( path ) )
			return false;
		return true;
	}

	/**
	 * Adds the given extension to the file given in the file-path, but only if
	 * the extension is missing.
	 * @param filePath
	 * @param extension - pure extension without a '.' separator
	 * @return
	 */
	public static String addFileExtension( String filePath, String extension )
	{
		if ( filePath.endsWith( "." + extension ) )
			return filePath;
		else return filePath + "." + extension;
	}

	/**
	 * Replaces all character that are not allowed in a correct filename for
	 * Windows with the given replacement. The following characters will be
	 * replaced:<br>
	 * <ul>
	 * <li>"</li>
	 * <li>\</li>
	 * <li>/</li>
	 * <li>:</li>
	 * <li>*</li>
	 * <li>?</li>
	 * <li>&lt;</li>
	 * <li>&gt;</li>
	 * </ul>
	 * @param str - the String that might contain illegal characters
	 * @param replacemetForIllegalCharacters - the replacement (if the given
	 *            replacement contains illegal characters, is null or of length 0
	 *            the default replacement '_' will be used instead.)
	 * @return
	 */
	public static String strToWinFilename( String str, String replacemetForIllegalCharacters )
	{
		if ( replacemetForIllegalCharacters == null || replacemetForIllegalCharacters.length( ) == 0 )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "*" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "\"" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "\\" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "/" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( ":" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "?" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( "<" ) )
			replacemetForIllegalCharacters = "_";
		if ( replacemetForIllegalCharacters.contains( ">" ) )
			replacemetForIllegalCharacters = "_";

		String result = str.replaceAll( "\\*|\\?|\"|\\\\|\\/|<|>|:", replacemetForIllegalCharacters );
		return result;
	}
}
