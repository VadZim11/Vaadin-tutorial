package com.jpsolution.vaadin.form;

import java.util.Set;

import com.jpsolution.vaadin.MyUI;
import com.jpsolution.vaadin.entity.CategoryEntity;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CategoryForm extends FormLayout{

	private TextField hotelCategoryField = new TextField("HotelEntity Category");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");


	private CategoryServiceImpl service = CategoryServiceImpl.getInstance();
	private CategoryEntity categoryEntity;
	private MyUI myUI;
	private Binder<CategoryEntity> binder =new Binder<>(CategoryEntity.class);

	public CategoryForm(MyUI myUI) {
		this.myUI = myUI;
	
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(hotelCategoryField, buttons);
	
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
	
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
	
		hotelCategoryField.setDescription("Enter the hotel category");
		binder.forField(hotelCategoryField)
		  	  .asRequired("HotelEntity category in not null")
		  	  .bind(CategoryEntity:: getCategory, CategoryEntity:: setCategory);
	}

	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
		binder.setBean(categoryEntity);
	
		delete.setVisible(categoryEntity.isPersisted());
		setVisible(true);
		hotelCategoryField.selectAll();
		binder.validate();
}

	private void delete() {
		service.delete(categoryEntity);
		myUI.updateHotelCategory();
		setVisible(false);
	}

	public void delete(Set<CategoryEntity> set) {
		for (CategoryEntity categoryEntity : set) {
			service.delete(categoryEntity);
		}
		myUI.updateHotelCategory();
		setVisible(false);
	}

	private void save() {
		binder.validate();
		if (binder.isValid()) {
			service.save(categoryEntity);
			myUI.updateHotelCategory();
			setVisible(false);
		}
	}
}
