package com.vss.departmentservice.schedule.controller;

import com.vss.departmentservice.modal.response.BaseResponse;
import com.vss.departmentservice.schedule.entity.Job;
import com.vss.departmentservice.schedule.modal.JobRequest;
import com.vss.departmentservice.schedule.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class TaskSchedulingController {

    @Autowired
    private JobService jobService;

    @PostMapping(path="/taskdef")
    public ResponseEntity<BaseResponse<Void>> scheduleATask(@RequestBody JobRequest jobRequest) {
        jobService.save(jobRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @Scheduled(fixedDelay = 5000)
    private void scheduleATaskJob() {
        List<Job> jobs = jobService.findJobByUpdatedAt();
        jobService.scheduleTask(jobs);
    }
}
