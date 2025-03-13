package com.example.demo.services;

import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Request.IntrospectRequest;
import com.example.demo.DTO.Response.AuthenticationResponse;
import com.example.demo.DTO.Response.IntrospectResponse;
import com.example.demo.config.ENVConfig;
import com.example.demo.exceptions.APPException;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    UserRepository userRepository;

    @NonFinal
    protected static final String SIGN_KEY = ENVConfig.getEnv("JWT_SECRET");

    //Phương thức xác định tính hơợp lệ của JWT
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        //Cái này là kiểm tra chữ kí của JWT
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        //Cái này là phân tích (parse) token từ chuỗi JWT (token) để lấy thông tin bên trong
        SignedJWT signedJWT = SignedJWT.parse(token);

        //Lấy thười gia hết hạn của token từ claim
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        //Kiểm tra xem chữ kí của token có hợp lệ không
        var verified = signedJWT.verify(verifier);
        signedJWT.verify(verifier);

        // Trả về kết quả kiểm tra token
        return IntrospectResponse.builder()
                .valid(verified  && expiryTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUserName(authenticationRequest.getUserName())
                .orElseThrow(()-> new APPException(ErrorCode.USER_NOT_EXISTS));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest
                .getPassword(), user.getPassword());

        if(!authenticated)
            throw new APPException(ErrorCode.PASSWORD_IS_INCORRECT);

        var token = generateToken(authenticationRequest.getUserName());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    public String generateToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet  = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("Men_T-shirt")
                .issueTime(new Date())
                .claim("CustomClaim", "Custom")
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot sign JWT", e);
            throw new RuntimeException(e);
        }
    }
}
