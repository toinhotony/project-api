package com.project.api.config;

import com.project.api.model.User;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class UserWriterConfig {

    @Autowired
    private MongoOperations mongoOperations;

    @Bean
    public MongoItemWriter<User> userWriter() {
        return new MongoItemWriterBuilder<User>()
                .template(mongoOperations)
                .collection("user")
                .delete(false)
                .build();
    }
}