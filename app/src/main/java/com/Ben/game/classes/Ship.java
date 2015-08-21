package com.Ben.game.classes;

import android.graphics.Bitmap;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.ProjectileTask;
import com.Ben.framework.util.RandomNumberGenerator;
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
    protected boolean shielded;
    protected boolean activated;  // has moved or attacked this round
    protected Tile currentTile;
    protected boolean renderable;
    protected int offset;
    protected boolean increasing;
    protected Bitmap laserImage;

    public Ship(){
        dead = false;
        attack = 10;
        defense = 5;
        maxHealth = health = 20;
        activated = false;
        shielded = false;
        currentTile = null;
        renderable = true;
        offset = RandomNumberGenerator.getRandInt(200);
        increasing = true;
    }

    /* getters and setters */

    public void setPosition(int x, int y){
        positionX = x;
        positionY = y;
    }

    public void increaseMaxHealth(int amount){
        maxHealth += amount;
        setHealth(maxHealth);
    }

    public void setTile(Tile t){
        currentTile = t;
    }

    public void setHealth(int h){
        health = h;
    }

    public Tile getTile(){
        return currentTile;
    }

    public void setShield(boolean set){
        shielded = set;
    }

    public int getMaxHealth(){
        return maxHealth;
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

    public void setDead(boolean set){
        dead = set;
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

    public abstract Bitmap getShipImage();


    /* methods for battle */

    public void hit(int power){
        int damage = power - getDefense();
        if(shielded){
            shielded = false;
        }
        else {
            health = Math.min(health - damage, maxHealth);
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
