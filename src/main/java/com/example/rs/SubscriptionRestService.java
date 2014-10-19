package com.example.rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.model.Result;
import com.example.model.Subscription;
import com.example.services.AppDirectConnector;
import com.example.services.AppDirectEventConsumer;
import com.example.services.SubscriptionService;

@Path( "/subscription" )
public class SubscriptionRestService {
	@Inject private SubscriptionService subscriptionService;
	@Inject private AppDirectConnector connector;
	@Inject private AppDirectEventConsumer consumer;
	
	@Path( "/create" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response create( @QueryParam( "url" ) final String eventUrl ) {
		try {				
			final String eventXml = connector.get( eventUrl );
			
			final Subscription subscription = consumer.consume( eventXml, 
			    AppDirectEventConsumer.newSubscription( () -> subscriptionService.addNewSubscription() ) );
			
			return Response.ok( Result
			    .successful( "Subscription created successfuly" )
			    .withAccountIdentifier( subscription.getId() ) 
			).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex, AppDirectConnector.ERROR_SUBSCRIPTION_ORDER ) ).build();
		}		
	}
}
