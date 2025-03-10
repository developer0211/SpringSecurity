package org.onlin.election.service;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	    @PreAuthorize("hasRole('ROLE_USER')")
	    public String preAuthorizeExample() {
	        return "PreAuthorize - Access granted!";
	    }

	    @PostAuthorize("returnObject == authentication.name")
	    public String postAuthorizeExample(String username) {
	        return username;
	    }

	    @Secured("ROLE_ADMIN")
	    public String securedExample() {
	        return "Secured - Access granted!";
	    }

	    @RolesAllowed("ROLE_USER")
	    public String rolesAllowedExample() {
	        return "RolesAllowed - Access granted!";
	    }
}