package download;

import java.util.ArrayList;

import java.lang.Exception;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import methods.ValidateURL;

public class URLSelector {
	public static ArrayList<String> selectURLs(ArrayList<String> newsSites, ValidateURL validateURL){
		ArrayList<String> selectedURLs = new ArrayList<String>();
		for(String site : newsSites){
			try{
				// Get all accessible links from site
				Document doc = Jsoup.connect(site).get();
				Elements urls = doc.select("a[href]");
				
				// Choose which links are valid
				for (Element url : urls) {
					String possibleURL = url.attr("abs:href");
					if((Boolean) validateURL.validate(possibleURL)){
						if(! selectedURLs.contains(possibleURL))
							selectedURLs.add(possibleURL);
					}
	            }
			}
			catch(Exception e){
				// TODO
			}
		}
		return selectedURLs;
	}
}
