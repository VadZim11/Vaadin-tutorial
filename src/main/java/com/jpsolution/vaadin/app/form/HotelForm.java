package com.jpsolution.vaadin.app.form;

import com.jpsolution.vaadin.app.MyUI;
import com.jpsolution.vaadin.app.views.HotelView;
import com.jpsolution.vaadin.converter.DataConverter;
import com.jpsolution.vaadin.entity.Hotel;
import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.jpsolution.vaadin.service.impl.HotelServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class HotelForm extends FormLayout{

	private TextField name = new TextField("Name");
	private TextField address = new TextField("Address");
	private TextField rating = new TextField("Rating");
	private TextField url = new TextField("URL");
	private TextArea description = new TextArea("Description");
	private DateField operatesFrom = new DateField("Operates From");
	private NativeSelect<Category> category = new NativeSelect<>("Category");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private HotelServiceImpl service;
	private Hotel hotel;
	private HotelView myUI;
	private Binder<Hotel> binder = new Binder<>(Hotel.class);
	
	public HotelForm(HotelView myUI, HotelServiceImpl service){
		this.myUI = myUI;
		this.service=service;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save,delete);
		addComponents(name,address,rating, operatesFrom, category, url, description, buttons);
		refreshField();
		category.setItemCaptionGenerator(Category::getCategory);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> saveHotel());
		delete.addClickListener(e -> deleteHotel());
		toolTipFields();
		bindFields();
	}
	
	public void bindFields(){
		binder.forField(rating)
			  .asRequired("Rating is not null")
			  .withConverter(new StringToIntegerConverter(0, "Only digits!"))			  
			  .withValidator(v -> (v < 6 ), "Rating is not > 5")
			  .withValidator(v -> (v >= 0), "Rating is not < 0")
			  .bind(Hotel:: getRating, Hotel:: setRating);
		
		binder.forField(operatesFrom)
			  .asRequired("OperatesFrom is not null")
			  .withConverter(new DataConverter())
			  .withValidator(v -> (v >= 0), "OperatesFrom is not > Today")
			  .bind(Hotel:: getOperatesFrom, Hotel:: setOperatesFrom);
		
		binder.forField(name)
			  .asRequired("Name is not null")
			  .bind(Hotel:: getName, Hotel:: setName);
		
		binder.forField(address)
		  	  .asRequired("Address is not null")
			  .bind(Hotel:: getAddress, Hotel:: setAddress);

		binder.forField(category)
		  	  .asRequired("Category is not null")
			  .bind(Hotel:: getCategory, Hotel:: setCategory);

		binder.forField(url)
		  	  .asRequired("Url is not null")
			  .bind(Hotel:: getUrl, Hotel:: setUrl);
		
		binder.forField(description)
			  .bind(Hotel:: getDescription, Hotel:: setDescription);
	} 
		
	 public void toolTipFields(){
		 name.setDescription("Enter the name of the hotel");
		 address.setDescription("Enter the address of the hotel");
		 rating.setDescription("Enter the hotel rating");
		 operatesFrom.setDescription("Enter from what date does the hotel operate");
		 category.setDescription("Enter hotel category");
		 url.setDescription("Enter the link to the hotel's website");
		 description.setDescription("Enter your description of the hotel");
		 save.setDescription("Save");
		 delete.setDescription("Delete");
	}

	public void refreshField() {
		category.clear();
		category.setItems(service.getCategories());
	}

	public Hotel getHotel() {
		return hotel;
	}
	
	public void setHotel(Hotel hotel){
		refreshField();
		this.hotel = hotel;
		binder.setBean(hotel);
		delete.setVisible(hotel.isPersisted());
		setVisible(true);
		name.selectAll();
		binder.validate();
	}
	
	private void deleteHotel(){
		service.deleteHotel(hotel);
		this.myUI.updateHotels();
		setVisible(false);
	}
	
	private void saveHotel(){
		if (binder.isValid()) {
			service.saveHotel(this.hotel);
			this.myUI.updateHotels();
			setVisible(false);
		} else {
			Notification.show("Fields not correctly!!");
			binder.validate();
		}
	}
}