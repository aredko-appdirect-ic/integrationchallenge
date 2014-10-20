package com.example.services;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import com.example.model.Subscription;

@Named
public class SubscriptionService {
	private static final Map< String, Subscription > subscriptions = 
		new ConcurrentHashMap<String, Subscription>(); 
	
	public Subscription add() {
		final Subscription subscription =  new Subscription( UUID.randomUUID().toString() );
		subscriptions.putIfAbsent( subscription.getId(), subscription );
		return subscription;
	}
	
	public Subscription find( final String id ) {
		return subscriptions.get( id );
	}
	
	public Subscription remove( final String id ) {
		return subscriptions.remove( id );
	}
	
	public Collection< Subscription > getAll() {
		return subscriptions.values();
	}
}
