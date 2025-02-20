package org.rogue.domain;
import java.util.ArrayList;
import java.util.Random;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;

public class RoomConfig {
    public int roomY;
    public int roomX;
    public final int roomWidth;
    public final int roomHeight;
    public final ArrayList<Integer> doorX=new ArrayList<>();
    public final ArrayList<Integer> doorY=new ArrayList<>();
    boolean isChecked=false;
    final Random random = new Random();
    int frameX;
    int frameY;
    public final ArrayList<Enemy> enemySet=new ArrayList<>();
    public final ArrayList<Aid> aidSet=new ArrayList<>();

    public void drawRoomEmpty(){
        for(int y = roomY +1; y< roomY +roomHeight; y+=1)
            for(int x = roomX +1; x< roomX +roomWidth; x+=1)
                Toolkit.printString("#", x, y, new CharColor(CharColor.BLACK, CharColor.BLACK));
    }
    public void drawRoomWalls(){
        for (int x = roomX; x <= roomX + roomWidth; x+=1) {
            int door=0;
            for (; door < doorX.size(); door+=1)
                if(doorX.get(door)==x && doorY.get(door)== roomY)
                    break;
                if(door<doorX.size())
                    Toolkit.printString("#", doorX.get(door), doorY.get(door), new CharColor(CharColor.BLACK, CharColor.BLACK));
                else Toolkit.printString("#", x, roomY, new CharColor(CharColor.YELLOW, CharColor.YELLOW));
            for (door=0; door < doorX.size(); door+=1)
                if(doorX.get(door)==x && doorY.get(door)== roomY +roomHeight)
                    break;
            if(door<doorX.size())
                Toolkit.printString("#", doorX.get(door), doorY.get(door), new CharColor(CharColor.BLACK, CharColor.BLACK));
            else Toolkit.printString("#", x, roomY +roomHeight, new CharColor(CharColor.YELLOW, CharColor.YELLOW));
        }
        for (int y = roomY; y <= roomY + roomHeight; y+=1) {
            int door=0;
            for (; door < doorX.size(); door+=1)
                if(doorX.get(door)== roomX && doorY.get(door)==y)
                    break;
            if(door<doorX.size())
                Toolkit.printString("#", doorX.get(door), doorY.get(door), new CharColor(CharColor.BLACK, CharColor.BLACK));
            else Toolkit.printString("#", roomX, y, new CharColor(CharColor.YELLOW, CharColor.YELLOW));
            for (door=0; door < doorX.size(); door+=1)
                if(doorX.get(door)== roomX +roomWidth && doorY.get(door)==y)
                    break;
            if(door<doorX.size())
                Toolkit.printString("#", doorX.get(door), doorY.get(door), new CharColor(CharColor.BLACK, CharColor.BLACK));
            else Toolkit.printString("#", roomX +roomWidth, y, new CharColor(CharColor.YELLOW, CharColor.YELLOW));
        }
        isChecked=true;
    }
    public RoomConfig(int n, int i, int j, ArrayList<Path> paths, boolean deadEnd, ArrayList<RoomConfig> rooms){
        final int lowerLimit=8;
        final int upperLimit=4;
        final int screenWidth=Toolkit.getScreenWidth();
        final int screenHeight=Toolkit.getScreenHeight()-3;
        roomWidth = random.nextInt(screenWidth/upperLimit-screenWidth/lowerLimit)+screenWidth/lowerLimit;
        roomHeight = random.nextInt(screenHeight/upperLimit-screenHeight/lowerLimit)+screenHeight/lowerLimit;
        roomX = random.nextInt(screenWidth/3- roomWidth-1)+j*screenWidth/3+1;
        roomY = random.nextInt(screenHeight/3- roomHeight-1)+i*screenHeight/3+1;
        frameX=(j+1)*screenWidth/3;
        frameY=(i+1)*screenHeight/3;
        if(n==1){
            Path path1=findPath(1, 2, paths);
            Path path2=findPath(1, 4, paths);
            if(deadEnd) {
                if (random.nextInt(2) == 0)
                    path1.present=false;
                else path2.present=false;
            }
        }
        else if(n==2){
            Path path1=findPath(1, 2, paths);
            Path path2=findPath(2, 3, paths);
            Path path3=findPath(2, 5, paths);
            int rand=random.nextInt(3);
            if(deadEnd){
                if(rand==0)
                    path1.present=path2.present=false;
                else if(rand==1)
                    path1.present=path3.present=false;
                else{
                    path2.present=path3.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(0).roomX +rooms.get(0).roomWidth);
                        path1.pathY.add(rooms.get(0).roomY +random.nextInt(rooms.get(0).roomHeight-1)+1);
                        rooms.get(0).doorX.add(path1.pathX.get(0));
                        rooms.get(0).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(0).roomX +rooms.get(0).roomWidth);
                    path1.pathY.add(rooms.get(0).roomY +random.nextInt(rooms.get(0).roomHeight-1)+1);
                    rooms.get(0).doorX.add(path1.pathX.get(0));
                    rooms.get(0).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
            }
        }
        else if(n==3){
            Path path1=findPath(2, 3, paths);
            Path path2=findPath(3, 6, paths);
            if(deadEnd) {
                if (random.nextInt(2) == 0)
                    path1.present=false;
                else{
                    path2.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(1).roomX +rooms.get(1).roomWidth);
                        path1.pathY.add(rooms.get(1).roomY +random.nextInt(rooms.get(1).roomHeight-1)+1);
                        rooms.get(1).doorX.add(path1.pathX.get(0));
                        rooms.get(1).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(1).roomX +rooms.get(1).roomWidth);
                    path1.pathY.add(rooms.get(1).roomY +random.nextInt(rooms.get(1).roomHeight-1)+1);
                    rooms.get(1).doorX.add(path1.pathX.get(0));
                    rooms.get(1).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
            }
        }
        else if(n==4){
            Path path1=findPath(4, 5, paths);
            Path path2=findPath(1, 4, paths);
            Path path3=findPath(4, 7, paths);
            int rand=random.nextInt(3);
            if(deadEnd){
                if(rand==0)
                    path1.present=path2.present=false;
                else if(rand==1){
                    path1.present=path3.present=false;
                    if(path2.present){
                        path2.pathX.add(rooms.get(0).roomX +random.nextInt(rooms.get(0).roomWidth-1)+1);
                        path2.pathY.add(rooms.get(0).roomY +rooms.get(0).roomHeight);
                        rooms.get(0).doorX.add(path2.pathX.get(0));
                        rooms.get(0).doorY.add(path2.pathY.get(0));
                        path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path2.stopY= roomY;
                        doorX.add(path2.stopX);
                        doorY.add(path2.stopY);
                    }
                }
                else path2.present=path3.present=false;
            }
            else{
                if(path2.present){
                    path2.pathX.add(rooms.get(0).roomX +random.nextInt(rooms.get(0).roomWidth-1)+1);
                    path2.pathY.add(rooms.get(0).roomY +rooms.get(0).roomHeight);
                    rooms.get(0).doorX.add(path2.pathX.get(0));
                    rooms.get(0).doorY.add(path2.pathY.get(0));
                    path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path2.stopY= roomY;
                    doorX.add(path2.stopX);
                    doorY.add(path2.stopY);
                }
            }
        }
        else if(n==5){
            Path path1=findPath(4, 5, paths);
            Path path2=findPath(5, 6, paths);
            Path path3=findPath(2, 5, paths);
            Path path4=findPath(5, 8, paths);
            int doors=random.nextInt(3)+2;
            int rand=random.nextInt(4);
            if(deadEnd){
                if(rand==0)
                    path1.present=path2.present=path3.present=false;
                else if(rand==1)
                    path1.present=path3.present=path4.present=false;
                else if(rand==2){
                    path1.present=path2.present=path4.present=false;
                    if(path3.present){
                        path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                        rooms.get(1).doorX.add(path3.pathX.get(0));
                        rooms.get(1).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                }
                else{
                    path2.present=path3.present=path4.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                        path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                        rooms.get(3).doorX.add(path1.pathX.get(0));
                        rooms.get(3).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else if(doors==3){
                if (rand == 0) {
                    path1.present=false;
                    if(path3.present){
                        path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                        rooms.get(1).doorX.add(path3.pathX.get(0));
                        rooms.get(1).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                }
                else if(rand==1) {
                    path2.present=false;
                    if(path3.present){
                        path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                        rooms.get(1).doorX.add(path3.pathX.get(0));
                        rooms.get(1).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                    if(path1.present){
                        path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                        path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                        rooms.get(3).doorX.add(path1.pathX.get(0));
                        rooms.get(3).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
                else if(rand==2) {
                    path3.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                        path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                        rooms.get(3).doorX.add(path1.pathX.get(0));
                        rooms.get(3).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
                else{
                    path4.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                        path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                        rooms.get(3).doorX.add(path1.pathX.get(0));
                        rooms.get(3).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                    if(path3.present){
                        path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                        rooms.get(1).doorX.add(path3.pathX.get(0));
                        rooms.get(1).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                }
            }
            else if(doors==2){
                if(rand==0){
                    path1.present=path2.present=false;
                    if(path3.present){
                        path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                        rooms.get(1).doorX.add(path3.pathX.get(0));
                        rooms.get(1).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                }
                else if(rand==1)
                    path1.present=path3.present=false;
                else if(rand==2){
                    path2.present=path3.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                        path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                        rooms.get(3).doorX.add(path1.pathX.get(0));
                        rooms.get(3).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
                else{
                    path4.present=false;
                    if (random.nextInt(2)==0) {
                        path1.present=false;
                        if(path3.present){
                            path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                            path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                            rooms.get(1).doorX.add(path3.pathX.get(0));
                            rooms.get(1).doorY.add(path3.pathY.get(0));
                            path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                            path3.stopY= roomY;
                            doorX.add(path3.stopX);
                            doorY.add(path3.stopY);
                        }
                    }
                    else{
                        path2.present=false;
                        if(path1.present){
                            path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                            path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                            rooms.get(3).doorX.add(path1.pathX.get(0));
                            rooms.get(3).doorY.add(path1.pathY.get(0));
                            path1.stopX= roomX;
                            path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                            doorX.add(path1.stopX);
                            doorY.add(path1.stopY);
                        }
                        if(path3.present){
                            path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                            path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                            rooms.get(1).doorX.add(path3.pathX.get(0));
                            rooms.get(1).doorY.add(path3.pathY.get(0));
                            path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                            path3.stopY= roomY;
                            doorX.add(path3.stopX);
                            doorY.add(path3.stopY);
                        }
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(3).roomX +rooms.get(3).roomWidth);
                    path1.pathY.add(rooms.get(3).roomY +random.nextInt(rooms.get(3).roomHeight-1)+1);
                    rooms.get(3).doorX.add(path1.pathX.get(0));
                    rooms.get(3).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
                if(path3.present){
                    path3.pathX.add(rooms.get(1).roomX +random.nextInt(rooms.get(1).roomWidth-1)+1);
                    path3.pathY.add(rooms.get(1).roomY +rooms.get(1).roomHeight);
                    rooms.get(1).doorX.add(path3.pathX.get(0));
                    rooms.get(1).doorY.add(path3.pathY.get(0));
                    path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path3.stopY= roomY;
                    doorX.add(path3.stopX);
                    doorY.add(path3.stopY);
                }
            }
        }
        else if(n==6){
            Path path1=findPath(5, 6, paths);
            Path path2=findPath(3, 6, paths);
            Path path3=findPath(6, 9, paths);
            int rand=random.nextInt(3);
            if(deadEnd){
                if(rand==0){
                    path1.present=path2.present=false;
                }
                else if(rand==1){
                    path1.present=path3.present=false;
                    if(path2.present){
                        path2.pathX.add(rooms.get(2).roomX +random.nextInt(rooms.get(2).roomWidth-1)+1);
                        path2.pathY.add(rooms.get(2).roomY +rooms.get(2).roomHeight);
                        rooms.get(2).doorX.add(path2.pathX.get(0));
                        rooms.get(2).doorY.add(path2.pathY.get(0));
                        path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path2.stopY= roomY;
                        doorX.add(path2.stopX);
                        doorY.add(path2.stopY);
                    }
                }
                else{
                    path2.present=path3.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(4).roomX +rooms.get(4).roomWidth);
                        path1.pathY.add(rooms.get(4).roomY +random.nextInt(rooms.get(4).roomHeight-1)+1);
                        rooms.get(4).doorX.add(path1.pathX.get(0));
                        rooms.get(4).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(4).roomX +rooms.get(4).roomWidth);
                    path1.pathY.add(rooms.get(4).roomY +random.nextInt(rooms.get(4).roomHeight-1)+1);
                    rooms.get(4).doorX.add(path1.pathX.get(0));
                    rooms.get(4).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
                if(path2.present){
                    path2.pathX.add(rooms.get(2).roomX +random.nextInt(rooms.get(2).roomWidth-1)+1);
                    path2.pathY.add(rooms.get(2).roomY +rooms.get(2).roomHeight);
                    rooms.get(2).doorX.add(path2.pathX.get(0));
                    rooms.get(2).doorY.add(path2.pathY.get(0));
                    path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path2.stopY= roomY;
                    doorX.add(path2.stopX);
                    doorY.add(path2.stopY);
                }
            }
        }
        else if(n==7){
            Path path1=findPath(7, 8, paths);
            Path path2=findPath(4, 7, paths);
            if(deadEnd) {
                if (random.nextInt(2) == 0) {
                    path1.present=false;
                    if(path2.present){
                        path2.pathX.add(rooms.get(3).roomX +random.nextInt(rooms.get(3).roomWidth-1)+1);
                        path2.pathY.add(rooms.get(3).roomY + rooms.get(3).roomHeight);
                        rooms.get(3).doorX.add(path2.pathX.get(0));
                        rooms.get(3).doorY.add(path2.pathY.get(0));
                        path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path2.stopY= roomY;
                        doorX.add(path2.stopX);
                        doorY.add(path2.stopY);
                    }
                }
                else path2.present=false;
            }
            else{
                if(path2.present){
                    path2.pathX.add(rooms.get(3).roomX +random.nextInt(rooms.get(3).roomWidth-1)+1);
                    path2.pathY.add(rooms.get(3).roomY + rooms.get(3).roomHeight);
                    rooms.get(3).doorX.add(path2.pathX.get(0));
                    rooms.get(3).doorY.add(path2.pathY.get(0));
                    path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path2.stopY= roomY;
                    doorX.add(path2.stopX);
                    doorY.add(path2.stopY);
                }
            }
        }
        else if(n==8){
            Path path1=findPath(7, 8, paths);
            Path path2=findPath(8, 9, paths);
            Path path3=findPath(5, 8, paths);
            int rand=random.nextInt(3);
            if(deadEnd){
                if(rand==0){
                    path1.present=path2.present=false;
                    if(path3.present){
                        path3.pathX.add(rooms.get(4).roomX +random.nextInt(rooms.get(4).roomWidth-1)+1);
                        path3.pathY.add(rooms.get(4).roomY +rooms.get(4).roomHeight);
                        rooms.get(4).doorX.add(path3.pathX.get(0));
                        rooms.get(4).doorY.add(path3.pathY.get(0));
                        path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path3.stopY= roomY;
                        doorX.add(path3.stopX);
                        doorY.add(path3.stopY);
                    }
                }
                else if(rand==1)
                    path1.present=path3.present=false;
                else{
                    path2.present=path3.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(6).roomX +rooms.get(6).roomWidth);
                        path1.pathY.add(rooms.get(6).roomY +random.nextInt(rooms.get(6).roomHeight-1)+1);
                        rooms.get(6).doorX.add(path1.pathX.get(0));
                        rooms.get(6).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(6).roomX +rooms.get(6).roomWidth);
                    path1.pathY.add(rooms.get(6).roomY +random.nextInt(rooms.get(6).roomHeight-1)+1);
                    rooms.get(6).doorX.add(path1.pathX.get(0));
                    rooms.get(6).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
                if(path3.present){
                    path3.pathX.add(rooms.get(4).roomX +random.nextInt(rooms.get(4).roomWidth-1)+1);
                    path3.pathY.add(rooms.get(4).roomY +rooms.get(4).roomHeight);
                    rooms.get(4).doorX.add(path3.pathX.get(0));
                    rooms.get(4).doorY.add(path3.pathY.get(0));
                    path3.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path3.stopY= roomY;
                    doorX.add(path3.stopX);
                    doorY.add(path3.stopY);
                }
            }
        }
        else if(n==9){
            Path path1=findPath(8, 9, paths);
            Path path2=findPath(6, 9, paths);
            if(deadEnd) {
                if (random.nextInt(2) == 0) {
                    path1.present=false;
                    if(path2.present){
                        path2.pathX.add(rooms.get(5).roomX + random.nextInt(rooms.get(5).roomWidth-1)+1);
                        path2.pathY.add(rooms.get(5).roomY +rooms.get(5).roomHeight);
                        rooms.get(5).doorX.add(path2.pathX.get(0));
                        rooms.get(5).doorY.add(path2.pathY.get(0));
                        path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                        path2.stopY= roomY;
                        doorX.add(path2.stopX);
                        doorY.add(path2.stopY);
                    }
                }
                else{
                    path2.present=false;
                    if(path1.present){
                        path1.pathX.add(rooms.get(7).roomX +rooms.get(7).roomWidth);
                        path1.pathY.add(rooms.get(7).roomY +random.nextInt(rooms.get(7).roomHeight-1)+1);
                        rooms.get(7).doorX.add(path1.pathX.get(0));
                        rooms.get(7).doorY.add(path1.pathY.get(0));
                        path1.stopX= roomX;
                        path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                        doorX.add(path1.stopX);
                        doorY.add(path1.stopY);
                    }
                }
            }
            else{
                if(path1.present){
                    path1.pathX.add(rooms.get(7).roomX +rooms.get(7).roomWidth);
                    path1.pathY.add(rooms.get(7).roomY +random.nextInt(rooms.get(7).roomHeight-1)+1);
                    rooms.get(7).doorX.add(path1.pathX.get(0));
                    rooms.get(7).doorY.add(path1.pathY.get(0));
                    path1.stopX= roomX;
                    path1.stopY= roomY +random.nextInt(roomHeight-1)+1;
                    doorX.add(path1.stopX);
                    doorY.add(path1.stopY);
                }
                if(path2.present){
                    path2.pathX.add(rooms.get(5).roomX + random.nextInt(rooms.get(5).roomWidth-1)+1);
                    path2.pathY.add(rooms.get(5).roomY +rooms.get(5).roomHeight);
                    rooms.get(5).doorX.add(path2.pathX.get(0));
                    rooms.get(5).doorY.add(path2.pathY.get(0));
                    path2.stopX= roomX +random.nextInt(roomWidth-1)+1;
                    path2.stopY= roomY;
                    doorX.add(path2.stopX);
                    doorY.add(path2.stopY);
                }
            }
        }
    }
    public Path findPath(int from, int to, ArrayList<Path> paths){
        int i=0;
        for(; i<paths.size() && (paths.get(i).from!=from || paths.get(i).to!=to); i+=1);
        return i<paths.size() ? paths.get(i) : null;
    }
}
