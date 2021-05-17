package com.glofox.api.services.impl;

import com.glofox.api.entity.Booking;
import com.glofox.api.entity.Member;
import com.glofox.api.entity.StudioClass;
import com.glofox.api.enums.ResponseCode;
import com.glofox.api.pojo.request.BookingRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.repositories.BookingsRepository;
import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.repositories.MembersRepository;
import com.glofox.api.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Charles on 16/05/2021
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private MembersRepository membersRepository;

    @Override
    public List<Booking> getAllBookings(int page, int size) {
        long skip = page == 1 ? 0 : (long) page *size;
        List<Booking> bookings = bookingsRepository.findAllBookings(skip, size);
        return bookings;
    }

    @Override
    public BaseResponse saveBooking(BookingRequest bookingRequest) {
        StudioClass studioClass = classesRepository.findValidClassByName(bookingRequest.getClassName());
        if (studioClass == null) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(),
                    "The class you are trying to book has either ended or does not exist.");
        }

        long bookingsCount = bookingsRepository.countBookingsByClassName(bookingRequest.getClassName());
        if (bookingsCount >= studioClass.getCapacity()) {
            return new BaseResponse(ResponseCode.Bad_Request.getCode(),
                    "This class has been fully booked.");
        }

        Booking booking = new Booking();
        booking.setStudioClass(studioClass);
        Member member;
        member = membersRepository.findByEmail(bookingRequest.getEmail());
        if (member == null){
            member = new Member();
            member.setEmail(bookingRequest.getEmail());
            member.setFirstName(bookingRequest.getFirstName());
            member.setLastName(bookingRequest.getLastName());
            membersRepository.save(member);
        }
        booking.setMember(member);
        bookingsRepository.save(booking);
        return new BaseResponse(ResponseCode.Success);
    }
}
