package com.example.rs;

import java.util.logging.Logger;

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
	private static final Logger LOG = Logger.getLogger( SubscriptionRestService.class.getName() );
	
	@Inject private SubscriptionService subscriptionService;
	@Inject private AppDirectConnector connector;
	@Inject private AppDirectEventConsumer consumer;
	
	@Path( "/create" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response create( @QueryParam( "url" ) final String eventUrl ) {
		LOG.info( "Subscription create notification received" );
		
		try {				
			final String eventXml = connector.get( eventUrl );
			
			final Subscription subscription = consumer.consume( eventXml, 
			    AppDirectEventConsumer.newSubscription( () -> subscriptionService.addNew() ) );
			
			return Response.ok( Result
			    .successful( "Subscription created successfuly" )
			    .withAccountIdentifier( subscription.getId() ) 
			).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex ) ).build();
		}		
	}
	
	@Path( "/update" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response update( @QueryParam( "url" ) final String eventUrl ) {
		LOG.info( "Subscription change notification received" );
		
		try {				
			final String eventXml = connector.get( eventUrl );
			
			final Subscription subscription = consumer.consume( eventXml, 
			    AppDirectEventConsumer.updateSubscription( 
			        ( suscriptionId ) -> subscriptionService.find( suscriptionId) ) );
			
			if( subscription == null ) {
				return Response.ok( Result.fail( "Subscription does not exist", "ACCOUNT_NOT_FOUND" ) ).build();
			}
			
			return Response.ok( Result.successful( "Subscription updated successfuly" )	).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex ) ).build();
		}		
	}
	
	@Path( "/cancel" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response delete( @QueryParam( "url" ) final String eventUrl ) {
		LOG.info( "Subscription cancelation notification received" );
		
		try {				
			final String eventXml = connector.get( eventUrl );
			
			final Subscription subscription = consumer.consume( eventXml, 
			    AppDirectEventConsumer.deleteSubscription( 
			        ( suscriptionId ) -> subscriptionService.remove( suscriptionId ) ) );
			
			if( subscription == null ) {
				return Response.ok( Result.fail( "Subscription does not exist", "ACCOUNT_NOT_FOUND" ) ).build();
			}
			
			return Response.ok( Result.successful( "Subscription updated successfuly" )	).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex ) ).build();
		}		
	}
	
	@Produces( { MediaType.APPLICATION_JSON } )
	@GET
	public Response list() {
		return Response.ok( subscriptionService.getAll() ).build();
	}

}
