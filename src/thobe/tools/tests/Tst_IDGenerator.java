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

import org.junit.Test;

import thobe.tools.utils.IDGenerator;

/**
 * @author Thomas Obenaus
 * @source Tst_IDGenerator.java
 * @date Jul 28, 2012
 */
public class Tst_IDGenerator
{
	@Test
	public void tst_gainIDS_1( )
	{
		IDGenerator idgen = new IDGenerator( );

		for ( int i = 0; i < 1000; i++ )
			idgen.getNewId( );

		for ( int i = 0; i < 1000; i++ )
			idgen.removeID( i );

		assertEquals( "ID 0 expected", 0, idgen.getNewId( ) );

		for ( int i = 0; i < 1000; i++ )
			idgen.getNewId( );

		for ( int i = 999; i >= 0; i-- )
			idgen.removeID( i );

		assertEquals( "ID 999 expected", 999, idgen.getNewId( ) );
	}

	@Test
	public void tst_gainIDS_2( )
	{
		IDGenerator idgen = new IDGenerator( );

		for ( int i = 0; i < 1000; i++ )
			idgen.getNewId( );

		for ( int i = 0; i < 1000; i++ )
			idgen.removeID( i );

		for ( int i = 999; i >= 0; i-- )
			idgen.removeID( i );

		assertEquals( "ID 999 expected", 0, idgen.getNewId( ) );
	}

	@Test
	public void tst_treadSafety( ) throws InterruptedException
	{
		IDGenerator idgen = new IDGenerator( );

		Thread t1 = new Thread( new IDConsumer( idgen, 7, 30, 15 ) );
		Thread t2 = new Thread( new IDConsumer( idgen, 10, 30, 15 ) );
		Thread t3 = new Thread( new IDConsumer( idgen, 8, 30, 15 ) );
		Thread t4 = new Thread( new IDConsumer( idgen, 9, 30, 15 ) );
		Thread t5 = new Thread( new IDConsumer( idgen, 11, 30, 15 ) );

		t1.start( );
		t2.start( );
		t3.start( );
		t4.start( );
		t5.start( );

		System.out.println( "0/5 done..." );
		t1.join( );
		System.out.println( "1/5 done..." );
		t2.join( );
		System.out.println( "2/5 done..." );
		t3.join( );
		System.out.println( "3/5 done..." );
		t4.join( );
		System.out.println( "4/5 done..." );
		t5.join( );
		System.out.println( "5/5 done" );
	}

	private class IDConsumer implements Runnable
	{

		private IDGenerator	idgen;
		private final long	millis;
		private final int	iterations;
		private final int	freeAfter;

		public IDConsumer( IDGenerator idgen, long millis, int iterations, int freeAfter )
		{
			this.idgen = idgen;
			this.millis = millis;
			this.iterations = iterations;
			this.freeAfter = freeAfter;
		}

		@Override
		public void run( )
		{
			for ( int i = 0; i < this.iterations; i++ )
			{

				// request ids
				for ( int n = 0; n < this.freeAfter; n++ )
				{
					this.idgen.getNewId( );
					try
					{
						Thread.sleep( millis );
					}
					catch ( InterruptedException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace( );
					}
				}

				// free ids
				for ( int n = 0; n < this.freeAfter; n++ )
				{
					this.idgen.removeID( n );
					try
					{
						Thread.sleep( millis );
					}
					catch ( InterruptedException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace( );
					}
				}
			}
		}

	}

}
