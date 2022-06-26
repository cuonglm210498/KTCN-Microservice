package com.vss.departmentservice.controller;

import com.vss.departmentservice.modal.response.BaseResponse;
import com.vss.departmentservice.modal.response.DepartmentResponse;
import com.vss.departmentservice.service.DepartmentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DepartmentResponse>> findById(@PathVariable Long id) {

        DepartmentResponse departmentResponse = departmentService.findById(id);
        return ResponseEntity.ok(BaseResponse.ofSuccess(departmentResponse));
    }
}
