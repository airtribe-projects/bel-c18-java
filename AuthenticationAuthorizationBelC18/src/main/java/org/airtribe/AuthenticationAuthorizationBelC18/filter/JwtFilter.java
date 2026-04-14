package org.airtribe.AuthenticationAuthorizationBelC18.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import jakarta.annotation.Priority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.airtribe.AuthenticationAuthorizationBelC18.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String jwtToken = request.getHeader("Authorization");

    if (jwtToken == null || jwtToken.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Missing Authorization header");
      return;
    }

    // You decode the token and verify the signature, and if the token is valid -> claims object
    Claims claims;
    try {
      claims = JwtUtil.validateAndExtractJwtToken(jwtToken);
      String role = claims.get("roles", String.class);
      List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (ExpiredJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT token is expired: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.SignatureException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid JWT signature: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.MalformedJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid JWT token: " + exception.getMessage());
      return;
    } catch (io.jsonwebtoken.UnsupportedJwtException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT token is unsupported: " + exception.getMessage());
      return;
    } catch (IllegalArgumentException exception) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("JWT claims string is empty: " + exception.getMessage());
      return;
    }

    filterChain.doFilter(request, response);

  }

  public boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return path.equals("/signin") || path.equals("/register") || path.equals("/verifyRegistrationToken");
  }
}
