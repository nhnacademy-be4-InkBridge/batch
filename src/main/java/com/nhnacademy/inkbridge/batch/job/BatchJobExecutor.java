package com.nhnacademy.inkbridge.batch.job;

/**
 * class: BatchJobExecutor.
 *
 * @author JBum
 * @version 2024/03/06
 */
import com.nhnacademy.inkbridge.batch.config.BatchConfig;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobExecutor implements Job {

    public BatchJobExecutor(BatchConfig batchConfig) {
        this.batchConfig = batchConfig;
    }

    private final BatchConfig batchConfig;

    @Override
    public void execute(JobExecutionContext context) {
        // Spring Batch 작업 실행
        batchConfig.couponExpiration();
    }
}