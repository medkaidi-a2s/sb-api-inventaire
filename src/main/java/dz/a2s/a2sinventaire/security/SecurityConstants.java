package dz.a2s.a2sinventaire.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SecurityConstants {

    @Value("${params.security.jwt.exp}")
    private long JWT_EXPIRATION;

    @Value("${params.security.jwt.secret}")
    private String JWT_SECRET;

}
