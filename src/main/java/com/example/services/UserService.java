package com.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Named;

import com.example.model.User;

@Named
public class UserService {
    private static final Map< String, User > users = new ConcurrentHashMap<String, User>(); 
    
    public User add( final User user ) {
        users.putIfAbsent( user.getOpenIdUrl(), user );
        return user;
    }
        
    public User find( final String openIdUrl ) {
        return users.get( openIdUrl );
    }
    
    public User remove( final String id ) {
        return users.remove( id );
    }
    
    public Collection< User > getAll() {
        return users.values();
    }
    
    public void removeAllUsers( final String subscriptionId ) {
        final Collection< String > removed = users
           .entrySet()
           .stream()
           .filter( ( entry ) -> entry.getValue().getSubscription().getId().equalsIgnoreCase( subscriptionId ) )
           .map( ( entry ) -> entry.getKey() )
           .collect( Collectors.toCollection( ArrayList::new ) );   
        
        removed.forEach( ( id ) -> users.remove( id ) );        
    }

}
