package com.example.batchprocessing.Partitioner.Partitioner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerPartitioner implements Partitioner {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    List<String> fileNameList = Arrays.asList("sample-data1.csv", "sample-data1.csv",
            "sample-data1.csv", "sample-data1.csv");

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitionData = new HashMap<>(gridSize);
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext executionContext = new ExecutionContext();
            logger.info("Starting : Thread" + i);
            logger.info("File Name: " + fileNameList.get(i));

            // give fileName for ExecutionContext
            executionContext.putString("filename", fileNameList.get(i));
            // give a thread name for ExecutionContext
            executionContext.putString("name", "Thread" + i);

            partitionData.put("partition: " + i, executionContext);
        }
        return partitionData;
    }

}
