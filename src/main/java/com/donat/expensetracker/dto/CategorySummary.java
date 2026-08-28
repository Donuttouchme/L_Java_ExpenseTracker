package com.donat.expensetracker.dto;

import java.math.BigDecimal;

public class CategorySummary {
    private String categoryName;
    private BigDecimal total;

    public CategorySummary(String _categoryName, BigDecimal _total){
        categoryName = _categoryName;
        total = _total;
    }

    public void setCategoryName(String _categoryName) {
        categoryName = _categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setTotal(BigDecimal _total) {
        total = _total;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
