package org.rogue.domain;
import java.util.ArrayList;

public class VampMovePattern {
    public static void vampMovePattern(int room, ArrayList<RoomConfig> rooms, int enemy){
        if (rooms.get(room).enemySet.get(enemy).left) {
            if (rooms.get(room).enemySet.get(enemy).enemyX + 1 < rooms.get(room).roomX + rooms.get(room).roomWidth)
                rooms.get(room).enemySet.get(enemy).enemyX += 1;
            else{
                if(rooms.get(room).enemySet.get(enemy).down){
                    if (rooms.get(room).enemySet.get(enemy).enemyY + 1 < rooms.get(room).roomY + rooms.get(room).roomHeight)
                        rooms.get(room).enemySet.get(enemy).enemyY += 1;
                    else{
                        rooms.get(room).enemySet.get(enemy).down=false;
                        rooms.get(room).enemySet.get(enemy).up=true;
                        rooms.get(room).enemySet.get(enemy).enemyY -=1;
                    }
                }
                else{
                    if (rooms.get(room).enemySet.get(enemy).enemyY - 1 > rooms.get(room).roomY)
                        rooms.get(room).enemySet.get(enemy).enemyY -= 1;
                    else{
                        rooms.get(room).enemySet.get(enemy).down=true;
                        rooms.get(room).enemySet.get(enemy).up=false;
                        rooms.get(room).enemySet.get(enemy).enemyY+=1;
                    }
                }
                rooms.get(room).enemySet.get(enemy).left=false;
                rooms.get(room).enemySet.get(enemy).right=true;
                rooms.get(room).enemySet.get(enemy).enemyX -=1;
            }
        }
        else{
            if (rooms.get(room).enemySet.get(enemy).enemyX - 1 > rooms.get(room).roomX)
                rooms.get(room).enemySet.get(enemy).enemyX -= 1;
            else{
                if(rooms.get(room).enemySet.get(enemy).down){
                    if (rooms.get(room).enemySet.get(enemy).enemyY + 1 < rooms.get(room).roomY + rooms.get(room).roomHeight)
                        rooms.get(room).enemySet.get(enemy).enemyY += 1;
                    else{
                        rooms.get(room).enemySet.get(enemy).down=false;
                        rooms.get(room).enemySet.get(enemy).up=true;
                        rooms.get(room).enemySet.get(enemy).enemyY -=1;
                    }
                }
                else {
                    if (rooms.get(room).enemySet.get(enemy).enemyY - 1 > rooms.get(room).roomY)
                        rooms.get(room).enemySet.get(enemy).enemyY -= 1;
                    else{
                        rooms.get(room).enemySet.get(enemy).down=true;
                        rooms.get(room).enemySet.get(enemy).up=false;
                        rooms.get(room).enemySet.get(enemy).enemyY+=1;
                    }
                }
                rooms.get(room).enemySet.get(enemy).left=true;
                rooms.get(room).enemySet.get(enemy).right=false;
                rooms.get(room).enemySet.get(enemy).enemyX +=1;
            }
        }
    }
}
