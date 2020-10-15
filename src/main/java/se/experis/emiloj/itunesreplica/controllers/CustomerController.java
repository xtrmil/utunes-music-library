package se.experis.emiloj.itunesreplica.controllers;

import org.springframework.http.MediaType;
import se.experis.emiloj.itunesreplica.data_access.CustomerRepository;
import se.experis.emiloj.itunesreplica.models.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
//    @RequestMapping(value = "/api/customers", method = RequestMethod.PUT)
//    public Boolean updateCustomer(@RequestBody Customer customer){
//        return customerRepository.updateCustomer(customer);
//    }

//    @RequestMapping(value = "/api/customers/{country}", method = RequestMethod.GET)
//    public TreeMap<Int,String> getNumberOfCostumersPerCountry(@PathVariable String country){
//        return customerRepository.getNumberOfCostumersPerCountry(country);

//    getHigestSpendersDecendingOrder(){

//    @RequestMapping(value = "/api/customers/{id}/popular/{genre}", method = RequestMethod.GET)
//    public Customer getSpecificCustomersFavouriteGenres(@PathVariable String id,@PathVariable String genre){
//        return customerRepository.getSpecificCustomer(id);
}
