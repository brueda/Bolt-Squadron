package com.Ben.framework.util;

import android.util.Log;
import android.util.Xml;

import com.Ben.game.classes.EnemyShip;
import com.Ben.game.classes.GenericEnemy;
import com.Ben.simpleandroidgdf.Assets;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Created by homedesk on 7/12/2015.
 */
public class EnemyTypes {

    private static XmlPullParser parser;
    private static Map<String, GenericEnemy> enemyMap;

    public static void initialize(InputStream file) {
        try {
            parser = Xml.newPullParser();
            InputStreamReader reader = new InputStreamReader(file);
            parser.setInput(reader);
        }
        catch (XmlPullParserException e) {
            Log.d("EnemyTypes", "Problem setting up parser.");
            System.exit(1);
        }

        enemyMap = new HashMap<String, GenericEnemy>();

        readEnemyData();
    }

    public static EnemyShip get(String type) {
        GenericEnemy gen = enemyMap.get(type.toLowerCase());
        return new EnemyShip(gen);
    }

    private static void readEnemyData() {
        try {
            parser.nextTag();
            while (parser.next() != END_TAG) {
                if (parser.getEventType() == START_TAG) {
                    if (parser.getName().toLowerCase().equals("enemy")) {
                        parseEnemy();
                    } else {
                        skip();
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

    private static void parseEnemy() throws IOException, XmlPullParserException {
        GenericEnemy e = new GenericEnemy();
        String name = parser.getAttributeValue(null, "type").toLowerCase();
        e.health = Integer.parseInt(parser.getAttributeValue(null, "hp"));
        e.attack = Integer.parseInt(parser.getAttributeValue(null, "att"));
        e.defense = Integer.parseInt(parser.getAttributeValue(null, "def"));
        e.imageID = Integer.parseInt(parser.getAttributeValue(null, "id"));
        enemyMap.put(name, e);
        skip();
    }

    private static void skip() throws IOException, XmlPullParserException {
        while (parser.next() != END_TAG) {
            if (parser.getEventType() == START_TAG) {
                skip();
            }
        }
    }
}
