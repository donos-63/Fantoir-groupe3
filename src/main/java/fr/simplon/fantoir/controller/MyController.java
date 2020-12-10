package fr.simplon.fantoir.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.simplon.fantoir.models.Client;
import fr.simplon.fantoir.service.IClientService;

@Controller
public class MyController {

    @Autowired
    IClientService clientService;

    // @GetMapping("/showClientEnding")
    // public String findCitiesNameEndsWith(Model model, @RequestParam String name) {

    //     var clients = (List<Client>) clientService.findByNameEndsWith(name);
    //     System.out.println(clients);
    //     model.addAttribute("Client", clients);

    //     return "showClient";
    // }

    // @GetMapping(value="/clients")
    // public String rechercheCommunes(Model model, @RequestParam String name) {

    //     var clients = (List<Client>) clientService.findByNameEndsWith(name);
    //     // System.out.println("test");
    //     model.addAttribute("Client", clients);
    //     System.out.println(clients);
    //     // System.out.println(clients.size());

    //     return "showClient";
    // }

    @RequestMapping(path = "/GetCities", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Client> findCities(Model model, @RequestParam String name) {
        System.out.println(name);
        var cities = (List<Client>) clientService.findAll(name);

        return cities;
    }
}