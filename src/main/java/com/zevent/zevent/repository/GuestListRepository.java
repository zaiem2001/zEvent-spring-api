package com.zevent.zevent.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zevent.zevent.model.GuestList;
import com.zevent.zevent.model.enums.StatusEnum;

@Repository
public interface GuestListRepository extends MongoRepository<GuestList, ObjectId> {
    GuestList findOneByUserAndEvent(ObjectId userId, ObjectId eventId);

    List<GuestList> findByUserAndStatus(ObjectId userId, StatusEnum status);

    List<GuestList> findByUser(ObjectId userId);
}
