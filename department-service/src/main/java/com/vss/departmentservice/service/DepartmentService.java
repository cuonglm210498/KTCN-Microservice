package com.vss.departmentservice.service;

import com.vss.departmentservice.modal.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse findById(Long id);
}
