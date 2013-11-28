package main;

import java.util.Calendar;
import methods.ValidateURL;

class ValidateURLWrapper implements ValidateURL{
	public boolean validate(String url){
		
		// if it is a BBC link, contains the word 'news' in it and the last character is a number
		if(url.contains("www.bbc") && url.contains("news") && Character.isDigit(url.charAt(url.length() - 1)))
			return true;
		
		// if it is a The Guardian link, is not a video and contains month initials in it
		if(url.contains("www.theguardian") && !url.contains("/video/") && containsMonth(url))
			return true;
		
		// if it is a New York Time link and contains the actual date (yyyy/mm)
		if(url.contains("www.nytime") && containsDate(url))
			return true;
		
		// default case
		return false;
	}
	
	// tests whether a String contains month initials (jan, feb, mar, ...) 
	private boolean containsMonth(String url){ 
		return url.contains("jan") || url.contains("feb") || url.contains("mar") || url.contains("apr")
				 || url.contains("may") || url.contains("jun") || url.contains("jul") || url.contains("aug")
				 || url.contains("sep") || url.contains("oct") || url.contains("nov") || url.contains("dec");
	}
	
	// tests whether a String contains the actual date formated like yyyy/mm
	private boolean containsDate(String url){
		String date = Calendar.YEAR + "/" + Calendar.MONTH;
		return url.contains(date);
	}
}