package com.glofox.api.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Charles on 16/05/2021
 */
@Getter
@Setter
@ToString
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest implements Serializable {

    @NotBlank(message = "Class name is required")
    private String className;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    private String email;

    public BookingRequest() {}

    public BookingRequest(String className, String firstName, String lastName) {
        this.className = className;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
