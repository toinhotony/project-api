package com.project.api.repository;

import com.project.api.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("appDataSource")
public interface UserRepository extends MongoRepository<User, String> {
}