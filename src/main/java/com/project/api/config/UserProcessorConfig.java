package com.project.api.config;

import com.project.api.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserProcessorConfig {

    @Bean
    public ItemProcessor<User, User> userProcessor() {
        ValidatingItemProcessor<User> processor = new ValidatingItemProcessor<>();
        processor.setValidator(validatorCpf());
        processor.setFilter(true);

        return processor;
    }

    private Validator<User> validatorCpf() {
        return user -> {
            user.setCpf(user.getCpf().trim());
            if(!user.getCpf().matches("[0-9]*") || user.getCpf().length() != 11)
                throw new ValidationException("Cpf Invalido!");

            int number = Integer.parseInt(user.getCpf().substring(user.getCpf().length() - 1));

            if(number % 2 != 0)
                throw new ValidationException("Cpf não é par!");
        };
    }
}