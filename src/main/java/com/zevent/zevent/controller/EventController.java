package com.zevent.zevent.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zevent.zevent.model.Event;
import com.zevent.zevent.model.GuestList;
import com.zevent.zevent.model.interfaces.EnterEventReqBody;
import com.zevent.zevent.model.interfaces.EventReqBody;
import com.zevent.zevent.model.interfaces.EventResponse;
import com.zevent.zevent.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody EventReqBody event) {
        return new ResponseEntity<>(this.eventService.createEvent(event), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<EventResponse>> getUsersEvents() {
        return new ResponseEntity<>(eventService.getUsersEvents(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getSingleEvent(@PathVariable ObjectId eventId) {
        return new ResponseEntity<>(this.eventService.findById(eventId), HttpStatus.OK);
    }

    @PostMapping("/join/{eventId}")
    public ResponseEntity<GuestList> EnterTheEvent(@PathVariable ObjectId eventId,
            @RequestBody EnterEventReqBody enterEventReqBody) {

        return new ResponseEntity<>(eventService.enterTheEvent(eventId, enterEventReqBody), HttpStatus.OK);
    }

    @PostMapping("/update-status/{eventId}")
    public ResponseEntity<GuestList> updateGuestStatus(@PathVariable ObjectId eventId,
            @RequestBody EnterEventReqBody enterEventReqBody) {
        return new ResponseEntity<>(eventService.approveOrRejectEventRequest(eventId, enterEventReqBody),
                HttpStatus.OK);
    }

}
