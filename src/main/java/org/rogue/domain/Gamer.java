package org.rogue.domain;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.util.Rectangle;
import jcurses.widgets.Window;
import javax.swing.*;

public class Gamer {
    public final static JFrame area=new JFrame();
    public static String name;
    public static int level=0;
    public static int health=6;
    public static int power=6;
    public static int skill=6;
    public static int maxHealth=12;
    public static int gold=0;
    public static Bag bag=new Bag();
    public static int x=0;
    public static int y=0;
    public static int prevX=0;
    public static int prevY=0;
    public static boolean armorSelection=false;
    public static long gamerTimeCount;
    public static long gamerCurrentTime;
    public static int signal=0;
    public static int totalArmor=0;
    public static void reset(){
        signal=x=y=prevX=prevY=0;
        armorSelection=false;
    }
}
