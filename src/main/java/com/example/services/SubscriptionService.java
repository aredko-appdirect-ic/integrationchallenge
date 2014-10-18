package com.example.services;

import javax.inject.Named;

import com.example.model.Subscription;

@Named
public class SubscriptionService {
	public Subscription addNewSubscription() {
		return new Subscription();
	}
}
