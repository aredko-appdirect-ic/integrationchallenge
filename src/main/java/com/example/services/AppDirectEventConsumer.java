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
import com.example.model.User;
import com.google.common.base.Function;

@Named
public class AppDirectEventConsumer {
	public static BiFunction< Document, XPath, User > newSubscription( final Supplier< Subscription > supplier ) {
		return ( document, xpath ) -> {
			try {
				final Subscription subscription = supplier.get();						
				
				final User user = new User( subscription );
				user.setEmail( xpath.compile( "/event/creator/email" ).evaluate( document ) );
				user.setFirstName( xpath.compile( "/event/creator/firstName" ).evaluate( document ) );
				user.setLastName( xpath.compile( "/event/creator/lastName" ).evaluate( document ) );
				user.setOpenIdUrl( xpath.compile( "/event/creator/openId" ).evaluate( document ) );
				
				return user;
			} catch( final Exception ex ) {
				throw new RuntimeException( "Unable to interpret event payload", ex );
			}				
		};
	};
	
	public static BiFunction< Document, XPath, Subscription > updateSubscription( final Function< String, Subscription > supplier ) {
		return ( document, xpath ) -> {
			try {
				final String accountId = xpath.compile( "/event/payload/account/accountIdentifier" ).evaluate( document );
				return supplier.apply( accountId );			
			} catch( final Exception ex ) {
				throw new RuntimeException( "Unable to interpret event payload", ex );
			}				
		};
	};
	
   public static BiFunction< Document, XPath, User > assignUser( final Function< String, Subscription > supplier ) {
        return ( document, xpath ) -> {
            try {
                final String accountId = xpath.compile( "/event/payload/account/accountIdentifier" ).evaluate( document );
                
                final Subscription subscription = supplier.apply( accountId );                                
                if( subscription != null ) {
                    final User user = new User( subscription );                    
                    
                    user.setFirstName( xpath.compile( "/event/payload/user/firstName" ).evaluate( document ) );
                    user.setLastName( xpath.compile( "/event/payload/user/lastName" ).evaluate( document ) );
                    user.setEmail( xpath.compile( "/event/payload/user/email" ).evaluate( document ) );
                    user.setOpenIdUrl( xpath.compile( "/event/payload/user/openId" ).evaluate( document ) );
                    
                    return user;
                }               
                
                return null;
            } catch( final Exception ex ) {
                throw new RuntimeException( "Unable to interpret event payload", ex );
            }               
        };
    };
    
    public static BiFunction< Document, XPath, User > unassignUser( final Function< String, User > supplier ) {
        return ( document, xpath ) -> {
            try {
                final String opseIdUrl = xpath.compile( "/event/payload/user/openId" ).evaluate( document );
                final User user = supplier.apply( opseIdUrl );
                
                if( user != null ) {
                    final String accountId =  xpath.compile( "/event/payload/account/accountIdentifier" ).evaluate( document );
                    
                    if( user.getSubscription().getId().equalsIgnoreCase(accountId ) ) {                                                                
                        return user;
                    }
                }               
                
                return null;
            } catch( final Exception ex ) {
                throw new RuntimeException( "Unable to interpret event payload", ex );
            }               
        };
    };

	public static BiFunction< Document, XPath, Subscription > deleteSubscription( final Function< String, Subscription > supplier ) {
		return ( document, xpath ) -> {
			try {
				final String accountId = xpath.compile( "/event/payload/account/accountIdentifier" ).evaluate( document );
				return supplier.apply( accountId );			
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
