package com.poly.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class ReportByUser {
    @Id
    Serializable user;
    Serializable address;
    Serializable totalOrder;
    Serializable totalPrice;

}
