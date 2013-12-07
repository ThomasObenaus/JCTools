/*
 *  Copyright (C) 2013, Thomas Obenaus. All rights reserved.
 *  Licensed under the New BSD License (3-clause lic)
 *  See attached license-file.
 *
 *	Author: 	Thomas Obenaus
 *	EMail:		obenaus.thomas@gmail.com
 *  Project:    JavaComponents/Tools
 */
package thobe.tools.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Thomas Obenaus
 * @source XMLDocument.java
 * @date 26.08.2010
 */
public abstract class XMLDocument
{
	private static final String	ATTR_VERSION	= "version";

	/**
	 * Das XML Dokument
	 */
	private Document			xmlDocument;

	public XMLDocument( )
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
		DocumentBuilder builder = null;
		try
		{
			builder = factory.newDocumentBuilder( );
			this.xmlDocument = builder.newDocument( );

		}
		catch ( ParserConfigurationException pce )
		{
			/* Parser with specified options can't be built */
			pce.printStackTrace( );
			return;
		}
		this.initDocumentStructure( this.xmlDocument );
	}

	/**
	 * Loads an xml-document from file.
	 * @param fileToLoad
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void loadXMLDocument( File fileToLoad ) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
		DocumentBuilder builder = null;

		builder = factory.newDocumentBuilder( );
		this.xmlDocument = builder.parse( fileToLoad );
	}

	/**
	 * Returns the root node of the document, or null if it doesn't exist.
	 * @return
	 */
	public Node getRoot( )
	{
		NodeList ndList = this.getXMLDocument( ).getChildNodes( );
		if ( ndList == null || ndList.getLength( ) == 0 )
			return null;

		return ndList.item( 0 );
	}

	protected Document getXMLDocument( )
	{
		return this.xmlDocument;
	}

	protected abstract void initDocumentStructure( Document xmlDocument );

	/**
	 * This method should return the name of the root-tag.
	 * @return
	 */
	protected abstract String getRootTAG( );

	/**
	 * This method should return the current version of this document. The version can be set using the setCurrentVersion()-method.
	 * @return
	 */
	protected abstract int currentDocumentVersion( );

	public int getVersion( )
	{
		Element root = ( Element ) this.xmlDocument.getElementsByTagName( this.getRootTAG( ) ).item( 0 );
		return this.getAttribute( root, ATTR_VERSION, 0 );
	}

	public void setCurrentVersion( )
	{
		Element root = ( Element ) this.xmlDocument.getElementsByTagName( this.getRootTAG( ) ).item( 0 );
		root.setAttribute( ATTR_VERSION, "" + currentDocumentVersion( ) );
	}

	public boolean hasReadableVersion( )
	{
		return ( this.getVersion( ) == this.currentDocumentVersion( ) );
	}

	/**
	 * Kommentar zur XML-Datei. Der Kommentar sollte schon einem XML-Kommentar der Form <!-- KOMMENTAR --> entsprechen"
	 * @return
	 */
	protected abstract String initialDocumentComment( );

	/**
	 * Stores the xml-document to file
	 * @param targetFile
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void saveXMLDocument( File targetFile ) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, IOException
	{
		/* ---- Use a XSLT transformer for writing the new XML file ---- */
		Transformer transformer;
		transformer = TransformerFactory.newInstance( ).newTransformer( );
		DOMSource source = new DOMSource( this.xmlDocument );
		FileOutputStream os;
		os = new FileOutputStream( targetFile );

		StreamResult result = new StreamResult( os );
		transformer.transform( source, result );
		os.close( );
	}

	protected Double getAttribute( Element element, String attributeName, double defaultValue )
	{
		Double result = defaultValue;
		if ( !element.hasAttribute( attributeName ) )
			return result;
		try
		{
			result = Double.parseDouble( element.getAttribute( attributeName ) );
		}
		catch ( NumberFormatException e )
		{}

		return result;
	}

	protected String getAttribute( Element element, String attributeName, String defaultValue )
	{
		String result = defaultValue;
		if ( !element.hasAttribute( attributeName ) )
			return result;

		result = element.getAttribute( attributeName );
		return result;
	}

	protected Boolean getAttribute( Element element, String attributeName, boolean defaultValue )
	{
		Boolean result = defaultValue;
		if ( !element.hasAttribute( attributeName ) )
			return result;
		try
		{
			result = Boolean.parseBoolean( element.getAttribute( attributeName ) );
		}
		catch ( NumberFormatException e )
		{}

		return result;
	}

	protected Integer getAttribute( Element element, String attributeName, int defaultValue )
	{
		Integer result = defaultValue;
		if ( !element.hasAttribute( attributeName ) )
			return result;
		try
		{
			result = Integer.parseInt( element.getAttribute( attributeName ) );
		}
		catch ( NumberFormatException e )
		{}

		return result;
	}

	protected Element getElementForTagName( String tagName, Node parentNode )
	{
		NodeList ndList = parentNode.getChildNodes( );
		Element result = null;
		for ( int i = 0; i < ndList.getLength( ); i++ )
		{
			result = ( Element ) ndList.item( i );
			if ( result.getNodeName( ).equals( tagName ) )
				return result;
		}
		return result;
	}

	protected List<Element> getElementsForTagName( String tagName, Node parentNode )
	{
		List<Element> result = new ArrayList<Element>( );
		NodeList ndList = parentNode.getChildNodes( );
		for ( int i = 0; i < ndList.getLength( ); i++ )
		{
			Element element = ( Element ) ndList.item( i );
			if ( element.getNodeName( ).equals( tagName ) )
				result.add( element );
		}
		return result;
	}

}
