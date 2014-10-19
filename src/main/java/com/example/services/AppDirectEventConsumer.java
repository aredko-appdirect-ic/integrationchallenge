package com.example.services;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.model.Subscription;

@Named
public class AppDirectEventConsumer {
	public static BiFunction< Document, XPath, Subscription > newSubscription( final Supplier< Subscription > supplier ) {
		return ( document, xpath ) -> {
			try {
				final Subscription newSubscription = supplier.get();						
				
				newSubscription.setEmail( xpath.compile( "/event/creator/email" ).evaluate( document ) );
				newSubscription.setFirstName( xpath.compile( "/event/creator/firstName" ).evaluate( document ) );
				newSubscription.setLastName( xpath.compile( "/event/creator/lastName" ).evaluate( document ) );
				
				return newSubscription;
			} catch( final Exception ex ) {
				throw new RuntimeException( "Unable to interpret event payload", ex );
			}				
		};
	};
	
    public< T > T consume( final String eventXml, final BiFunction< Document, XPath, T > consumer ) throws IOException, ParserConfigurationException, SAXException {
    	final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();    	
    	final DocumentBuilder builder = builderFactory.newDocumentBuilder();
    	
    	try( Reader reader = new StringReader( eventXml ) ) {
    		final Document document = builder.parse( new InputSource( reader ) );
    		final XPath xpath =  XPathFactory.newInstance().newXPath();
    		return consumer.apply( document, xpath );
    	}
    }
}
