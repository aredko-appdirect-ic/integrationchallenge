package com.example.rs;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath( "api" )
public class JaxRsApiApplication extends Application {
	@PostConstruct
	public void init() {
	}
}
