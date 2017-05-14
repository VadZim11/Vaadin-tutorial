package com.jpsolution.vaadin.form;

import java.util.Set;

import com.jpsolution.vaadin.MyUI;
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


	private CategoryServiceImpl service = CategoryServiceImpl.getInstance();
	private Category category;
	private MyUI myUI;
	private Binder<Category> binder =new Binder<>(Category.class);

	public CategoryForm(MyUI myUI) {
		this.myUI = myUI;
	
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
		service.delete(category);
		myUI.updateHotelCategory();
		setVisible(false);
	}

	public void delete(Set<Category> set) {
		for (Category category : set) {
			service.delete(category);
		}
		myUI.updateHotelCategory();
		setVisible(false);
	}

	private void save() {
		binder.validate();
		if (binder.isValid()) {
			service.save(category);
			myUI.updateHotelCategory();
			setVisible(false);
		}
	}
}
