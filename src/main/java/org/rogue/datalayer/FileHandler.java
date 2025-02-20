package org.rogue.datalayer;
import com.google.gson.*;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.rogue.domain.BagItem;
import org.rogue.domain.Gamer;

public class FileHandler {
    final static Gson gson=new Gson();
    public static void ReadFromFile() {
        FileReader reader;
        Envelope game=null;
        long minTime= System.currentTimeMillis();
        System.out.println("Enter your name: ");
        Scanner scan=new Scanner(System.in);
        while(!scan.hasNext());
        Gamer.name= scan.nextLine();
        scan.close();
        try {
            reader=new FileReader("/home/dik/Desktop/Java/RogueGame/src/archive.json");
            scan=new Scanner(reader);
            if(scan.hasNextLine()) {
                JsonArray jsonArray = JsonParser.parseString(scan.nextLine()).getAsJsonArray();
                for(int jsn=0; jsn<jsonArray.size(); jsn+=1){
                    Envelope envelope = gson.fromJson(jsonArray.get(jsn), Envelope.class);
                    if (Gamer.name.equals(envelope.name) && System.currentTimeMillis() - envelope.time < minTime) {
                        minTime = System.currentTimeMillis() - envelope.time;
                        game = envelope;
                    }
                }
            }
            else{
                scan.close();
                return;
            }
        } catch (FileNotFoundException e) {
            scan.close();
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(game!=null) {
            Gamer.name = game.name;
            Gamer.level = game.level;
            Gamer.health = game.health;
            Gamer.maxHealth = game.maxHealth;
            Gamer.power = game.power;
            Gamer.skill = game.skill;
            Gamer.gold = game.gold;
            Gamer.totalArmor = game.totalArmor;
            Gamer.bag.max = game.max;
            Gamer.bag.sword = game.sword;
            Gamer.bag.club = game.club;
            Gamer.bag.food = game.food;
            Gamer.bag.drink = game.drink;
            Gamer.bag.scroll = game.scroll;
            for (int i = 0; i < game.id.length; i += 1) {
                if (game.id[i] >= 0) {
                    Gamer.bag.items.add(new BagItem(game.id[i], game.target[i]));
                }
            }
        }
        scan.close();
    }
    public static void WriteToFile() {
        JsonArray jsonArray=null;
        FileReader reader;
        Scanner scan;
        try {
            reader=new FileReader("/home/dik/Desktop/Java/RogueGame/src/archive.json");
            scan=new Scanner(reader);
            if(scan.hasNextLine())
                jsonArray =JsonParser.parseString(scan.nextLine()).getAsJsonArray();
            else jsonArray=new JsonArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Envelope envelope=new Envelope();
        JsonObject jsonObject=JsonParser.parseString(gson.toJson(envelope)).getAsJsonObject();
        jsonArray.add(jsonObject);
        FileWriter writer;
        try {
            writer=new FileWriter("/home/dik/Desktop/Java/RogueGame/src/archive.json");
            writer.write(jsonArray.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*String bag= gson.toJson(Gamer.bag);
        String level=gson.toJson((Gamer.level));
        String health=gson.toJson((Gamer.health));
        String maxHealth=gson.toJson((Gamer.maxHealth));
        String power=gson.toJson((Gamer.power));
        String skill=gson.toJson((Gamer.skill));
        String gold=gson.toJson((Gamer.gold));
        for(int room=0; room<9; room+=1)*/
    }
}
