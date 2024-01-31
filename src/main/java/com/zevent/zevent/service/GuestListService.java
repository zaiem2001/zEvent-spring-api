package com.zevent.zevent.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.zevent.zevent.model.GuestList;
import com.zevent.zevent.model.enums.StatusEnum;
import com.zevent.zevent.repository.GuestListRepository;

@Service
public class GuestListService {

    @Autowired
    private GuestListRepository guestListRepository;

    @Autowired
    private LoggedInUserDetails loggedInUserDetails;

    @Autowired
    MongoTemplate mongoTemplate;

    public GuestList addGuestToEvent(GuestList guestList) {
        return guestListRepository.insert(guestList);
    }

    public GuestList checkIfUserIsAlreadyInEvent(ObjectId userId, ObjectId eventId) {
        return guestListRepository.findOneByUserAndEvent(userId, eventId);
    }

    public GuestList updateGuestStatus(GuestList guestList, StatusEnum joineeStatus) {
        Query query = new Query(Criteria.where("_id").is(guestList.getId()));
        Update update = new Update().set("status", joineeStatus);
        mongoTemplate.updateFirst(query, update, GuestList.class);

        return guestListRepository.findById(guestList.getId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));
    }

    public List<GuestList> findByUserAndStatus(Optional<StatusEnum> status) {
        ObjectId userId = loggedInUserDetails.getUserDetails().get_id();

        if (status.isPresent()) {
            return guestListRepository.findByUserAndStatus(userId, status.get());
        }
        return guestListRepository.findByUser(userId);
    }
}
