package download;

import java.util.Calendar;

public class FolderName {
	public static String generateFolderName(){
		// Return a string with current date formated like mm-dd-yyyy
		String folderName;
		
		int day = Calendar.DAY_OF_MONTH;
		int month = Calendar.MONTH;
		int year = Calendar.YEAR;
		
		folderName = (month < 10 ? "0" + month : month) + "-";
		folderName = folderName + (day < 10 ? "0" + day : day) + "-";
		folderName = folderName + year;
		
		return folderName;
	}
}
