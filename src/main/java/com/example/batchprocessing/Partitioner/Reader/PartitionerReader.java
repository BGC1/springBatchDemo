package com.example.batchprocessing.Partitioner.Reader;

import com.example.batchprocessing.entity.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PartitionerReader {

    @StepScope
    @Bean(name = "PartitionerReader")
    public FlatFileItemReader<Person> reader(@Value("#{stepExecutionContext['filename']}") String path) {
        return new FlatFileItemReaderBuilder<Person>()
                .name("PersonItemReader")
                .resource(new ClassPathResource(path))
                .delimited()
                .names("firstName", "lastName")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }
}
