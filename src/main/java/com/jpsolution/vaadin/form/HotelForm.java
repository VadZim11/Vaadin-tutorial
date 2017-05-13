package com.jpsolution.vaadin.form;

import com.jpsolution.vaadin.MyUI;
import com.jpsolution.vaadin.converter.DataConverter;
import com.jpsolution.vaadin.entity.HotelEntity;
import com.jpsolution.vaadin.entity.CategoryEntity;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.jpsolution.vaadin.service.impl.HotelServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class HotelForm extends FormLayout{

	private TextField name = new TextField("Name");
	private TextField address = new TextField("Address");
	private TextField rating = new TextField("Rating");
	private DateField operatesFrom = new DateField("Operates From");
	private NativeSelect<CategoryEntity> category = new NativeSelect<>("Category");
	private TextField url = new TextField("URL");
	private TextArea description = new TextArea("Description");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private HotelServiceImpl service = HotelServiceImpl.getInstance();
	private HotelEntity hotelEntity;
	private MyUI myUI;
	private Binder<HotelEntity> binder = new Binder<>(HotelEntity.class);
	
	public HotelForm(MyUI myUI){
		this.myUI = myUI;
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save,delete);
		addComponents(name,address,rating, operatesFrom, category, url, description, buttons);
		category.setItems(CategoryServiceImpl.getInstance().findAll().toArray(new CategoryEntity[(int) CategoryServiceImpl.getInstance().count()]));
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		toolTipFields();
		bindFields();
	}
	
	public void bindFields(){
		binder.forField(rating)
			  .asRequired("Rating is not null")
			  .withConverter(new StringToIntegerConverter(0, "Only digits!"))			  
			  .withValidator(v -> (v < 6 ), "Rating is not > 5")
			  .withValidator(v -> (v >= 0), "Rating is not < 0")
			  .bind(HotelEntity:: getRating, HotelEntity:: setRating);
		
		binder.forField(operatesFrom)
			  .asRequired("OperatesFrom is not null")
			  .withConverter(new DataConverter())
			  .withValidator(v -> (v >= 0), "OperatesFrom is not > Today")
			  .bind(HotelEntity:: getOperatesFrom, HotelEntity:: setOperatesFrom);
		
		binder.forField(name)
			  .asRequired("Name is not null")
			  .bind(HotelEntity:: getName, HotelEntity:: setName);
		
		binder.forField(address)
		  	  .asRequired("Address is not null")
			  .bind(HotelEntity:: getAddress, HotelEntity:: setAddress);

		binder.forField(category)
		  	  .asRequired("Category is not null")
			  .bind(HotelEntity:: getCategory, HotelEntity:: setCategory);

		binder.forField(url)
		  	  .asRequired("Url is not null")
			  .bind(HotelEntity:: getUrl, HotelEntity:: setUrl);
		
		binder.forField(description)
			  .bind(HotelEntity:: getDescription, HotelEntity:: setDescription);
	} 
		
	 public void toolTipFields(){
		 name.setDescription("Enter the name of the hotelEntity");
		 address.setDescription("Enter the address of the hotelEntity");
		 rating.setDescription("Enter the hotelEntity rating");
		 operatesFrom.setDescription("Enter from what date does the hotelEntity operate");
		 category.setDescription("Enter hotelEntity category");
		 url.setDescription("Enter the link to the hotelEntity's website");
		 description.setDescription("Enter your description of the hotelEntity");
		 save.setDescription("Save");
		 delete.setDescription("Delete");
		
	}

	public void refreshField() {
		category.clear();
		category.setItems(CategoryServiceImpl.getInstance().findAll()
				.toArray(new CategoryEntity[(int) CategoryServiceImpl.getInstance().count()]));
	}
	
	public void setHotelEntity(HotelEntity hotelEntity){
		this.hotelEntity = hotelEntity;
		binder.setBean(hotelEntity);
		
		delete.setVisible(hotelEntity.isPersisted());
		setVisible(true);
		name.selectAll();
		binder.validate();
	}
	
	private void delete(){
		service.delete(hotelEntity);
		myUI.updateHotels();
		setVisible(false);
	}
	
	private void save(){
		binder.validate();
		if (binder.isValid()) {
			service.save(hotelEntity);
			myUI.updateHotels();
			setVisible(false);
		}
	}
}