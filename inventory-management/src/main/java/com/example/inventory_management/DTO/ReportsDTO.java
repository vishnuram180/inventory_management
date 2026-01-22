package com.example.inventory_management.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ReportsDTO {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date getDate();
    Integer getStock_in();
    Integer getStock_out();
    String getProduct_Name();
    Integer getAvailable_stock();
    
    default Integer getStockInSafe() {
        return getStock_in() == null ? 0 : getStock_in();
    }

    default Integer getStockOutSafe() {
        return getStock_out() == null ? 0 : getStock_out();
    }

    default Integer getAvailableStockSafe() {
        return getAvailable_stock() == null ? 0 : getAvailable_stock();
    }
}