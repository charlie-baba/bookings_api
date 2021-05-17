package com.glofox.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Charles on 16/05/2021
 */
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;
}
