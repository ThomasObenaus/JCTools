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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Class that can be used a type reference. Since this class is abstract you
 * have to use it as follows:<br>
 * <code>new TypeRef<List<String>>( )
			{
			}.newInstance( ) </code>
 * @author Thomas Obenaus
 * @source TypeRef.java
 * @date 10.05.2012
 */
public abstract class TypeRef<T>
{
	private final Type				type;
	private volatile Constructor<?>	constructor;

	protected TypeRef( )
	{
		Type superclass = getClass( ).getGenericSuperclass( );
		if ( superclass instanceof Class )
		{
			throw new RuntimeException( "Wrong usage of TypeRef: Missing type parameter." );
		}
		this.type = ( ( ParameterizedType ) superclass ).getActualTypeArguments( )[0];
	}

	/**
	 * Instantiates a new instance of {@code T} using the default, no-arg
	 * constructor.
	 */
	@SuppressWarnings ( "unchecked")
	public T newInstance( ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		if ( constructor == null )
		{
			Class<?> rawType = type instanceof Class<?> ? ( Class<?> ) type : ( Class<?> ) ( ( ParameterizedType ) type ).getRawType( );
			constructor = rawType.getConstructor( );
		}
		return ( T ) constructor.newInstance( );
	}

	/**
	 * Returns the referenced type.
	 * @return
	 */
	public Type getType( )
	{
		return this.type;
	}
}
