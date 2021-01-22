package com.project.api.config;

import com.project.api.model.User;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step StepUserRestService(ItemReader<User> userReader,
                                    ItemProcessor<User, User> userProcessor,
                                    ItemWriter<User> userWriter) {
        return stepBuilderFactory
                .get("StepUserRestService")
                .<User, User>chunk(1)
                .reader(userReader)
                .processor(userProcessor)
                .writer(userWriter)
                .build();
    }
}