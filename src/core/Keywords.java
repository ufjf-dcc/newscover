
package core; 

import java.util.ArrayList;
import java.util.Collections;

public class Keywords implements Comparable<Keywords>{        
    public Keywords(ArrayList<String> kw, ArrayList<Double> cs, ArrayList<Double> fs){
        this.data = new ArrayList<KWNode>();
        for(int i = 0; i < kw.size(); i++)
        	this.data.add(new KWNode(kw.get(i), cs.get(i), fs.get(i)));
    }
    
    public void sort(){
        Collections.sort(data);
    }
    
    public int compareTo(Keywords that){
        double meanCS1 = 0;
        double meanFS1 = 0;
        double meanCS2 = 0;
        double meanFS2 = 0;
        for(KWNode a : data){
            meanCS1 += a.contextualScore;
            meanFS1 += a.finalScore;
        }
        for(KWNode b : that.data){
        	meanCS2 += b.contextualScore;
        	meanFS2 += b.finalScore;
        }
        if(meanCS1 < meanCS2) return -1;
        if(meanCS1 == meanCS2){
            if(meanFS1 < meanFS2) return -1;
            if(meanFS1 == meanFS2) return 0;
            return 1;
        }
        return 1;
    }
    
    public ArrayList<KWNode> data;
}