package main;

import methods.ExtractText;
import java.lang.IllegalArgumentException;

class ExtractTextWrapper implements ExtractText{
	// Returns the two first paragraphs of the article
	public String extract(String content) throws IllegalArgumentException{
		String relevant;

		// BBC
		if(content.contains("resource=\"http://www.bbc.co.uk")){
			// First paragraph
			int pOffSet = content.indexOf("class=\"introduction\"");
			if(pOffSet == -1)
				return null;
			int startOffSet = content.indexOf(">", pOffSet) + 1;
			relevant = content.substring(startOffSet, content.indexOf("<", startOffSet));
			// Second paragraph
			pOffSet = content.indexOf("<p>", startOffSet);
			startOffSet = pOffSet + 3;
			relevant = relevant + content.substring(startOffSet, content.indexOf("<", startOffSet));
		}
		
		// The Guardian
		else if(content.contains("content=\"http://www.theguardian.com")){
			// First paragraph
			int pOffSet = content.indexOf("<div class=\"section\">");
			if(pOffSet == -1)
				return null;
			int startOffSet = content.indexOf("<strong>", pOffSet) + 8;
			relevant = content.substring(startOffSet, content.indexOf("<", startOffSet));
			// Seconf paragraph
			pOffSet = content.indexOf("<div id=\"article-body-blocks\">", startOffSet);
			startOffSet = content.indexOf("<p>", pOffSet) + 3;
			relevant = relevant + content.substring(startOffSet, content.indexOf("</p>", startOffSet));
		}
		
		// New York Times
		else if(content.contains("itemid=\"http://www.nytimes.com")){
			// First paragraph
			int pOffSet = content.indexOf("<NYT_HEADLINE");
			if(pOffSet == -1)
				return null;
			int startOffSet = content.indexOf(">", pOffSet) + 1;
			relevant = content.substring(startOffSet, content.indexOf("<", startOffSet));
			// Seconf paragraph
			pOffSet = content.indexOf("<p itemprop=\"articleBody\">", startOffSet);
			startOffSet = content.indexOf(">", pOffSet) + 1;
			relevant = relevant + content.substring(startOffSet, content.indexOf("<", startOffSet));
		}
		
		else // TODO: throw Exception
			relevant = "";

		relevant = removeAnchors(relevant);
		return relevant;
	}
	
	// Remove all anchor tags from the string <a ...> </a>
	private String removeAnchors(String relevant){
		String clean = "";
		int offSetA = 0;
		int offSetB = relevant.indexOf("<a");
		
		while(true){
			if(offSetB == -1)
				break;
			clean = clean + relevant.substring(offSetA, offSetB);
			offSetA = relevant.indexOf(">", offSetB);
			offSetB = relevant.indexOf("</a>", offSetA);
			clean = clean + relevant.substring(offSetA, offSetB);
			offSetA = offSetB + 4;
			offSetB = relevant.indexOf("<a", offSetA);
		}
		
		clean = clean + relevant.substring(offSetA);
		return clean;
	}
}
