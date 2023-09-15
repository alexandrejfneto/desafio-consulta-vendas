package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public class SaleSummaryDTO {
	
	private String sellerName;
	private Double total;
	
	public SaleSummaryDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}
	
	public SaleSummaryDTO (SaleSummaryProjection projection) {
		sellerName = projection.getSellerName();
		total = projection.getTotal();
	}
	
	public SaleSummaryDTO (Sale sale) {
		sellerName = sale.getSeller().getName();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
	

}
