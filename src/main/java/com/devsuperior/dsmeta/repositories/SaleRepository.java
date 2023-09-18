package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query (nativeQuery=true, value=""
			+ "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name AS sellername "
			+ "FROM TB_SALES "
			+ "INNER JOIN tb_seller "
			+ "ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE tb_sales.date BETWEEN :resultMinDate AND :resultMaxDate "
			+ "AND UPPER (tb_seller.name) LIKE UPPER (CONCAT ('%',:name,'%'))",
			countQuery = "SELECT COUNT (tb_sales.id) "
					+ "FROM TB_SALES "
					+ "INNER JOIN tb_seller "
					+ "ON tb_sales.seller_id = tb_seller.id "
					+ "WHERE tb_sales.date BETWEEN :resultMinDate AND :resultMaxDate "
					+ "AND UPPER (tb_seller.name) LIKE UPPER (CONCAT ('%',:name,'%'))")
	Page <SaleReportProjection> getReport2 (LocalDate resultMinDate, LocalDate resultMaxDate, String name, Pageable page);
	
	
	@Query (value = "SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO (sale.id, sale.date, sale.amount, sale.seller.name) "
			+ "FROM Sale sale "
			+ "WHERE sale.date BETWEEN :resultMinDate AND :resultMaxDate "
			+ "AND UPPER (sale.seller.name) LIKE UPPER (CONCAT ('%',:name,'%'))",
			countQuery = "SELECT COUNT (sale) FROM Sale sale")
	Page <SaleReportDTO> getReport (LocalDate resultMinDate, LocalDate resultMaxDate, String name, Pageable page);
	
	
	@Query (nativeQuery=true, value=""
			+ "SELECT tb_seller.name AS sellerName, cast ((SUM (tb_sales.amount)) AS DECIMAL (15, 1)) AS total "
			+ "FROM tb_seller INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id "
			+ "WHERE tb_sales.date BETWEEN :resultMinDate AND :resultMaxDate "
			+ "GROUP BY tb_seller.name")
	List <SaleSummaryProjection> getSummarySales2(LocalDate resultMinDate, LocalDate resultMaxDate);
	
	@Query (""
			+ "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO (sale.seller.name, (SUM (sale.amount))) "
			+ "FROM Sale sale "
			+ "WHERE sale.date BETWEEN :resultMinDate AND :resultMaxDate "
			+ "GROUP BY sale.seller.name")
	List <SaleSummaryDTO> getSummarySales(LocalDate resultMinDate, LocalDate resultMaxDate);
}
