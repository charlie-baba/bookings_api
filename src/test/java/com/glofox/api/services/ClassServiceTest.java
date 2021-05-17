package com.glofox.api.services;

import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.services.impl.ClassServiceImpl;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Charles on 17/05/2021
 */
public class ClassServiceTest {

    @Mock
    private ClassesRepository mockRepository;

    @InjectMocks
    private ClassServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


}