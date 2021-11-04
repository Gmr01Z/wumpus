package com.wumpus.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WumpusWrl {
    private static int world[][];
    private static boolean visible[][];
    private Texture ground;
    private Texture black;
    private Texture gliter;
    private Texture gold;
    private Texture pit;
    private Texture problem;
    private Texture spider;
    private Texture spiderweb;
    private Texture stink;
    private Texture wind;
    private Texture wumpus;
    private Texture trophy;
    public static final int GROUND=0, SPIDER=2,PIT = 3, WUMPUS=4,GOLD=5,WEB=12,BREEZ=13,STINK=14,GLITTER=15;

    public static boolean hideWorld = true;

    public WumpusWrl(int row, int col) {
        world = new int[row][col];
        visible = new boolean[row][col];
        ground = new Texture("groundTile.png");
        black = new Texture("blackTile.png");
        gliter = new Texture("gliter.png");
        gold = new Texture("gold.png");
        pit = new Texture("pit.png");
        problem = new Texture("problem.png");
        spider = new Texture("spider.png");
        spiderweb  = new Texture("spiderweb.png");
        stink  = new Texture("stink.png");
        wind  = new Texture("wind.png");
        wumpus  = new Texture("wumpus.png");
        trophy  = new Texture("trophy.png");
    }
    public void setVisible(int row, int col){
        visible[row][col] = true;
    }
    public boolean isLocDeath(Location loc){
        return world[loc.getRow()][loc.getCol()] >= 2 &&
                world[loc.getRow()][loc.getCol()] <= 4;
    }
    public int getNumCol(){
        return world[0].length;
    }
    public int getNumRow(){
        return world.length;
    }

    public void flipWorldVisisible(){
        hideWorld = !hideWorld;
    }
    public Texture getTextureByID(int id){
        switch(id){
            case 0: return ground;
            case 2: return spider;
            case 3: return pit;
            case 4: return wumpus;
            case 5: return gold;
            case 10: return ground;
            case 12: return spiderweb;
            case 13: return wind;
            case 14: return stink;
            case 15: return gliter;
            default: return null;
        }
    }
    /*
    tileID --> type of main tile that was placed(PIT SPIDER GOLD WUMPUS GROUND)
    loc --> the location of main tileID
     */
    public void placeTileHints(int tileID, Location loc){
        Location above = new Location(loc.getRow()-1, loc.getCol());
        Location below = new Location(loc.getRow()+1, loc.getCol());
        Location left = new Location(loc.getRow(), loc.getCol()-1);
        Location right = new Location(loc.getRow(), loc.getCol()+1);
        if(isLocValid(above)){
            world[above.getRow()][above.getCol()] = tileID+10;
        }
        if(isLocValid(below)){
            world[below.getRow()][below.getCol()] = tileID+10;
        }
        if(isLocValid(left)){
            world[left.getRow()][left.getCol()] = tileID+10;
        }
        if(isLocValid(right)){
            world[right.getRow()][right.getCol()] = tileID+10;
        }
    }
    public boolean isLocValid(Location loc){
        return loc.getRow() >=0 && loc.getRow() < world.length &&
                loc.getCol() >=0 && loc.getCol() < world[0].length;
    }
    public void placeTileInWorld(int tileID, int x, int y){
        Location loc = mouseToLoc(x,y);
        world[loc.getRow()][loc.getCol()] = tileID;
        placeTileHints(tileID, loc);
    }
    public Location mouseToLoc(int x, int y){
        int col = x/50;
        int row = (y/50)-1;
        return new Location(row,col);
    }
    public void draw(SpriteBatch spriteBatch){
        for(int row = 0; row < world.length; row++){
            for(int col = 0; col < world[0].length; col++){
                if(!visible[row][col]&&hideWorld)
                    spriteBatch.draw(black, col*50, 500-(row*50));
                else
                    spriteBatch.draw(getTextureByID(world[row][col]), col*50, 500-(row*50));
            }
        }
    }

    public Texture getGround() {
        return ground;
    }

    public Texture getBlack() {
        return black;
    }

    public Texture getGliter() {
        return gliter;
    }

    public Texture getGold() {
        return gold;
    }

    public Texture getPit() {
        return pit;
    }

    public Texture getProblem() {
        return problem;
    }

    public Texture getSpider() {
        return spider;
    }

    public Texture getSpiderweb() {
        return spiderweb;
    }

    public Texture getStink() {
        return stink;
    }

    public Texture getWind() {
        return wind;
    }

    public Texture getWumpus() {
        return wumpus;
    }

    public Texture getTrophe() {
        return trophy;
    }
}
