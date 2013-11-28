package core;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class XMLParser{
    public static ArrayList<Keywords> parseXMLFiles(String path) throws Exception{
        ArrayList<Keywords> keywords = new ArrayList<Keywords>();
        ArrayList<String> resources = new ArrayList<String>();
        ArrayList<Double> contextualScore = new ArrayList<Double>();
        ArrayList<Double> finalScore = new ArrayList<Double>();
        
    	File dir = new File(path);
    	String [] files = dir.list();
    	for(String file : files){ // for each XML file
			BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
			String line = reader.readLine();
			line = reader.readLine(); // skip to 2nd line
			while(line != null){
				if(line.contains("surfaceForm")){
					line = reader.readLine();
					if(line == null)
						break;
					if(line.contains("surfaceForm")) // previous surfaceForm has no candidates associated
						continue;
					if(line.contains("resource")){
					    int ind = line.indexOf("label=") + 7;
					    resources.add(line.substring(ind, line.indexOf("\"", ind))); // get the resource label
					    ind = line.indexOf("contextualScore=", ind) + 17;
					    contextualScore.add(Double.parseDouble(line.substring(ind, line.indexOf("\"", ind)))); // get the resource's contextual score
					    ind = line.indexOf("finalScore=", ind) + 12;
					    finalScore.add(Double.parseDouble(line.substring(ind, line.indexOf("\"", ind)))); // get the resource's final score
					    
					    line = reader.readLine();
					    while(line != null && ! line.contains("surfaceForm")) // skip to next surfaceForm (only one resource per surface form is recorded)
					    	line = reader.readLine();
					    continue;
					}
				}
				else
					line = reader.readLine();
			}
			for(int i = 0; i < resources.size(); i++){ // remove all resouces whose scores are below 10%
				if(contextualScore.get(i) < 0.1 || finalScore.get(i) < 0.1){
					resources.remove(i);
					contextualScore.remove(i);
					finalScore.remove(i);
					i--;
				}
			}
			if(resources.size() > 0){
    			keywords.add(new Keywords(resources, contextualScore, finalScore)); // record all information before proceeding to the next XML file
    			resources.clear();
    			contextualScore.clear();
    			finalScore.clear();
    		}
			reader.close();
    	}
    	return keywords;
    }
}