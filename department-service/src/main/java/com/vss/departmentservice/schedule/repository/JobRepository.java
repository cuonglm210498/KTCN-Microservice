package com.vss.departmentservice.schedule.repository;

import com.vss.departmentservice.schedule.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "SELECT * FROM job WHERE updated_at >= (current_time() - 10)", nativeQuery = true)
    List<Job> findJobByUpdatedAt();

    @Query(value = "SELECT * FROM job WHERE job_name= :jobName", nativeQuery = true)
    Optional<Job> findJobByJobName(String jobName);

}
