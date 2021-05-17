package com.glofox.api.repositories;

import com.glofox.api.entity.Booking;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Charles on 16/05/2021
 */
@Repository
public class BookingsRepository {

    private static HashMap<Long, Booking> bookings = new HashMap<>();
    private static long lastId = 0;

    public List<Booking> findAllBookings(long skip, long size){
        return bookings.values().stream()
                .skip(skip)
                .limit(size)
                .collect(Collectors.toList());
    }

    public long countBookingsByClassName(String className){
        return bookings.values().stream()
                .filter(n -> n.getStudioClass().getName().equals(className))
                .count();
    }

    public synchronized void save(Booking booking){
        booking.setId(++lastId);
        bookings.put(lastId, booking);
    }
}
