package com.vss.userservice.modal.request;

import lombok.Data;

@Data
public class UserAuthRequest {

    private String userName;
    private String password;
}
