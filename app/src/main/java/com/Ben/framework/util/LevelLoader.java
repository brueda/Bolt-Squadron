package com.Ben.framework.util;

import android.util.Log;
import android.util.Xml;

import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.Grid;
import com.Ben.game.classes.Level;
import com.Ben.simpleandroidgdf.Assets;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Created by homedesk on 6/27/2015.
 */
public class LevelLoader {

    private static XmlPullParser parser;
    private static final ArrayList<Level> levelList = new ArrayList<Level>();

    public static void initialize(InputStream file) {
        try {
            parser = Xml.newPullParser();
            InputStreamReader reader = new InputStreamReader(file);
            parser.setInput(reader);
        }
        catch (XmlPullParserException e) {
            Log.d("LevelLoader", "Problem setting up parser.");
            System.exit(1);
        }

        readLevelData();
        sanityCheck();
    }

    public static ArrayList<EnemyShip> getEnemies(int level) {
        return levelList.get(level - 1).getEnemies();
    }


    //for this to really be correct... I should have another layer of this just for the root node.
    //  I'm going to assume that we're capable of basic XML, though, and not account for errors
    //  with this.
    private static void readLevelData() {
        try {
            parser.nextTag();
            while (parser.next() != END_TAG) {
                if (parser.getEventType() == START_TAG) {
                    if (parser.getName().toLowerCase().equals("level")) {
                        int levelNum = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        addLevel(levelNum, parseLevel());
                    }
                }
            }
        } catch (XmlPullParserException e) {
            Log.d("LevelLoader", "Problem when parsing level file.");
            System.exit(1);
        } catch (IOException e) {
            Log.d("LevelLoader", "IOException when parsing level file");
            System.exit(1);
        }

    }

    private static Level parseLevel() throws IOException, XmlPullParserException {
        Level ret = new Level();
        while (parser.next() != END_TAG) {
            if (parser.getEventType() == START_TAG
                && parser.getName().toLowerCase().equals("enemy")) {
                ret.addEnemy(parseEnemy());
            }
        }
        return ret;
    }

    private static String parseEnemy() throws IOException, XmlPullParserException {
        String ret = parser.getAttributeValue(null, "type").toLowerCase();
        skip();
        return ret;
    }

    private static void skip() throws IOException, XmlPullParserException {
        while (parser.next() != END_TAG) {
            if (parser.getEventType() == START_TAG) {
                skip();
            }
        }
    }

    private static void addLevel(int number, Level l) {
        --number;
        while (levelList.size() < number) {
            levelList.add(null);
        }
        levelList.add(l);
    }

    private static void sanityCheck() {
        for (int i = 0; i < levelList.size(); ++i) {
            if (levelList.get(i) == null) {
                Log.d("LEVELLOADER", "Missing level: " + (i + 1));
                System.exit(1);
            }
        }
    }

    public static int getNumberOfLevels() {
        return levelList.size();
    }

}
