package com.Ben.game.classes;

import android.graphics.Rect;

/**
 * Created by Benjamin on 6/1/2015.
 */
public class Tile {
    private Ship ship;
    private int positionX;
    private int positionY;
    private Rect area;
    public int x_coordinate;
    public int y_coordinate;

    public Tile(int x, int y){
        ship = null;
        positionX = x;
        positionY = y;
        x_coordinate = 50 + x * 100;
        y_coordinate = 25 + y * 100;
        area = new Rect(x_coordinate, y_coordinate, x_coordinate + 100, y_coordinate + 100);
    }

    public boolean wasPressed(int x, int y){
        return area.contains(x,y);
    }


    public void setShip(Ship s){
        ship = s;
        if(ship != null){
            ship.setPosition(positionX, positionY);
        }
    }

    public Ship getShip(){
        return ship;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}
