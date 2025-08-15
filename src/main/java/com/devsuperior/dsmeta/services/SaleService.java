package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerSalesDto;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerSalesSumProjection;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private SellerRepository sellerRepository;

	LocalDate finalData , inicialData;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = saleRepository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	//RELATÓRIO DE VENDAS
	public Page<SellerSalesDto> searchSellerSales(
			String dataInicial, String dataFinal, String name, Pageable pageable) {

		convertStringToDate(dataInicial, dataFinal);
		Page<Seller> result = sellerRepository.searchSellerSales(
				inicialData, finalData,name, pageable);
		return result.map(s -> new SellerSalesDto(s));
	}

	//SUMÁRIO DE VENDAS POR VENDEDOR
    public List<SellerSalesSumProjection> SellerSalesSum(String dataInicial, String dataFinal) {
		convertStringToDate(dataInicial, dataFinal);
		List<SellerSalesSumProjection> result = sellerRepository.searchSellerSalesSum(inicialData, finalData);
		return result;
    }

	//CONVERTE STRING PARA LOCAL DATE
	public void convertStringToDate(String dataInicial, String dataFinal) {

		if(dataFinal.isBlank()) {
			finalData = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {finalData = LocalDate.parse(dataFinal); }

		if(dataInicial.isBlank()) {
			inicialData = finalData.minusYears(1L);
		} else {inicialData = LocalDate.parse(dataInicial);}

	}

}
