package com.vss.departmentservice.schedule.mapper;

import com.vss.departmentservice.schedule.entity.Job;
import com.vss.departmentservice.schedule.modal.JobRequest;
import com.vss.departmentservice.utils.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobMapper {

    public Job to(JobRequest jobRequest) {
        Job job = new Job();
        BeanUtils.refine(jobRequest, job, BeanUtils::copyNonNull);
        return job;
    }
}
