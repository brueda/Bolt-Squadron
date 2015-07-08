package com.Ben.game.classes;

import android.graphics.Bitmap;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.ProjectileTask;
import com.Ben.framework.util.TaskList;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 5/26/2015.
 */
public abstract class Ship {
    protected int attack;
    protected int defense;
    protected int maxHealth;
    protected int health;
    protected int positionX;
    protected int positionY;
    protected boolean dead;
    protected boolean cloaked;
    protected boolean shielded;
    protected boolean activated;  // has moved or attacked this round
    protected Tile currentTile;
    protected boolean renderable;

    public Bitmap image;

    public Ship(){
        dead = false;
        attack = 10;
        defense = 5;
        maxHealth = health = 20;
        activated = false;
        cloaked = false;
        shielded = false;
        currentTile = null;
        renderable = true;
    }

    /* getters and setters */

    public void setPosition(int x, int y){
        positionX = x;
        positionY = y;
    }

    public void setTile(Tile t){
        currentTile = t;
    }

    public Tile getTile(){
        return currentTile;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public int getHealth(){
        return health;
    }

    public int getAttack(){
        return attack;
    }

    public int getDefense(){
        // apply shields
        return defense;
    }

    public void setDefense(int d){
        defense = d;
    }

    public void setAttack(int a){
        attack = a;
    }

    public void setActivated(boolean set){
        activated = set;
    }

    public boolean isActivated(){
        return activated;
    }

    public boolean isDead(){
        return dead;
    }

    public boolean isRenderable() {return renderable;}

    /* abstract methods for animation */

    public abstract void update();

    public abstract void render(Painter g, int state, Ship selected);


    /* methods for battle */

    public void hit(int power){
        int damage = power - getDefense();
        if(shielded){
            shielded = false;
        }
        else {
            health -= damage;
        }
        if(health <= 0){
            dead = true;
            currentTile.setShip(null);
        }
    }

    public void fire(Ship target){
        Assets.playSound(Assets.laserID, 4f);
        int power = attack;
        target.hit(power);
    }

    public abstract void destroy();

}
