package com.jpsolution.vaadin;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.jpsolution.vaadin.entity.Hotel;
import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.form.CategoryForm;
import com.jpsolution.vaadin.form.HotelForm;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.jpsolution.vaadin.service.impl.HotelServiceImpl;
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
	
	private HotelServiceImpl serviceHotel = HotelServiceImpl.getInstance();
	private CategoryServiceImpl serviceCategory = CategoryServiceImpl.getInstance();
	
	private Grid<Hotel> gridHotel = new Grid<>(Hotel.class);
	private Grid<Category> gridCategory = new Grid<>(Category.class);
	
	private TextField filterName = new TextField();
	private TextField filterAddress = new TextField();

	private HotelForm formHotel = new HotelForm(this);
    private CategoryForm formCategory = new CategoryForm(this);
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
            formHotel.setHotelEntity(new Hotel(0L, "",0L, "", 0, 0L, serviceCategory.getDefault(), "", " "));
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
                formHotel.setHotelEntity(event.getValue());
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

                gridCategory.setSelectionMode(SelectionMode.MULTI);
                formCategory.setVisible(false);

                Button addNewHotelCategoryButton = new Button("New");
                addNewHotelCategoryButton.setDescription("Add new Hotel Category");
                addNewHotelCategoryButton.addClickListener(e -> {
                    gridCategory.asMultiSelect().clear();
                    formCategory.setCategory(new Category(null, ""));
                    gridCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                Button editCategoryButton = new Button("Edit");
                editCategoryButton.setDescription("Edit Hotel Category");
                editCategoryButton.addClickListener(event -> {
                    formCategory.setCategory(gridCategory.asMultiSelect().getSelectedItems().iterator().next());
                    gridCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                Button deleteCategoryButton = new Button("Delete");
                deleteCategoryButton.setDescription("Delete Hotel Category");
                deleteCategoryButton.addClickListener(e -> {
                    formCategory.delete(gridCategory.asMultiSelect().getSelectedItems());
                    gridCategory.asMultiSelect().clear();
                    formHotel.refreshField();
                });

                addNewHotelCategoryButton.setVisible(true);
                editCategoryButton.setVisible(false);
                deleteCategoryButton.setVisible(false);

                gridCategory.asMultiSelect().addValueChangeListener(event -> {
                    if(event.getValue().size() == 1) {
                        editCategoryButton.setVisible(true);
                        deleteCategoryButton.setVisible(true);
                    } else if(event.getValue().size() == 0) {
                        editCategoryButton.setVisible(false);
                        deleteCategoryButton.setVisible(false);
                    }
                    else if(event.getValue().size() > 1) {
                        editCategoryButton.setVisible(false);
                        deleteCategoryButton.setVisible(true);
                    }
                });

                HorizontalLayout toolbar = new HorizontalLayout(addNewHotelCategoryButton,
                                         editCategoryButton, deleteCategoryButton);
                HorizontalLayout category = new HorizontalLayout(gridCategory, formCategory);
                gridCategory.setSizeUndefined();

                gridCategory.asMultiSelect().clear();
                gridCategory.setColumns("category");

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
		List<Category> hotelCategories = serviceCategory.findAll();
        gridCategory.setItems(hotelCategories);
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
