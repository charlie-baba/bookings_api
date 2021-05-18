package com.glofox.api.services;

import com.glofox.api.entity.Booking;
import com.glofox.api.entity.Member;
import com.glofox.api.entity.StudioClass;
import com.glofox.api.pojo.request.BookingRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.repositories.BookingsRepository;
import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.repositories.MembersRepository;
import com.glofox.api.services.impl.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Charles on 17/05/2021
 */
public class BookingServiceTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private MembersRepository membersRepository;

    @InjectMocks
    private BookingServiceImpl service;

    public final Date now = new Date();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllClassesShouldBeSuccessful() {
        //Arrange
        int page = 0;
        int size = 15;
        List<Booking> bookings = new ArrayList<>();
        StudioClass studioClass = new StudioClass(1L, "Yoga", now, now, 20);
        Member member = new Member(1L, "Charles", "Okonkwo",  null);
        bookings.add(new Booking(member, studioClass));
        doReturn(bookings).when(bookingsRepository).findAllBookings(page, size);

        //Act
        List<Booking> fetchedList = service.getAllBookings(page, size);

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(fetchedList),
                () -> Assertions.assertEquals(1, fetchedList.size()),
                () -> Assertions.assertEquals(1L, fetchedList.get(0).getMember().getId())
        );
    }

    @Test
    public void saveBookingShouldBeSuccessful() {
        //Arrange
        String className = "Cycling";
        BookingRequest request = new BookingRequest(className, "Charles", "Okonkwo", null);
        StudioClass studioClass = new StudioClass(1L, className, now, now, 20);
        doReturn(studioClass).when(classesRepository).findValidClassByName(className);

        //Act
        BaseResponse response = service.saveBooking(request);

        //Assert
        Assertions.assertEquals("00", response.getResponseCode());
        verify(bookingsRepository).save(any(Booking.class));
    }

    @Test
    public void saveBookingClassShouldNotExist() {
        //Arrange
        String className = "Cycling";
        BookingRequest request = new BookingRequest(className, "Charles", "Okonkwo", null);
        doReturn(null).when(classesRepository).findValidClassByName(className);

        //Act
        BaseResponse response = service.saveBooking(request);

        //Assert
        Assertions.assertEquals("33", response.getResponseCode());
        verify(bookingsRepository, never()).save(any(Booking.class));
    }

    @Test
    public void saveBookingShouldBeFullyBooked() {
        //Arrange
        String className = "Cycling";
        BookingRequest request = new BookingRequest(className, "Charles", "Okonkwo", null);
        StudioClass studioClass = new StudioClass(1L, className, now, now, 10);
        doReturn(studioClass).when(classesRepository).findValidClassByName(className);
        doReturn(10L).when(bookingsRepository).countBookingsByClassName(className);

        //Act
        BaseResponse response = service.saveBooking(request);

        //Assert
        Assertions.assertEquals("22", response.getResponseCode());
        verify(bookingsRepository, never()).save(any(Booking.class));
    }
}
