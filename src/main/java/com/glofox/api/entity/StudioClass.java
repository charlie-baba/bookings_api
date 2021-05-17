package com.glofox.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Charles on 15/05/2021
 */
@Getter
@Setter
@ToString
public class StudioClass extends BaseEntity {

    private String name;

    private Date startDate;

    private Date endDate;

    private long capacity;

    public StudioClass() {}

    public StudioClass(Long id, String name, Date startDate, Date endDate, long capacity) {
        this.setId(id);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
    }
}
