package main.java.fantoir.fantoir_talend_1_2;

import java.nio.file.Paths;

public class TalendLauncher {
	public static void StartImport(String input_folder, String output_folder, String output_filepath, String fantoir_db) {
		fantoir_talend talendJob=new fantoir_talend();

		String output_file=Paths.get(output_folder,output_filepath).toString().replace("\\","/");
		String fantoir_db_File=Paths.get(output_folder,fantoir_db).toString().replace("\\","/");
		String [] context=new String[] {"--context_param input_folder="+input_folder,"--context_param output_file="+output_file,"--context_param fantoir_db_File="+fantoir_db_File};
		talendJob.runJob(context);
	}

}
