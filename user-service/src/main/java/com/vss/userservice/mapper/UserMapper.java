package com.vss.userservice.mapper;

import com.vss.userservice.entity.Role;
import com.vss.userservice.entity.User;
import com.vss.userservice.modal.request.UserSaveRequest;
import com.vss.userservice.modal.response.UserResponse;
import com.vss.userservice.repository.RoleRepository;
import com.vss.userservice.utils.AlgorithmSha;
import com.vss.userservice.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    public User to(UserSaveRequest userSaveRequest) {
        User user = new User();
        BeanUtils.refine(userSaveRequest, user, BeanUtils::copyNonNull);
        user.setPassword(AlgorithmSha.hash(userSaveRequest.getPassword()));

        Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(userSaveRequest.getIds()));
        user.setRoles(roles);

        return user;
    }

    public UserResponse to(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.refine(user, userResponse, BeanUtils::copyNonNull);
        return userResponse;
    }
}
