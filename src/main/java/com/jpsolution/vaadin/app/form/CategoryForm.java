package com.jpsolution.vaadin.app.form;

import java.util.Set;

import com.jpsolution.vaadin.app.views.CategoryView;
import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CategoryForm extends FormLayout{
	private TextField categoryField = new TextField("Hotel Category");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private CategoryServiceImpl categoryServiceImpl;
	private Category category;
	private CategoryView myUI;
	private CategoryServiceImpl service;

	private Binder<Category> binder =new Binder<>(Category.class);

	public CategoryForm(CategoryView myUI, CategoryServiceImpl service) {
		this.myUI = myUI;
		this.service = service;
		this.setVisible(false);
	
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		addComponents(categoryField, buttons);
	
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
	
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
	
		categoryField.setDescription("Enter the hotel category");
		binder.forField(categoryField)
		  	  .asRequired("Hotel category in not null")
		  	  .bind(Category:: getCategory, Category:: setCategory);
	}

	public void setCategory(Category category) {
		this.category = category;
		binder.setBean(category);
	
		delete.setVisible(category.isPersisted());
		setVisible(true);
		categoryField.selectAll();
		binder.validate();
}

	private void delete() {
		service.deleteCategory(category);
		setVisible(false);
	}

	public void delete(Set<Category> set) {
		for (Category category : set) {
			service.deleteCategory(category);
		}
		setVisible(false);
	}

	private void save() {
		binder.validate();
		if (binder.isValid()) {
			service.saveCategory(category);
			setVisible(false);
		}
	}
}
