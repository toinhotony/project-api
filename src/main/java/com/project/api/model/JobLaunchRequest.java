package com.project.api.model;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.util.Properties;

public class JobLaunchRequest {

    private String name;
    private Properties jobParameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getJobParamsProperties() {
        return jobParameters;
    }

    public void setJobParamsProperties(Properties jobParameters) {
        this.jobParameters = jobParameters;
    }

    public JobParameters getJobParameters() {
        Properties properties = new Properties();
        properties.putAll(jobParameters);
        return new JobParametersBuilder(properties).toJobParameters();
    }
}