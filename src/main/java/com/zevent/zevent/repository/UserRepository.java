package com.zevent.zevent.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zevent.zevent.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

}
