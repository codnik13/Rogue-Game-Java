package org.rogue.presentation;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import org.rogue.domain.Gamer;

public class GameInfo {
    public static void Info() {
        int x = 1;
        final int y = Toolkit.getScreenHeight() - 1;
        Toolkit.printString("Level:", 1, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.level), 7, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Health:", 11, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.health) + "/" + Integer.toString(Gamer.maxHealth), 18, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Power:", 25, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.power)+"/12", 31, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Skill:", 38, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.skill)+"/12", 44, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Gold:", 51, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.gold), 56, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Bag:", 60, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.bag.max) + "/9", 64, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("(Food:", 68, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.bag.food), 74, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Cure:", 78, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.bag.drink), 83, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Roll:", 87, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.bag.scroll), 92, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Armor:", 96, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString(Integer.toString(Gamer.bag.sword+Gamer.bag.club)+"/2)", 102, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        if (Gamer.signal == 1) {
            x=Toolkit.getScreenWidth()/2+30;
            for (int i = x; i < Toolkit.getScreenWidth(); i += 1)
                Toolkit.printString("#", i, y, new CharColor(CharColor.BLACK, CharColor.BLACK));
            Toolkit.printString("Select", x + 10, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("armor:", x + 17, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("press", x + 24, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("S", x + 30, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("for", x + 32, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("Sword", x + 36, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("or", x + 42, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("C", x + 45, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("for", x + 47, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
            Toolkit.printString("Club:", x + 51, y, new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
        else if(Gamer.signal==0){
            for (int i = Toolkit.getScreenWidth() / 2 + 30; i < Toolkit.getScreenWidth(); i += 1)
                Toolkit.printString("#", i, y, new CharColor(CharColor.BLACK, CharColor.BLACK));
        }
    }
    public static void gameOver(){
        Gamer.area.setVisible(false);
        Gamer.area.dispose();
        Toolkit.shutdown();
        Toolkit.clearScreen(new CharColor(CharColor.BLACK, CharColor.BLACK));
        Toolkit.printString("GAME", Toolkit.getScreenWidth() / 2 - 2, Toolkit.getScreenHeight() / 2, new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("OVER", Toolkit.getScreenWidth() / 2 + 3, Toolkit.getScreenHeight() / 2, new CharColor(CharColor.BLACK, CharColor.WHITE));
    }
}
