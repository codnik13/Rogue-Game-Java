package org.rogue.domain;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import org.rogue.domain.RoomConfig;
import org.rogue.presentation.RoomInterior;
import java.util.ArrayList;
import java.util.Random;

public class Layout {
    boolean inside =true;
    public final int screenWidth= Toolkit.getScreenWidth();
    public final int screenHeight=Toolkit.getScreenHeight()-3;
    int initialRoom;
    int portalRoom;
    int portalX=0;
    int portalY=0;
    RoomInterior roomInterior;

    public Layout(ArrayList<RoomConfig> rooms, ArrayList<Path> paths, int deadEnd, int enemies, int aids){
        final Random random=new Random();
        do initialRoom =random.nextInt(9);
        while(initialRoom ==deadEnd);
        do portalRoom=random.nextInt(9);
        while(portalRoom== initialRoom || rooms.get(portalRoom).doorX.size()==1);
        while(true) {
            portalX = random.nextInt(rooms.get(portalRoom).roomWidth - 1) + 1 + rooms.get(portalRoom).roomX;
            portalY = random.nextInt(rooms.get(portalRoom).roomHeight - 1) + 1 + rooms.get(portalRoom).roomY;
            int e=0;
            for(; e<rooms.get(portalRoom).enemySet.size(); e+=1)
                if(portalX== rooms.get(portalRoom).enemySet.get(e).enemyX && portalY== rooms.get(portalRoom).enemySet.get(e).enemyY)
                    break;
            if(e==rooms.get(portalRoom).enemySet.size())
                break;
        }
        roomInterior=new RoomInterior(initialRoom, rooms, enemies, aids, portalX, portalY);
        Gamer.x=random.nextInt(rooms.get(initialRoom).roomWidth-4)+rooms.get(initialRoom).roomX +2;
        Gamer.y=random.nextInt(rooms.get(initialRoom).roomHeight-4)+rooms.get(initialRoom).roomY +2;
        rooms.get(initialRoom).drawRoomWalls();
        roomInterior.drawRoomInterior(initialRoom, rooms, -1, portalX, portalY);
        Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
    }
    public boolean keepPlaying(ArrayList<RoomConfig> rooms, ArrayList<Path> paths){
        if(Gamer.x==portalX && Gamer.y==portalY)
            return false;
        for(int item=0; item<Gamer.bag.items.size(); item+=1){
            if(Gamer.bag.items.get(item).id==3 && Gamer.bag.items.get(item).selected && System.currentTimeMillis()-Gamer.bag.items.get(item).count>60000) {
                if(Gamer.bag.items.get(item).target.equals("maxHealth")) {
                    Gamer.maxHealth -= 3;
                    while(Gamer.maxHealth<0) Gamer.maxHealth+=1;
                }
                else if(Gamer.bag.items.get(item).target.equals("skill")) {
                    Gamer.skill -= 3;
                    while(Gamer.skill<0) Gamer.skill+=1;
                }
                else {
                    Gamer.power -= 3;
                    while(Gamer.power<0) Gamer.power+=1;
                }
                Gamer.bag.items.remove(item);
                item=-1;
            }
        }
        int room=0;
        while(room<9 && (Gamer.x< rooms.get(room).roomX || Gamer.x> rooms.get(room).roomX + rooms.get(room).roomWidth ||
                Gamer.y< rooms.get(room).roomY || Gamer.y> rooms.get(room).roomY + rooms.get(room).roomHeight))
            room+=1;
        if(room==9) {
            boolean onPath=false;
            for(int path=0; path<paths.size(); ++path){
                for(int v=0; v<paths.get(path).pathX.size(); ++v)
                    if(Gamer.x==paths.get(path).pathX.get(v) && Gamer.y==paths.get(path).pathY.get(v)) {
                        onPath=true;
                        break;
                    }
                if(onPath){
                    Toolkit.printString("#", Gamer.prevX, Gamer.prevY, new CharColor(CharColor.WHITE, CharColor.WHITE));
                    Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
                    break;
                }
            }
            if (!onPath){
                Gamer.x=Gamer.prevX;
                Gamer.y=Gamer.prevY;
            }
            return true;
        }
        if(!inside) {
            int door = 0;
            while (door < rooms.get(room).doorX.size() && (Gamer.x != rooms.get(room).doorX.get(door) || Gamer.y != rooms.get(room).doorY.get(door)))
                door+=1;
            if (door < rooms.get(room).doorX.size()) {
                if(!rooms.get(room).isChecked)
                    rooms.get(room).drawRoomWalls();
                roomInterior.drawRoomInterior(room, rooms, -1, portalX, portalY);
                inside =true;
                Toolkit.printString("#", Gamer.prevX, Gamer.prevY, new CharColor(CharColor.WHITE, CharColor.WHITE));
                Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
            }
            else{
                if(Gamer.x>rooms.get(room).roomX && Gamer.x<rooms.get(room).roomX +rooms.get(room).roomWidth &&
                        Gamer.y>rooms.get(room).roomY && Gamer.y<rooms.get(room).roomY +rooms.get(room).roomHeight) {
                    inside = true;
                    Toolkit.printString("#", Gamer.prevX, Gamer.prevX, new CharColor(CharColor.BLACK, CharColor.BLACK));
                    Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
                }
                else {
                    Gamer.x=Gamer.prevX;
                    Gamer.y=Gamer.prevY;
                }
            }
            return true;
        }  // enemy motion logic
        Enemy.enemyCurrentTime=System.currentTimeMillis();
        if(Enemy.enemyCurrentTime-Enemy.enemyTimeCount>100-Gamer.level*2) {
            Enemy.enemyTimeCount=Enemy.enemyCurrentTime;
            Enemy.EnemyMoveLogic(room, rooms);
        }
        if(Gamer.x> rooms.get(room).roomX && Gamer.x< rooms.get(room).roomX + rooms.get(room).roomWidth &&
                Gamer.y> rooms.get(room).roomY && Gamer.y< rooms.get(room).roomY +rooms.get(room).roomHeight) {
            int enemy=ActionHandling.CheckForHit(room, rooms);
            if(enemy==rooms.get(room).enemySet.size()) { // if not hit an enemy
                roomInterior.drawRoomInterior(room, rooms, -1, portalX, portalY);
                rooms.get(room).drawRoomWalls();
                Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
            }
            else{
                roomInterior.drawRoomInterior(room, rooms, enemy, portalX, portalY);
                ActionHandling.HitEnemy(room, rooms, enemy);
            }
            ActionHandling.TakeAid(room, rooms);
        }
        else {
            int door = 0;
            while (door < rooms.get(room).doorX.size() && !(Gamer.x == rooms.get(room).doorX.get(door) && Gamer.y == rooms.get(room).doorY.get(door)))
                door+=1;
            if (door < rooms.get(room).doorX.size()) {
                inside = false;
                rooms.get(room).drawRoomEmpty();
                Toolkit.printString("#", Gamer.prevX, Gamer.prevY, new CharColor(CharColor.BLACK, CharColor.BLACK));
                Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
            }
            else{
                Gamer.x=Gamer.prevX;
                Gamer.y=Gamer.prevY;
                int enemy=ActionHandling.CheckForHit(room, rooms);
                if(enemy==rooms.get(room).enemySet.size()) { // if not hit an enemy
                    roomInterior.drawRoomInterior(room, rooms, -1, portalX, portalY);
                    rooms.get(room).drawRoomWalls();
                    Toolkit.printString("@", Gamer.x, Gamer.y, new CharColor(CharColor.BLACK, CharColor.GREEN));
                }
                else{
                    roomInterior.drawRoomInterior(room, rooms, enemy, portalX, portalY);
                    ActionHandling.HitEnemy(room, rooms, enemy);
                }
            }
        }
        return true;
    }
}