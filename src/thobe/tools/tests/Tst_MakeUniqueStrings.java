/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import thobe.tools.utils.Utilities;

/**
 * @author Thomas Obenaus
 * @source Tst_MakeUniqueStrings.java
 * @date 02.03.2010
 */
public class Tst_MakeUniqueStrings
{
	@Before
	public void init( )
	{

	}

	@Test
	public void tst_nodoubles( )
	{
		List<String> stri = new ArrayList<String>( );
		stri.add( "hallo1" );
		stri.add( "hallo2" );
		stri.add( "hallo3" );
		stri.add( "hallo4" );
		stri.add( "hallo5" );
		stri.add( "hallo6" );
		stri.add( "hallo7" );
		stri.add( "hallo8" );
		stri.add( "hallo9" );

		List<String> uniqueStrings = Utilities.makeUnique( stri );

		for ( int i = 0; i < stri.size( ); i++ )
		{
			assertEquals( stri.get( i ), uniqueStrings.get( i ) );
		}

	}

	@Test
	public void tst_onlydoubles( )
	{
		List<String> stri = new ArrayList<String>( );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo" );

		List<String> uniqueStrings = Utilities.makeUnique( stri );

		for ( int i = 0; i < stri.size( ); i++ )
		{
			if ( i == 0 )
				assertEquals( stri.get( i ), uniqueStrings.get( i ) );
			else
			{
				assertEquals( stri.get( i ) + "_" + ( i - 1 ), uniqueStrings.get( i ) );
			}
		}
	}

	@Test
	public void tst_mix( )
	{
		List<String> stri = new ArrayList<String>( );
		stri.add( "hallo" );
		stri.add( "hallo2" );
		stri.add( "hallo3" );
		stri.add( "hallo" );
		stri.add( "hallo5" );
		stri.add( "hallo" );
		stri.add( "hallo" );
		stri.add( "hallo8" );
		stri.add( "hallo" );

		List<String> uniqueStrings = Utilities.makeUnique( stri );
		int idx = 0;
		for ( int i = 0; i < stri.size( ); i++ )
		{
			if ( i == 0 || i == 1 || i == 2 || i == 4 || i == 7 )
				assertEquals( stri.get( i ), uniqueStrings.get( i ) );
			else
			{
				assertEquals( stri.get( i ) + "_" + ( idx ), uniqueStrings.get( i ) );
				idx++;
			}
		}

	}
}
