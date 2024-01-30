package com.zevent.zevent.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.zevent.zevent.model.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "guestList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestList {

    @Id
    private ObjectId id;

    private StatusEnum status;

    @DocumentReference
    private User user;

}
