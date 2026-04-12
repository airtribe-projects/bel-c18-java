package org.airtribe.AuthenticationAuthorizationBelC18.repository;

import org.airtribe.AuthenticationAuthorizationBelC18.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
