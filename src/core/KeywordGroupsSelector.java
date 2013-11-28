package core;

import java.util.ArrayList;
import java.util.Collections;

public class KeywordGroupsSelector{
	public static ArrayList<Keywords> selectKeywordGroups(ArrayList<Keywords> kw, int maxKeywords, int maxClusters){
	    ArrayList<Keywords> groups = new ArrayList<Keywords>();
	    Integer [][] relations = new Integer[kw.size()][2];
	    for(int i = 0; i < kw.size(); i++){
	    	relations[i][0] = i;
	    	relations[i][1] = 0;
	   }
		for(int i = 0; i < kw.size(); i++){
		    for(int j = i + 1; j < kw.size(); j++){
		        int relatedKeywords = 0;
		        for(KWNode key1 : kw.get(i).data){
		            for(KWNode key2 : kw.get(j).data){
		                if(key1.keyword.compareToIgnoreCase(key2.keyword) == 0)
		                	relatedKeywords++;
		            }
		        }
		        if(relatedKeywords >= 3 && relatedKeywords > relations[j][1]){
		        	 relations[j][0] = i;
		        	 relations[j][1] = relatedKeywords;
		        }
		        else if(relatedKeywords == kw.get(i).data.size() || relatedKeywords == kw.get(j).data.size()){
		            relations[j][0] = i;
		            relations[j][1] = relatedKeywords;
		        }
		    }
		}
		for(int i = kw.size() - 1; i >= 0; i--){
			if(relations[i][0] != i){
			    int j;
			    for(j = relations[i][0]; relations[j][0] != j; j = relations[j][0]); // (nop)
			    relations[i][0] = j;
			}
		}
		for(int i = 0; i < kw.size(); i++){
		    if(relations[i][0] != i){
		        kw.get(relations[i][0]).data.addAll(kw.get(i).data);
		    }
		}
		for(int i = 0; i < kw.size(); i++){
		    if(relations[i][0] == i)
		    	groups.add(kw.get(i));
		}
		
		if(groups.size() > maxClusters){
			Collections.sort(groups);
			groups.subList(maxClusters, groups.size()).clear();
		}
		for(int i = 0; i < groups.size(); i++){
		    Keywords key = groups.get(i);
	        key.sort();
	        for(int j = 0; j < key.data.size(); j++){
	        	for(int k = j + 1; k < key.data.size(); k++){
	        	    if(key.data.get(j).keyword.compareToIgnoreCase(key.data.get(k).keyword) == 0){
	        	    	key.data.remove(k);
	        	    	k--;
	        	 	}
	        	 }
	      	}
            if(key.data.size() > maxKeywords)
                key.data.subList(maxKeywords, key.data.size()).clear();
		}
		return groups;   
	}
}