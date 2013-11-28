package download;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;

public class CandidatesDownloader {

    public static void downloadCandidates(String inPath, String outPath) throws Exception {
        String spotlight = "http://spotlight.dbpedia.org/rest/candidates?text=";
        if(! inPath.endsWith("/"))
        	inPath = inPath + "/";
        if(! outPath.endsWith("/"))
        	outPath = outPath + "/";
        
        inPath = inPath + FolderName.generateFolderName();
        File dir = new File(inPath);
        String[] files = dir.list();
        String[] urls = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            BufferedReader reader = new BufferedReader(new FileReader(inPath + "/" + files[i]));
            String line = reader.readLine();
            String text = "";
            while (line != null) {
                text = text + line;
                line = reader.readLine();
            }
            urls[i] = spotlight + text;
            reader.close();
        }

        File urlFile = new File(outPath + "/urls.txt");
        if (!urlFile.exists()) {
            urlFile.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(urlFile.getAbsoluteFile()));
        for (String url : urls) {
            writer.write(url);
            writer.newLine();
        }
        writer.close();

        ProcessBuilder shellScript = new ProcessBuilder(outPath + "download.sh", outPath);
        Process p = shellScript.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = reader.readLine();
        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}