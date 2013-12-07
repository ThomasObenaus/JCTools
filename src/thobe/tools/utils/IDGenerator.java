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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thread-safe class for the creation of unique ids
 * @author Thomas Obenaus
 * @source DataSourceIDGenerator.java
 * @date 15.10.2009
 */
public class IDGenerator
{
	private List<Integer>	freeIDs;
	private List<Integer>	occupiedIDs;

	public IDGenerator( )
	{
		this.occupiedIDs = new ArrayList<Integer>( );
		this.freeIDs = new ArrayList<Integer>( );
	}

	public synchronized void removeID( int id )
	{
		if ( this.occupiedIDs.contains( id ) )
		{
			this.occupiedIDs.remove( Integer.valueOf( id ) );
			this.freeIDs.add( id );
		}
	}

	public synchronized int getNewId( )
	{
		int result = 0;
		if ( this.freeIDs.size( ) <= 0 )
		{
			Collections.sort( this.occupiedIDs );

			if ( this.occupiedIDs.size( ) > 0 )
				result = this.occupiedIDs.get( this.occupiedIDs.size( ) - 1 ) + 1;
		}
		else result = this.freeIDs.remove( 0 );
		this.occupiedIDs.add( result );
		return result;
	}

	public synchronized void reset( )
	{
		this.occupiedIDs.clear( );
		this.freeIDs.clear( );
	}

}
