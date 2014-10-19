package com.example.services;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import com.example.model.Subscription;

@Named
public class SubscriptionService {
	private Map< String, Subscription > subscriptions = 
			new ConcurrentHashMap<String, Subscription>(); 
	
	public Subscription addNewSubscription() {
		final Subscription subscription =  new Subscription( UUID.randomUUID().toString() );
		subscriptions.putIfAbsent( subscription.getId(), subscription );
		return subscription;
	}
	
	public Collection< Subscription > getSubscriptions() {
		return subscriptions.values();
	}
}
