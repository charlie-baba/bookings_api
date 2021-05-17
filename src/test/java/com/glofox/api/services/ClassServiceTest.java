package com.glofox.api.services;

import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.services.impl.ClassServiceImpl;
import org.junit.Before;
import org.junit.Test;
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

    @Test
    public void getAllClassesShouldBeSuccessful() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void getAllActiveClassesShouldBeSuccessful() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void saveClassShouldBeSuccessful() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void saveClassShouldReturnClassNameExists() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void updateClassShouldBeSuccessful() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void updateClassShouldReturnIdNotFound() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void updateClassShouldReturnClassNameExists() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void deleteClassShouldBeSuccessful() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void deleteClassShouldReturnIdNotFound() {
        //Arrange

        //Act

        //Assert
    }
}
