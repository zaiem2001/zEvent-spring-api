package com.zevent.zevent.model.interfaces;

import com.zevent.zevent.model.Event;
import com.zevent.zevent.model.enums.MediumEnum;
import com.zevent.zevent.model.enums.PrivacyEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private String id;
    private String name;
    private String description;
    private String image;
    private String duration;
    private String language;
    private String category;
    private String location;

    private int maxCapacity;
    private MediumEnum medium;

    private UserDto user;

    private PrivacyEnum privacy;

    private IDate date;

    public EventResponse(Event event) {
        this.id = event.getId().toHexString();
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.duration = event.getDuration();
        this.language = event.getLanguage();
        this.category = event.getCategory();
        this.location = event.getLocation();
        this.maxCapacity = event.getMaxCapacity();
        this.medium = event.getMedium();
        this.privacy = event.getPrivacy();
        this.date = event.getDate();
        this.user = new UserDto(event.getUser().get_id().toHexString(), event.getUser().getUsername());
    }
}
