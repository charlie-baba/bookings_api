package com.glofox.api.services;

import com.glofox.api.entity.StudioClass;
import com.glofox.api.pojo.request.ClassRequest;
import com.glofox.api.pojo.response.BaseResponse;
import com.glofox.api.repositories.ClassesRepository;
import com.glofox.api.services.impl.ClassServiceImpl;
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
public class ClassServiceTest {

    @Mock
    private ClassesRepository mockRepository;

    @InjectMocks
    private ClassServiceImpl service;

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
        List<StudioClass> classes = new ArrayList<>();
        classes.add(new StudioClass(1L, "Yoga", now, now, 20));
        doReturn(classes).when(mockRepository).findAllClasses(page, size);

        //Act
        List<StudioClass> fetchedList = service.getAllClasses(page, size);

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(fetchedList),
                () -> Assertions.assertEquals(1, fetchedList.size()),
                () -> Assertions.assertEquals(1L, fetchedList.get(0).getId())
        );
    }

    @Test
    public void getAllActiveClassesShouldBeSuccessful() {
        //Arrange
        int page = 0;
        int size = 15;
        List<StudioClass> classes = new ArrayList<>();
        classes.add(new StudioClass(1L, "Cycling", now, now, 10));
        doReturn(classes).when(mockRepository).findAllActiveClasses(page, size);

        //Act
        List<StudioClass> fetchedList = service.getAllActiveClasses(page, size);

        //Assert
        Assertions.assertAll(
                () -> Assertions.assertNotNull(fetchedList),
                () -> Assertions.assertEquals(1, fetchedList.size()),
                () -> Assertions.assertEquals(1L, fetchedList.get(0).getId())
        );
    }

    @Test
    public void saveClassShouldBeSuccessful() {
        //Arrange
        ClassRequest request = new ClassRequest("Cycling", now, now, 10);

        //Act
        BaseResponse response = service.saveClass(request);

        //Assert
        Assertions.assertEquals("00", response.getResponseCode());
        verify(mockRepository).save(any(StudioClass.class));
    }

    @Test
    public void saveClassShouldReturnClassNameExists() {
        //Arrange
        ClassRequest request = new ClassRequest("Cycling", now, now, 10);
        StudioClass studioClass = new StudioClass(1L, "Cycling", now, now, 10);
        doReturn(studioClass).when(mockRepository).findValidClassByName("Cycling");

        //Act
        BaseResponse response = service.saveClass(request);

        //Assert
        Assertions.assertEquals("33", response.getResponseCode());
        verify(mockRepository, never()).save(any(StudioClass.class));
    }

    @Test
    public void updateClassShouldBeSuccessful() {
        //Arrange
        Long id  = 1L;
        ClassRequest request = new ClassRequest("Yoga", now, now, 10);
        StudioClass studioClass = new StudioClass(id, "Cycling", now, now, 10);
        doReturn(studioClass).when(mockRepository).findById(id);
        doReturn(studioClass).when(mockRepository).findValidClassByName("Cycling");

        //Act
        BaseResponse response = service.updateClass(id, request);

        //Assert
        Assertions.assertEquals("00", response.getResponseCode());
        verify(mockRepository).update(anyLong(), any(StudioClass.class));
    }

    @Test
    public void updateClassShouldReturnIdNotFound() {
        //Arrange
        Long id  = 1L;
        ClassRequest request = new ClassRequest("Yoga", now, now, 10);
        doReturn(null).when(mockRepository).findById(id);

        //Act
        BaseResponse response = service.updateClass(id, request);

        //Assert
        Assertions.assertEquals("22", response.getResponseCode());
        verify(mockRepository, never()).update(anyLong(), any(StudioClass.class));
    }

    @Test
    public void updateClassShouldReturnClassNameExists() {
        Long id  = 2L;
        String newName = "Cycling";
        ClassRequest request = new ClassRequest(newName, now, now, 10);
        StudioClass existingClass = new StudioClass(1L, "Yoga", now, now, 10);
        StudioClass classByName = new StudioClass(id, "Cycling", now, now, 10);
        doReturn(existingClass).when(mockRepository).findById(id);
        doReturn(classByName).when(mockRepository).findValidClassByName(newName);

        //Act
        BaseResponse response = service.updateClass(id, request);

        //Assert
        Assertions.assertEquals("33", response.getResponseCode());
        verify(mockRepository, never()).update(anyLong(), any(StudioClass.class));
    }

    @Test
    public void deleteClassShouldBeSuccessful() {
        //Arrange
        Long id = 1L;
        StudioClass studioClass = new StudioClass(id, "Cycling", now, now, 10);
        doReturn(studioClass).when(mockRepository).findById(id);

        //Act
        BaseResponse response = service.deleteClass(id);

        //Assert
        Assertions.assertEquals("00", response.getResponseCode());
        verify(mockRepository).delete(anyLong());
    }

    @Test
    public void deleteClassShouldReturnIdNotFound() {
        //Arrange
        doReturn(null).when(mockRepository).findById(1L);

        //Act
        BaseResponse response = service.deleteClass(1L);

        //Assert
        Assertions.assertEquals("22", response.getResponseCode());
        verify(mockRepository, never()).delete(anyLong());
    }
}
