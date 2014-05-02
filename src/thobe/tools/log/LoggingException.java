/*
 *  Copyright (C) 2014, j.umbel. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    j.umbel
 */

package thobe.tools.log;

/**
 * @author Thomas Obenaus
 * @source LoggingException.java
 * @date May 2, 2014
 */
@SuppressWarnings ( "serial")
public class LoggingException extends Exception
{
	public LoggingException( String cause )
	{
		super( cause );
	}
}
