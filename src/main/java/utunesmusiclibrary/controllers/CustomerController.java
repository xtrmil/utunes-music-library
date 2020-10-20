package utunesmusiclibrary.controllers;

import utunesmusiclibrary.data_access.CustomerRepository;
import utunesmusiclibrary.models.Customer;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestController
public class CustomerController { // API REST Controller for Postman-requests
    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "/api/customers", method = RequestMethod.POST)
    public Boolean addCustomer(@RequestBody Customer customer) {
        return customerRepository.addCustomer(customer);
    }

    @RequestMapping(value = "/api/customers", method = RequestMethod.PUT)
    public Boolean updateCustomer(@RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @RequestMapping(value = "/api/customers/countries", method = RequestMethod.GET)
    public LinkedHashMap<String, Integer> getNumberOfCustomersPerCountry() {
        return customerRepository.getNumberOfCustomersPerCountry();
    }

    @RequestMapping(value = "/api/customers/spending", method = RequestMethod.GET)
    public LinkedHashMap<String, Double> getHighestSpender() {
        return customerRepository.getHighestSpender();
    }

    @RequestMapping(value = "/api/customers/{id}/popular/genre", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getCustomersFavoriteGenre(@PathVariable int id) {
        return customerRepository.getCustomersFavoriteGenre(id);
    }
}
