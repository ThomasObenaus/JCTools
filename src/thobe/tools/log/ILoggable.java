/*
 *  Copyright (C) 2014, j.umbel. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    j.umbel
 */

package thobe.tools.log;

import java.util.logging.Logger;

/**
 * Interface for all classes who needs to log into a separate log-channel.
 * @author Thomas Obenaus
 * @source ILoggable.java
 * @date May 2, 2014
 */
public abstract class ILoggable
{
	protected abstract String getLogChannelName( );

	protected final Logger LOG( )
	{
		return Logger.getLogger( getLogChannelName( ) );
	}
}


