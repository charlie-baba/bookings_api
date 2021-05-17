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

    public Member() {}

    public Member(Long id, String firstName, String lastName, String email) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
