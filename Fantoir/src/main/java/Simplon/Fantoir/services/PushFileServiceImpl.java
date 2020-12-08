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
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.web.bind.annotation.RestController;

import main.java.Simplon.Fantoir.services.Interfaces.PushFileService;

@RestController
public class PushFileServiceImpl implements PushFileService {

	@Override
	public String pushFile(int nb_files) {
        Document doc;
		try {
			ArrayList<String> processed_files = GetProcessedFiles();
			ArrayList<String> new_files = new ArrayList<>();
						
			doc = Jsoup.connect("https://adresse.data.gouv.fr/data/ban/adresses/latest/csv/").get();
	        for (Element file : doc.select("a")) {
	        	String fileName = file.attr("href");
	        	if(!fileName.startsWith("adresses-"))
	        		continue;
	        	if(processed_files.contains(fileName.replace(".gz", "")))
	        		continue;
	        	
	        	new_files.add(fileName);
	        	
	        	if(new_files.size() >= nb_files)
	        		break;
	        }
	        
        	CleanFiles();
	        for(String file : new_files)
	        {
	        	LoadFile(file, "https://adresse.data.gouv.fr/data/ban/adresses/latest/csv/");
	        	DecompressGzip("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/"+file, "C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/"+file.replace(".gz", ""));
	        }
	        CleanArchiveFiles();
	        
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void CleanFiles() throws IOException{
		//FileUtils.cleanDirectory(new File("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/"));
		
		File folder = new File("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/");
		for (File f : folder.listFiles()) {
		    if (!f.getName().endsWith(".gitkeep")) {
		        f.delete(); 
		    }
		}
	}
	
	private void CleanArchiveFiles(){
		File folder = new File("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/");
		for (File f : folder.listFiles()) {
		    if (f.getName().endsWith(".gz")) {
		        f.delete(); 
		    }
		}
	}
	
	private void LoadFile(String fileName, String filepath){
		
		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(filepath+fileName).openStream());
				  FileOutputStream fileOS = new FileOutputStream("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/"+fileName)) {
				    byte data[] = new byte[1024];
				    int byteContent;
				    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				        fileOS.write(data, 0, byteContent);
				    }
				} catch (IOException e) {
					System.out.println("An error occurred.");
				      e.printStackTrace();
				}
	}
	
	private ArrayList<String> GetProcessedFiles(){
		    try {
		      ArrayList<String> files = new ArrayList<String>();
		      File myObj = new File("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/out/processed_files.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        files.add(data);
		      }
		      myReader.close();
		      
		      return files;
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
			return null;
	}
	
	private void DecompressGzip(String source, String target) throws IOException {

        try (GZIPInputStream gis = new GZIPInputStream(
                                      new FileInputStream( Paths.get(source).toFile()));
             FileOutputStream fos = new FileOutputStream( Paths.get(target).toFile())) {

            // copy GZIPInputStream to FileOutputStream
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

        }

    }
}
