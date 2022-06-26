package com.vss.userservice.controller;

import com.vss.userservice.modal.response.BaseResponse;
import com.vss.userservice.security.TokenAuthenticator;
import lombok.Data;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/users")
public class VerifyTokenController {

    @Autowired
    private final TokenAuthenticator tokenAuthenticator;

    @PostMapping("/token/verify")
    public ResponseEntity<BaseResponse<?>> verifyToken(@RequestParam("token") String token) throws InvalidJwtException {
        var userInfo = tokenAuthenticator.getAuthentication(token);
        return ResponseEntity.ok(BaseResponse.ofSuccess(userInfo));
    }
}
