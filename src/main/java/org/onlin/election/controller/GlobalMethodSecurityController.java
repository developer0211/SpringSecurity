package org.onlin.election.controller;

import org.onlin.election.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalMethodSecurityController {
	@Autowired
	MyService myService;

	@PreAuthorize("hasRole('ADMIN')")
    public String getAdminData() {
        return "Sensitive admin data";
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String getUserData() {
        return "User data accessible by both USER and ADMIN";
    }
    @Secured("ROLE_ADMIN")
    public String performAdminTask() {
        return "Admin task executed";
    }
    @GetMapping("postAuthoExample")
    public String postAuth(String userName)
    {
    	System.out.println("Post authorized");
    	return this.myService.postAuthorizeExample(userName);
    }
    @GetMapping("preAuthoExample")
    public String preAuth(String userName)
    {
    	return this.myService.preAuthorizeExample();
    }

}
