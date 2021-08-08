package com.comp.orderprocessorapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.model.OrderDetails;
import com.comp.orderprocessorapi.model.OrderDetailsStatus;
import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.repository.OrderRepository;

import com.comp.orderprocessorapi.service.impl.OrderDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class OrderDetailsServiceTest {

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    Response response;

    @Test
    public void saveOrderSuccess() {

        OrderDetailsStatus mockOrderStatus = new OrderDetailsStatus("204","PROCESSED");
        OrderDetails mockOrderDetails = new OrderDetails();
        mockOrderDetails.setOrderDate("07-Aug-2021");
        mockOrderDetails.setOrderId(1279);
        mockOrderDetails.setOrderTotal("1375");
        Order mockOrder = new Order();
        mockOrder.setOrderDetailsStatus(mockOrderStatus);
        mockOrder.setOrderDetails(mockOrderDetails);
        Mockito.when(response.getStatus()).thenReturn("success");
        Mockito.when(response.getMessage()).thenReturn("Saved Order details Successfully");
        Mockito.doNothing().when(orderRepository).save(mockOrder);

        Response responseExpected = orderDetailsService.saveOrder(mockOrder);

        assertEquals(responseExpected.getStatus(),"success");
        assertEquals(responseExpected.getMessage(),"Saved Order details Successfully");

    }

    @Test
    public void saveOrderFailure() {

        OrderDetailsStatus mockOrderStatus = new OrderDetailsStatus("209","PROCESSED");
        OrderDetails mockOrderDetails = new OrderDetails();
        mockOrderDetails.setOrderDate("07-Aug-2021");
        mockOrderDetails.setOrderId(3456);
        mockOrderDetails.setOrderTotal("37324");
        Order mockOrder = new Order();
        mockOrder.setOrderDetailsStatus(mockOrderStatus);
        mockOrder.setOrderDetails(mockOrderDetails);
        Mockito.when(response.getStatus()).thenReturn("failure");
        Mockito.when(response.getMessage()).thenReturn("Failed to save order details");
        Mockito.doThrow(IllegalStateException.class).when(orderRepository).save(mockOrder);

        Response responseExpected = orderDetailsService.saveOrder(mockOrder);

        assertEquals(responseExpected.getStatus(),"failure");
        assertEquals(responseExpected.getMessage(),"Failed to save order details");

    }

    @Test
    public void getOrderStatusFailed() {

        String orderRequestId = "900";
        Mockito.when(orderRepository.findById(Mockito.anyString())).thenReturn(null);

        OrderDetailsStatus expected = orderDetailsService.getOrderStatus(orderRequestId);

        assertThat(expected).isNull();

    }

    @Test
    public void getOrderStatusSuccess() {

        String orderRequestId = "894";
        OrderDetailsStatus mockOrderStatus = new OrderDetailsStatus("894","PROCESSED");
        Order mockOrder = new Order();
        mockOrder.setOrderDetailsStatus(mockOrderStatus);
        Mockito.when(orderRepository.findById(Mockito.anyString())).thenReturn(mockOrder);

        OrderDetailsStatus expected = orderDetailsService.getOrderStatus(orderRequestId);

        assertThat(expected).isNotNull();
        assertEquals(expected.getOrderRequestId(), "894");
        assertEquals(expected.getOrderRequestStatus(), "PROCESSED");

    }

}
