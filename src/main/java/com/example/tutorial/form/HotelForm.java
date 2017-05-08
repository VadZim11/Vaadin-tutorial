package com.example.tutorial.form;

import com.example.tutorial.MyUI;
import com.example.tutorial.converter.DataConverter;
import com.example.tutorial.entity.Hotel;
import com.example.tutorial.entity.HotelCategory;
import com.example.tutorial.service.HotelCategoryService;
import com.example.tutorial.service.HotelService;
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
	private NativeSelect<HotelCategory> category = new NativeSelect<>("Category");
	private TextField url = new TextField("URL");
	private TextArea description = new TextArea("Description");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private HotelService service = HotelService.getInstance();
	private Hotel hotel;
	private MyUI myUI;
	private Binder<Hotel> binder = new Binder<>(Hotel.class);
	
	public HotelForm(MyUI myUI){
		this.myUI = myUI;
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save,delete);
		addComponents(name,address,rating, operatesFrom, category, url, description, buttons);
		category.setItems(HotelCategoryService.getInstance().findAll().toArray(new HotelCategory[(int)HotelCategoryService.getInstance().count()]));
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		toolTipFields();
		bindFields();
	}
	
	public void bindFields(){
		binder.forField(rating)
			  .asRequired("Rating in not null")
			  .withConverter(new StringToIntegerConverter(0, "Only digits!"))			  
			  .withValidator(v -> (v < 6 ), "Rating in not > 5")
			  .withValidator(v -> (v >= 0), "Rating in not < 0")
			  .bind(Hotel:: getRating, Hotel:: setRating);
		
		binder.forField(operatesFrom)
			  .asRequired("OperatesFrom in not null")
			  .withConverter(new DataConverter())
			  .withValidator(v -> (v >= 0), "OperatesFrom in not > Today")
			  .bind(Hotel:: getOperatesFrom, Hotel:: setOperatesFrom);
		
		binder.forField(name)
			  .asRequired("Name in not null")
			  .bind(Hotel:: getName, Hotel:: setName);
		
		binder.forField(address)
		  	  .asRequired("Address in not null")
			  .bind(Hotel:: getAddress, Hotel:: setAddress);		
		
		binder.forField(category)
		  	  .asRequired("Category in not null")
			  .bind(Hotel:: getCategory, Hotel:: setCategory);
		
		binder.forField(url)
		  	  .asRequired("Url in not null")
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
		category.setItems(HotelCategoryService.getInstance().findAll()
				.toArray(new HotelCategory[(int)HotelCategoryService.getInstance().count()]));
	}
	
	public void setHotel(Hotel hotel){
		this.hotel = hotel;
		binder.setBean(hotel);
		
		delete.setVisible(hotel.isPersisted());
		setVisible(true);
		name.selectAll();
	}
	
	private void delete(){
		service.delete(hotel);
		myUI.updateHotels();
		setVisible(false);
	}
	
	private void save(){
		service.save(hotel);
		myUI.updateHotels();
		setVisible(false);
	}
}