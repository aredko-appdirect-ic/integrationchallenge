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
import com.example.model.User;
import com.example.services.AppDirectConnector;
import com.example.services.AppDirectEventConsumer;
import com.example.services.SubscriptionService;
import com.example.services.UserService;

@Path( "/user" )
public class UserRestService {
	private static final Logger LOG = Logger.getLogger( UserRestService.class.getName() );
	
	@Inject private SubscriptionService subscriptionService;
	@Inject private UserService userService;
	@Inject private AppDirectConnector connector;
	@Inject private AppDirectEventConsumer consumer;
	
	@Path( "/assign" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response assign( @QueryParam( "url" ) final String eventUrl ) {
		LOG.info( "User assignment notification received" );
		
		try {				
			final String eventXml = connector.get( eventUrl );
			
            final User user = consumer.consume( eventXml, 
                AppDirectEventConsumer.assignUser( 
                    ( subscriptionId ) -> subscriptionService.find( subscriptionId ) ) );
			
            if( user == null ) {
                return Response.ok( Result.fail( "User has no subscriptions", "ACCOUNT_NOT_FOUND" ) ).build();
            }
            
            userService.add( user );
            return Response.ok( Result.successful( "User assigned successfully" ) ).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex ) ).build();
		}		
	}
	
	@Path( "/unassign" )
	@Produces( { MediaType.APPLICATION_XML } )
	@GET
	public Response unassign( @QueryParam( "url" ) final String eventUrl ) {
		LOG.info( "User unassignment notification received" );
		
		try {				
			final String eventXml = connector.get( eventUrl );
			
			final User user = consumer.consume( eventXml, 
			    AppDirectEventConsumer.unassignUser( 
			        ( accountId ) -> userService.find( accountId ) ) );
			
			if( user == null ) {
				return Response.ok( Result.fail( "User has no subscriptions", "ACCOUNT_NOT_FOUND" ) ).build();
			}
			
			userService.remove( user.getOpenIdUrl() );
			return Response.ok( Result.successful( "User unassigned successfully" )	).build();
		} catch( final Exception ex ) {
			return Response.ok( Result.fail( ex ) ).build();
		}		
	}
	
	@Produces( { MediaType.APPLICATION_JSON } )
	@GET
	public Response list() {
		return Response.ok( userService.getAll() ).build();
	}

}
