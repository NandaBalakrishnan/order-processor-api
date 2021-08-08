package com.comp.orderprocessorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {
	
	private long orderId;
	private String orderDate;
	private String orderTotal;
	private PaymentInfo paymentInfo;
	private String shippingAddress;

}
