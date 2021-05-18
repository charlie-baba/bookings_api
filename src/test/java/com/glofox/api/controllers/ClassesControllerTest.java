package com.glofox.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glofox.api.entity.StudioClass;
import com.glofox.api.pojo.request.ClassRequest;
import com.glofox.api.services.ClassService;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Charles on 17/05/2021
 */
@RunWith(SpringRunner.class)
public class ClassesControllerTest {

    @Mock
    private ClassService mockService;

    @InjectMocks
    private ClassesController classesControllerTest;

    private MockMvc mockMvc;
    public final Date now = new Date();
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(classesControllerTest, "pageSize", 15);
        this.mockMvc = MockMvcBuilders.standaloneSetup(classesControllerTest).build();
    }

    @Test
    public void getAllClassesShouldBeSuccessful() throws Exception {
        //Arrange
        int page = 0;
        int size = 15;
        List<StudioClass> classes = new ArrayList<>();
        classes.add(new StudioClass(1L, "Yoga", now, now, 20));
        doReturn(classes).when(mockService).getAllClasses(page, size);

        //Act //Assert
        this.mockMvc.perform(get("/api/classes/getAll/{page}", page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void getActiveClassesShouldBeSuccessful() throws Exception {
        //Arrange
        int page = 0;
        int size = 15;
        List<StudioClass> classes = new ArrayList<>();
        classes.add(new StudioClass(1L, "Yoga", now, now, 20));
        doReturn(classes).when(mockService).getAllActiveClasses(page, size);

        //Act //Assert
        this.mockMvc.perform(get("/api/classes/getActive/{page}", page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void saveClassShouldBeSuccessful() throws Exception {
        //Arrange
        LocalDateTime ldt = LocalDateTime.now().plusDays(2);
        Date endDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ClassRequest request = new ClassRequest("Cycling", now, endDate, 10);

        //Act //Assert
        this.mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void saveClassesWithErrorsShouldReturnBadRequest() throws Exception {
        //Arrange
        ClassRequest request = new ClassRequest("", now, now, 10);

        //Act //Assert
        this.mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateClassesShouldBeSuccessful() throws Exception {
        //Arrange
        Long id = 1L;
        LocalDateTime ldt = LocalDateTime.now().plusDays(2);
        Date endDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        ClassRequest request = new ClassRequest("Yoga", now, endDate, 10);

        //Act //Assert
        this.mockMvc.perform(put("/api/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateClassesWithErrorsShouldReturnBadRequest() throws Exception {
        //Arrange
        Long id = 1L;
        ClassRequest request = new ClassRequest("", now, now, 10);

        //Act //Assert
        this.mockMvc.perform(put("/api/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void deleteClassShouldBeSuccessful() throws Exception {
        //Arrange
        Long id = 1L;

        //Act //Assert
        this.mockMvc.perform(delete("/api/classes/{id}", id))
                .andExpect(status().isOk());
    }

}
