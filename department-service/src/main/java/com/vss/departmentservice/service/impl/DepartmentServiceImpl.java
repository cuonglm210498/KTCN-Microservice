package com.vss.departmentservice.service.impl;

import com.vss.departmentservice.entity.Department;
import com.vss.departmentservice.exception.BusinessException;
import com.vss.departmentservice.exception.StatusTemplate;
import com.vss.departmentservice.mapper.DepartmentMapper;
import com.vss.departmentservice.modal.response.DepartmentResponse;
import com.vss.departmentservice.repository.DepartmentRepository;
import com.vss.departmentservice.service.DepartmentService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse findById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        department.orElseThrow(() -> new BusinessException(StatusTemplate.DEPARTMENT_NOT_FOUND));

        DepartmentResponse departmentResponse = departmentMapper.to(department.get());
        return departmentResponse;
    }
}
