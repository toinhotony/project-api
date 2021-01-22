package com.project.api.config;

import com.project.api.model.User;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class UserWriterConfig {

    @Bean
    public JdbcBatchItemWriter<User> userWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .sql("INSERT INTO user (name, cpf) VALUES (?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<User> itemPreparedStatementSetter() {
        return (user, preparedStatement) -> {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getCpf());
        };
    }
}