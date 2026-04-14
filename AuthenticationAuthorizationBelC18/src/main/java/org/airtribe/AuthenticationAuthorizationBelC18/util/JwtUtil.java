package org.airtribe.AuthenticationAuthorizationBelC18.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.User;


public class JwtUtil {
  public static String generateJwtToken(User user) {
    return Jwts.builder().subject(user.getUsername())
        .setIssuedAt(new java.util.Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
        .claim("roles", "ROLE_" + user.getRole())
        .claim("emailVerified", user.isEnabled())
        .claim("dummy", "test")
        .signWith(SignatureAlgorithm.HS256, "airtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebToken")
        .compact();

  }

  public static Claims validateAndExtractJwtToken(String jwtToken) {
    Claims claims = Jwts.parser()
        .setSigningKey(
            "airtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebTokenairtribeC18TestingJsonWebToken")
        .build()
        .parseClaimsJws(jwtToken)
        .getPayload();

    return claims;
  }
}
