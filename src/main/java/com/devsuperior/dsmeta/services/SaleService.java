package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	@Transactional
	public List<SaleSummaryDTO> getSummarySales (String minDate, String maxDate) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate resultMinDate = minDate.equals("") ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate resultMaxDate = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		
		//Duas linhas a seguir são uma chamada de consulta em versão alternativa com SQL Raiz
		//List<SaleSummaryProjection> list = repository.getSummarySales2(resultMinDate, resultMaxDate);
		//List<SaleSummaryDTO> result = list.stream().map(x-> new SaleSummaryDTO(x)).collect(Collectors.toList());
		List<SaleSummaryDTO> result = repository.getSummarySales(resultMinDate, resultMaxDate);
		return result;
		
	}

	public Page<SaleReportDTO> report(String minDate, String maxDate, String name, Pageable page) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate resultMinDate = minDate.equals("") ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate resultMaxDate = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		
		//Duas linhas a seguir são uma chamada de consulta em versão alternativa com SQL Raiz
		//Page<SaleReportProjection> list = repository.getReport2(resultMinDate, resultMaxDate, name, page);
		//Page<SaleReportDTO> result = list.map(x-> new SaleReportDTO(x));
		
		Page<SaleReportDTO> result = repository.getReport(resultMinDate, resultMaxDate, name, page);
		
		return result;
		
	}
}
