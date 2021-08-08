package com.comp.orderprocessorapi.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDetails implements Serializable {
	
	private String customerId;
	private String customerName;
	private String customerContact;
}
