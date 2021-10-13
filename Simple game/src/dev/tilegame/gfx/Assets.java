package dev.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player, enemy, grass1, grass2, dirt;
    private static final int width = 32, height = 32;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));

        player = sheet.crop(0,0, width, height);
        enemy = sheet.crop(width, 0, width, height);
        grass1 = sheet.crop(0, height, width, height);
        grass2 = sheet.crop(width, height, width, height);
        dirt = sheet.crop(width*2, height, width, height);
    }

}
