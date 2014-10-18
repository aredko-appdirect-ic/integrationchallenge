package com.example.rs;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.model.Result;
import com.example.model.Subscription;
import com.example.services.SubscriptionService;

@Path( "/subscription" )
public class SubscriptionRestService {
	@Inject private SubscriptionService subscriptionService;	
	
	@Path( "/create" )
	@Produces( { MediaType.APPLICATION_XML } )
	@POST
	public Response create( @QueryParam( "url" ) final String eventUrl ) {		
		final Subscription subscription = subscriptionService.addNewSubscription();
		return Response.ok( Result.successful() ).build();
	}
}
