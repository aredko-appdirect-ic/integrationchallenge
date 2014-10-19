package com.example.services;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import com.example.model.Subscription;


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
}
