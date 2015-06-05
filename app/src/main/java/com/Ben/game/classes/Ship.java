package com.Ben.game.classes;

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

    public Ship(){
        dead = false;
        attack = 10;
        defense = 5;
        maxHealth = health = 20;
        activated = false;
        cloaked = false;
        shielded = false;
    }

    /* getters and setters */

    public void setPosition(int x, int y){
        positionX = x;
        positionY = y;
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

    public void setActivated(boolean set){
        activated = set;
    }

    public boolean isActivated(){
        return activated;
    }

    public boolean isDead(){
        return dead;
    }

    /* methods for battle */

    public void hit(int power){
        int damage = power - getDefense();
        health -= damage;
        if(health <= 0){
            dead = true;
        }
    }

    public void fire(Ship target){
        Assets.playSound(Assets.laserID);
        int power = attack;
        target.hit(power);
    }

}
