/**
*
* Interface du repository Adresse
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-08
*
**/

package main.java.Simplon.Fantoir.repositories.Interfaces;

import main.java.Simplon.Fantoir.models.Adresse;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository{
    public List<Adresse> getAllAddresses();
    public List<Adresse> searchAddress(String location);
	public List<Adresse> searchByTopCounts(int nb_top);
	public List<Adresse> searchByPostalCode(int postal_code);
}
