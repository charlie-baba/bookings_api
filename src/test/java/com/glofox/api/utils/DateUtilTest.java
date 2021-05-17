package com.glofox.api.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Charles on 17/05/2021
 */
public class DateUtilTest {

    @Test
    public void isStillValid_ShouldReturnTrue() {
        //Arrange
        Date d1 = new Date();
        LocalDateTime ldt = LocalDateTime.now().plusDays(2);
        Date d2 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        //Act
        boolean isValid = DateUtil.isStillValid(d1, d2);

        //Assert
        Assertions.assertTrue(isValid);
    }

    @Test
    public void isStillValid_ShouldReturnFalse() {
        //Arrange
        LocalDateTime ldt = LocalDateTime.now().minusDays(4);
        Date d1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime ldt2 = LocalDateTime.now().minusMinutes(1);
        Date d2 = Date.from(ldt2.atZone(ZoneId.systemDefault()).toInstant());

        //Act
        boolean isValid = DateUtil.isStillValid(d1, d2);

        //Assert
        Assertions.assertFalse(isValid);
    }
}
