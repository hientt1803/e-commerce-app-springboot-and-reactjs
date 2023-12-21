package com.poly.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
// @AllArgsConstructor
public class ReportByCategory {
    @Id
    Serializable category;
    Serializable quantity;
    Double totalPrice;

    public ReportByCategory(Serializable category, Serializable quantity, Double totalPrice) {
        this.category = category;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
