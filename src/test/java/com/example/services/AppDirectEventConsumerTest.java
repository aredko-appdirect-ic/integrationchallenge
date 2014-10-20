package com.example.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import com.example.model.Subscription;
import com.example.model.User;


public class AppDirectEventConsumerTest {
	private AppDirectEventConsumer consumer;
	
	@Before
	public void setUp() {
		consumer = new AppDirectEventConsumer();
	}
	
	@Test 
	public void testSubcriptionOrderXmlIsParsedProperly() throws Exception {
		final String eventXml = IOUtils.toString( getClass().getResourceAsStream( "/SUBSCRIPTION_ORDER.xml" ), 
			StandardCharsets.UTF_8 );
		
		final Subscription subscription = consumer.consume( eventXml, 
	        AppDirectEventConsumer.newSubscription( () -> new Subscription() ) );
		
		assertThat( subscription.getFirstName(), equalTo( "DummyCreatorFirst" ) );
		assertThat( subscription.getLastName(), equalTo( "DummyCreatorLast" ) );
		assertThat( subscription.getEmail(), equalTo( "test-email+creator@appdirect.com" ) );
	}
	
	   @Test 
	    public void testUserAssigmentXmlIsParsedProperly() throws Exception {
	        final String eventXml = IOUtils.toString( getClass().getResourceAsStream( "/USER_ASSIGNMENT.xml" ), 
	            StandardCharsets.UTF_8 );
	        
	        final User user = consumer.consume( eventXml, 
	            AppDirectEventConsumer.assignUser( ( subscriptionId ) -> {
	                assertThat( subscriptionId, equalTo( "2dcf86aa-a20b-4765-82b6-5aab5f48d41a" ) );
	                return new Subscription();
	            }
	        ) );
	        
	        assertThat( user.getFirstName(), equalTo( "DummyCreatorFirst" ) );
	        assertThat( user.getLastName(), equalTo( "DummyCreatorLast" ) );
	        assertThat( user.getEmail(), equalTo( "test-email+creator@appdirect.com" ) );
	        assertThat( user.getOpenIdUrl(), equalTo( "https://www.appdirect.com/openid/id/8272951b-1da9-4519-99b1-2b2cbd1db3ed" ) );
	    }

}
