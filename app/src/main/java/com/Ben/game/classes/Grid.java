package com.Ben.game.classes;

/**
 * Created by Benjamin on 6/25/2015.
 */
public class Grid {
    public static Tile[][] grid;
    public static Tile deadManTile;

    public Grid(){
        grid = new Tile[7][4];
        deadManTile = new Tile(10,1);

        for(int i = 0; i < 3; i++){       // make the Tiles for player ships
            for(int j = 0; j < 4; j++){
                grid[i][j] = new Tile(i,j);
            }
        }

        for(int i = 4; i < 7; i++){       // make the Tiles for enemy ships
            for(int j = 0; j < 4; j++){
                grid[i][j] = new Tile(i,j);
            }
        }
    }

    public static Tile touchInGrid(int x, int y){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(grid[i][j].wasPressed(x,y)) return grid[i][j];
            }
        }

        for(int i = 4; i < 7; i++){
            for(int j = 0; j < 4; j++){
                if(grid[i][j].wasPressed(x,y)) return grid[i][j];
            }
        }

        return null;
    }



}
