package com.jpsolution.vaadin.app.views;


import com.jpsolution.vaadin.app.form.CategoryForm;
import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.service.impl.CategoryServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Theme("mytheme")
@SpringView(name = CategoryView.VIEW_NAME)
public class CategoryView extends VerticalLayout implements View {
	@Autowired
	private CategoryServiceImpl service;
	public static final String VIEW_NAME = "categories";
	private Menu menu = new Menu();
	private ListSelect<Category> categorySelect;
	private Button addCategoryBtn;
	private Button editCategoryBtn;
	private Button delCategoryBtn;
	private CategoryForm categoryForm;

	public CategoryView() {

	}

	@PostConstruct
	void init() {
		categoryForm = new CategoryForm(this, service);
		HorizontalLayout layout=createContent();
		addComponents(menu,layout);
	}

	private HorizontalLayout createContent() {
		categorySelect=new ListSelect<Category>();
		updateSelect();
		categorySelect.setItemCaptionGenerator(Category::getCategory);
		this.categorySelect.setWidth("220px");
		editCategoryBtn=new Button("Edit");
		editCategoryBtn.setEnabled(false);
		editCategoryBtn.addClickListener(e->editBtnClick());
		delCategoryBtn=new Button("Delete");
		delCategoryBtn.setEnabled(false);
		delCategoryBtn.addClickListener(e->delBtnClick());
		addCategoryBtn=new Button("Add");
		addCategoryBtn.addClickListener(e->{
			categoryForm.setCategory(new Category(""));
			categoryForm.setVisible(true);
		});
		categorySelect.addValueChangeListener(e->menuLogic(e.getValue().size()));
		CssLayout menu=new CssLayout(editCategoryBtn,delCategoryBtn,addCategoryBtn);
		menu.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		VerticalLayout l=new VerticalLayout(menu,categorySelect);
		HorizontalLayout layout=new HorizontalLayout(l,categoryForm);
		return layout;
	}
	private void menuLogic(int size){
		if(size==0){
			editCategoryBtn.setEnabled(false);
			delCategoryBtn.setEnabled(false);
			
		}else if(size==1){
			editCategoryBtn.setEnabled(true);
			delCategoryBtn.setEnabled(true);
		}else if(size>1){
			editCategoryBtn.setEnabled(false);
			delCategoryBtn.setEnabled(true);
		}
	}
	private void delBtnClick(){
		categorySelect.getValue().forEach(category->{
			service.deleteCategory(category);
		});
		updateSelect();
	}
	private void editBtnClick(){
		categoryForm.setCategory(categorySelect.getValue().iterator().next());
		categoryForm.setVisible(true);
	}

	public void updateSelect() {
		
		this.categorySelect.setItems(service.getCategory());
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}

