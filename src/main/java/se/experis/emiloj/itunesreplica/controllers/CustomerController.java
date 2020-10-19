package se.experis.emiloj.itunesreplica.controllers;

import org.springframework.http.MediaType;
import se.experis.emiloj.itunesreplica.data_access.CustomerRepository;
import se.experis.emiloj.itunesreplica.models.Customer;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

@RestController
public class CustomerController {
    CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }

    @RequestMapping(value = "/api/customers", method = RequestMethod.POST)
    public Boolean addCustomer(@RequestBody Customer customer){
        return customerRepository.addCustomer(customer);
    }

    @RequestMapping(value = "/api/customers", method = RequestMethod.PUT)
    public Boolean updateCustomer(@RequestBody Customer customer){
        return customerRepository.updateCustomer(customer);
    }

    @RequestMapping(value = "/api/customers/countries", method = RequestMethod.GET)
    public LinkedHashMap<String, Integer> getNumberOfCustomersPerCountry(){
        return customerRepository.getNumberOfCustomersPerCountry();
    }

    @RequestMapping(value = "/api/customers/spending", method = RequestMethod.GET)
    public LinkedHashMap<String, Double> getHighestSpender(){
        return customerRepository.getHighestSpender();
    }

//    @RequestMapping(value = "/api/customers/{id}/popular/{genre}", method = RequestMethod.GET)
//    public Customer getSpecificCustomersFavouriteGenres(@PathVariable String id,@PathVariable String genre){
//        return customerRepository.getSpecificCustomer(id);
}
