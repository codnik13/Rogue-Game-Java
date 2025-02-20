package org.rogue.domain;
import org.rogue.domain.RoomConfig;
import java.util.ArrayList;
import java.util.Random;

public class Aid {
    public int aidId;
    public int x;
    public int y;
    Aid(int id){
        aidId=id;
    }
    public static int DefineLocateAid(ArrayList<RoomConfig> rooms, int portalX, int portalY){
        Random random=new Random();
        Aid aid;
        Enemy enemy;
        int[] roomArmor={0,0,0,0,0,0,0,0,0};
        int[] roomAids={0,0,0,0,0,0,0,0,0};
        int e;
        int room;
        int mimic=0;
        while(true) {
            e = random.nextInt(7);
            if(e<2){
                if(Gamer.totalArmor<4){
                    do room = random.nextInt(9);
                    while(roomArmor[room]==1);
                    Gamer.totalArmor+=1;
                    roomArmor[room]=1;
                    break;
                }
            }
            else if(e==6){
                if((Gamer.level>6 && Gamer.level<14 && mimic<1) || (Gamer.level>13 && Gamer.level<21 && mimic<2) || (Gamer.level==21 ? mimic<3 : mimic>1000)){
                    mimic+=1;
                    room = random.nextInt(9);
                    break;
                }
            }
            else{
                do room = random.nextInt(9);
                while(roomAids[room]==4);
                roomAids[room]+=1;
                break;
            }
        }
        if(e<6) {
            aid = new Aid(e);
            do {
                aid.x = random.nextInt(rooms.get(room).roomWidth - 1) + 1 + rooms.get(room).roomX;
                aid.y = random.nextInt(rooms.get(room).roomHeight - 1) + 1 + rooms.get(room).roomY;
                for (e=0; e < rooms.get(room).aidSet.size(); e += 1)
                    if (aid.y == rooms.get(room).aidSet.get(e).y && aid.x == rooms.get(room).aidSet.get(e).x)
                        break;
            } while (e < rooms.get(room).aidSet.size() || (aid.y == portalY && aid.x == portalX));
            rooms.get(room).aidSet.add(aid);
            return 1;
        }
        enemy=new Enemy(5);
        do {
            enemy.enemyX = random.nextInt(rooms.get(room).roomWidth - 1) + 1 + rooms.get(room).roomX;
            enemy.enemyY = random.nextInt(rooms.get(room).roomHeight - 1) + 1 + rooms.get(room).roomY;
            for (e=0; e < rooms.get(room).enemySet.size(); e += 1)
                if (enemy.enemyY == rooms.get(room).enemySet.get(e).enemyY && enemy.enemyX == rooms.get(room).enemySet.get(e).enemyX)
                    break;
        } while (e < rooms.get(room).enemySet.size() || (enemy.enemyY == portalY && enemy.enemyX == portalX));
        rooms.get(room).enemySet.add(enemy);
        return 0;
    }
}
