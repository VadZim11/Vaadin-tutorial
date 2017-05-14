package com.jpsolution.vaadin.repository;

import com.jpsolution.vaadin.entity.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository(value="hotelRepository")
@Transactional
public class HotelRepository extends GenericDaoJpaImpl{
    public List<Hotel> filter(String name, String address){
        Query query = currentSession()
                .createQuery("select h from Hotel h where lower(h.name) like lower('%"+name+"%') and lower(h.address) like lower('%"+address+"%')");
        @SuppressWarnings("unchecked")
        List <Hotel> result=query.getResultList();
        return result;

    }
}
