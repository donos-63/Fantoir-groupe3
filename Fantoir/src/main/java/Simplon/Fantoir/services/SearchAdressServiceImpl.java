/**
*
* Service de sélection des adresses disponible en base de donnée
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-07
*
**/
package main.java.Simplon.Fantoir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import main.java.Simplon.Fantoir.models.Adresse;
import main.java.Simplon.Fantoir.repositories.Interfaces.AddressRepository;
import main.java.Simplon.Fantoir.services.Interfaces.SearchAdressService;

@RestController
public class SearchAdressServiceImpl implements SearchAdressService{
	
	/**
	 * Repo des adresses
	 */
    private AddressRepository adressRepository;

    /**
     * Constructeur avec initialisation du repository
     * @param adressRepository
     */
    @Autowired
    public SearchAdressServiceImpl(final AddressRepository adressRepository){
        this.adressRepository = adressRepository;
    }

    /**
     * Recherche des adresses correspondant à une chaine en entrée
     * @param adressRepository
     */
	@Override
	public List<Adresse> search(String address) {
		return this.adressRepository.searchAddress(address);
	}

	/**
	 * Recherche du top nb_top des villes ayant le plus de référence d'adresse
	 * @param nb_top
     */
	@Override
	public List<Adresse> searchByTopCounts(int nb_top) {
		return this.adressRepository.searchByTopCounts(nb_top);
	}

	/**
	 * Recherche des addresses disponible pour un code postal
	 * @param postal_code
     */
	@Override
	public List<Adresse> searchByPostalCode(int postal_code) {
		return this.adressRepository.searchByPostalCode(postal_code);
	}
}