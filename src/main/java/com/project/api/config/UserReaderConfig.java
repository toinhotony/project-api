package com.project.api.config;


import com.project.api.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class UserReaderConfig {

    @StepScope
    @Bean
    public FlatFileItemReader<User> userReader(@Value("#{jobParameters['fileUser']}") String path) {
        return new FlatFileItemReaderBuilder<User>()
                .name("userReader")
                .resource(new FileSystemResource(path))
                .delimited()
                .names("name", "cpf")
                .targetType(User.class)
                .build();
    }
}