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
}
