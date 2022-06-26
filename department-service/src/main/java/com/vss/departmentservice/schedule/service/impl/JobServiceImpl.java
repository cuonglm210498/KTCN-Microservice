package com.vss.departmentservice.schedule.service.impl;

import com.vss.departmentservice.exception.BusinessException;
import com.vss.departmentservice.exception.StatusTemplate;
import com.vss.departmentservice.schedule.LockRunable;
import com.vss.departmentservice.schedule.entity.Job;
import com.vss.departmentservice.schedule.mapper.JobMapper;
import com.vss.departmentservice.schedule.modal.JobRequest;
import com.vss.departmentservice.schedule.repository.JobRepository;
import com.vss.departmentservice.schedule.service.JobService;
import net.javacrumbs.shedlock.core.LockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private LockProvider lockProvider;

    private Map<String, ScheduledFuture> jobsMap = new HashMap<>();

    public JobServiceImpl(TaskScheduler taskScheduler, LockProvider lockProvider) {
        this.taskScheduler = taskScheduler;
        this.lockProvider = lockProvider;
    }

    @Override
    public void save(JobRequest jobRequest) {

        Optional<Job> jobFindByName = jobRepository.findJobByJobName(jobRequest.getJobName());

        if (!jobFindByName.isPresent()) {
            Job job = jobMapper.to(jobRequest);
            job.setCreatedAt(LocalDateTime.now());
            job.setUpdatedAt(LocalDateTime.now());
            jobRepository.save(job);
        } else {
            jobRepository.findJobByJobName(jobRequest.getJobName())
                    .map(jobUpdate -> {
                                jobUpdate.setCronExpression(jobRequest.getCronExpression());
                                jobUpdate.setUpdatedAt(LocalDateTime.now());
                                return jobRepository.save(jobUpdate);
                            }
                    ).orElseThrow(() -> new BusinessException(StatusTemplate.JOB_NOT_FOUND));
        }
    }

    @Override
    public List<Job> findJobByUpdatedAt() {
        return jobRepository.findJobByUpdatedAt();
    }

    @Override
    public void scheduleTask(List<Job> jobs) {
        jobs.forEach(job -> {
                    jobsMap.put(
                            job.getJobName(),
                            taskScheduler.schedule(
                                    wrap(() -> {
                                        System.out.println("Save job successfully" + job.getCronExpression());
                                    }, job.getJobName()),
                                    new CronTrigger(job.getCronExpression()))
                    );
                }
        );
    }

    public Runnable wrap(Runnable runnable, String lockName) {
        return new LockRunable(runnable, lockName, lockProvider);
    }

}
