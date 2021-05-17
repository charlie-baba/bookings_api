package com.glofox.api.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
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
public class ClassRequest implements Serializable {

    @NotBlank(message = "Class name is required")
    private String name;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @Future(message = "End date must be in the future")
    @NotNull(message = "End date is required")
    private Date endDate;

    @Min(value = 1, message = "Enter a valid class capacity")
    private long capacity;

    public ClassRequest() {}

    public ClassRequest(String name, Date startDate, Date endDate, int capacity) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
    }
}
