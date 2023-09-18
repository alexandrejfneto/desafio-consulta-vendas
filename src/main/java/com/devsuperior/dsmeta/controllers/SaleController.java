package com.devsuperior.dsmeta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
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
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			@RequestParam (required = false, name = "minDate", defaultValue = "") String minDate, 
			@RequestParam (required = false, name = "maxDate", defaultValue = "") String maxDate,
			@RequestParam (required = false, name = "name", defaultValue = "") String name,
			Pageable page
			) {
		Page<SaleReportDTO> sellerReport = service.report(minDate, maxDate, name, page);
		return ResponseEntity.ok(sellerReport);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List <SaleSummaryDTO>> getSummary(
			@RequestParam (required = false, name = "minDate", defaultValue = "") String minDate, 
			@RequestParam (required = false, name = "maxDate", defaultValue = "") String maxDate) {
		List <SaleSummaryDTO> salesSumary = service.getSummarySales(minDate, maxDate);
		return ResponseEntity.ok(salesSumary);
	}
}
