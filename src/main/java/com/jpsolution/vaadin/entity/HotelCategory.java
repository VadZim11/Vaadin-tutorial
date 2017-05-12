package com.jpsolution.vaadin.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HotelCategory implements Serializable, Cloneable {
	
	private Long id;
	
	private String hotelCategory = "";
	
	public boolean isPersisted() {
		return id != null;
	}
	
	@Override
	public String toString() {
		return hotelCategory;
	}
	
	@Override
	public HotelCategory clone() throws CloneNotSupportedException {
		return (HotelCategory) super.clone();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public HotelCategory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHotelCategory() {
		return hotelCategory;
	}

	public void setHotelCategory(String hotelCategory) {
		this.hotelCategory = hotelCategory;
	}

	public HotelCategory(Long id, String hotelCategory) {
		super();
		this.id = id;
		this.hotelCategory = hotelCategory;
	}
	
}
