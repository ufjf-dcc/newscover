package download;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

import java.lang.Exception;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import methods.ExtractText;
import methods.ValidateURL;

public class NewsDownloader {
	public static void downloadNews(String path, ArrayList<String> newsSites, ValidateURL validateURL, ExtractText extractText){		
		// Create list of URLs whose contents are to be downloaded
		ArrayList<String> urls = URLSelector.selectURLs(newsSites, validateURL);
		int i = 0; // news ID
		// Create a folder whose name is current date
		if(! path.endsWith("/"))
			path = path + "/";
		path = path + FolderName.generateFolderName();
		File folder = new File(path);
		folder.mkdir();
		path = path + "/";
		
		// Open an HTTP connection
		DefaultHttpClient httpclient = new DefaultHttpClient();
		for(String url : urls){
			try{
				// Set the connection to access 'url'
				HttpGet httpget = new HttpGet(url);
				// Access 'url'
				HttpResponse response = httpclient.execute(httpget);
				// Get the downloaded content
				HttpEntity entity = response.getEntity();
				if(entity == null){ // If no content was found
					// TODO
					continue;
				}
				String content = EntityUtils.toString(entity);
				if(content == null || content == ""){
					// TODO
					continue;
				}
				
				// Release the connection
				httpget.releaseConnection();
				
				// Create file with relevant text extracted from content
				String text = (String) extractText.extract(content);
				FileWriter file = new FileWriter(new File(path + Integer.toString(i) + ".txt"));
				file.write(text);
				file.close();
				
				i++;
			}
			catch(Exception e){
				System.out.println(e.toString() + "\n");
				// TODO
			}
		}
		// Close the HTTP connection
		httpclient.getConnectionManager().shutdown();
	}
}

