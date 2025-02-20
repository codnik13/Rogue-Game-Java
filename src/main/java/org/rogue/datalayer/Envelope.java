package org.rogue.datalayer;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.rogue.domain.Gamer;

public class Envelope {
    String name;
    long time;
    int level;
    int health;
    int power;
    int skill;
    int maxHealth;
    int gold;
    int totalArmor;
    int max;
    int sword;
    int club;
    int scroll;
    int drink;
    int food;
    int[] id={-1,-1,-1,-1,-1,-1,-1,-1,-1};
    String[] target=new String[9];
    Envelope(){
        name=Gamer.name;
        time=System.currentTimeMillis();
        level=Gamer.level;
        health=Gamer.health;
        power=Gamer.power;
        skill=Gamer.skill;
        maxHealth=Gamer.maxHealth;
        gold=Gamer.gold;
        totalArmor=Gamer.totalArmor;
        max=Gamer.bag.max;
        sword=Gamer.bag.sword;
        club=Gamer.bag.club;
        food=Gamer.bag.food;
        drink=Gamer.bag.drink;
        scroll=Gamer.bag.scroll;
        for(int i=0; i<Gamer.bag.items.size(); i+=1){
            if(!(Gamer.bag.items.get(i).selected && Gamer.bag.items.size()>Gamer.bag.max && Gamer.bag.items.get(i).id>2)) {
                id[i]=Gamer.bag.items.get(i).id;
                target[i]=Gamer.bag.items.get(i).target;
            }
        }
    }
}
