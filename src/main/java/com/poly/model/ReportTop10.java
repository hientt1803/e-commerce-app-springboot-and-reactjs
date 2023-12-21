package com.poly.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
// @AllArgsConstructor
public class ReportTop10 {
    @Id
    Serializable category;// category
    Serializable name;// category
    Double price;
    Serializable quantity;

    public ReportTop10(Serializable name, Serializable category, Double price, Serializable quantity) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
