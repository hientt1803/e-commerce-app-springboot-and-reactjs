package com.poly.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ReportByProduct {
    @Id
    Serializable product;
    Serializable category;
    Serializable quantity;
    Double totalPrice;

    public ReportByProduct(Serializable product, Serializable category, Serializable quantity, Double totalPrice) {
        this.product = product;
        this.category = category;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
