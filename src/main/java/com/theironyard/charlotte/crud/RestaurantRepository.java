package com.theironyard.charlotte.crud;

import com.theironyard.charlotte.orm.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by charlesrath on 8/29/16.
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    List<Restaurant> findByZipcode(Integer zipcode);

}
