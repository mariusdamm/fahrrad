package org.mariusdamm.fahrrad.service;

import org.mariusdamm.fahrrad.dao.AppUserRepository;
import org.mariusdamm.fahrrad.entity.AppUser;
import org.mariusdamm.fahrrad.exception.DataNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final AppUserRepository appUserRepository;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, AppUserRepository appUserRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.appUserRepository = appUserRepository;
    }

    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();
        Instant expired = now.plus(30, ChronoUnit.MINUTES);

        String scope = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).subject(auth.getName())
                .claim("roles", scope).expiresAt(expired).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getUsernameFromToken(String token) {
        try {
            token = token.substring(token.indexOf(" ") + 1);
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject();
        } catch (JwtException e) {
            return "";
        }
    }

    public AppUser getUserFromToken(String token) throws DataNotFoundException {
        String username = getUsernameFromToken(token);
        return appUserRepository.findByUsername(username).orElseThrow(() ->
                new DataNotFoundException("There is no user with username " + username + " in the database")
        );
    }

    public String getRolesFromToken(String token) {
        try {
            token = token.substring(token.indexOf(" ") + 1);
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getClaim("roles");
        } catch (JwtException e) {
            return null;
        }
    }
}
