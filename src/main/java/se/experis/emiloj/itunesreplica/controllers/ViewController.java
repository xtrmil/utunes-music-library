package se.experis.emiloj.itunesreplica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.experis.emiloj.itunesreplica.data_access.CustomerRepository;
import se.experis.emiloj.itunesreplica.data_access.TrackRepository;
import se.experis.emiloj.itunesreplica.models.Customer;
import se.experis.emiloj.itunesreplica.models.Track;

import java.util.ArrayList;

@Controller
public class ViewController {
    CustomerRepository customerRepository = new CustomerRepository();
    TrackRepository trackRepository = new TrackRepository();

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

    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public String getCustomersByCountry() { return "popular"; }

    @RequestMapping(value = "/search/{searchTerm}", method = RequestMethod.GET)
    public String getTracksByName(@PathVariable String searchTerm, Model model) {
        ArrayList<Track> tracks = trackRepository.getTracksByName(searchTerm);
        model.addAttribute("tracks", tracks);
        return "search";}



    //@RequestMapping(value = "/", method = RequestMethod.GET)
    //public String placeholder() { return "placeholder";

}
