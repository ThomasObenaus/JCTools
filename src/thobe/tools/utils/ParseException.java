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
 * Class representing an {@link Exception} that can occur while parsing a list of values (see {@link ListParser})
 * @author Thomas Obenaus
 * @source ParseException.java
 * @date 09.07.2012
 */
@SuppressWarnings ( "serial")
public class ParseException extends Exception
{
	private String	parserName;
	private String	valueToParse;

	public ParseException( String valueToParse, String parserName, String cause )
	{
		super( cause );
		this.parserName = parserName;
		this.valueToParse = valueToParse;
	}

	public String getValueToParse( )
	{
		return valueToParse;
	}

	public String getParserName( )
	{
		return parserName;
	}
}
