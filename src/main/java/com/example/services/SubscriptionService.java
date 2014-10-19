package com.example.services;

import java.util.UUID;

import javax.inject.Named;

import com.example.model.Subscription;

@Named
public class SubscriptionService {
	public Subscription addNewSubscription() {
		return new Subscription( UUID.randomUUID().toString() );
	}
}
