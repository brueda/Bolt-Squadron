package com.Ben.game.classes;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.ProjectileTask;
import com.Ben.framework.util.RandomNumberGenerator;
import com.Ben.framework.util.TaskList;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public class EnemyShip extends Ship {
    private int offset;
    private boolean increasing;
    private Bitmap laserImage;
    private Bitmap shipImage;

    public EnemyShip(){
        super();
        offset = RandomNumberGenerator.getRandInt(200);
        increasing = true;
        laserImage = Assets.redLaser;
        shipImage = Assets.UFO;
    }

    @Override
    public void fire(Ship target){
        super.fire(target);
        ProjectileTask laser = new ProjectileTask();
        laser.initialize(this, target, attack, target.isDead(), laserImage);
        laser.makeRunnable();
        TaskList.addTask(laser);
    }

    public void attack(){
        int targetColumn;
        boolean foundTarget = false;
        ArrayList<Ship> targets = new ArrayList<Ship>();
        boolean[] columnChecked = new boolean[3];

        int rand = RandomNumberGenerator.getRandInt(100);
        if(rand < 75) targetColumn = 2;
        else if(rand >= 75 && rand < 95) targetColumn = 1;
        else targetColumn = 0;

        while(!checkedAllColumns(columnChecked)){
            for(Ship s: Player.getParty()){
                if (s.getPositionX() == targetColumn && !s.isDead()){
                    foundTarget = true;
                    targets.add(s);
                }
            }
            columnChecked[targetColumn] = true;
            if(foundTarget) break;
            if(!columnChecked[2]) targetColumn = 2;
            else if(!columnChecked[1]) targetColumn = 1;
            else targetColumn = 0;
        }

        if(!foundTarget) return;   // there were no living player ships
        int targetIndex = RandomNumberGenerator.getRandInt(targets.size());  // pick a random ship from the targeted column
        this.fire(targets.get(targetIndex));                                 // shoot him in the face
    }

    public void move(){
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for(int i = 4; i < 7; i++){
            for(int j = 0; j < 3; j++){
                if(checkValid(Grid.grid[i][j])){
                    tiles.add(Grid.grid[i][j]);
                }
            }
        }
        if(tiles.isEmpty()) return;
        int randIndex = RandomNumberGenerator.getRandInt(tiles.size() * 2);
        if(randIndex < tiles.size()){
            currentTile.setShip(null);
            tiles.get(randIndex).setShip(this);
        }
    }

    private boolean checkValid(Tile t){
        Tile source = currentTile;
        Tile destination = t;
        if(t.getShip() != null) return false;
        if(Math.abs(destination.getPositionY() - source.getPositionY()) + Math.abs(destination.getPositionX() - source.getPositionX()) <= 1){
            return true;
        }
        return false;
    }

    public void destroy(){
        //Enemies.getEnemies().remove(this);
        renderable = false;
    }

    public void update(){
        if(increasing) offset++;
        else offset--;
        if(offset == 200) increasing = false;
        if(offset == 0) increasing = true;
    }

    public void render(Painter g, int state, Ship selected){
        int x = currentTile.x_coordinate;
        int y = currentTile.y_coordinate;
        int sway = (offset - 100)/20;
        if(renderable) {
            g.drawImage(shipImage, x, y + sway, 80, 80);
            g.setFont(Assets.tf, 15f);
            g.setColor(Color.GREEN);
            g.drawString("" + health, x + 85, y + 20 + sway);
            g.setColor(Color.RED);
            g.drawString("" + attack, x + 85, y + 40 + sway);
            g.setColor(Color.CYAN);
            g.drawString("" + defense, x + 85, y + 60 + sway);
        }
    }

   private boolean checkedAllColumns(boolean[] checked){
       boolean all = true;
       for(int i = 0; i < checked.length; i++){
           if(!checked[i]) all = false;
       }
       return all;
   }

    public void setShipImage(Bitmap image) {
        shipImage = image;
    }
}
