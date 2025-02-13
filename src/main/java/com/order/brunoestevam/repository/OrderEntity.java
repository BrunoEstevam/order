package com.order.brunoestevam.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "tb_order")
public class OrderEntity implements Serializable {
	
	private static final long serialVersionUID = -3113035883977568537L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "id_customer")
    private Long idCustomer;
	
	@Column(name = "idempotent_key")
	private String idempotentKey;
    
	@Column(name = "status")
    private String status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items;
    
	@Column(name = "total_price")
    private BigDecimal totalPrice;
	
	@Version
    private Long version;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdempotentKey() {
		return idempotentKey;
	}
	
	public void setIdempotentKey(String idempotentKey) {
		this.idempotentKey = idempotentKey;
	}
	
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemEntity> getItems() {
		return items;
	}

	public void setItems(List<ItemEntity> items) {
		this.items = items;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
