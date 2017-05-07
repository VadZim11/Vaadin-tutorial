package com.example.tutorial;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
public class MyUI extends UI {
	
	private HotelService service = HotelService.getInstance();
	private Grid<Hotel> gridHotel = new Grid<>(Hotel.class);
	private TextField filterName = new TextField();
	private TextField filterAddress = new TextField();
	private HotelForm hotelForm = new HotelForm(this);
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
                
        filterName.setPlaceholder("filtering by name");
        filterName.addValueChangeListener(e -> updateHotels());
        filterName.setValueChangeMode(ValueChangeMode.LAZY);
        filterName.setDescription("filtering by name");
        
        filterAddress.setPlaceholder("filtering by address");
        filterAddress.addValueChangeListener(e -> updateHotels());
        filterAddress.setValueChangeMode(ValueChangeMode.LAZY);
        filterAddress.setDescription("filtering by address");
        
        Button clearFilterNameButton = new Button(VaadinIcons.CLOSE);
        clearFilterNameButton.setDescription("Name filter");
        clearFilterNameButton.addClickListener(e -> filterName.clear());
        
        Button clearFilterAddressButton = new Button(VaadinIcons.CLOSE);
        clearFilterAddressButton.setDescription("Address filter");
        clearFilterAddressButton.addClickListener(e -> filterAddress.clear());
        
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterName, clearFilterNameButton,filterAddress, clearFilterAddressButton);
    
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        
        Button addHotelButton = new Button("Add hotel");
        addHotelButton.setDescription("Add new hotel");
        addHotelButton.addClickListener(e -> {
        	gridHotel.asSingleSelect().clear();
        	hotelForm.setHotel(new Hotel());
        });
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addHotelButton);
        
        
        gridHotel.setColumns("name","address","rating","operatesFrom","category","description");
        
        gridHotel.addColumn(e ->"<a href='" + e.getUrl()
                           + "' target='_blank'>" + e.getUrl() +"</a>",new HtmlRenderer())
                .setCaption("Url");
        
        
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
		List<Hotel> hotel = service.findAll(filterName.getValue(), filterAddress.getValue());
	
        gridHotel.setItems(hotel);
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
