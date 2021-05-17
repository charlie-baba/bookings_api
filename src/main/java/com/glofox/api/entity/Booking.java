package com.glofox.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Charles on 15/05/2021
 */
@Getter
@Setter
@ToString
public class Booking extends BaseEntity {

    private Member member;

    private StudioClass studioClass;

    public Booking() {}

    public Booking(Member member, StudioClass studioClass) {
        this.member = member;
        this.studioClass = studioClass;
    }
}
