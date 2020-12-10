package fr.simplon.fantoir.service;

import fr.simplon.fantoir.models.Client;
import fr.simplon.fantoir.repositories.ClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository repository;

    // @Override
    // public List<Client> findByNameEndsWith(String name) {

    //     var clients = (List<Client>) repository.findByNameEndsWith(name);
    //     return clients;
    // }

    public List<Client> findAll(String name) {

        var clients = (List<Client>) repository.findAll("%" + name + "%");
        return clients;
    }
}