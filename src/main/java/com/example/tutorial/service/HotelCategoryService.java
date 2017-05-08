package com.example.tutorial.service;

import com.example.tutorial.entity.HotelCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class HotelCategoryService {
	
	private static HotelCategoryService instance;
	private static final Logger LOGGER = Logger.getLogger(HotelCategoryService.class.getName());
	
	private final HashMap<Long, HotelCategory> hotelCategories = new HashMap<>();
	private long nextId = 0;
	
	private HotelCategoryService() {
	}

	public static HotelCategoryService getInstance() {
		if (instance == null) {
			instance = new HotelCategoryService();
			instance.ensureTestData();
		}
		return instance;
	}
	
	public synchronized List<HotelCategory> findAll() {
		ArrayList<HotelCategory> arrayList = new ArrayList<>();
		for (HotelCategory hotelCategory : hotelCategories.values()) {
			try {
				arrayList.add(hotelCategory.clone());
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(HotelCategoryService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<HotelCategory>() {

			@Override
			public int compare(HotelCategory o1, HotelCategory o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized long count() {
		return hotelCategories.size();
	}
	
	public synchronized HotelCategory getDefault() {
		HotelCategory def = null;
		if (hotelCategories.values().iterator().hasNext()) {
			def = hotelCategories.values().iterator().next();
		}
		return def;
	}

	public synchronized void delete(HotelCategory value) {
		hotelCategories.remove(value.getId());
	}
	
	public synchronized void save(HotelCategory entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE, "Hotel category is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (HotelCategory) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		hotelCategories.put(entry.getId(), entry);
	}
	
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			String[] hotelCategoryData = new String[] {"Hotel", "Hostel", "GuestHouse", "Appartments"};
			for (String hotelCategory : hotelCategoryData) {
				HotelCategory c = new HotelCategory();
				c.setHotelCategory(hotelCategory);
				save(c);
			}
		}
	}
}