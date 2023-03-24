package com.example.batchprocessing.Controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("PartitionerJob")
    private Job partitionerJob;

    @Autowired
    @Qualifier("WorkerJob")
    private Job workerJob;

    @RequestMapping("/runPartitionerJob")
    public String runPartitionerJob() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(partitionerJob, jobParameters);
        return "Batch job has been invoked";
    }

    @RequestMapping("/runWorkerJob")
    public String handle() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(workerJob, jobParameters);
        return "Batch job has been invoked";
    }
}
