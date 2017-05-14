package com.jpsolution.vaadin.service.impl;

import com.jpsolution.vaadin.entity.CategoryEntity;
import com.jpsolution.vaadin.repository.CategoryEntityRepository;
import com.jpsolution.vaadin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    @Override
    public List<CategoryEntity> getdAll() {
        return categoryEntityRepository.findAll();
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        CategoryEntity savedCategoryEntity = categoryEntityRepository.saveAndFlush(categoryEntity);

        return savedCategoryEntity;
    }

    @Override
    public void delete(Long id) {
        categoryEntityRepository.delete(id);
    }

	private static CategoryServiceImpl instance;
	private static final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class.getName());
	
	private final HashMap<Long, CategoryEntity> categories = new HashMap<>();
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
	
	public synchronized List<CategoryEntity> findAll() {
		ArrayList<CategoryEntity> arrayList = new ArrayList<>();
		for (CategoryEntity categoryEntity : categories.values()) {
			try {
				arrayList.add(categoryEntity.clone());
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<CategoryEntity>() {

			@Override
			public int compare(CategoryEntity o1, CategoryEntity o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized long count() {
		return categories.size();
	}
	
	public synchronized CategoryEntity getDefault() {
		CategoryEntity def = null;
		if (categories.values().iterator().hasNext()) {
			def = categories.values().iterator().next();
		}
		return def;
	}

	public synchronized void delete(CategoryEntity value) {
		categories.remove(value.getId());
	}


	
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			String[] categoryData = new String[] {"Hotel", "Hostel", "GuestHouse", "Appartments"};
			for (String category : categoryData) {
				CategoryEntity c = new CategoryEntity();
				c.setCategory(category);
				save(c);
			}
		}
	}
}