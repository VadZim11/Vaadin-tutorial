package com.jpsolution.vaadin.service.impl;

import com.jpsolution.vaadin.entity.Category;
import com.jpsolution.vaadin.repository.CategoryRepository;
import com.jpsolution.vaadin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    @Qualifier("categoryRepository")
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategory(){
        List<Category> list = categoryRepository.list(Category.class);
        return list;
    }

    @Override
    public void saveCategory(Category category){
        categoryRepository.apdate(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(Category.class, category.getId());
    }

    /*private static CategoryServiceImpl instance;
	private static final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class.getName());
	
	private final HashMap<Long, Category> categories = new HashMap<>();
	private long nextId = 0;
	
	private CategoryServiceImpl() {
	}

	public static CategoryServiceImpl getInstance() {
		if (instance == null) {
			instance = new CategoryServiceImpl();
			instance.ensureTestData();
		}
		return instance;
	}
	
	public synchronized List<Category> findAll() {
		ArrayList<Category> arrayList = new ArrayList<>();
		for (Category category : categories.values()) {
			try {
				arrayList.add(category.clone());
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized long count() {
		return categories.size();
	}
	
	public synchronized Category getDefault() {
		Category def = null;
		if (categories.values().iterator().hasNext()) {
			def = categories.values().iterator().next();
		}
		return def;
	}

	public synchronized void delete(Category value) {
		categories.remove(value.getId());
	}

	public void ensureTestData() {
		if (findAll().isEmpty()) {
			String[] categoryData = new String[] {"Hotel", "Hostel", "GuestHouse", "Appartments"};
			for (String category : categoryData) {
				Category c = new Category();
				c.setCategory(category);
				save(c);
			}
		}
	}*/
}