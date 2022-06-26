package com.vss.departmentservice.mapper;

import com.vss.departmentservice.entity.Department;
import com.vss.departmentservice.modal.response.DepartmentResponse;
import com.vss.departmentservice.utils.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse to(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        BeanUtils.refine(department, departmentResponse, BeanUtils::copyNonNull);
        return departmentResponse;
    }
}
