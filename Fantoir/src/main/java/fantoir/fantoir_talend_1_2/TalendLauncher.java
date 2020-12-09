/**
*
* Launcher du job Talend
*
* @author  Mathieu Simon
* @version 1.0
* @since   2020-12-08
*
**/

package main.java.fantoir.fantoir_talend_1_2;

import java.nio.file.Paths;

public class TalendLauncher {
	
	/**
	 * Appel du job talend en lui passant en entrée les chemins vers les répertoires d'entrée et de sortie 
	 * de l'application, et du chemin vers la base de donnée
	 * @param input_folder
	 * @param output_folder
	 * @param output_filepath
	 * @param fantoir_db
	 */
	public static void StartImport(String input_folder, String output_folder, String output_filepath, String fantoir_db) {
		fantoir_talend talendJob=new fantoir_talend();

		String output_file=Paths.get(output_folder,output_filepath).toString().replace("\\","/"); //force unix path format or Talend job will crash
		String fantoir_db_File=Paths.get(output_folder,fantoir_db).toString().replace("\\","/"); //force unix path format or Talend job will crash
		String [] context=new String[] {"--context_param input_folder="+input_folder,"--context_param output_file="+output_file,"--context_param fantoir_db_File="+fantoir_db_File};
		talendJob.runJob(context);
	}

}
