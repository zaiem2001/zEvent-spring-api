package com.zevent.zevent.model.interfaces;

import org.bson.types.ObjectId;

import com.zevent.zevent.model.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

enum EnterEventReqBodyEnum {
    INVITED,
    APPLIED
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterEventReqBody {
    private StatusEnum status;
    private ObjectId userId;
}
