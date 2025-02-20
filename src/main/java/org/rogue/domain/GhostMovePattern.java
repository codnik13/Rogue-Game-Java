package org.rogue.domain;
import org.rogue.domain.RoomConfig;
import java.util.ArrayList;

public class GhostMovePattern {
    public static void ghostMovePattern(int room, ArrayList<RoomConfig> rooms, int enemy){
        int k=(rooms.get(room).enemySet.get(enemy).enemyName=='g' ? 1 : 2);
        if (rooms.get(room).enemySet.get(enemy).down) {
            if (rooms.get(room).enemySet.get(enemy).enemyY + k < rooms.get(room).roomY + rooms.get(room).roomHeight)
                rooms.get(room).enemySet.get(enemy).enemyY += k;
            else{
                if(rooms.get(room).enemySet.get(enemy).left){
                    if (rooms.get(room).enemySet.get(enemy).enemyX + k < rooms.get(room).roomX + rooms.get(room).roomWidth)
                        rooms.get(room).enemySet.get(enemy).enemyX += k;
                    else{
                        rooms.get(room).enemySet.get(enemy).left=false;
                        rooms.get(room).enemySet.get(enemy).right=true;
                        rooms.get(room).enemySet.get(enemy).enemyX -=k;
                    }
                }
                else{
                    if (rooms.get(room).enemySet.get(enemy).enemyX - k > rooms.get(room).roomX)
                        rooms.get(room).enemySet.get(enemy).enemyX -= k;
                    else{
                        rooms.get(room).enemySet.get(enemy).right=false;
                        rooms.get(room).enemySet.get(enemy).left=true;
                        rooms.get(room).enemySet.get(enemy).enemyX+=1;
                    }
                }
                rooms.get(room).enemySet.get(enemy).down=false;
                rooms.get(room).enemySet.get(enemy).up=true;
                rooms.get(room).enemySet.get(enemy).enemyY -=k;
            }
        }
        else{
            if (rooms.get(room).enemySet.get(enemy).enemyY - k > rooms.get(room).roomY)
                rooms.get(room).enemySet.get(enemy).enemyY -= k;
            else{
                if(rooms.get(room).enemySet.get(enemy).left){
                    if (rooms.get(room).enemySet.get(enemy).enemyX + k < rooms.get(room).roomX + rooms.get(room).roomWidth)
                        rooms.get(room).enemySet.get(enemy).enemyX += k;
                    else{
                        rooms.get(room).enemySet.get(enemy).left=false;
                        rooms.get(room).enemySet.get(enemy).right=true;
                        rooms.get(room).enemySet.get(enemy).enemyX -=k;
                    }
                }
                else {
                    if (rooms.get(room).enemySet.get(enemy).enemyX - k > rooms.get(room).roomX)
                        rooms.get(room).enemySet.get(enemy).enemyX -= k;
                    else{
                        rooms.get(room).enemySet.get(enemy).right=false;
                        rooms.get(room).enemySet.get(enemy).left=true;
                        rooms.get(room).enemySet.get(enemy).enemyX+=1;
                    }
                }
                rooms.get(room).enemySet.get(enemy).up=false;
                rooms.get(room).enemySet.get(enemy).down=true;
                rooms.get(room).enemySet.get(enemy).enemyY +=k;
            }
        }
    }
}
