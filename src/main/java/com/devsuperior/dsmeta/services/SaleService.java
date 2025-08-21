package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.projections.SellerSalesReportProjection;
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
	public Page<SellerSalesReportProjection> searchSellerSales(
			String dataInicial, String dataFinal, String name, Pageable pageable) {

		convertStringToDate(dataInicial, dataFinal);
		Page<SellerSalesReportProjection> result = sellerRepository.searchSellerSalesReport(
				inicialData, finalData, name, pageable);
		return result;
	}

	//SUMÁRIO DE VENDAS POR VENDEDOR
    public List<SellerSalesSumProjection> SellerSalesSum(String dataInicial, String dataFinal) {
		convertStringToDate(dataInicial, dataFinal);
		List<SellerSalesSumProjection> result = sellerRepository.searchSellerSalesSum(inicialData, finalData);
		return result;
    }

	//CONVERTE STRING PARA LOCAL DATE
	public void convertStringToDate(String dataInicial, String dataFinal) {

		if(dataFinal.isBlank() || dataFinal.isEmpty() || dataFinal == null) {
			finalData = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {finalData = LocalDate.parse(dataFinal); }

		if(dataInicial.isBlank() || dataInicial.isEmpty() || dataInicial == null) {
			inicialData = finalData.minusYears(1L);
		} else {inicialData = LocalDate.parse(dataInicial);}

	}

}
