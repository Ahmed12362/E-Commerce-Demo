package com.example.Demo_E_Commerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static String SECRET_KEY = "81bcd14dae67313eee7d7d41c1c18aa83720724bee8b57fff32acd0b73" +
            "5e4ee8dc73a9fa941b80864673551ad0df0d7f77a4444dcca44a231015b94b798853272" +
            "b6d910848853b3b26a558c453ae30302a2f49b6e00b9d12514bd3301ebdcb907ff6605801bdc39d623dd198fce8e2e69dd319cd5eb9f28da51b0be52f7e61b703db8a932a31d953b663b63b7a1d23321bee3983f4a4b9d911f3e6cc427e15485293fe4f58967388fa723617df2048a7c6518dc4ad0f24b0669ff1e23dad1b2aa3da22c81b14c04905afd28d16c369355e40649bcf4dd53a2fd7bbddf9a4a5f55cf32d1f6a8e3a5038b4f90f15580fb6236a8862cf056d1c2f93ed1583397b96";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    // Extract the role from the token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // Get the authority string (e.g., "ROLE_USER")
                .collect(Collectors.joining(", "));
        extraClaims.put("role", role);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
