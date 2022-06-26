package com.vss.departmentservice.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private StatusResponse statusResponse;

    public BusinessException(StatusResponse statusResponse) {
        this.statusResponse = statusResponse;
    }
}
