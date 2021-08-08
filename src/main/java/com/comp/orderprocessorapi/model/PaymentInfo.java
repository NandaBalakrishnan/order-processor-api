package com.comp.orderprocessorapi.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentInfo implements Serializable {
	
	private String paymentMethod;
	private String billingAddress;
}
