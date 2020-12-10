package fr.simplon.fantoir.repositories;

//Importation des librairies
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.simplon.fantoir.models.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    //Déclaration de la requête
    @Query("select c from Client c where c.nom_voie LIKE :name")
    List<Client> findAll(@Param("name")String name);
}
