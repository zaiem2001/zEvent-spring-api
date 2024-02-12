package com.zevent.zevent.model.interfaces;

import com.zevent.zevent.model.enums.MediumEnum;
import com.zevent.zevent.model.enums.PrivacyEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventReqBody {

    private String name;
    private String description;
    private String image;
    private String duration;
    private String language;
    private String category;
    private String location;

    private int maxCapacity;
    private MediumEnum medium;

    @Builder.Default
    private PrivacyEnum privacy = PrivacyEnum.PUBLIC;

    private IDate date;

}
