package com.devsuperior.dsmeta.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({ "id", "date", "amount", "sellerName" })
public interface SellerSalesReportProjection {

    Long getid();
    LocalDate getdate();
    String getamount();
    String getsellerName();
}
