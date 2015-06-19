package com.Ben.game.classes;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.RandomNumberGenerator;
import com.Ben.game.state.State;
import com.Ben.simpleandroidgdf.Assets;

import java.util.ArrayList;

/**
 * Created by Benjamin on 5/26/2015.
 */
public abstract class EnemyShip extends Ship {
    private int offset;
    private boolean increasing;

    public EnemyShip(){
        super();
        offset = RandomNumberGenerator.getRandInt(200);
        increasing = true;
    }

    public void attack(Player p){
        int targetColumn;
        boolean foundTarget = false;
        ArrayList<Ship> targets = new ArrayList<Ship>();
        boolean[] columnChecked = new boolean[3];

        int rand = RandomNumberGenerator.getRandInt(100);
        if(rand < 75) targetColumn = 2;
        else if(rand >= 75 && rand < 95) targetColumn = 1;
        else targetColumn = 0;

        while(!checkedAllColumns(columnChecked)){
            for(Ship s: p.getParty()){
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

    public void update(){
        if(increasing) offset++;
        else offset--;
        if(offset == 200) increasing = false;
        if(offset == 0) increasing = true;
    }

    public void render(Painter g, int state, Tile selected){
        int x = currentTile.x_coordinate;
        int y = currentTile.y_coordinate;
        int sway = (offset - 100)/20;
        if(!dead){
            g.drawImage(Assets.UFO, x, y + sway, 80, 80);
        }
    }

   private boolean checkedAllColumns(boolean[] checked){
       boolean all = true;
       for(int i = 0; i < checked.length; i++){
           if(!checked[i]) all = false;
       }
       return all;
   }


}
