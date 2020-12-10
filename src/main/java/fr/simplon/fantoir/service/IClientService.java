package fr.simplon.fantoir.service;

//Importation des librairies
import java.util.List;
import fr.simplon.fantoir.models.Client;

public interface IClientService {
    // Déclaration de la liste
    List<Client> findAll(String name);
}
