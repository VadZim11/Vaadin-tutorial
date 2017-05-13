package com.jpsolution.vaadin.service.impl;

import com.jpsolution.vaadin.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/*@Repository
@Service("jpaCategoryDAO")
@Transactional*/
public class CategoryServiceImpl {

	/*@PersistenceContext
	private EntityManager em;

	@Override
	public List<CategoryEntity> findAll() {
		return em.createNamedQuery("CategoryEntity.findAll", CategoryEntity.class).getResultList();
	}

	@Override
	public CategoryEntity save(CategoryEntity categoryEntity) { return null;}

	@Override
	public void delete(CategoryEntity categoryEntity) {return null;}
	*/
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
	
	public synchronized void save(CategoryEntity entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "HotelEntity category is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (CategoryEntity) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		categories.put(entry.getId(), entry);
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