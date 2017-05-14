package com.jpsolution.vaadin.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity{

	private String category;

	public Category() {
	}

	@Basic
	@Column(name = "CATEGORY")
	@NotNull(message = "Hotel category is required")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Category(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}

	@Override
	public Category clone() throws CloneNotSupportedException {
		return (Category) super.clone();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (category != null ? category.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}

	@Transient
	public boolean isPersisted() {
		return getId()!=null;
	}

}
