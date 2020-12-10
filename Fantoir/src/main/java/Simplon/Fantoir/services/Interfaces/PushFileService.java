/**
*
* Interface des méthodes récupération des référentiels adresse et intégration des données
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-07
*
**/
package main.java.Simplon.Fantoir.services.Interfaces;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PushFileService {
	public String PushFilesInDatabase(int nb_files) throws IOException;
}
