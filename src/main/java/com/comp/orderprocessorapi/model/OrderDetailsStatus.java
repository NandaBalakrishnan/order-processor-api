package com.comp.orderprocessorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsStatus implements Serializable {
	
	private String orderRequestId;
	private String orderRequestStatus;

	@Override
	public String toString() {
		return "OrderStatus [orderRequestId=" + orderRequestId + ", orderRequestStatus=" + orderRequestStatus + "]";
	}

	

}
