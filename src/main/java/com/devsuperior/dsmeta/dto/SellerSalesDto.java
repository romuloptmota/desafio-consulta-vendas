package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerSalesDto {

    //ATRIBUTE
    private String name;

    //ATRIBUTOS ASSOCIADOS
    private List<SaleMinDTO> sales = new ArrayList<>();

    //CONSTRUTORES
    public SellerSalesDto(String name) {
        this.name = name;
    }

    public SellerSalesDto(Seller entity){
        this.name = entity.getName();
        for (Sale sale : entity.getSales()) {
            this.sales.add(new SaleMinDTO(sale));
        }
    }

    //GETTER
    public String getName() {
        return name;
    }

    public List<SaleMinDTO> getSales() {
        return sales;
    }
}
