package com.vss.userservice.service.impl;

import com.vss.userservice.entity.Role;
import com.vss.userservice.entity.User;
import com.vss.userservice.exception.BusinessException;
import com.vss.userservice.exception.StatusTemplate;
import com.vss.userservice.mapper.UserMapper;
import com.vss.userservice.modal.request.UserAuthRequest;
import com.vss.userservice.modal.request.UserSaveRequest;
import com.vss.userservice.modal.response.UserResponse;
import com.vss.userservice.repository.UserRespository;
import com.vss.userservice.service.UserService;
import com.vss.userservice.utils.AlgorithmSha;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRespository userRespository;

    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = userMapper.to(userSaveRequest);
        userRespository.save(user);
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = userRespository.findById(id);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));


        return userMapper.to(user.get());
    }

    /**
     *
     * @param userAuthRequest
     * @return UserResponse
     */
    @Override
    public UserResponse auth(UserAuthRequest userAuthRequest) {
        String password = AlgorithmSha.hash(userAuthRequest.getPassword());

        Optional<User> user = userRespository.findByUserNameAndPassword(userAuthRequest.getUserName(), password);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));

        UserResponse userResponse = userMapper.to(user.get());
        List<String> roles = user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userResponse.setRoleName(roles);

        return userResponse;
    }

    /**
     *
     * @param userName
     * @return UserResponse
     */
    @Override
    public UserResponse getByUserName(String userName) {
        Optional<User> user = userRespository.findByUserName(userName);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));

        return userMapper.to(user.get());
    }
}
