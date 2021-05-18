package com.glofox.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glofox.api.entity.Booking;
import com.glofox.api.entity.Member;
import com.glofox.api.entity.StudioClass;
import com.glofox.api.pojo.request.BookingRequest;
import com.glofox.api.services.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charles on 17/05/2021
 */
@RunWith(SpringRunner.class)
public class BookingsControllerTest {

    @Mock
    private BookingService mockService;

    @InjectMocks
    private BookingsController bookingsControllerTest;

    private MockMvc mockMvc;
    public final Date now = new Date();
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookingsControllerTest, "pageSize", 15);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookingsControllerTest).build();
    }

    @Test
    public void getAllBookingsShouldBeSuccessful() throws Exception {
        //Arrange
        int page = 0;
        int size = 15;
        List<Booking> bookings = new ArrayList<>();
        StudioClass studioClass = new StudioClass(1L, "Yoga", now, now, 20);
        Member member = new Member(1L, "Charles", "Okonkwo",  null);
        bookings.add(new Booking(member, studioClass));
        doReturn(bookings).when(mockService).getAllBookings(page, size);

        //Act //Assert
        this.mockMvc.perform(get("/api/bookings/getAll/{page}", page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void saveBookingShouldBeSuccessful() throws Exception {
        //Arrange
        BookingRequest request = new BookingRequest("Yoga", "Charles", "Okonkwo", null);

        //Act //Assert
        this.mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void saveBookingWithErrorsShouldReturnBadRequest() throws Exception {
        //Arrange
        BookingRequest request = new BookingRequest("", "Charles", "Okonkwo", null);

        //Act //Assert
        this.mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
