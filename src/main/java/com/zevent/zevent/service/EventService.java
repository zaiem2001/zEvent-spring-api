package com.zevent.zevent.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zevent.zevent.exceptions.CustomException;
import com.zevent.zevent.model.Event;
import com.zevent.zevent.model.GuestList;
import com.zevent.zevent.model.User;
import com.zevent.zevent.model.enums.StatusEnum;
import com.zevent.zevent.model.interfaces.EnterEventReqBody;
import com.zevent.zevent.model.interfaces.EventReqBody;
import com.zevent.zevent.model.interfaces.EventResponse;
import com.zevent.zevent.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LoggedInUserDetails loggedInUserDetails;

    @Autowired
    private UserService userService;

    @Autowired
    private GuestListService guestListService;

    public Event createEvent(EventReqBody eventBody) {
        User userDetails = loggedInUserDetails.getUserDetails();
        Event event = getEvent(eventBody, userDetails);

        return eventRepository.insert(event);
    }

    public List<EventResponse> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventResponse::new).collect(Collectors.toList());

    }

    public List<Event> getUsersEvents() {
        return eventRepository.findByUser(this.loggedInUserDetails.getUserDetails().get_id());
    }

    public GuestList enterTheEvent(ObjectId eventId, EnterEventReqBody enterEventReqBody) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new CustomException("Event not found"));
        StatusEnum joineeStatus = enterEventReqBody.getStatus();

        if (joineeStatus == StatusEnum.APPROVED || joineeStatus == StatusEnum.REJECTED) {
            throw new CustomException("You cannot join this event.");
        }

        User currentUser = loggedInUserDetails.getUserDetails();
        User joinee = null;
        GuestList guestList = null;

        if (event.getGuestList().size() >= event.getMaxCapacity()) {
            throw new CustomException("Event is full");
        }

        if (joineeStatus == StatusEnum.INVITED) {
            joinee = userService.findById(enterEventReqBody.getUserId());
        } else {
            joinee = currentUser;
        }

        GuestList isAlreadyInEvent = guestListService.checkIfUserIsAlreadyInEvent(joinee.get_id(), eventId);

        if (canNotJoinEvent(isAlreadyInEvent, joinee, event)) {
            throw new CustomException("You cannot join this event.");
        }

        guestList = getGuestList(event, joinee, joineeStatus);
        GuestList savedGuestList = guestListService.addGuestToEvent(guestList);

        return savedGuestList;
    }

    public GuestList approveOrRejectEventRequest(ObjectId eventId, EnterEventReqBody enterEventReqBody) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new CustomException("Event not found"));
        StatusEnum joineeStatus = enterEventReqBody.getStatus();
        ObjectId userId = enterEventReqBody.getUserId();
        User currentUser = loggedInUserDetails.getUserDetails();

        if (joineeStatus == StatusEnum.INVITED || joineeStatus == StatusEnum.APPLIED) {
            throw new CustomException("Something went wrong!");
        }

        if (event.getGuestList().size() >= event.getMaxCapacity()) {
            throw new CustomException("Event is full");
        }

        GuestList isUserPresent = guestListService.checkIfUserIsAlreadyInEvent(userId, eventId);

        if (isUserPresent == null) {
            throw new CustomException("Cannot update status of user who is not in the event");
        }

        System.out.println(currentUser.get_id() != event.getUser().get_id());

        if (joineeStatus == StatusEnum.APPROVED || joineeStatus == StatusEnum.REJECTED) {
            if (!currentUser.get_id().equals(event.getUser().get_id())
                    || isUserPresent.getStatus() != StatusEnum.APPLIED) {
                throw new CustomException("Error modifying the Request!");
            }

            return guestListService.updateGuestStatus(isUserPresent, joineeStatus);
        }

        if (currentUser.get_id() != userId || isUserPresent.getStatus() != StatusEnum.INVITED) {
            throw new CustomException("You cannot modify this request");
        }

        return guestListService.updateGuestStatus(isUserPresent, joineeStatus);
    }

    private boolean canNotJoinEvent(GuestList gList, User joinee, Event event) {
        return gList != null
                || event.getUser().get_id().equals(joinee.get_id());
    }

    private GuestList getGuestList(Event event, User user, StatusEnum status) {
        return GuestList.builder().event(event).user(user)
                .status(status).build();
    }

    private Event getEvent(EventReqBody eventBody, User userDetails) {
        return Event.builder()
                .name(eventBody.getName())
                .image(eventBody.getImage())
                .duration(eventBody.getDuration())
                .category(eventBody.getCategory().toUpperCase())
                .language(eventBody.getLanguage().toUpperCase())
                .maxCapacity(eventBody.getMaxCapacity())
                .medium(eventBody.getMedium())
                .privacy(eventBody.getPrivacy())
                .description(eventBody.getDescription())
                .date(eventBody.getDate())
                .location(eventBody.getLocation())
                .user(userDetails)
                .build();
    }
}
