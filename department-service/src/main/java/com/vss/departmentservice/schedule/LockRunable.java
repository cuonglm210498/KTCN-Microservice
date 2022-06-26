package com.vss.departmentservice.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

@Data
@NoArgsConstructor
public class LockRunable implements Runnable {

    @Autowired
    private Runnable runnable;

    @Autowired
    private LockProvider lockProvider;

    private String name;

    public LockRunable(Runnable runnable, String name, LockProvider lockProvider) {
        this.runnable = runnable;
        this.lockProvider = lockProvider;
        this.name = name;
    }

    @Override
    public void run() {
        LockingTaskExecutor executor = new DefaultLockingTaskExecutor(lockProvider);
        executor.executeWithLock(runnable, new LockConfiguration(Instant.now(), name, Duration.ofSeconds(60), Duration.ofMillis(1000)));
    }
}
