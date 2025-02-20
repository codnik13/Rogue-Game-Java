package org.rogue.domain;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class Enemy{
    final Random random=new Random();
    int health;
    int power;
    int skill;
    int animus;
    public int enemyY;
    public int enemyX;
    boolean left;
    boolean right;
    boolean down;
    boolean up;
    public char enemyName;
    public int hit;
    static long enemyTimeCount;
    static long enemyCurrentTime;
    boolean closest=false;
    long hitCountdown;
    public String mim;
    Enemy(int e){
        health=(e==0 || e==1 ? Gamer.level/7+4 : (e==2 ? Gamer.level/7+2 : (e==3 || e==5 ? Gamer.level/7+5 : Gamer.level/7+3)));
        power=(e==0 || e==1 ? Gamer.level/7+3 : (e==2 || e==5 ? Gamer.level/7+2 : (e==3 ? Gamer.level/7+5 : Gamer.level/7+4)));
        skill=(e==0 || e==2 || e==5 ? Gamer.level/7+4 : (e==1 || e==3 ? Gamer.level/7+2 : Gamer.level/7+5));
        animus=(e==0 || e==4 ? 3 : (e==1 || e==2 || e==5 ? 1 : 2));
        enemyName=(e==0 ? 'v' : (e==1 ? 'z' : (e==2 ? 'g' : (e==3 ? 'O' : (e==4 ? 's' : 'm')))));
        hit=(e==0 || e==5 ? 0 : 1);
        left=(e==0 || e==4 || e==2);
        right=(e==1 || e==3);
        down=(e==0 || e==2);
        up=(e==1 || e==4 || e==3);
        hitCountdown=0;
        if(e<5) mim="";
        else{
            int rand=random.nextInt(6);
            mim=(rand==0 ? "sword" : (rand==1 ? "club" : (rand==2 ? "food" : (rand==3 ? "drink" : (rand==4 ? "scroll" : "gold")))));
        }
    }
    public static void EnemyMoveLogic(int room, ArrayList<RoomConfig> rooms){
        int minDistX=10000;
        int minDistY=10000;
        int closest=-1;
        for(int e=0; e<rooms.get(room).enemySet.size(); e+=1)
            if(rooms.get(room).enemySet.get(e).closest)
                rooms.get(room).enemySet.get(e).closest=false;
        for(int enemy=0; enemy<rooms.get(room).enemySet.size(); enemy+=1){
            int animus=rooms.get(room).enemySet.get(enemy).animus;
            if(System.currentTimeMillis()- rooms.get(room).enemySet.get(enemy).hitCountdown>2000 &&
                    (Math.abs(rooms.get(room).enemySet.get(enemy).enemyX-Gamer.x)<(animus==1 ? 2 : (animus==2 ? 3 : (animus==3 ? 4 : 5))) &&
                    Math.abs(rooms.get(room).enemySet.get(enemy).enemyY-Gamer.y)<(animus==1 ? 2 : (animus==2 ? 3 : (animus==3 ? 4 : 5))))){
                if(rooms.get(room).enemySet.get(enemy).enemyX>Gamer.x)
                    rooms.get(room).enemySet.get(enemy).enemyX-=1;
                else if(rooms.get(room).enemySet.get(enemy).enemyX<Gamer.x)
                    rooms.get(room).enemySet.get(enemy).enemyX+=1;
                if(rooms.get(room).enemySet.get(enemy).enemyY>Gamer.y)
                    rooms.get(room).enemySet.get(enemy).enemyY-=1;
                else if(rooms.get(room).enemySet.get(enemy).enemyY<Gamer.y)
                    rooms.get(room).enemySet.get(enemy).enemyY+=1;
                if((minDistX>Math.abs(rooms.get(room).enemySet.get(enemy).enemyX-Gamer.x) && minDistY>=Math.abs(rooms.get(room).enemySet.get(enemy).enemyY-Gamer.y)) ||
                        (minDistX>=Math.abs(rooms.get(room).enemySet.get(enemy).enemyX-Gamer.x) && minDistY>Math.abs(rooms.get(room).enemySet.get(enemy).enemyY-Gamer.y))) {
                    minDistX = Math.abs(rooms.get(room).enemySet.get(enemy).enemyX - Gamer.x);
                    minDistY = Math.abs(rooms.get(room).enemySet.get(enemy).enemyY - Gamer.y);
                    if(closest>=0)
                        rooms.get(room).enemySet.get(closest).closest = false;
                    closest=enemy;
                    rooms.get(room).enemySet.get(enemy).closest=true;
                }
            }else {
                if (rooms.get(room).enemySet.get(enemy).enemyName == 'v' || rooms.get(room).enemySet.get(enemy).enemyName == 'z') {
                    VampMovePattern.vampMovePattern(room, rooms, enemy);
                } else if (rooms.get(room).enemySet.get(enemy).enemyName == 'g' || rooms.get(room).enemySet.get(enemy).enemyName == 'O') {
                    GhostMovePattern.ghostMovePattern(room, rooms, enemy);
                } else if (rooms.get(room).enemySet.get(enemy).enemyName == 's') {
                    SnakeMovePattern.snakeMovePattern(room, rooms, enemy);
                }
            }
        }
        if (closest < 0) Gamer.signal = 0;
        else if (Gamer.signal < 1) {
            Gamer.signal = -1;
            Enemy.Info(rooms, room, closest);
        }
    }
    public static void DefineLocateEnemy(int initialRoom, ArrayList<RoomConfig> rooms){
        Random random=new Random();
        int[] roomEnemies={0,0,0,0,0,0,0,0,0};
        int e = random.nextInt(5);
        int room;
        do room = random.nextInt(9);
        while(room == initialRoom || roomEnemies[room]==(Gamer.level<9 ? 2 : (Gamer.level<17 ? 3 : 4)));
        roomEnemies[room]+=1;
        Enemy enemy=new Enemy(e);
        do {
            enemy.enemyX = random.nextInt(rooms.get(room).roomWidth - 1) + 1 + rooms.get(room).roomX;
            enemy.enemyY = random.nextInt(rooms.get(room).roomHeight - 1) + 1 + rooms.get(room).roomY;
            for(e=0; e<rooms.get(room).enemySet.size(); e+=1)
                if(enemy.enemyY == rooms.get(room).enemySet.get(e).enemyY && enemy.enemyX == rooms.get(room).enemySet.get(e).enemyX)
                    break;
        } while(e<rooms.get(room).enemySet.size());
        rooms.get(room).enemySet.add(enemy);
    }
    public static void Info(ArrayList<RoomConfig> rooms, int room, int enemy) {
        int x = Toolkit.getScreenWidth() / 2+30;
        final int y = Toolkit.getScreenHeight() - 1;x=Toolkit.getScreenWidth()/2+30;
        for (int i = x; i < Toolkit.getScreenWidth(); i += 1)
            Toolkit.printString("#", i, y, new CharColor(CharColor.BLACK, CharColor.BLACK));
        char name = rooms.get(room).enemySet.get(enemy).enemyName;
        Toolkit.printString("Health:", x, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(rooms.get(room).enemySet.get(enemy).health), x + 7, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Power:", x + 11, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(rooms.get(room).enemySet.get(enemy).power), x + 17, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Skill:", x + 21, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(rooms.get(room).enemySet.get(enemy).skill), x + 27, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Animus:", x + 31, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(rooms.get(room).enemySet.get(enemy).animus), x + 38, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Type:", x + 42, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString((name == 'v' ? "Vampire" : (name == 'z' ? "Zombie" : (name == 'g' ? "Ghost" : (name == 'O' ? "Ogr" : (name=='s' ? "Snake" : "Mimic"))))), x+48, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
    }
}
