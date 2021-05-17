package com.glofox.api.utils;

import com.glofox.api.pojo.response.BaseResponse;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.doReturn;

/**
 * @author Charles on 17/05/2021
 */
public class ErrorUtilTest {

    @Mock
    private Errors mockErrors;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getResponseMessage_ShouldReturnErrors() {
        //Arrange
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new ObjectError("firstName", "First name is required"));
        doReturn(errors).when(mockErrors).getAllErrors();

        //Act
        String message = ErrorUtil.getResponseMessage(mockErrors);

        //Assert
        Assertions.assertEquals("First name is required, ", message);
    }

    @Test
    public void getBaseResponseResponseEntity_ShouldReturnErrors() {
        //Arrange
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new ObjectError("firstName", "First name is required"));
        doReturn(errors).when(mockErrors).getAllErrors();
        doReturn(true).when(mockErrors).hasErrors();

        //Act
        ResponseEntity<BaseResponse> response = ErrorUtil.getBaseResponseResponseEntity(mockErrors);

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> Assertions.assertNotNull(Objects.requireNonNull(response.getBody()).getResponseMessage()),
                () -> Assertions.assertEquals("First name is required, ", response.getBody().getResponseMessage())
        );
    }
}
