package com.zevent.zevent.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zevent.zevent.model.Event;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, ObjectId> {
    List<Event> findByUser(ObjectId userId);
}
