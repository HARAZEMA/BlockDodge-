package com.blockdodge.mods;

import com.blockdodge.core.GamePanel;
import com.blockdodge.blocks.Block;
import java.awt.Color;
import java.util.Random;

public class ExampleMod implements Mod {
    private Random random = new Random();

    @Override
    public void onStart(GamePanel game) {
        System.out.println("Example Mod Loaded!");
    }

    @Override
    public void onUpdate(GamePanel game) {
        // Add a random block sometimes
        if (random.nextInt(500) == 0) {
            int x = random.nextInt(game.getWidth() - 40);
            int speed = random.nextInt(15) + 3;
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            game.addBlock(new Block(x, 0, 40, speed, color));
        }
    }
}
