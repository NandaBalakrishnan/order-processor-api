package com.comp.orderprocessorapi.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class LineItemDetails implements Serializable {
	
	private int itemNo;
	private String itemId;
	private String itemName;
	private String itemPrice;

}
