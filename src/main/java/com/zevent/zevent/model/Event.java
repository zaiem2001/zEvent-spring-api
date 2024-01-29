package com.zevent.zevent.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.zevent.zevent.model.enums.MediumEnum;
import com.zevent.zevent.model.enums.PrivacyEnum;
import com.zevent.zevent.model.interfaces.IDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private String image;
    private String duration;
    private String language;
    private String category;
    private String location;

    private int maxCapacity;

    private Enum<MediumEnum> medium;
    private Enum<PrivacyEnum> privacy;

    private IDate date;

    @DocumentReference
    private User user;

    @DocumentReference
    private GuestList[] guestList;

}
