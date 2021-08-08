package com.comp.orderprocessorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
	
	private CustomerDetails customerDetails;
	private OrderDetails orderDetails;
	private ArrayList<LineItemDetails> lineItems;
	private OrderSummary orderSummary;
	private OrderDetailsStatus orderDetailsStatus;

	@Override
	public String toString() {
		return "Order [customerDetails=" + customerDetails + ", orderDetails=" + orderDetails + ", lineItems="
				+ lineItems + ", orderSummary=" + orderSummary + ", orderDetailsStatus=" + orderDetailsStatus + "]";
	}
}
