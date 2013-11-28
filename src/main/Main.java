package main;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;

import java.lang.Exception;

import download.NewsDownloader;
import core.KeywordListGenerator;

public class Main {
	public static void main(String[] args){
		// Validate args
		if(args.length != 4){
			System.out.println(usage(0));
			return;
		}
		
		
		try{
			// Read from file the paths where will be saved:
			// - downloaded articles
			// - downloaded candidates XMLs
			// - generated keyword lists
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String newsPath = reader.readLine(); // path to save the downloaded news
			String XMLsPath = reader.readLine(); // path to save the downloaded XMLs
			String keywordListPath = reader.readLine(); // path to save the generated keyword list
			reader.close();
		
			// Read from file the news sites to be used
			ArrayList<String> newsSites = new ArrayList<String>(); // news sites to be used
			reader = new BufferedReader(new FileReader(args[1]));
			String aux;
			while(true){
				aux = reader.readLine();
				if(aux == null)
					break;
				newsSites.add(aux);
			}
			reader.close();	
		
			// Download the news and generate the keyword list
			System.out.println("down");
			//NewsDownloader.downloadNews(newsPath, newsSites, new ValidateURLWrapper(), new ExtractTextWrapper());
			System.out.println("gen");
			KeywordListGenerator.generateKeywordList(newsPath, XMLsPath, keywordListPath, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			System.out.println("ok");
		}
		catch(Exception e){
			System.out.println();
			e.printStackTrace();
			//TODO
		}
	}
	
	private static String usage(int errorID){
		// error ID
		// == 0 -> invalid number of args
		// == x -> invalid args[x - 1]
		switch(errorID){
			case 0:
				return new String("");
			case 1:
				return new String("");
			case 2:
				return new String("");
			default:
				return new String("");
		}
	}
}