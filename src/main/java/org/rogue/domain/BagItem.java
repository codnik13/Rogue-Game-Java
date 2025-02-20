package org.rogue.domain;
import java.util.Random;

public class BagItem {
    public long count;
    public boolean selected=false;
    public int id;
    public String target;
    final Random random=new Random();

    BagItem(int id){
        this.id=id;
        int rand=random.nextInt(3);
        if(id==3 || id==4)
            target=(rand==0 ? "maxHealth" : (rand==1 ? "power" : "skill"));
        else target="";
    }
    public BagItem(int id, String target){
        this.id=id;
        this.target=target;
    }
}
