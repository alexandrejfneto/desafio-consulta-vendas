package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.projections.SaleReportProjection;

public class SaleReportDTO {
	
	
	private Long id;
	private LocalDate date;
	private Double amount;
	private String sellerName;
	
	public SaleReportDTO(Long id, LocalDate date, Double amount, String sellerName) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.sellerName = sellerName;
	}
	
	public SaleReportDTO (SaleReportProjection projection) {
		id = projection.getId();
		date = projection.getDate();
		amount = projection.getAmount();
		sellerName = projection.getSellerName();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getAmount() {
		return amount;
	}

	public String getSellerName() {
		return sellerName;
	}
	

}
