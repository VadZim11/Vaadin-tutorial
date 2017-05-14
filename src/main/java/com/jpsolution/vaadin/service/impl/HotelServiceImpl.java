package com.jpsolution.vaadin.service.impl;

import com.jpsolution.vaadin.entity.HotelEntity;
import com.jpsolution.vaadin.repository.HotelEntityRepository;
import com.jpsolution.vaadin.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelEntityRepository hotelEntityRepository;

    @Override
    public List<HotelEntity> getdAll() {
        return hotelEntityRepository.findAll();
    }

    @Override
    public HotelEntity save(HotelEntity hotelEntity) {
        HotelEntity savedHotelEntity = hotelEntityRepository.saveAndFlush(hotelEntity);

        return savedHotelEntity;
    }

    @Override
    public void delete(Long id) {
        hotelEntityRepository.delete(id);
    }

	private static HotelServiceImpl instance;
	private static final Logger LOGGER = Logger.getLogger(HotelServiceImpl.class.getName());

	private final HashMap<Long, HotelEntity> hotels = new HashMap<>();
	private long nextId = 0;

	private HotelServiceImpl() {
	}

	public static HotelServiceImpl getInstance() {
		if (instance == null) {
			instance = new HotelServiceImpl();
			instance.ensureTestData();
		}
		return instance;
	}

	public synchronized List<HotelEntity> findAll() {
		return findAll(null);
	}

	public synchronized List<HotelEntity> findAll(String stringFilter) {
		ArrayList<HotelEntity> arrayList = new ArrayList<>();
		for (HotelEntity hotelEntity : hotels.values()) {
			try {				
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| hotelEntity.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(hotelEntity.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(HotelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<HotelEntity>() {

			@Override
			public int compare(HotelEntity o1, HotelEntity o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public synchronized List<HotelEntity> findAll(String stringFilter, String stringFilter1) {
		ArrayList<HotelEntity> arrayList = new ArrayList<>();
		for (HotelEntity hotelEntity : hotels.values()) {
			try {
				
				boolean passesFilter = ((stringFilter == null && stringFilter1==null) || (stringFilter.isEmpty() && stringFilter1.isEmpty()))
						|| (hotelEntity.getName().toLowerCase().contains(stringFilter.toLowerCase()) && hotelEntity.getAddress().toLowerCase().contains(stringFilter1.toLowerCase())
						|| (stringFilter == null && hotelEntity.getAddress().toLowerCase().contains(stringFilter1.toLowerCase()))
						|| (hotelEntity.getName().toLowerCase().contains(stringFilter.toLowerCase()) && stringFilter1==null));
				if (passesFilter) {
					arrayList.add(hotelEntity.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(HotelServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<HotelEntity>() {

			@Override
			public int compare(HotelEntity o1, HotelEntity o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	public synchronized long count() {
		return hotels.size();
	}

	public synchronized void delete(HotelEntity value) {
		hotels.remove(value.getId());
	}


	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] hotelData = new String[] {
					"3 Nagas Luang Prabang - MGallery by Sofitel;4;https://www.booking.com/hotel/la/3-nagas-luang-prabang-by-accor.en-gb.html;Vat Nong Village, Sakkaline Road, Democratic Republic Lao, 06000 Luang Prabang, Laos;",
					"Abby Boutique Guesthouse;1;https://www.booking.com/hotel/la/abby-boutique-guesthouse.en-gb.html;Ban Sawang , 01000 Vang Vieng, Laos",
					"Bountheung Guesthouse;1;https://www.booking.com/hotel/la/bountheung-guesthouse.en-gb.html;Ban Tha Heua, 01000 Vang Vieng, Laos",
					"Chalouvanh HotelEntity;2;https://www.booking.com/hotel/la/chalouvanh.en-gb.html;13 road, Ban Phonesavanh, Pakse District, 01000 Pakse, Laos",
					"Chaluenxay Villa;3;https://www.booking.com/hotel/la/chaluenxay-villa.en-gb.html;Sakkarin Road Ban Xienthong Luang Prabang Laos, 06000 Luang Prabang, Laos",
					"Dream Home Hostel 1;1;https://www.booking.com/hotel/la/getaway-backpackers-hostel.en-gb.html;049 Sihome Road, Ban Sihome, 01000 Vientiane, Laos",
					"Inpeng HotelEntity and Resort;2;https://www.booking.com/hotel/la/inpeng-and-resort.en-gb.html;406 T4 Road, Donekoy Village, Sisattanak District, 01000 Vientiane, Laos",
					"Jammee Guesthouse II;2;https://www.booking.com/hotel/la/jammee-guesthouse-vang-vieng1.en-gb.html;Vang Vieng, 01000 Vang Vieng, Laos",
					"Khemngum Guesthouse 3;2;https://www.booking.com/hotel/la/khemngum-guesthouse-3.en-gb.html;Ban Thalat No.10 Road Namngum Laos, 01000 Thalat, Laos",
					"Khongview Guesthouse;1;https://www.booking.com/hotel/la/khongview-guesthouse.en-gb.html;Ban Klang Khong, Khong District, 01000 Muang Không, Laos",
					"Kong Kham Pheng Guesthouse;1;https://www.booking.com/hotel/la/kong-kham-pheng-guesthouse.en-gb.html;Mixay Village, Paksan district, Bolikhamxay province, 01000 Muang Pakxan, Laos",
					"Laos Haven HotelEntity & Spa;3;https://www.booking.com/hotel/la/laos-haven.en-gb.html;047 Ban Viengkeo, Vang Vieng , 01000 Vang Vieng, Laos",
					"Lerdkeo Sunset Guesthouse;1;https://www.booking.com/hotel/la/lerdkeo-sunset-guesthouse.en-gb.html;Muang Ngoi Neua,Ban Ngoy-Nua, 01000 Muang Ngoy, Laos",
					"Luangprabang River Lodge Boutique 1;3;https://www.booking.com/hotel/la/luangprabang-river-lodge.en-gb.html;Mekong River Road, 06000 Luang Prabang, Laos",
					"Manichan Guesthouse;2;https://www.booking.com/hotel/la/manichan-guesthouse.en-gb.html;Ban Pakham Unit 4/143, 60000 Luang Prabang, Laos",
					"Mixok Inn;2;https://www.booking.com/hotel/la/mixok-inn.en-gb.html;188 Sethathirate Road , Mixay Village , Chanthabuly District, 01000 Vientiane, Laos",
					"Ssen Mekong;2;https://www.booking.com/hotel/la/muang-lao-mekong-river-side-villa.en-gb.html;Riverfront, Mekong River Road, 06000 Luang Prabang, Laos",
					"Nammavong Guesthouse;2;https://www.booking.com/hotel/la/nammavong-guesthouse.en-gb.html;Ban phone houang Sisalearmsak Road , 06000 Luang Prabang, Laos",
					"Niny Backpacker hotel;1;https://www.booking.com/hotel/la/niny-backpacker.en-gb.html;Next to Wat Mixay, Norkeokhunmane Road., 01000 Vientiane, Laos",
					"Niraxay Apartment;2;https://www.booking.com/hotel/la/niraxay-apartment.en-gb.html;Samsenthai Road Ban Sihom , 01000 Vientiane, Laos",
					"Pakse Mekong HotelEntity;2;https://www.booking.com/hotel/la/pakse-mekong.en-gb.html;No 062 Khemkong Road, Pakse District, Champasak, Laos, 01000 Pakse, Laos",
					"Phakchai HotelEntity;2;https://www.booking.com/hotel/la/phakchai.en-gb.html;137 Ban Wattay Mueng Sikothabong Vientiane Laos, 01000 Vientiane, Laos",
					"Phetmeuangsam HotelEntity;2;https://www.booking.com/hotel/la/phetmisay.en-gb.html;Ban Phanhxai, Xumnuea, Xam Nua, 01000 Xam Nua, Laos" };

			Random r = new Random(0);
			for (String hotel : hotelData) {
				String[] split = hotel.split(";");
				HotelEntity h = new HotelEntity();
				h.setName(split[0]);
				h.setRating(Integer.parseInt(split[1]));
				h.setUrl(split[2]);
				h.setAddress(split[3]);
				h.setCategory(CategoryServiceImpl.getInstance().findAll()
						.get(r.nextInt((int) CategoryServiceImpl.getInstance().count())));
				long daysOld = r.nextInt(365 * 30);
				h.setOperatesFrom(daysOld);
				save(h);
			}
		}
	}

}