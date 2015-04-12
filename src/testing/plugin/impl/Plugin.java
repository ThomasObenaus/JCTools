/*
 *  Copyright (C) 2015, Thomas Obenaus. All rights reserved.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */

package testing.plugin.impl;

import testing.plugin.api.IPlugin;

/**
 * @author Thomas Obenaus
 * @source Plugin.java
 * @date Apr 11, 2015
 */
public class Plugin implements IPlugin
{

	@Override
	public String getVersion( )
	{
		return "2.0.0";
	}

}
