package com.example.tutorial;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.example.tutorial.entity.Hotel;
import com.example.tutorial.entity.HotelCategory;
import com.example.tutorial.form.HotelCategoryForm;
import com.example.tutorial.form.HotelForm;
import com.example.tutorial.service.HotelCategoryService;
import com.example.tutorial.service.HotelService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("mytheme")
public class MyUI extends UI {
	
	private HotelService serviceHotel = HotelService.getInstance();
	private HotelCategoryService serviceHotelCategory = HotelCategoryService.getInstance();
	
	private Grid<Hotel> gridHotel = new Grid<>(Hotel.class);
	private Grid<HotelCategory> gridHotelCategory = new Grid<>(HotelCategory.class);
	
	private TextField filterName = new TextField();
	private TextField filterAddress = new TextField();

	private HotelForm formHotel = new HotelForm(this);
    private HotelCategoryForm formHotelCategory = new HotelCategoryForm(this);
    private MenuBar barmenu = new MenuBar();
    private Label selection = new Label();
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
                
        filterName.setPlaceholder("filtering by name...");
        filterName.addValueChangeListener(e -> updateHotels());
        filterName.setValueChangeMode(ValueChangeMode.LAZY);
        filterName.setDescription("filtering by name");
        
        filterAddress.setPlaceholder("filtering by address...");
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
            formHotel.setHotel(new Hotel(0L, "", "", 0, 0L, serviceHotelCategory.getDefault(), "", ""));
        });
        
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addHotelButton);
        
        
        gridHotel.setColumns("name","address","rating","operatesFrom","category","description");
        
        gridHotel.addColumn(e ->"<a href='" + e.getUrl()
                           + "' target='_blank'>" + e.getUrl() +"</a>",new HtmlRenderer())
                .setCaption("Url");
        
        
        HorizontalLayout main = new HorizontalLayout(gridHotel, formHotel);
        main.setSizeFull();
        gridHotel.setSizeFull();
        main.setExpandRatio(gridHotel, 1);
        
        layout.addComponents(selection, barmenu, toolbar, main);
        
        updateHotels();
        
        setContent(layout);

        formHotel.setVisible(false);
        
        gridHotel.asSingleSelect().addValueChangeListener(event -> {
        	if(event.getValue()==null){
                formHotel.setVisible(false);
        	} else {
                formHotel.setHotel(event.getValue());
        	}
        });

        MenuBar.Command  hotelCategories = new MenuBar.Command() {
            MenuItem previous = null;
            @Override
            public void menuSelected(MenuItem selectedItem) {

                selection.setValue("Open " + selectedItem.getText());
                if (previous != null) previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;

                gridHotelCategory.setSelectionMode(SelectionMode.MULTI);
                formHotelCategory.setVisible(false);

                Button addNewHotelCategoryButton = new Button("New");
                addNewHotelCategoryButton.setDescription("Add new Hotel Category");
                addNewHotelCategoryButton.addClickListener(e -> {
                    gridHotelCategory.asMultiSelect().clear();
                    formHotelCategory.setHotelCategory(new HotelCategory(null, ""));
                    gridHotelCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                Button editHotelCategoryButton = new Button("Edit");
                editHotelCategoryButton.setDescription("Edit Hotel Category");
                editHotelCategoryButton.addClickListener(event -> {
                    formHotelCategory.setHotelCategory(gridHotelCategory.asMultiSelect().getSelectedItems().iterator().next());
                    gridHotelCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                Button deleteHotelCategoryButton = new Button("Delete");
                deleteHotelCategoryButton.setDescription("Delete Hotel Category");
                deleteHotelCategoryButton.addClickListener(e -> {
                    formHotelCategory.delete(gridHotelCategory.asMultiSelect().getSelectedItems());
                    gridHotelCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                addNewHotelCategoryButton.setVisible(true);
                editHotelCategoryButton.setVisible(false);
                deleteHotelCategoryButton.setVisible(false);

                gridHotelCategory.asMultiSelect().addValueChangeListener(event -> {
                    if(event.getValue().size() == 1) {
                        editHotelCategoryButton.setVisible(true);
                        deleteHotelCategoryButton.setVisible(true);
                    } else if(event.getValue().size() == 0) {
                        editHotelCategoryButton.setVisible(false);
                        deleteHotelCategoryButton.setVisible(false);
                    }
                    else if(event.getValue().size() > 1) {
                        editHotelCategoryButton.setVisible(false);
                        deleteHotelCategoryButton.setVisible(true);
                    }
                });

                HorizontalLayout toolbar = new HorizontalLayout(addNewHotelCategoryButton,
                                         editHotelCategoryButton, deleteHotelCategoryButton);
                HorizontalLayout category = new HorizontalLayout(gridHotelCategory, formHotelCategory);
                gridHotelCategory.setSizeUndefined();

                gridHotelCategory.asMultiSelect().clear();
                gridHotelCategory.setColumns("hotelCategory");

                layout.removeAllComponents();
                layout.addComponents(selection, barmenu, toolbar, category);

                updateHotelCategory();
            }
        };

        MenuBar.Command hotelList = new MenuBar.Command() {
            MenuItem previous = null;
            @Override
            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Open " + selectedItem.getText());
                if (previous != null) previous.setStyleName(null);
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
                layout.removeAllComponents();
                layout.addComponents(selection, barmenu, toolbar, main);

                updateHotels();
            }
        };

        barmenu.addItem("Hotel List", null, hotelList);
        barmenu.addItem("Hotel Categories", null, hotelCategories);
    }    

	public void updateHotels(){
		List<Hotel> hotel = serviceHotel.findAll(filterName.getValue(), filterAddress.getValue());
	
        gridHotel.setItems(hotel);
	}
	
	public void updateHotelCategory(){
		List<HotelCategory> hotelCategories = serviceHotelCategory.findAll();
        gridHotelCategory.setItems(hotelCategories);
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
