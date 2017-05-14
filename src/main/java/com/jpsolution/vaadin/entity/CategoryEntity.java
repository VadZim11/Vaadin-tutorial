package com.jpsolution.vaadin.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name = "category")
public class CategoryEntity {

	private Long id;
	private String category;

	public CategoryEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CategoryEntity(Long id, String category) {
		this.id = id;
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}

	@Override
	public CategoryEntity clone() throws CloneNotSupportedException {
		return (CategoryEntity) super.clone();
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
		CategoryEntity other = (CategoryEntity) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}

	@Transient
	public boolean isPersisted() {
		return id != null;
	}

}
