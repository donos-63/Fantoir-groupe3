package fr.simplon.fantoir.controller;

//Importation des librairies
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.simplon.fantoir.models.Client;
import fr.simplon.fantoir.service.IClientService;

@Controller
public class MyController {

    //Lien avec le clientService
    @Autowired
    IClientService clientService;

    //Création de la page dédiée au localhost
    @RequestMapping(path = "/GetCities", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Client> findCities(Model model, @RequestParam String name) {
        var cities = (List<Client>) clientService.findAll(name);

        return cities;
    }
}