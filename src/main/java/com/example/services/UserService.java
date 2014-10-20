package com.example.services;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
}
