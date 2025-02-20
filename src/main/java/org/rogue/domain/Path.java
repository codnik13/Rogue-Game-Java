package org.rogue.domain;
import java.util.ArrayList;

public class Path {
    int from;
    int to;
    boolean present=true;
    int stopX=0;
    int stopY=0;
    final ArrayList<Integer> pathX=new ArrayList<>();
    final ArrayList<Integer> pathY=new ArrayList<>();
    boolean passed=false;
    boolean reverse=false;
    int step=0;

    public Path(int from, int to){
        this.from=from;
        this.to=to;
    }
}
