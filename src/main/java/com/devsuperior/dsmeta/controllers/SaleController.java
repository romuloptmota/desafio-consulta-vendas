package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerSalesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

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
		Page<SellerSalesDto> dtos = service.findAll(
				dataInicial, dataFinal, name, pageable);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary() {
		// TODO
		return null;
	}
}
