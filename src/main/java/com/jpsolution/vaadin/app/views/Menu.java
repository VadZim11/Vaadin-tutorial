package com.jpsolution.vaadin.app.views;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class Menu extends HorizontalLayout {
	
	public Menu() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setHeight("100px");
		layout.addComponents(createMenu());
		this.addComponent(layout);
	}

	private MenuBar createMenu() {
		MenuBar menu=new MenuBar();

		Command navigateToHotels = new Command(){

			@Override
			public void menuSelected(MenuItem selectedItem) {
				getUI().getNavigator().navigateTo("hotels");

			}

		};
		Command navigateToCategories = new Command(){
			@Override
			public void menuSelected(MenuItem selectedItem) {
				
				getUI().getNavigator().navigateTo("categories");
				
			}
			
		};
	
		menu.addItem("Hotel List", navigateToHotels);
		menu.addItem("Categories List", navigateToCategories);
		
		return menu;
	}
}
