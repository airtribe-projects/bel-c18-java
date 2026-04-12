package org.airtribe.AuthenticationAuthorizationBelC18.repository;

import org.airtribe.AuthenticationAuthorizationBelC18.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;


@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);
}
