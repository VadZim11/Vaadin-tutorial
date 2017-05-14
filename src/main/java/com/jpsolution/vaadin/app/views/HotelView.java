package com.jpsolution.vaadin.app.views;


import com.jpsolution.vaadin.app.form.HotelForm;
import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.entity.Hotel;
import com.jpsolution.vaadin.service.impl.HotelServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.Grid.Column;

import javax.annotation.PostConstruct;

@Theme("mytheme")
@SpringView(name = HotelView.VIEW_NAME)
public class HotelView extends VerticalLayout implements View {
	@Autowired
	private HotelServiceImpl service;
	public static final String VIEW_NAME = "Hotel list";
	private Menu menu = new Menu();
	private Grid<Hotel> hotelGrid = new Grid<>(Hotel.class);
	private TextField filterField = new TextField();
	private TextField filterByAddressField = new TextField();
	private HotelForm hotelForm;
	private Button clearNameFilterButton = new Button(VaadinIcons.CLOSE_CIRCLE);
	private Button clearAddressFilterButton = new Button(VaadinIcons.CLOSE_CIRCLE);
	private CssLayout filtering = buildFilteringLayout();

	public void updateHotels() {
		hotelGrid.setItems(service.getHotel());
		filterField.clear();
		filterByAddressField.clear();
	}

	public HotelView() {
	}

	private void filter(){
		hotelGrid.setItems(service.filter(filterField.getValue(),filterByAddressField.getValue()));
	}

	private CssLayout buildFilteringLayout() {
		filterByAddressField.setPlaceholder("Enter address u want to see...");
		filterByAddressField.addValueChangeListener(e -> filter());
		clearNameFilterButton.addClickListener(e -> filterByAddressField.clear());
		CssLayout lay1 = new CssLayout(filterByAddressField, clearNameFilterButton);
		lay1.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		filterField.setPlaceholder("Enter ur searchstring...");
		filterField.addValueChangeListener(e -> filter());
		clearAddressFilterButton.addClickListener(e -> filterField.clear());
		CssLayout lay2 = new CssLayout(filterField, clearAddressFilterButton);
		lay2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		CssLayout lay = new CssLayout(lay2, lay1);
		return lay;
	}

	@PostConstruct
	void init() {
		final VerticalLayout layout = new VerticalLayout();
		this.hotelForm=new HotelForm(this,service);
		hotelGrid.setColumns("name","address","rating","operatesFrom","category","description");
		Column<Hotel, String> htmlColumn = hotelGrid.addColumn(
				e -> "<a href='" + e.getUrl()+ "' target='_blank'>" + e.getUrl() +"</a>",
				new HtmlRenderer());
		htmlColumn.setCaption("Link");
		Column<Hotel, String> column= hotelGrid.addColumn(e -> e.getCategory().getCategory());
		column.setCaption("Category");
		updateHotels();
		HorizontalLayout main = new HorizontalLayout(hotelGrid, hotelForm);
		main.setSizeFull();
		hotelGrid.setSizeFull();
		main.setExpandRatio(hotelGrid, 1);

		hotelForm.setVisible(false);
		hotelGrid.asSingleSelect().addValueChangeListener(e -> {
			if (e.getValue() == null) {
				hotelForm.setVisible(false);
			} else
				hotelForm.setHotel(e.getValue());
		});
		Button addNewHotelBtn = new Button("New Hotel");
		addNewHotelBtn.addClickListener(e -> {
			hotelGrid.asSingleSelect().clear();
			hotelForm.setHotel(new Hotel("",1L, "", 1, 123L, new Category(), "", ""));

		});
		HorizontalLayout toolbar = new HorizontalLayout(filtering, addNewHotelBtn);

		layout.addComponents(toolbar, main);
		this.addComponents(menu, layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
