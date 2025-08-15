package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerSalesDto;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = saleRepository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SellerSalesDto> findAll(
			String dataInicial, String dataFinal, String name, Pageable pageable) {

		LocalDate finalData , inicialData;
		if(dataFinal.isBlank()) {
			finalData = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {finalData = LocalDate.parse(dataFinal); }

		if(dataInicial.isBlank()) {
			inicialData = finalData.minusYears(1L);
		} else {inicialData = LocalDate.parse(dataInicial);}

		Page<Seller> result = sellerRepository.searchSellerSales(
				inicialData, finalData,name, pageable);

		return result.map(s -> new SellerSalesDto(s));
	}
}
