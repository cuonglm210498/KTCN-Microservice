package com.vss.userservice.controller.user;

import com.vss.userservice.modal.request.UserSaveRequest;
import com.vss.userservice.modal.response.BaseResponse;
import com.vss.userservice.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/users/info/register")
public class UserRegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> save(@RequestBody UserSaveRequest userSaveRequest) {
        userService.save(userSaveRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }
}
