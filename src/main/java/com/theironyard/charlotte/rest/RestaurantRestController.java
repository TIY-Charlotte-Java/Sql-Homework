package com.theironyard.charlotte.rest;

import com.google.gson.Gson;
import com.theironyard.charlotte.crud.RestaurantRepository;
import com.theironyard.charlotte.orm.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlesrath on 8/29/16.
 */
@RestController
public class RestaurantRestController {

    Gson gson = new Gson();

    @Autowired
    RestaurantRepository restaurants;


    /** /api/restaurants is used by angular RestaurantsFactory service **/

    /** createRestaurant() **/
    @RequestMapping(path = "/api/restaurants", method = RequestMethod.POST)
    public String createRestaurant(@RequestBody Restaurant restaurant) {
        restaurants.save(restaurant);

        return "";
    }

    @RequestMapping(path = "/api/restaurants", method = RequestMethod.GET)
    public List<Restaurant> getRestaurants() {
        System.out.printf("\n\ngetRestaurants() [GET]\n\n");
        List<Restaurant> restaurantList = (List<Restaurant>)restaurants.findAll();
        return restaurantList;
    }

    /** /api/restaurant/{id} is used by angular RestaurantFactory service **/

    @RequestMapping(path = "/api/restaurant/{id}", method = RequestMethod.GET)
    public String getRestaurant(@PathVariable(value = "id") Integer id) {
        System.out.printf("\n\ngetRestaurant() [GET] [id = "+id+"]\n\n");
        return gson.toJson(restaurants.findOne(id));
    }

    @RequestMapping(path = "/api/restaurant/{id}", method = RequestMethod.PUT)
    public String updateRestaurant(@PathVariable(value = "id") Integer id, @RequestBody Restaurant restaurant) {
        System.out.printf("\n\nupdateRestaurant() [PUT] [id = "+id+"]\n\n");
        Restaurant old = restaurants.findOne(id);
        old.update(restaurant);
        restaurants.save(old);
        return "";
    }

    @RequestMapping(path = "/api/restaurant/{id}", method = RequestMethod.DELETE)
    public String deleteRestaurant(@PathVariable(value = "id") Integer id) {
        System.out.printf("\n\ndeleteRestaurant() [DELETE] [id = "+id+"]\n\n");
        restaurants.delete(id);
        return "";
    }

}
