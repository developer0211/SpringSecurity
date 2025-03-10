package org.onlin.election.repository;

import java.util.Optional;

import org.onlin.election.model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<UserMaster, Integer> {
	Optional<UserMaster> findByUsername(String userName);

	Optional<UserMaster> findByPassword(String password);

	User findByUsernameAndPassword(String username, String password);
}
