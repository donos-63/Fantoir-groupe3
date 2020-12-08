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
	
	@Override
	public String pushFile(int nb_files) {
		Stopwatch stopwatch = Stopwatch.createStarted();
        Document doc;
		try {
			ArrayList<String> processed_files = GetProcessedFiles(env.getProperty("fantoir.out.folder"), env.getProperty("fantoir.out.processed_files"));
			ArrayList<String> new_files = new ArrayList<>();
			
			doc = Jsoup.connect(env.getProperty("fantoir.remote.url")).get();
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
	        
        	CleanFiles(env.getProperty("fantoir.in.folder"));
	        for(String file : new_files)
	        {
	        	LoadFile(env.getProperty("fantoir.remote.url"), env.getProperty("fantoir.in.folder"), file);
	        	DecompressGzip(env.getProperty("fantoir.in.folder"), env.getProperty("fantoir.in.folder"), file);
	        }
	        CleanArchiveFiles(env.getProperty("fantoir.in.folder"));
	        
	        
	        TalendLauncher.StartImport(env.getProperty("fantoir.in.folder"), env.getProperty("fantoir.out.folder"), env.getProperty("fantoir.out.processed_files"), env.getProperty("fantoir.out.db") );
	        
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return "Import failed";
		}
		
		return String.format("Import of %d files succeed in %d seconds", nb_files, stopwatch.elapsed(TimeUnit.SECONDS));
	}
	
	private void CleanFiles(String folder_path) throws IOException{
		//FileUtils.cleanDirectory(new File("C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in/"));
		
		File folder = new File(folder_path);
		for (File f : folder.listFiles()) {
		    if (!f.getName().endsWith(".gitkeep")) {
		        f.delete(); 
		    }
		}
	}
	
	private void CleanArchiveFiles(String folder_path){
		File folder = new File(folder_path);
		for (File f : folder.listFiles()) {
		    if (f.getName().endsWith(".gz")) {
		        f.delete(); 
		    }
		}
	}
	
	private void LoadFile(String file_url, String folder_path, String fileName){
		
		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(file_url+fileName).openStream());
				  FileOutputStream fileOS = new FileOutputStream(Paths.get(folder_path,fileName).toFile())) {
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
	
	private ArrayList<String> GetProcessedFiles(String folder_path, String filename){
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
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
			return null;
	}
	
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
