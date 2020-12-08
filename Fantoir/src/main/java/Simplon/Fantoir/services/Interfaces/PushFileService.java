package main.java.Simplon.Fantoir.services.Interfaces;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;

@RestController
public interface PushFileService {
	public String pushFile(int nb_files) throws IOException;
}
