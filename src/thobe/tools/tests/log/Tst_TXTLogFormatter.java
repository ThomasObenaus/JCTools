/*
 *  Copyright (C) 2013 ThObe. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		ThomasObenaus@gmx.net
 *  Project:    JavaComponents - Tools
 */

package thobe.tools.tests.log;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.BeforeClass;
import org.junit.Test;

import thobe.tools.log.Log;
import thobe.tools.log.TXTLogFormatter;

/**
 * @author Thomas Obenaus
 * @source Tst_Log.java
 * @date 29.03.2012
 */
public class Tst_TXTLogFormatter
{
	
	@BeforeClass
	public static void init() throws SecurityException, IOException
	{
		Log.initLog( "UnitTest",Level.ALL);
	}
	
	@Test
	public void tst_txtFormatter()
	{
		TXTLogFormatter f = new TXTLogFormatter( );
		LogRecord lRecord = new LogRecord( Level.SEVERE,"Dies ist ein Fehler ({0}) der ausgegeben wird." );
		lRecord.setParameters( new String[]{"Schwerer Fehler"} );
		lRecord.setLoggerName("Logger.log.sublog");
		
		
		String formattedMessage = f.format( lRecord );
		
		// cur off the time
		formattedMessage = formattedMessage.replaceAll( "[0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{3}","").trim( );		
	
		assertEquals( "Logger.log.sublog [SEVERE] Dies ist ein Fehler (Schwerer Fehler) der ausgegeben wird.", formattedMessage );
		
		
//		lRecord = new LogRecord( Level.SEVERE,"Dies ist ein Fehler ({0}) der ausgegeben wird." );
//		lRecord.setParameters( new String[]{"Schwerer Fehler"} );
//		lRecord.setThrown(new IllegalArgumentException("EXCEPTION"));
//		
//		
//		formattedMessage = f.format( lRecord );
//		
//		// cur off the time
//		formattedMessage = formattedMessage.replaceAll( "[0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{3}","").trim( );		
//	
//		assertEquals( "[SEVERE] Dies ist ein Fehler (Schwerer Fehler) der ausgegeben wird.", formattedMessage );
	}
}


