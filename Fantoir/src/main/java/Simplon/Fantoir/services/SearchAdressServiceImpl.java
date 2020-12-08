package main.java.Simplon.Fantoir.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import main.java.Simplon.Fantoir.models.Adresse;
import main.java.Simplon.Fantoir.repositories.Interfaces.AddressRepository;
import main.java.Simplon.Fantoir.services.Interfaces.SearchAdressService;

@RestController
public class SearchAdressServiceImpl implements SearchAdressService{

    private AddressRepository adressRepository;

    @Autowired
    public SearchAdressServiceImpl(final AddressRepository adressRepository){
        this.adressRepository = adressRepository;
    }
    
	@Override
	public List<Adresse> search(String address) {
		return this.adressRepository.searchAddress(address);
	}
}