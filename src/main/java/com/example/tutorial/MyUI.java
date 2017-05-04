package com.example.tutorial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
public class MyUI extends UI {
	
	private HotelService service = HotelService.getInstance();
	private Grid<Hotel> gridHotel = new Grid<>(Hotel.class);
	private TextField filterName = new TextField();
	private TextField filterAdress = new TextField();
	private HotelForm hotelForm = new HotelForm(this);
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        filterName.setPlaceholder("filtering by name...");
        filterName.addValueChangeListener(e -> updateHotels());
        filterName.setValueChangeMode(ValueChangeMode.LAZY);
        
        filterAdress.setPlaceholder("filtering by address...");
        filterAdress.addValueChangeListener(e -> updateHotels());
        filterAdress.setValueChangeMode(ValueChangeMode.LAZY);
        
        Button clearFilterNameButton = new Button(VaadinIcons.CLOSE);
        clearFilterNameButton.setDescription("Name filter...");
        clearFilterNameButton.addClickListener(e -> filterName.clear());
        
        Button clearFilterAdressButton = new Button(VaadinIcons.CLOSE);
        clearFilterAdressButton.setDescription("Adress filter...");
        clearFilterAdressButton.addClickListener(e -> filterAdress.clear());
        
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterName, clearFilterNameButton,filterAdress, clearFilterAdressButton);
    
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        Button addHotelButton = new Button("Add hotel...");
        addHotelButton.addClickListener(e -> {
        	gridHotel.asSingleSelect().clear();
        	hotelForm.setHotel(new Hotel());
        });
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addHotelButton);
        
        
        gridHotel.setColumns("name","address","rating","operatesFrom","category","specification");
        
        gridHotel.addColumn(e ->"<a href='" + e.getUrl() + "' target='_top'>Explore website</a>",
        new HtmlRenderer());
        
        
        HorizontalLayout main = new HorizontalLayout(gridHotel, hotelForm);
        main.setSizeFull();
        gridHotel.setSizeFull();
        main.setExpandRatio(gridHotel, 1);
        
        layout.addComponents(toolbar, main);
        
        updateHotels();
        
        setContent(layout);
        
        hotelForm.setVisible(false);
        
        gridHotel.asSingleSelect().addValueChangeListener(event -> {
        	if(event.getValue()==null){
        		hotelForm.setVisible(false);
        	} else {
        		hotelForm.setHotel(event.getValue());
        	}
        });
    }

	public void updateHotels(){
		List<Hotel> hotel = service.findAllByNameAndAddress(filterName.getValue(), filterAdress.getValue());
	
        gridHotel.setItems(hotel);
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}