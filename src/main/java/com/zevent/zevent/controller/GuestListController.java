package com.zevent.zevent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zevent.zevent.model.GuestList;
import com.zevent.zevent.model.enums.StatusEnum;
import com.zevent.zevent.service.GuestListService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/guests")
public class GuestListController {

    @Autowired
    private GuestListService guestListService;

    @GetMapping("")
    public ResponseEntity<List<GuestList>> getUsersAttendeeList(@RequestParam Optional<StatusEnum> status) {
        return new ResponseEntity<>(guestListService.findByUserAndStatus(status), HttpStatus.OK);
    }

}
