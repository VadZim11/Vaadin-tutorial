package com.example.tutorial.form;

import java.util.Set;

import com.example.tutorial.MyUI;
import com.example.tutorial.entity.HotelCategory;
import com.example.tutorial.service.HotelCategoryService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class HotelCategoryForm  extends FormLayout{

private TextField hotelCategoryField = new TextField("Hotel Category");
private Button save = new Button("Save");
private Button delete = new Button("Delete");


private HotelCategoryService service = HotelCategoryService.getInstance();
private HotelCategory hotelCategory;
private MyUI myUI;
private Binder<HotelCategory> binder =new Binder<>(HotelCategory.class);

public HotelCategoryForm(MyUI myUI) {
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
		  .asRequired("Hotel category in not null")
		  .bind(HotelCategory:: getHotelCategory, HotelCategory:: setHotelCategory);
}

public void setHotelCategory(HotelCategory hotelCategory) {
	this.hotelCategory = hotelCategory;
	binder.setBean(hotelCategory);
	
	delete.setVisible(hotelCategory.isPersisted());
	setVisible(true);
	hotelCategoryField.selectAll();
}

private void delete() {
	service.delete(hotelCategory);
	myUI.updateHotelCategory();
	setVisible(false);
}

public void delete(Set<HotelCategory> set) {
	for (HotelCategory hotelCategory : set) {
		service.delete(hotelCategory);
	}
	myUI.updateHotelCategory();
	setVisible(false);
}

private void save() {
	service.save(hotelCategory);
	myUI.updateHotelCategory();
	
	setVisible(false);
}

}
