package com.example.batchprocessing.Partitioner.Step;

import com.example.batchprocessing.Partitioner.Partitioner.CustomerPartitioner;
import com.example.batchprocessing.Partitioner.Processor.PartitionerPersonItemProcessor;
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
public class PartitionerStep {

    @Autowired
    @Qualifier("PartitionerReader")
    private FlatFileItemReader<Person> reader;

    @Autowired
    @Qualifier("PartitionerWriter")
    private JdbcBatchItemWriter<Person> writer;

    @Bean(name = "PartitionerStep")
    public Step PartitionerStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("PartitionerStep", jobRepository)
                .partitioner("PartitionerStep", new CustomerPartitioner())
                .gridSize(4)
                .step(new StepBuilder("step", jobRepository)
                        .<Person, Person>chunk(10, transactionManager)
                        .reader(reader)
                        .processor(new PartitionerPersonItemProcessor())
                        .writer(writer)
                        .build())
                .build();
    }

}
