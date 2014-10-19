package com.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Named;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

@Named
public class AppDirectConnector {
	private static final Logger LOG = Logger.getLogger( AppDirectConnector.class.getName() );
	
	private final String oauthConsumerKey = "integrationchallenge-15821";
	private final String oauthConsumerSecret = "LUtZSr5RrIQ2Ln84";
	
	public String get( final String eventUrl ) throws IOException, OAuthException {
		final OAuthConsumer consumer = new DefaultOAuthConsumer( oauthConsumerKey, oauthConsumerSecret );
		
		final URL url = new URL( eventUrl );
		LOG.info( "Requesting URL: " + eventUrl );
		
		final HttpURLConnection request = ( HttpURLConnection )url.openConnection();
		consumer.sign( request );
		request.connect();
		
		try {
			final Object response = request.getContent();			
			final StringBuilder sb = new StringBuilder();
			
			try( BufferedReader reader = new BufferedReader( new InputStreamReader( ( InputStream )response ) ) ) {
				String line = reader.readLine();
				
				while( line != null ) {
					sb.append( line );
					line = reader.readLine();
				}
			}
			
			final String xml = sb.toString();
			LOG.info( "Response received: " + xml );
			return xml;
		} finally {
			request.disconnect();
		}
	}
}
