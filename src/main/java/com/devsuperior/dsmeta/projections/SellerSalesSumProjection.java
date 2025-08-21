package com.devsuperior.dsmeta.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "sellerName", "total" })
public interface SellerSalesSumProjection {
    String getsellerName();
    Double gettotal();
}
