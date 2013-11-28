package core;

public class KWNode implements Comparable<KWNode>{
    public KWNode(String kw, Double cs, Double fs){
        this.keyword = kw;
        this.contextualScore = cs;
        this.finalScore = fs;
    }
    
    public int compareTo(KWNode that){
        if(this.contextualScore < that.contextualScore) return -1;
        if(this.contextualScore == that.contextualScore){
            if(this.finalScore < that.finalScore) return -1;
            if(this.finalScore == that.finalScore) return 0;
            return 1;
        }
        return 1;
    }
    
    public final String keyword;
    public final Double contextualScore;
    public final Double finalScore;
}