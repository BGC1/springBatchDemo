package com.example.batchprocessing.Partitioner.Job;

import com.example.batchprocessing.Partitioner.Listener.PartitionerJobCompletionNotificationListener;
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
public class PartitionerJob {

    @Autowired
    @Qualifier("PartitionerStep")
    private Step partitionerStep;

    @Bean(name = "PartitionerJob")
    public Job PartitionerJob(JobRepository jobRepository, PartitionerJobCompletionNotificationListener listener) {
        return new JobBuilder("PartitionerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(partitionerStep)
                .end()
                .build();
    }
}
