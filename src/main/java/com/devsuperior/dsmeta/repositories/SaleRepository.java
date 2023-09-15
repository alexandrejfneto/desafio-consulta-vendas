package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query (nativeQuery=true, value=""
			+ "SELECT tb_seller.name AS sellerName, cast ((SUM (tb_sales.amount)) AS DECIMAL (15, 1)) AS total "
			+ "FROM tb_seller INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id "
			+ "WHERE tb_sales.date BETWEEN :resultMinDate AND :resultMaxDate "
			+ "GROUP BY tb_seller.name")
	List <SaleSummaryProjection> summarySales2(LocalDate resultMinDate, LocalDate resultMaxDate);
	
	@Query (""
			+ "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO (sale.seller.name, (SUM (sale.amount))) "
			+ "FROM Sale sale "
			+ "WHERE sale.date BETWEEN '2022-01-01' AND '2022-06-30' "
			+ "GROUP BY sale.seller.name")
	List <SaleSummaryDTO> summarySales(LocalDate resultMinDate, LocalDate resultMaxDate);
}
