package com.example.batchprocessing.Worker.Job;

import com.example.batchprocessing.Worker.Listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerJob {

    @Autowired
    @Qualifier("WorkerStep")
    private Step workerStep;

    @Bean(name = "WorkerJob")
    public Job WorkerJob(JobRepository jobRepository, JobCompletionNotificationListener listener) {
        return new JobBuilder("WorkerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(workerStep)
                .end()
                .build();
    }
}
