package com.comp.orderprocessorapi.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSummary implements Serializable {
	
	private String itemsTotalPrice;
	private String packingCost;
	private String totalBeforeTax;
	private String tax;
	private String total;

}
