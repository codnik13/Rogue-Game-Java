package org.rogue.domain;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import org.rogue.domain.RoomConfig;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BuildPaths {
    final Random random=new Random();
    public void buildPaths(ArrayList<RoomConfig> rooms, ArrayList<Path> paths) {
        for (int i = 0; i < 12; i += 1) {
            Path path = paths.get(i);
            if (path.present){
                int x = path.pathX.get(0);
                int y = path.pathY.get(0);
                int room=0;
                for(int door=0; room<9; room+=1, door=0) {
                    for (; door < rooms.get(room).doorX.size(); door += 1)
                        if (rooms.get(room).doorX.get(door) == x && rooms.get(room).doorY.get(door) == y)
                            break;
                    if(door < rooms.get(room).doorX.size()) break;
                }
                if (i == 0 || i == 2 || i == 5 || i == 7 || i == 10 || i == 11) {
                    while (true) {
                        if(x+1<rooms.get(room).frameX) {
                            path.pathX.add(++x);
                            path.pathY.add(y);
                        }
                        else if(x+1>path.stopX) {
                            if (y == path.stopY)
                                break;
                            path.pathX.add(x);
                            path.pathY.add(y+=(Integer.compare(y,path.stopY)));
                        }
                        else {
                            if (y==path.stopY || x==path.pathX.get(0)) {
                                path.pathX.add(++x);
                                path.pathY.add(y);
                            }
                            else{
                                path.pathX.add(x);
                                path.pathY.add(y += (y > path.stopY ? -1 : 1));
                            }
                        }
                    }
                } else {
                    while (true) {
                        if(y+1<rooms.get(room).frameY) {
                            path.pathX.add(x);
                            path.pathY.add(++y);
                        }
                        else if(y+1>path.stopY) {
                            if (x == path.stopX)
                                break;
                            path.pathY.add(y);
                            path.pathX.add(x+=(Integer.compare(x,path.stopX)));
                        }
                        else {
                            if (x==path.stopX || y==path.pathY.get(0)) {
                                path.pathY.add(++y);
                                path.pathX.add(x);
                            }
                            else{
                                path.pathY.add(y);
                                path.pathX.add(x += (x > path.stopX ? -1 : 1));
                            }
                        }
                    }
                }
            }
        }
    }
}
