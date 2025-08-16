package com.example.car.challenge.controller;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VehicleControllerTest.class)
@AutoConfigureMockMvc
public class VehicleControllerTest  {



 /**
 * @deprecated MockMvc is a component of the Spring Test framework used for testing Spring MVC applications.
  */
 @Autowired
 MockMvc mockMvc;

// @MockBean
// VehicleControllerTest vehicleControllerTest;




}
