package org.art.tetragallery.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.art.tetragallery.model.entity.Role;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private static final String SECRET = "super-secret-key";
    private static final Algorithm ALGO = Algorithm.HMAC256(SECRET);
    private static final String ISSUER = "tetra-gallery";

    public String generateToken(Long userId, Role role, String email) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(String.valueOf(userId))
                .withClaim("role", role.name())
                .withClaim("email", email)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(2, ChronoUnit.HOURS))
                .sign(ALGO);
    }

    public DecodedJWT verify(String token) {
        return JWT.require(ALGO)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }
}
