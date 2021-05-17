package com.glofox.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Charles on 15/05/2021
 */
@Getter
@Setter
public abstract class BaseEntity {

    private Long id;

    private Date dateCreated = new Date();

}
