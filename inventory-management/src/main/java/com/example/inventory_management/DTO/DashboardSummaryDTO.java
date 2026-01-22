package com.example.inventory_management.DTO;

import java.util.List;
import com.example.inventory_management.entity.Stock_Logs;
import com.example.inventory_management.entity.Products;

public class DashboardSummaryDTO {
    private long totalProducts;
    private long totalCategories;
    private long lowStockCount;
    private long totalSuppliers;
    private List<Stock_Logs> recentActivities;
    private List<Products> lowStockProducts;
    private List<CategoryDistributionDTO> categoryDistribution;
    private List<MovementTrendDTO> movementTrends;

    public DashboardSummaryDTO() {
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    public long getTotalSuppliers() {
        return totalSuppliers;
    }

    public void setTotalSuppliers(long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }

    public List<Stock_Logs> getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List<Stock_Logs> recentActivities) {
        this.recentActivities = recentActivities;
    }

    public List<Products> getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(List<Products> lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public List<CategoryDistributionDTO> getCategoryDistribution() {
        return categoryDistribution;
    }

    public void setCategoryDistribution(List<CategoryDistributionDTO> categoryDistribution) {
        this.categoryDistribution = categoryDistribution;
    }

    public List<MovementTrendDTO> getMovementTrends() {
        return movementTrends;
    }

    public void setMovementTrends(List<MovementTrendDTO> movementTrends) {
        this.movementTrends = movementTrends;
    }

    public static class MovementTrendDTO {
        private String date;
        private long stockIn;
        private long stockOut;

        public MovementTrendDTO(String date, long stockIn, long stockOut) {
            this.date = date;
            this.stockIn = stockIn;
            this.stockOut = stockOut;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getStockIn() {
            return stockIn;
        }

        public void setStockIn(long stockIn) {
            this.stockIn = stockIn;
        }

        public long getStockOut() {
            return stockOut;
        }

        public void setStockOut(long stockOut) {
            this.stockOut = stockOut;
        }
    }

    public static class CategoryDistributionDTO {
        private String name;
        private long value;

        public CategoryDistributionDTO(String name, long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }
}
