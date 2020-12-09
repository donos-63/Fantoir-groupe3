/**
*
* Service de récupération des référentiels adresse et intégration des données
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-08
*
**/

package main.java.Simplon.Fantoir.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Stopwatch;

import main.java.Simplon.Fantoir.services.Interfaces.PushFileService;
import main.java.fantoir.fantoir_talend_1_2.TalendLauncher;

@RestController
public class PushFileServiceImpl implements PushFileService {
	@Autowired
	private Environment env;
	
	/**
	 * Télécharge les fichiers d'adresse à partir du référentiel distant, les extrait et appel le job Talend pour les intégrer en bdd
	 * @workflow:
	 * 		1: chargement des noms des fichiers déjà traités
	 * 		2: chargement des fichiers disponible au téléchargement
	 * 		3: identification des nb_files fichiers à traiter
	 * 		4: nettoyage du répertoire d'entrée
	 * 		5: téléchargement en extraction des archives d'adresse
	 * 		6: suppression des archives
	 * 		7: lancement du traitement Talend d'intégration des données en bdd
	 */
	@Override
	public String PushFilesInDatabase(int nb_files) {
		Stopwatch stopwatch = Stopwatch.createStarted();
        Document doc;
		try {
			//chargement des noms des fichiers déjà traités
			ArrayList<String> processed_files = GetProcessedFiles(env.getProperty("fantoir.out.folder"), env.getProperty("fantoir.out.processed_files"));
			ArrayList<String> new_files = new ArrayList<>();
			
			//chargement des fichiers disponible au téléchargement
			doc = Jsoup.connect(env.getProperty("fantoir.remote.url")).get();
	        for (Element file : doc.select("a")) {
	        	String fileName = file.attr("href");
	        	//seul les fichiers adresse sont traités dans la v1
	        	if(!fileName.startsWith("adresses-"))
	        		continue;
	        	if(processed_files.contains(fileName.replace(".gz", "")))
	        		continue;
	        	
	        	//si le fichier n'est pas déjà traité, ajout à la liste des fichiers à traiter
	        	new_files.add(fileName);
	        	
	        	if(new_files.size() >= nb_files)
	        		break;
	        }
	        
	        //nettoyage du répertoire d'entrée (au cas où le précédent traitement aurait échoué)
	        CleanFilesInFolder(env.getProperty("fantoir.in.folder"), true);
	        for(String file : new_files)
	        {
	        	//Téléchargement en extraction des archives d'adresse
	        	DownloadFile(env.getProperty("fantoir.remote.url"), env.getProperty("fantoir.in.folder"), file);
	        	DecompressGzip(env.getProperty("fantoir.in.folder"), env.getProperty("fantoir.in.folder"), file);
	        }
	        //les archives .gz sont supprimé du répertoire d'entrée
	        CleanFilesInFolder(env.getProperty("fantoir.in.folder"), false);
	        
	        //lancement du traitement Talend d'intégration des données en bdd
	        TalendLauncher.StartImport(env.getProperty("fantoir.in.folder"), env.getProperty("fantoir.out.folder"), env.getProperty("fantoir.out.processed_files"), env.getProperty("fantoir.out.db") );
	        
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return "Import failed";
		}
		
		return String.format("Import of %d files succeed in %d seconds", nb_files, stopwatch.elapsed(TimeUnit.SECONDS));
	}
	
	/**
	 * Purge les fichiers contenu dans un dossier mais en conservant le .gitkeep
	 * @param folder_path
	 * @param all : supprime tout si true, sinon ne supprime que les .gz
	 * @throws IOException
	 */
	private void CleanFilesInFolder(String folder_path, boolean all) throws IOException{
		File folder = new File(folder_path);
		for (File f : folder.listFiles()) {
			if (f.getName().endsWith(".gitkeep")) {
		        continue; 
		    }
			
		    if (f.getName().endsWith(".gz") || all) {
		        f.delete(); 
		    }
		}
	}
	
	/**
	 * Télécharge une archive d'adresse à partir d'une url
	 * @param file_url
	 * @param folder_path
	 * @param filename
	 * @throws IOException
	 */
	private void DownloadFile(String file_url, String folder_path, String filename) throws IOException{
		
		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(file_url+filename).openStream());
				  FileOutputStream fileOS = new FileOutputStream(Paths.get(folder_path,filename).toFile())) {
				    byte data[] = new byte[1024];
				    int byteContent;
				    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				        fileOS.write(data, 0, byteContent);
				    }
				} catch (IOException e) {
					System.out.println("An error occurred with file "+ filename);
				      e.printStackTrace();
				      throw e;
				}
	}
	
	/**
	 * Récupère et charge le contenu du fichier contenant le nom des fichiers déjà intégrés à la bdd
	 * @param folder_path
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	private ArrayList<String> GetProcessedFiles(String folder_path, String filename) throws FileNotFoundException{
		    try {
		      ArrayList<String> files = new ArrayList<String>();
		      File myObj = Paths.get(folder_path, filename).toFile();
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        files.add(data);
		      }
		      myReader.close();
		      
		      return files;
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred with file "+ filename);
		      e.printStackTrace();
		      throw e;
		    }
	}
	
	/**
	 * Décompresse les archives .gz contenant les adresses
	 * @param folder_source
	 * @param folder_target
	 * @param filename
	 * @throws IOException
	 */
	private void DecompressGzip(String folder_source, String folder_target, String filename) throws IOException {
		
        try (GZIPInputStream gis = new GZIPInputStream(
                                      new FileInputStream( Paths.get(folder_source, filename).toFile()));
             FileOutputStream fos = new FileOutputStream( Paths.get(folder_target, filename.replace(".gz", "")).toFile())) {

            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        }

    }
}
