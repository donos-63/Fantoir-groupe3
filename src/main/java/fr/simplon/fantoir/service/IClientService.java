package fr.simplon.fantoir.service;

import java.util.List;
import fr.simplon.fantoir.models.Client;

public interface IClientService {
    // List<Client> findByNameEndsWith(String name);
    List<Client> findAll(String name);
}
