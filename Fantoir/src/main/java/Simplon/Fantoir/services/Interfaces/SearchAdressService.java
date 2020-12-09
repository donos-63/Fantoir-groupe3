/**
*
* Interface des méthodes de sélection des adresses
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-07
*
**/
package main.java.Simplon.Fantoir.services.Interfaces;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import main.java.Simplon.Fantoir.models.Adresse;

@RestController
public interface SearchAdressService {
	public List<Adresse> search(String location);

	public List<Adresse> searchByTopCounts(int nb_top);

	public List<Adresse> searchByPostalCode(int postal_code);
}