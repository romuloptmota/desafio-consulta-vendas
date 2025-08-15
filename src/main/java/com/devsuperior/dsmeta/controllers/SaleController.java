package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerSalesDto;
import com.devsuperior.dsmeta.projections.SellerSalesSumProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SellerSalesDto>> getReport(
			@RequestParam(value = "minDate", defaultValue = "") String dataInicial,
			@RequestParam(value = "maxDate", defaultValue = "")String dataFinal,
			@RequestParam(value = "name", defaultValue = "")String name,
			Pageable pageable) {
		Page<SellerSalesDto> dtos = service.searchSellerSales(
				dataInicial, dataFinal, name, pageable);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SellerSalesSumProjection>> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String dataInicial,
			@RequestParam(value = "maxDate", defaultValue = "")String dataFinal
	){
		List<SellerSalesSumProjection> dtos = service.SellerSalesSum(
				dataInicial, dataFinal
		);
		return ResponseEntity.ok(dtos);
	}
}
