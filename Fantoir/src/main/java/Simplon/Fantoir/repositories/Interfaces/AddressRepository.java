package main.java.Simplon.Fantoir.repositories.Interfaces;

import main.java.Simplon.Fantoir.models.Adresse;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository{
    public List<Adresse> getAllAddresses();
    public List<Adresse> searchAddress(String location);
}
