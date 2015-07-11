package com.Ben.framework.util;

import android.util.Log;
import android.util.Xml;

import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.TestEnemy;
import com.Ben.game.classes.TestShip;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.xmlpull.v1.XmlPullParser.END_TAG;

/**
 * Created by homedesk on 6/27/2015.
 */
public class LevelLoader {

    private static XmlPullParser parser;

    public static void initialize(String filename) {
        parser = Xml.newPullParser();
        try {
            FileReader reader = new FileReader(new File(filename));
            parser.setInput(reader);
        } catch (FileNotFoundException e) {
            Log.d("LevelLoader", "Could not find level file.");
            System.exit(1);
        } catch (XmlPullParserException e) {
            Log.d("LevelLoader", "Problem setting up parser.");
            System.exit(1);
        }
    }

    public static ArrayList<EnemyShip> loadEnemies(int level) {
        ArrayList<EnemyShip> ret = null;
        try {
            parser.nextTag();
            while (parser.next() != END_TAG) {
                String name = parser.getName();
                if (name.toLowerCase() == "level") {
                    ret = parseLevel(level);
                    if (ret != null) { break; }
                }
            }
        } catch (XmlPullParserException e) {
            Log.d("LevelLoader", "Problem when parsing level file.");
            System.exit(1);
        } catch (IOException e) {
            Log.d("LevelLoader", "IOException when parsing level file");
            System.exit(1);
        }

        return ret;
    }

    private static ArrayList<EnemyShip> parseLevel(int level) throws IOException, XmlPullParserException {
        if (Integer.parseInt(parser.getAttributeValue(null, "id")) == level) {
            skip();
            return null;
        }
        ArrayList<EnemyShip> ret = new ArrayList<EnemyShip>();
        while (parser.next() != END_TAG) {
            if (parser.getName().toLowerCase().equals("enemy")) {
                ret.add(parseEnemy());
            }
        }
        return ret;
    }

    private static EnemyShip parseEnemy() throws IOException, XmlPullParserException {
        EnemyShip ret;
        //Create type of ship.
        if(parser.getAttributeValue(null, "type").toLowerCase().equals("test")) {
            ret = new TestEnemy();
        }
        else {
            return null;
        }

        //Get x and y position values. x is range 1-3. y is range 1-4.
        int xPosition = Integer.parseInt(parser.getAttributeValue(null, "x"));
        int yPosition = Integer.parseInt(parser.getAttributeValue(null, "y"));

        Grid.grid[5+xPosition-1][yPosition-1].setShip(ret);
        skip();
        return ret;
    }

    private static void skip() throws IOException, XmlPullParserException {
        while (parser.next() != END_TAG) { /*While-Header does the work here*/ }
    }

}
