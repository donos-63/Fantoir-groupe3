package fr.simplon.fantoir.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.simplon.fantoir.models.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    // @Query("select c from Client c")LIKE ‘%a%’
    // @Query("select c from Client c where c.nom_voie = '153'")
    // @Query("select c from Client c where c.nom_commune LIKE '%passe%'")
    // List<Client> findByNameEndsWith(String chars);
    // List<Client> findAll();
    // @Param("name") String name;
    // @Query("select c from Client c where c.nom_commune LIKE '%: " + name + "%'")
    // List<Client> findAll();

    @Query("select c from Client c where c.nom_commune LIKE :name")
    List<Client> findAll(@Param("name")String name);
}
