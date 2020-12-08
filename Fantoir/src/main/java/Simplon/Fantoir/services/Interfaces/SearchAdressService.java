package main.java.Simplon.Fantoir.services.Interfaces;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import main.java.Simplon.Fantoir.models.Adresse;

@RestController
public interface SearchAdressService {
	public List<Adresse> search(String location);
}