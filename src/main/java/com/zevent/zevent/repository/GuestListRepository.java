package com.zevent.zevent.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zevent.zevent.model.GuestList;

@Repository
public interface GuestListRepository extends MongoRepository<GuestList, ObjectId> {
    GuestList findOneByUserAndEvent(ObjectId userId, ObjectId eventId);

}
