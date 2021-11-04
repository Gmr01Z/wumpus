package com.wumpus.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Gameplay implements Screen {
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    //Object that allwos us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    //Textures
    WumpusWrl wrl = new WumpusWrl(10,10);

    //Bob
    Bob bob = new Bob(wrl);
    public static boolean runAI = false;
    int totalMoves = 10;
    public static boolean simOver = false;
    // creat UI
    BitmapFont defaultFont = new BitmapFont();
    int currentSelection = -1;

    //runs one time, at the very beginning
    //all setup should happen here
    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();

        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem


    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void getMouseInput(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if(currentSelection != -1){
                wrl.placeTileInWorld(currentSelection, x, y);
                currentSelection = -1;
            }
            if(x >= 630 && x <= 680 && y <= 500 && y >= 450){
                wrl.flipWorldVisisible();
            }
            if(x >= 630 && x <= 680 && y <= 555 && y >= 505){
                runAI = !runAI;
            }
            else if(x >= 630 && x <= 680 && y >= 130 && y <= 180){
                currentSelection = wrl.GROUND;
            }
            else if(x >= 630 && x <= 680 && y >= 185 && y <= 235){
                currentSelection = wrl.SPIDER;
            }
            else if(x >= 630 && x <= 680 && y >= 240 && y <= 290){
                currentSelection = wrl.PIT;
            }
            else if(x >= 630 && x <= 680 && y >= 295 && y <= 345){
                currentSelection = wrl.WUMPUS;
            }
            else if(x >= 630 && x <= 680 && y >= 350 && y <= 400){
                currentSelection = wrl.GOLD;
            }
        }
    }
    public void getKeyboardInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            bob.moveR();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            bob.moveL();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            bob.moveUp();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            bob.moveDown();
        }

    }
    public void drawToolbar(){
        defaultFont.draw(spriteBatch,"Toolbar", 630,520);
        defaultFont.draw(spriteBatch, "TotalMoves: " + totalMoves, 620, 550);
        spriteBatch.draw(wrl.getGround(), 630,420);
        spriteBatch.draw(wrl.getSpider(), 630,365);
        spriteBatch.draw(wrl.getPit(), 630,310);
        spriteBatch.draw(wrl.getWumpus(), 630,255);
        spriteBatch.draw(wrl.getGold(), 630,200);
        spriteBatch.draw(wrl.getProblem(), 630, 100);
        spriteBatch.draw(wrl.getTrophe(), 630,45);

        if(currentSelection != -1){
            spriteBatch.draw(wrl.getTextureByID(currentSelection), Gdx.input.getX()-25, 575-Gdx.input.getY());
        }
    }
    //this method runs as fast as it can, repeatedly, constantly looped
    @Override
    public void render(float delta) {
        clearScreen();

        //all drawing of shapes MUST be in between begin/end
        shapeRenderer.begin();

        shapeRenderer.end();

        //all drawing of graphics MUST be in between begin/end
        spriteBatch.begin();
        getKeyboardInput();
        getMouseInput();
        if(runAI){
            bob.step();
            totalMoves++;
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        wrl.draw(spriteBatch);
        bob.draw(spriteBatch);
        drawToolbar();
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
