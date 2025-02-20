package org.rogue.domain;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import org.rogue.domain.RoomConfig;
import org.rogue.presentation.GameInfo;

import java.util.ArrayList;
import java.util.Random;

public class ActionHandling {
    final static Random random=new Random();
    public static void TakeAid (int room, ArrayList<RoomConfig> rooms){
        int aid=0;
        for(; aid<rooms.get(room).aidSet.size(); aid+=1)
            if(Gamer.x== rooms.get(room).aidSet.get(aid).x && Gamer.y== rooms.get(room).aidSet.get(aid).y)
                break;
        if(aid<rooms.get(room).aidSet.size()) {
            if (rooms.get(room).aidSet.get(aid).aidId == 0 && Gamer.bag.sword == 0) {
                if (Gamer.bag.max < 9) {
                    Gamer.bag.max += 1;
                    Gamer.bag.sword = 1;
                    rooms.get(room).aidSet.remove(aid);
                    Gamer.bag.items.add(new BagItem(0));
                } else if (Gamer.bag.club == 1) {
                    Gamer.bag.sword = 1;
                    Gamer.bag.club = 0;
                    rooms.get(room).aidSet.get(aid).aidId = 1;
                    for(int item=0, secNumber=0; item<Gamer.bag.items.size(); item+=1)
                        if(Gamer.bag.items.get(item).id==1){
                            Gamer.bag.items.get(item).id=0;
                            if(Gamer.bag.items.get(item).selected){
                                Gamer.power-=1;
                                Gamer.skill-=1;
                                Gamer.power+=3;
                                Gamer.skill-=2;
                            }
                            break;
                        }
                }
            } else if (rooms.get(room).aidSet.get(aid).aidId == 1 && Gamer.bag.club == 0) {
                if (Gamer.bag.max < 9) {
                    Gamer.bag.max += 1;
                    Gamer.bag.club = 1;
                    rooms.get(room).aidSet.remove(aid);
                    Gamer.bag.items.add(new BagItem(1));
                } else if (Gamer.bag.sword == 1) {
                    Gamer.bag.club = 1;
                    Gamer.bag.sword = 0;
                    rooms.get(room).aidSet.get(aid).aidId = 0;
                    for(int item=0, secNumber=0; item<Gamer.bag.items.size(); item+=1)
                        if(Gamer.bag.items.get(item).id==0){
                            Gamer.bag.items.get(item).id=1;
                            if(Gamer.bag.items.get(item).selected){
                                Gamer.power+=3;
                                Gamer.skill-=2;
                                Gamer.power-=1;
                                Gamer.skill-=1;
                            }
                            break;
                        }
                }
            }
            else if (rooms.get(room).aidSet.get(aid).aidId == 2 && Gamer.bag.max < 9) {
                Gamer.bag.food += 1;
                Gamer.bag.max += 1;
                rooms.get(room).aidSet.remove(aid);
                Gamer.bag.items.add(new BagItem(2));
            } else if (rooms.get(room).aidSet.get(aid).aidId == 3 && Gamer.bag.max < 9) {
                Gamer.bag.drink += 1;
                Gamer.bag.max += 1;
                rooms.get(room).aidSet.remove(aid);
                Gamer.bag.items.add(new BagItem(3));
            } else if (rooms.get(room).aidSet.get(aid).aidId == 4 && Gamer.bag.max < 9) {
                Gamer.bag.scroll += 1;
                Gamer.bag.max += 1;
                rooms.get(room).aidSet.remove(aid);
                Gamer.bag.items.add(new BagItem(4));
            } else if(rooms.get(room).aidSet.get(aid).aidId == 5){
                Gamer.gold += 1;
                rooms.get(room).aidSet.remove(aid);
            }
        }
    }
    public static int CheckForHit(int room, ArrayList<RoomConfig> rooms){
        for(int enemy=0; enemy<rooms.get(room).enemySet.size(); enemy+=1)
            if((Gamer.x== rooms.get(room).enemySet.get(enemy).enemyX && Gamer.y== rooms.get(room).enemySet.get(enemy).enemyY) ||
                    (Gamer.prevX== rooms.get(room).enemySet.get(enemy).enemyX && Gamer.prevY== rooms.get(room).enemySet.get(enemy).enemyY))
                return enemy;
        return rooms.get(room).enemySet.size();
    }
    public static void HitEnemy(int room, ArrayList<RoomConfig> rooms, int enemy){
        if(Gamer.prevX== rooms.get(room).enemySet.get(enemy).enemyX && Gamer.prevY== rooms.get(room).enemySet.get(enemy).enemyY) {
            Gamer.x = Gamer.prevX;
            Gamer.y = Gamer.prevY;
        }
        if(Gamer.skill>rooms.get(room).enemySet.get(enemy).skill ||
                (Gamer.skill==rooms.get(room).enemySet.get(enemy).skill && Gamer.power>rooms.get(room).enemySet.get(enemy).power)){
            Gamer.skill-=(rooms.get(room).enemySet.get(enemy).skill/4);
            while(Gamer.skill<0) Gamer.skill+=1;
            if(rooms.get(room).enemySet.get(enemy).enemyName!='v' || rooms.get(room).enemySet.get(enemy).hit==1){
                rooms.get(room).enemySet.get(enemy).health-=(rooms.get(room).enemySet.get(enemy).power/2+1);
                Toolkit.printString("X", Gamer.x, Gamer.y, new CharColor(CharColor.CYAN, CharColor.WHITE));
            }
            else if(rooms.get(room).enemySet.get(enemy).enemyName=='v') {
                rooms.get(room).enemySet.get(enemy).hit = 1;
                Toolkit.printString("-", Gamer.x, Gamer.y, new CharColor(CharColor.WHITE, CharColor.WHITE));
            }
        }
        else if (Gamer.skill<rooms.get(room).enemySet.get(enemy).skill ||
                (Gamer.skill==rooms.get(room).enemySet.get(enemy).skill && Gamer.power<rooms.get(room).enemySet.get(enemy).power)){
            Gamer.skill -= rooms.get(room).enemySet.get(enemy).skill/2;
            while(Gamer.skill<0) Gamer.skill+=1;
            Gamer.health -= (rooms.get(room).enemySet.get(enemy).power/2-1);
            Toolkit.printString("X", Gamer.x, Gamer.y, new CharColor(CharColor.RED, CharColor.BLACK));
        }
        else {
            Gamer.skill -= (rooms.get(room).enemySet.get(enemy).skill / 2-1);
            while(Gamer.skill<0) Gamer.skill+=1;
            Toolkit.printString("X", Gamer.x, Gamer.y, new CharColor(CharColor.RED, CharColor.WHITE));
        }
        if(rooms.get(room).enemySet.get(enemy).health<=0)
            rooms.get(room).enemySet.remove(enemy);
        else{
            rooms.get(room).enemySet.get(enemy).hitCountdown=System.currentTimeMillis();
            if(rooms.get(room).enemySet.get(enemy).enemyName=='m')
                rooms.get(room).enemySet.get(enemy).hit=1;
        }
        if(Gamer.health<=0) {
            GameInfo.gameOver();
            System.exit(0);
        }
    }
}
