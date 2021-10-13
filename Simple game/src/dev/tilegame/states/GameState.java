package dev.tilegame.states;

import dev.tilegame.Game;
import dev.tilegame.entities.creatures.Player;
import dev.tilegame.gfx.Assets;

import java.awt.*;

public class GameState extends State{

    private Player player;

    public GameState(Game game){
        super(game);
        player = new Player(game,100,100);
    }

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
}
