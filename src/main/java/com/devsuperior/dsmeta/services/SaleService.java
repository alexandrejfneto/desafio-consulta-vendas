package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
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
	public List<SaleSummaryDTO> summarySales (String minDate, String maxDate) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate resultMinDate = minDate.equals("") ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate resultMaxDate = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		
		
		//List<SaleSummaryProjection> list = repository.summarySales2(resultMinDate, resultMaxDate);
		//List<SaleSummaryDTO> result = list.stream().map(x-> new SaleSummaryDTO(x)).collect(Collectors.toList());
		List<SaleSummaryDTO> result = repository.summarySales(resultMinDate, resultMaxDate);
		return result;
		
	}
}
