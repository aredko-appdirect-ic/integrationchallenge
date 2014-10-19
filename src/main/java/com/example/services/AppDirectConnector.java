package com.example.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Named;

import com.example.rs.SubscriptionRestService;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;

@Named
public class AppDirectConnector {
	private static final Logger LOG = Logger.getLogger( AppDirectConnector.class.getName() );
	
	public static final int ERROR_SUBSCRIPTION_ORDER = 1000;
	
	private final String oauthConsumerKey = "integrationchallenge-15821";
	private final String oauthConsumerSecret = "LUtZSr5RrIQ2Ln84";
	private final String endpoint = "https://www.appdirect.com/rest/api/";	
	
	public Object get( final String eventUrl ) throws IOException, OAuthException {
		final OAuthConsumer consumer = new DefaultOAuthConsumer( oauthConsumerKey, oauthConsumerSecret );
		
		final URL url = new URL( eventUrl );
		LOG.info( "Requeting URL: " + eventUrl );
		
		final HttpURLConnection request = ( HttpURLConnection )url.openConnection();
		consumer.sign( request );
		request.connect();
		
		try {
			Object response = request.getContent();
			LOG.info( "Response received:" + response );
			return response;
		} finally {
			request.disconnect();
		}
	}
}
