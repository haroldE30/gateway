package com.test.gateway.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DEVICE")
public class Device implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	private String vendor;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 8)
	private Status status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "gatewayId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Gateway gateway;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	public Gateway getGateway() {
		return gateway;
	}

	@JsonIgnore
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
}
