package com.theironyard.charlotte.controller;

import com.theironyard.charlotte.crud.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by charlesrath on 8/29/16.
 */
@Controller
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurants;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, ConfigurableEmbeddedServletContainer container) {
        return "home";
    }
}
