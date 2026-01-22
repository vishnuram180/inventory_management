package com.example.inventory_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Repository.CategoriesRepository;
import com.example.inventory_management.Repository.ProductsRepository;
import com.example.inventory_management.Repository.Stock_logsRepository;
import com.example.inventory_management.Repository.SupplierRepository;
import com.example.inventory_management.DTO.DashboardSummaryDTO;
import com.example.inventory_management.entity.Stock_Logs;
import com.example.inventory_management.entity.Products;
import com.example.inventory_management.Enum.actions;

import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

        @Autowired
        private ProductsRepository productRepo;

        @Autowired
        private CategoriesRepository categoryRepo;

        @Autowired
        private SupplierRepository supplierRepo;

        @Autowired
        private Stock_logsRepository logsRepo;

        @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
        @GetMapping("/summary")
        public DashboardSummaryDTO getSummary() {
                DashboardSummaryDTO summary = new DashboardSummaryDTO();

                summary.setTotalProducts(productRepo.count());
                summary.setTotalCategories(categoryRepo.count());
                summary.setTotalSuppliers(supplierRepo.count());

                List<Products> allProducts = productRepo.findAll();
                long lowStockCount = allProducts.stream()
                                .filter(p -> p.getCurrent_stock() <= p.getReorder_stock())
                                .count();
                summary.setLowStockCount(lowStockCount);

                List<Products> lowStockList = allProducts.stream()
                                .filter(p -> p.getCurrent_stock() <= p.getReorder_stock())
                                .limit(5)
                                .collect(Collectors.toList());
                summary.setLowStockProducts(lowStockList);

                // Get recent activities
                List<Stock_Logs> allLogs = logsRepo.findAll();
                summary.setRecentActivities(allLogs.stream()
                                .sorted((a, b) -> b.getAction_date().compareTo(a.getAction_date()))
                                .limit(10)
                                .collect(Collectors.toList()));

                // Category distribution
                summary.setCategoryDistribution(categoryRepo.findAll().stream()
                                .map(cat -> new DashboardSummaryDTO.CategoryDistributionDTO(
                                                cat.getName(),
                                                allProducts.stream()
                                                                .filter(p -> p.getCategories_id() != null && p
                                                                                .getCategories_id().getCategories_id()
                                                                                .equals(cat.getCategories_id()))
                                                                .count()))
                                .collect(Collectors.toList()));

                // Movement trends (Last 7 days)
                List<DashboardSummaryDTO.MovementTrendDTO> trends = new ArrayList<>();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
                OffsetDateTime today = OffsetDateTime.now();

                for (int i = 6; i >= 0; i--) {
                        OffsetDateTime targetDay = today.minusDays(i);
                        String dateStr = targetDay.format(formatter);

                        long stockIn = allLogs.stream()
                                        .filter(log -> log.getAction_date().toLocalDate().isEqual(
                                                        targetDay.toLocalDate()) && log.getAction() == actions.IN)
                                        .mapToLong(Stock_Logs::getQuantity)
                                        .sum();

                        long stockOut = allLogs.stream()
                                        .filter(log -> log.getAction_date().toLocalDate().isEqual(
                                                        targetDay.toLocalDate()) && log.getAction() == actions.OUT)
                                        .mapToLong(Stock_Logs::getQuantity)
                                        .sum();

                        trends.add(new DashboardSummaryDTO.MovementTrendDTO(dateStr, stockIn, stockOut));
                }
                summary.setMovementTrends(trends);

                return summary;
        }
}
