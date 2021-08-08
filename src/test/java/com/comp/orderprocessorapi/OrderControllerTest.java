package com.comp.orderprocessorapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.comp.orderprocessorapi.model.OrderDetailsStatus;
import com.comp.orderprocessorapi.service.OrderDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailsService orderDetailsDervice;

    @Test
    public void getOrderStatusForValidRequestId() throws Exception {

        OrderDetailsStatus mockOrderStatus = new OrderDetailsStatus("164","submitted");

        Mockito.when(orderDetailsDervice.getOrderStatus(Mockito.anyString())).thenReturn(mockOrderStatus);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "test");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/order/status/124")
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(jsonPath("$.orderRequestId", is("164")))
                .andExpect(jsonPath("$.orderRequestStatus", is("PROCESSED")));
    }

    @Test
    public void getOrderStatusForInValidRequestId() throws Exception {

        OrderDetailsStatus mockOrderStatus = null;

        Mockito.when(orderDetailsDervice.getOrderStatus(Mockito.anyString())).thenReturn(mockOrderStatus);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "test");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/order/status/xx")
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }


}
