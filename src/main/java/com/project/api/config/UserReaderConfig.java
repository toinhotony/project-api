package com.project.api.config;


import com.project.api.model.User;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class UserReaderConfig {

    @StepScope
    @Bean
    public FlatFileItemReader<User> userReader(@Value("#{jobParameters['file.input']}") String input){
        return new FlatFileItemReaderBuilder<User>()
                .name("userReader")
                .resource(new FileSystemResource(input))
                .delimited()
                .names("name", "cpf")
                .targetType(User.class)
                .build();
    }
}