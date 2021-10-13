package dev.tilegame;

import dev.tilegame.Display.Display;
import dev.tilegame.gfx.Assets;
import dev.tilegame.gfx.ImageLoader;
import dev.tilegame.gfx.SpriteSheet;
import dev.tilegame.input.KeyManager;
import dev.tilegame.states.GameState;
import dev.tilegame.states.MenuState;
import dev.tilegame.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {

    private Display display;

    public int width;
    public int height;
    public String title;
    private boolean running;
    private Thread thread;

    private BufferStrategy bs; // zorgt voor het vlot tonen van de graphics || geen flickering
    private Graphics g; // maakt het mogelijk om te tekenen op Canvas

    //input
    private KeyManager keyManager;

    //states
    private State gameState;
    private State menuState;


    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        running = false;
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title,width,height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    private void tick(){
        keyManager.tick();
        if(gameState != null){
            State.getState().tick();
        }
    }

    /**
     * renderd de grafische componenten
     */
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            // aanmaken van bufferStrategy als deze nog niet bestaat
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // leeg het scherm
        g.clearRect(0,0,width,height);
        // tekenen begin

        if(gameState != null){
            State.getState().render(g);
        }

        // einde tekenen
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000/fps;// 1 sec in nanosec gedeeld door aantal frames per seconde
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        // game loop
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                System.out.println("ticks and frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public KeyManager getKeyManager(){return keyManager;}

    /**
     * start de Thread bij het opstarten van het programma
     */
    public synchronized void start(){
            if(running){
                return;
            }

        running = true;
        thread = new Thread(this);
        thread.start();
    }


    /**
     * Stop de Thread op een veilige manier bij het afsluiten
     */
    public synchronized void stop(){
        if(!running){
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
