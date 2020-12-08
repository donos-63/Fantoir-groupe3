package main.java.fantoir.fantoir_talend_1_2;

public class TalendLauncher {

	public static void StartImport() {
		// TODO Auto-generated method stub
		fantoir_talend talendJob=new fantoir_talend();

		String input_folder="C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/in";
		String output_file="C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/out/processed_files.txt";
		String fantoir_db_File="C:/prairie/projet11/Fantoir-groupe3/Fantoir/data/out/fantoir.db";
		String [] context=new String[] {"--context_param input_folder="+input_folder,"--context_param output_file="+output_file,"--context_param fantoir_db_File="+fantoir_db_File};
		talendJob.runJob(context);
	}

}
