package org.rogue.domain;
import org.rogue.domain.BagItem;
import java.util.ArrayList;

public class Bag {
    public int max=0;
    public int sword=0;
    public int club=0;
    public int scroll=0;
    public int drink=0;
    public int food=0;
    public ArrayList<BagItem> items;
    Bag(){
        items=new ArrayList<>();
    }
}

