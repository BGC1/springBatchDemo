package com.example.batchprocessing.Worker.Step;

import com.example.batchprocessing.Worker.Processor.WorkerProcessor;
import com.example.batchprocessing.entity.Person;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class WorkerStep {

    @Autowired
    @Qualifier("WorkerReader")
    private FlatFileItemReader<Person> reader;

    @Autowired
    @Qualifier("WorkerWriter")
    private JdbcBatchItemWriter<Person> writer;

    @Bean(name = "WorkerStep")
    public Step WorkerStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader)
                .processor(new WorkerProcessor())
                .writer(writer)
                .build();
    }

}
