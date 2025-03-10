package org.onlin.election.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.onlin.election.model.UserMaster;
import org.onlin.election.repository.UserRepository;
import org.onlin.election.service.JwtRequest;
import org.onlin.election.service.JwtResponse;
import org.onlin.election.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	

	@PostMapping("/authenticate")
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (AuthenticationException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@PostMapping("/saveUser")
	public UserMaster saveUser(@RequestBody UserMaster userMaster) {
		UserMaster user = null;
		String pass = bCryptPasswordEncoder.encode(userMaster.getPassword());
		userMaster.setPassword(pass);
		Optional<UserMaster> list = userRepository.findByUsername(userMaster.getUsername());
		if (list.isPresent()) {
			System.out.println("");
		} else {
			user = userRepository.save(userMaster);
		}
		List<UserMaster> listUser = userRepository.findAll();
		System.out.println(listUser.size());
		return user;
	}

	@GetMapping("/getAllUser")
	public List<UserMaster> getAllUser() {
		System.out.println();
		List<UserMaster> list = userRepository.findAll();
		return list;

	}

	@GetMapping("/getUserById")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserMaster> getUserById(@RequestParam int id) {
		System.out.println();
		List<UserMaster> list = userRepository.findAll();
		return list;
	}
}
