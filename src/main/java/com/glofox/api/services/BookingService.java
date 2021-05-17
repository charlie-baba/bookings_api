package com.glofox.api.services;

import com.glofox.api.entity.Booking;
import com.glofox.api.pojo.request.BookingRequest;
import com.glofox.api.pojo.response.BaseResponse;

import java.util.List;

/**
 * @author Charles on 16/05/2021
 */
public interface BookingService {

    List<Booking> getAllBookings(int page, int size);

    BaseResponse saveBooking(BookingRequest bookingRequest);

}
