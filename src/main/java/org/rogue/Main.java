package org.rogue;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.widgets.Window;
import org.rogue.datalayer.FileHandler;
import org.rogue.domain.*;
import org.rogue.presentation.GameInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
// java -jar RogueGame-1.0-SNAPSHOT-all.jar
public class Main {
    final static Random random=new Random();
    public static void main(String[] args) {
        FileHandler.ReadFromFile();
        Gamer.area.setVisible(true);
        gameProcess();
    }
    public static void gameProcess() {
        final int count=Gamer.level+=1;
        int enemies=8+Gamer.level;
        int aids=(21-Gamer.level+Gamer.level/4)/2+Gamer.level/7;
        final int deadEnd=random.nextInt(9)+1;
        Gamer.reset();
        Gamer.gamerTimeCount=System.currentTimeMillis();
        ArrayList<RoomConfig> rooms=new ArrayList<>();
        ArrayList<Path> paths=new ArrayList<>();
        paths.add(new Path(1, 2)); paths.add(new Path(1, 4));
        paths.add(new Path(2, 3)); paths.add(new Path(2, 5));
        paths.add(new Path(3, 6)); paths.add(new Path(4, 5));
        paths.add(new Path(4, 7)); paths.add(new Path(5, 6));
        paths.add(new Path(5, 8)); paths.add(new Path(6, 9));
        paths.add(new Path(7, 8)); paths.add(new Path(8, 9));
        Toolkit.shutdown();
        Toolkit.clearScreen(new CharColor(CharColor.BLACK, CharColor.BLACK));
        for(int i=0, n=1; i<3; i+=1)
            for(int j=0; j<3; j+=1, n+=1)
                rooms.add(new RoomConfig(n, i, j, paths, deadEnd==n, rooms));
        BuildPaths buildPaths=new BuildPaths();
        buildPaths.buildPaths(rooms, paths);
        Layout game = new Layout(rooms, paths, deadEnd, enemies, aids);
        GameInfo.Info();
        Gamer.area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Gamer.gamerCurrentTime = System.currentTimeMillis();
                if (Gamer.gamerCurrentTime - Gamer.gamerTimeCount > 52-Gamer.skill*2) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT && Gamer.x > 0 && !Gamer.armorSelection) {
                        Gamer.prevX = Gamer.x;
                        Gamer.prevY = Gamer.y;
                        Gamer.x -= 1;
                        Gamer.gamerTimeCount = Gamer.gamerCurrentTime;
                        //game.keepPlaying(rooms, paths);
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && Gamer.x < game.screenWidth && !Gamer.armorSelection) {
                        Gamer.prevX = Gamer.x;
                        Gamer.prevY = Gamer.y;
                        Gamer.x += 1;
                        Gamer.gamerTimeCount = Gamer.gamerCurrentTime;
                        //game.keepPlaying(rooms, paths);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP && Gamer.y > 0 && !Gamer.armorSelection) {
                        Gamer.prevX = Gamer.x;
                        Gamer.prevY = Gamer.y;
                        Gamer.y -= 1;
                        Gamer.gamerTimeCount = Gamer.gamerCurrentTime;
                        //game.keepPlaying(rooms, paths);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && Gamer.y < game.screenHeight && !Gamer.armorSelection) {
                        Gamer.prevX = Gamer.x;
                        Gamer.prevY = Gamer.y;
                        Gamer.y += 1;
                        Gamer.gamerTimeCount = Gamer.gamerCurrentTime;
                        //game.keepPlaying(rooms, paths);
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        //level=21;
                        Gamer.area.removeKeyListener(this);
                        GameInfo.gameOver();
                        return;
                    } else if (e.getKeyCode() == KeyEvent.VK_J && !Gamer.armorSelection) {
                        for (int item = 0; item < Gamer.bag.items.size(); item += 1) {
                            if (Gamer.bag.items.get(item).id == 2) {
                                Gamer.health += 2;
                                while(Gamer.health>Gamer.maxHealth) Gamer.skill-=1;
                                Gamer.bag.items.remove(item);
                                Gamer.bag.food-=1;
                                Gamer.bag.max -= 1;
                                break;
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_K && !Gamer.armorSelection) {
                        for (int item = 0; item < Gamer.bag.items.size(); item += 1) {
                            if (Gamer.bag.items.get(item).id == 3) {
                                Gamer.bag.items.get(item).selected = true;
                                Gamer.bag.items.get(item).count = System.currentTimeMillis();
                                if (Gamer.bag.items.get(item).target.equals("maxHealth")) {
                                    Gamer.maxHealth += 3;
                                    while(Gamer.maxHealth>16) Gamer.maxHealth-=1;
                                }
                                else if (Gamer.bag.items.get(item).target.equals("power")) {
                                    Gamer.power += 3;
                                    while(Gamer.power>12) Gamer.power-=1;
                                }
                                else{
                                    Gamer.skill += 3;
                                    while(Gamer.skill>12) Gamer.skill-=1;
                                }
                                Gamer.bag.drink-=1;
                                Gamer.bag.max -= 1;
                                break;
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_E && !Gamer.armorSelection) {
                        for (int item = 0; item < Gamer.bag.items.size(); item += 1) {
                            if (Gamer.bag.items.get(item).id == 4) {
                                if (Gamer.bag.items.get(item).target.equals("maxHealth")) {
                                    Gamer.maxHealth += 1;
                                    while(Gamer.maxHealth>16) Gamer.maxHealth-=1;
                                }
                                else if (Gamer.bag.items.get(item).target.equals("power")){
                                    Gamer.power += 1;
                                    while(Gamer.power>12) Gamer.power-=1;
                                }
                                else {
                                    Gamer.skill += 1;
                                    while(Gamer.skill>12) Gamer.skill-=1;
                                }
                                Gamer.bag.items.remove(item);
                                Gamer.bag.scroll-=1;
                                Gamer.bag.max -= 1;
                                break;
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_H && !Gamer.armorSelection) {
                        int sword = -1;
                        int club = -1;
                        if (Gamer.bag.sword == 1 && Gamer.bag.club == 1) {
                            for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                                if (Gamer.bag.items.get(item).id == 0) {
                                    sword = item;
                                    break;
                                }
                            if (!Gamer.bag.items.get(sword).selected) {
                                for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                                    if (Gamer.bag.items.get(item).id == 1) {
                                        club = item;
                                        break;
                                    }
                                if (!Gamer.bag.items.get(club).selected) {
                                    Gamer.armorSelection = true;
                                    Gamer.signal = 1;
                                } else {
                                    Gamer.power=(Gamer.power>0 ? Gamer.power-1 : 0);
                                    Gamer.skill=(Gamer.skill>0 ? Gamer.skill-1 : 0);
                                    Gamer.power += 3;
                                    while(Gamer.power>12) Gamer.power-=1;
                                    Gamer.skill=(Gamer.skill>0 ? Gamer.skill-1 : 0);
                                    Gamer.bag.items.get(club).selected = false;
                                    Gamer.bag.items.get(sword).selected = true;

                                }
                            } else {
                                Gamer.power -= 3;
                                while(Gamer.power<0) Gamer.power+=1;
                                Gamer.power=(Gamer.power<12 ? Gamer.power+1 : Gamer.power);
                                Gamer.skill=(Gamer.skill<12 ? Gamer.skill+1 : Gamer.skill);
                                Gamer.bag.items.get(sword).selected = false;
                                for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                                    if (Gamer.bag.items.get(item).id == 1) {
                                        Gamer.bag.items.get(item).selected = true;
                                        break;
                                    }
                            }

                        } else if (Gamer.bag.sword == 1) {
                            for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                                if (Gamer.bag.items.get(item).id == 0 && !Gamer.bag.items.get(item).selected) {
                                    Gamer.bag.items.get(item).selected = true;
                                    Gamer.power += 3;
                                    while(Gamer.power>12) Gamer.power-=1;
                                    Gamer.skill=(Gamer.skill>0 ? Gamer.skill-1 : 0);
                                    break;
                                }
                        } else {
                            for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                                if (Gamer.bag.items.get(item).id == 1 && !Gamer.bag.items.get(item).selected) {
                                    Gamer.bag.items.get(item).selected = true;
                                    Gamer.power=(Gamer.power<12 ? Gamer.power+1 : Gamer.power);
                                    Gamer.skill=(Gamer.skill<12 ? Gamer.skill+1 : Gamer.skill);
                                    break;
                                }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_S && Gamer.armorSelection) {
                        Gamer.armorSelection = false;
                        Gamer.power += 3;
                        while(Gamer.power>12) Gamer.power-=1;
                        Gamer.skill=(Gamer.skill>0 ? Gamer.skill-1 : 0);
                        Gamer.signal=0;
                        for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                            if (Gamer.bag.items.get(item).id == 0) {
                                Gamer.bag.items.get(item).selected = true;
                                break;
                            }
                    } else if (e.getKeyCode() == KeyEvent.VK_C && Gamer.armorSelection) {
                        Gamer.armorSelection = false;
                        Gamer.power=(Gamer.power<12 ? Gamer.power+1 : Gamer.power);
                        Gamer.skill=(Gamer.skill<12 ? Gamer.skill+1 : Gamer.skill);
                        Gamer.signal=0;
                        for (int item = 0; item < Gamer.bag.items.size(); item += 1)
                            if (Gamer.bag.items.get(item).id == 1) {
                                Gamer.bag.items.get(item).selected = true;
                                break;
                            }
                    }
                    if(!Gamer.armorSelection) {
                        if (!game.keepPlaying(rooms, paths)) {
                            Gamer.area.removeKeyListener(this);
                            FileHandler.WriteToFile();
                            if (count == 21) {
                                GameInfo.gameOver();
                                return;
                            } else gameProcess();
                        }
                    }
                    GameInfo.Info();
                }
            }
        });
    }
}