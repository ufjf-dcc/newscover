package core;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import download.CandidatesDownloader;
import download.FolderName;

public class KeywordListGenerator {

    public static void generateKeywordList(String newsTxtDirectoryPath, String XMLDirectoryPath, String KeywordListFilePath, int maxKeywords, int maxClusters) throws Exception {
        // Please note that the download.sh script must be in the XMLDirectoryPath
        ArrayList<Keywords> keywords;
        CandidatesDownloader.downloadCandidates(newsTxtDirectoryPath, XMLDirectoryPath);
        keywords = KeywordGroupsSelector.selectKeywordGroups(XMLParser.parseXMLFiles(XMLDirectoryPath), maxKeywords, maxClusters);

        if(! KeywordListFilePath.endsWith("/"))
        	KeywordListFilePath = KeywordListFilePath + "/";
        KeywordListFilePath = KeywordListFilePath + FolderName.generateFolderName();
        File f = new File(KeywordListFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
        for (Keywords k : keywords) {
            for (KWNode kw : k.data) {
                writer.write(kw.keyword);
                writer.newLine();
            }
            writer.newLine();
        }
        writer.close();
    }
}