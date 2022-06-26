package com.vss.userservice.controller.user;

import com.vss.userservice.modal.request.UserAuthRequest;
import com.vss.userservice.modal.request.UserSaveRequest;
import com.vss.userservice.modal.response.BaseResponse;
import com.vss.userservice.modal.response.UserResponse;
import com.vss.userservice.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
      BaseResponse<UserResponse>   userResponseBaseResponse = BaseResponse.ofSuccess(userResponse);
        return ResponseEntity.ok(userResponseBaseResponse);
    }
}
