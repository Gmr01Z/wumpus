package com.wumpus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sun.java2d.pipe.SpanIterator;

import java.util.ArrayList;

public class Bob {
    private int row;
    private int col;
    private Texture texture;
    private WumpusWrl myWorld;
    public Bob(WumpusWrl myWorld){
        this.myWorld = myWorld;
        row=9;
        col=0;
        updateVision();
        texture = new Texture("guy.png");
    }

    public Bob(int row, int col, WumpusWrl myWorld) {
        this.myWorld = myWorld;

        this.row = row;
        this.col = col;
        texture = new Texture("guy.png");
    }
    public void updateVision(){
        myWorld.setVisible(this.row,this.col);
    }
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(texture,col*50, 500-(row*50));
    }

    public void moveR(){
        if(col+1 < myWorld.getNumCol()) {
            col++;
            updateVision();
        }
    }
    public void moveL(){
        if(col > 0) {
            col--;
            updateVision();
        }
    }
    public void moveDown(){
        if(row < myWorld.getNumRow()-1) {
            row++;
            updateVision();
        }
    }
    public void moveUp(){
        if(row > 0) {
            row--;
            updateVision();
        }
    }
    public void step(){
        Location loc = chooseRamdomMove();
        row = loc.getRow();
        col = loc.getCol();
        myWorld.setVisible(row, col);
        if(myWorld.isLocDeath(loc)){
            Gameplay.simOver = true;
            Gameplay.runAI = false;
        }
    }
    public Location chooseRamdomMove(){
        ArrayList<Location> possibleLocs = new ArrayList<>(4);
        Location above = new Location(row-1, col);
        Location below = new Location(row+1, col);
        Location left = new Location(row, col-1);
        Location right = new Location(row, col+1);
        if(myWorld.isLocValid(above))
            possibleLocs.add(above);
        if(myWorld.isLocValid(below))
            possibleLocs.add(below);
        if(myWorld.isLocValid(left))
            possibleLocs.add(left);
        if(myWorld.isLocValid(right))
            possibleLocs.add(right);

        return possibleLocs.get((int)(Math.random() * possibleLocs.size()));
    }
}
