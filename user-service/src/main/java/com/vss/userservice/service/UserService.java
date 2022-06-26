package com.vss.userservice.service;

import com.vss.userservice.modal.request.UserAuthRequest;
import com.vss.userservice.modal.request.UserSaveRequest;
import com.vss.userservice.modal.response.UserResponse;

import java.util.Optional;

public interface UserService {

    void save(UserSaveRequest userSaveRequest);

    UserResponse findById(Long id);

    UserResponse auth(UserAuthRequest userAuthRequest);

    UserResponse getByUserName(String userName);
}
