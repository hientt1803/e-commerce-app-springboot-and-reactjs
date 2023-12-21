package com.poly.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payments")
public class Payment implements Serializable {

	@Id
	@Column(name = "id_paymemt")
	private Integer idPaymemt;
	private String name;
	
	@OneToMany(mappedBy = "payment")
	private List<Order> orders;
	
	public Payment(Integer id) {
	    this.idPaymemt = id;
	}
	
	@Override
	public String toString() {
		return "Payment [idPaymemt=" + idPaymemt + ", name=" + name + "]";
	}
}