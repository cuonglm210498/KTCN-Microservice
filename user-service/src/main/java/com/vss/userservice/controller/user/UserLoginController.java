package com.vss.userservice.controller.user;

import com.vss.userservice.modal.request.UserAuthRequest;
import com.vss.userservice.modal.response.BaseResponse;
import com.vss.userservice.modal.response.UserResponse;
import com.vss.userservice.security.jwt.TokenProducer;
import com.vss.userservice.security.jwt.model.JwtPayLoad;
import com.vss.userservice.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/users/info/login")
public class UserLoginController {

    private final UserService userService;
    private final TokenProducer tokenProducer;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> login(@RequestBody UserAuthRequest userAuthRequest) {
        UserResponse userResponse = userService.auth(userAuthRequest);
        JwtPayLoad jwtPayload = createPayload(userResponse);
        String token = tokenProducer.token(jwtPayload);
        return ResponseEntity.ok(BaseResponse.ofSuccess(token));
    }

    private JwtPayLoad createPayload(UserResponse userResponse){
        JwtPayLoad jwtPayload = new JwtPayLoad();
        jwtPayload.setUserName(userResponse.getUserName());
        jwtPayload.setId(userResponse.getId());
        //String role = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        String role = String.join(",", userResponse.getRoleName());
        jwtPayload.setRole(role);

        return jwtPayload;
    }
}
