package se.experis.emiloj.itunesreplica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.experis.emiloj.itunesreplica.data_access.CustomerRepository;
import se.experis.emiloj.itunesreplica.models.Customer;

import java.util.ArrayList;

@Controller
public class ViewController {
    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        ArrayList<Customer> customers = customerRepository.getAllCustomers();
        model.addAttribute("customers", customers);
        return "home";
    }
    @RequestMapping(value = "/customerList", method = RequestMethod.GET)
    public String customerList(Model model) {
        ArrayList<Customer> customers = customerRepository.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customerList";
    }

}
