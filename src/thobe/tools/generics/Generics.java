/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.generics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Obenaus
 * @source Generics.java
 * @date 23.03.2012
 */
public class Generics
{
	/**
	 * Returns true if the given subClass is in the extension-hierarchy of the
	 * given superClass.
	 * @param superClass
	 * @param subClass
	 * @return
	 */
	public static boolean isSuperClass( @SuppressWarnings ( "rawtypes") Class superClass, @SuppressWarnings ( "rawtypes") Class subClass )
	{
		String nameOfSuperClass = superClass.getCanonicalName( );
		while ( subClass != null )
		{
			if ( nameOfSuperClass.equals( subClass.getCanonicalName( ) ) )
				return true;
			subClass = subClass.getSuperclass( );
		}
		return false;
	}

	@SuppressWarnings ( "unchecked")
	public static <D> Class<List<D>> createListTypeReference( Class<D> type )
	{
		Class<List<D>> classs = null;
		try
		{
			classs = ( Class<List<D>> ) new TypeRef<ArrayList<D>>( )
			{}.newInstance( ).getClass( );
		}
		catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

		return classs;
	}
}
