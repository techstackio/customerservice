package com.techstackio.customerservice;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
 class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Test
    void testGetCustomers() throws Exception {
       Customer customer1 = new Customer();
       customer1.setId(1001L);
       customer1.setName("Venkat");
       Customer customer2 = new Customer();
       customer2.setId(1002L);
       customer2.setName("Vignesh");
       Mockito.when(customerService.findAll()).thenReturn(Arrays.asList(customer1, customer2));
       this.mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                     .contentType(MediaType.APPLICATION_JSON))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$", hasSize(2)))
                     .andExpect(jsonPath("$[0].id", is(1111)));
    }

    @Test
    void testGetCustomerById() throws Exception {

       Customer customer = new Customer();
       customer.setId(2001L);
       customer.setName("Venkat");
       Mockito.when(customerService.findById(2001L)).thenReturn(customer);
       this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/{id}", 2001)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());


    }

   @Test
   void tetGetCustomerById_NOt() throws Exception {

      Customer customer = new Customer();
      customer.setId(2001L);
      customer.setName("Venkat");
      Mockito.when(customerService.findById(2001L)).thenReturn(customer);
      this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/{id}", 2000)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().is4xxClientError());


   }


}