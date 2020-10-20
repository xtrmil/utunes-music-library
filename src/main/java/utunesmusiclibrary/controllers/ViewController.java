package utunesmusiclibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import utunesmusiclibrary.data_access.CustomerRepository;
import utunesmusiclibrary.data_access.TrackRepository;
import utunesmusiclibrary.models.Customer;
import utunesmusiclibrary.models.Track;
import java.util.ArrayList;

@Controller
public class ViewController {
    CustomerRepository customerRepository = new CustomerRepository();
    TrackRepository trackRepository = new TrackRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, @ModelAttribute("track") Track track) {
        ArrayList<Track> tracks = this.trackRepository.getTracksByName(track.getTrackName());
        model.addAttribute("trackName", tracks);
        String[][] items = trackRepository.getRandomItems();
        model.addAttribute("items", items);
        return "home";
    }
    @RequestMapping(value = "/customerList", method = RequestMethod.GET)
    public String customerList(Model model) {
        ArrayList<Customer> customers = customerRepository.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customerList";
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public String getTracksByName(@RequestParam("trackName") String trackName, Model model) {
        ArrayList<Track> tracks = trackRepository.getTracksByName(trackName);
        model.addAttribute("tracks", tracks);
        model.addAttribute("trackName", trackName);
        return "search";
    }

}
