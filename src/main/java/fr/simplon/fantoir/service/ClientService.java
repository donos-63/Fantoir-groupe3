package fr.simplon.fantoir.service;

//Importation des librairies
import fr.simplon.fantoir.models.Client;
import fr.simplon.fantoir.repositories.ClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> findAll(String name) {
        //Cette déclaration est essentielle pour insérer dans la requête le mot-clé inséré dans le lien url
        var clients = (List<Client>) repository.findAll("%" + name + "%");
        return clients;
    }
}