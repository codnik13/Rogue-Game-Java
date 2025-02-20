package org.rogue.presentation;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import org.rogue.domain.Aid;
import org.rogue.domain.Enemy;
import org.rogue.domain.RoomConfig;
import java.util.ArrayList;
import java.util.Random;

public class RoomInterior {
    
    public RoomInterior(int initialRoom, ArrayList<RoomConfig> rooms, int enemies, int aids, int portalX, int portalY){
        for(int i=0; i<aids;) {
            if(Aid.DefineLocateAid(rooms, portalX, portalY)==1)
                i+=1;
            else enemies-=1;
        }
        for(int i=0; i<enemies; i+=1)
            Enemy.DefineLocateEnemy(initialRoom, rooms);
    }
    public  void drawRoomInterior(int room, ArrayList<RoomConfig> rooms, int hit, int portalX, int portalY){
        for(int y = rooms.get(room).roomY +1; y<rooms.get(room).roomY +rooms.get(room).roomHeight; y+=1)
            for(int x = rooms.get(room).roomX +1; x<rooms.get(room).roomX +rooms.get(room).roomWidth; x+=1) {
                int e=0;
                for(; e<rooms.get(room).enemySet.size(); e+=1)
                    if(rooms.get(room).enemySet.get(e).enemyX == x && rooms.get(room).enemySet.get(e).enemyY==y)
                        break;
                if(e==rooms.get(room).enemySet.size()) {
                    int k=0;
                    for(; k<rooms.get(room).aidSet.size(); k+=1)
                        if(rooms.get(room).aidSet.get(k).x==x && rooms.get(room).aidSet.get(k).y==y)
                            break;
                    if(k==rooms.get(room).aidSet.size() && (portalX != x || portalY != y))
                        Toolkit.printString("-", x, y, new CharColor(CharColor.BLACK, CharColor.YELLOW));
                }
            }
        for(int e=0; e<rooms.get(room).aidSet.size(); e+=1){
            if (rooms.get(room).aidSet.get(e).aidId == 0)
                Toolkit.printString("/", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
            else if (rooms.get(room).aidSet.get(e).aidId == 1)
                Toolkit.printString("|", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
            else if (rooms.get(room).aidSet.get(e).aidId == 2)
                Toolkit.printString("+", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
            else if(rooms.get(room).aidSet.get(e).aidId == 3)
                Toolkit.printString("*", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
            else if(rooms.get(room).aidSet.get(e).aidId == 4)
                Toolkit.printString("^", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
            else Toolkit.printString("$", rooms.get(room).aidSet.get(e).x, rooms.get(room).aidSet.get(e).y, new CharColor(CharColor.BLACK, CharColor.CYAN));
        }
        for(int e=0; e<rooms.get(room).enemySet.size(); e+=1) {
            if(e!=hit) {
                if (rooms.get(room).enemySet.get(e).enemyName == 'v')
                    Toolkit.printString("v", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.RED));
                else if (rooms.get(room).enemySet.get(e).enemyName == 'z')
                    Toolkit.printString("z", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.GREEN));
                else if (rooms.get(room).enemySet.get(e).enemyName == 'g')
                    Toolkit.printString("g", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.WHITE));
                else if (rooms.get(room).enemySet.get(e).enemyName == 'O')
                    Toolkit.printString("O", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.YELLOW));
                else if (rooms.get(room).enemySet.get(e).enemyName == 's')
                    Toolkit.printString("s", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.WHITE));
                else{
                    String mim;
                    if(rooms.get(room).enemySet.get(e).hit==0) {
                        if (rooms.get(room).enemySet.get(e).mim.equals("sword"))
                            mim = "/";
                        else if (rooms.get(room).enemySet.get(e).mim.equals("club"))
                            mim = "|";
                        else if (rooms.get(room).enemySet.get(e).mim.equals("food"))
                            mim = "+";
                        else if (rooms.get(room).enemySet.get(e).mim.equals("drink"))
                            mim = "*";
                        else if (rooms.get(room).enemySet.get(e).mim.equals("scroll"))
                            mim = "^";
                        else mim = "$";
                        Toolkit.printString(mim, rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.CYAN));
                    }
                    else Toolkit.printString("m", rooms.get(room).enemySet.get(e).enemyX, rooms.get(room).enemySet.get(e).enemyY, new CharColor(CharColor.BLACK, CharColor.WHITE));
                }
            }
        }
        for(int y = rooms.get(room).roomY +1; y<rooms.get(room).roomY +rooms.get(room).roomHeight; y+=1)
            for(int x = rooms.get(room).roomX +1; x<rooms.get(room).roomX +rooms.get(room).roomWidth; x+=1)
                if(x==portalX && y==portalY) {
                    Toolkit.printString("P", x, y, new CharColor(CharColor.BLACK, CharColor.CYAN));
                    break;
                }
        for(int door=0; door<rooms.get(room).doorX.size(); door+=1)
            Toolkit.printString("#", rooms.get(room).doorX.get(door), rooms.get(room).doorY.get(door), new CharColor(CharColor.BLACK, CharColor.BLACK));
    }
}
