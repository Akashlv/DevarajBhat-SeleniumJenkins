package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import com.google.common.io.Files;

public class ReadFile {
	
	static Logger logger = Logger.getLogger(ReadFile.class.getName());

	static FileReader FR;
	static BufferedReader BR;
	static File getLatestFile;
	static String content = "";
	static String[] split = new String[36];
	

	public static String readCsvFile(String filePath, int cellNumber) {

		try {
			
			
			getLatestFile = getLatestFile(filePath);
			String name = getLatestFile.getName();
			System.out.println("latest file name :"+getLatestFile.getName());
			
			String fileName = filePath+name;
			System.out.println("fileName :"+fileName);
			FR = new FileReader(fileName);
			BR = new BufferedReader(FR);

			try {
				while ((content = BR.readLine()) != null) {
					 System.out.println(content);
					split = content.split(",");
					 for(int i = 0; i<split.length;i++){

					 System.out.println("i = "+i+" "+split[i]);
//					 if(split[35].contains(expectedResult)){

//					 actualResultFlag = true;
//					 }else{

//					 actualResultFlag = false;
//					 }
					 }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.log("Exception while reading CSV file: "+e.getMessage());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log("Exception while opening CSV file: "+e.getMessage());
		}

		return split[cellNumber];

	}

//Method to get the latest file
	private static File getLatestFile(String filePath) {
		// TODO Auto-generated method stub
		
		
		 File dir = new File(filePath);
		    File[] files = dir.listFiles();
		    
		    if (files == null || files.length == 0) {
		        return null;
		    }
		
		    File lastModifiedFile = files[0];
		    System.out.println("files[0] :"+files[0]);
		    System.out.println("lastModifiedFile :"+lastModifiedFile);
		    for (int i = 1; i < files.length; i++) {
		       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
		           lastModifiedFile = files[i];
		       }
		    }
		    return lastModifiedFile;
		
	}
	
	//Method to rename file
	public static void renameFile(String presentName, String newName){
		
		File oldName = new File(presentName);
		File rename = new File(newName);
		
		oldName.renameTo(rename);
		
		logger.info("Renamed file : "+presentName+" to new name : "+newName);
	}
	
	//Method to copy file
	public static void copyFile(String presentPath, String newPath){
		
		try {
			
			File oldName = new File(presentPath);
			File rename = new File(newPath);		
		
			Files.copy(oldName, rename);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
		}		
		
		logger.info("Copied file from : "+presentPath+" to new directory : "+newPath);
	}


}
