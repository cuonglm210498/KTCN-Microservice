package com.vss.departmentservice.schedule.service;

import com.vss.departmentservice.schedule.entity.Job;
import com.vss.departmentservice.schedule.modal.JobRequest;

import java.util.List;

public interface JobService {

    void save(JobRequest jobRequest);

    void scheduleTask(List<Job> jobs);

    List<Job> findJobByUpdatedAt();
}
