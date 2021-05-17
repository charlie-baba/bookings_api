package com.glofox.api.services;

import com.glofox.api.repositories.BookingsRepository;
import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.repositories.MembersRepository;
import com.glofox.api.services.impl.BookingServiceImpl;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


}
