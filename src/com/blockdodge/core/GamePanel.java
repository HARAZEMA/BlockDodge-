package com.blockdodge.core;

import com.blockdodge.blocks.Block;
import com.blockdodge.mods.Mod;
import com.blockdodge.mods.ExampleMod;
import com.blockdodge.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    private int WIDTH, HEIGHT;
    private Player player;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Mod> mods = new ArrayList<>();

    private boolean left, right, paused;
    private int score = 0, highScore = 0, wave = 1;
    private boolean gameOver = false;

    private int spawnRate = 20;
    private int maxBlockSpeed = 10;

    private Thread gameThread;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);

        // Load example mod
        mods.add(new ExampleMod());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
                if (e.getKeyCode() == KeyEvent.VK_P) paused = !paused;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
            }
        });
    }

    public void startGame() {
        WIDTH = getParent().getWidth();
        HEIGHT = getParent().getHeight();

        player = new Player(WIDTH / 2 - 40 / 2, HEIGHT - 90, 40, 15);

        // Start mods
        for (Mod mod : mods) mod.onStart(this);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    private void resetGame() {
        blocks.clear();
        if (score > highScore) highScore = score;
        score = 0;
        wave = 1;
        spawnRate = 20;
        maxBlockSpeed = 10;
        player = new Player(WIDTH / 2 - 40 / 2, HEIGHT - 90, 40, 15);
        gameOver = false;
    }

    @Override
    public void run() {
        int frameCounter = 0;
        while (true) {
            if (!paused && !gameOver) {
                update();
                frameCounter++;

                // Increase difficulty each wave (every 500 frames)
                if (frameCounter % 500 == 0) {
                    wave++;
                    if (spawnRate > 5) spawnRate--;
                    if (maxBlockSpeed < 30) maxBlockSpeed++;
                }

                // Update mods
                for (Mod mod : mods) mod.onUpdate(this);
            } else if (gameOver) {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                resetGame();
            }

            repaint();

            try { Thread.sleep(30); } catch (InterruptedException e) {}
        }
    }

    private void update() {
        // Player movement
        if (left) player.moveLeft();
        if (right) player.moveRight();
        player.keepInBounds(WIDTH);

        // Spawn blocks
        if (Utils.randomInt(0, spawnRate) == 0) {
            int x = Utils.randomInt(0, WIDTH - 40);
            int speed = Utils.randomInt(3, maxBlockSpeed);
            blocks.add(new Block(x, 0, 40, speed, Utils.randomColor()));
        }

        // Move blocks and check collisions
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            block.update();

            if (block.getBounds().intersects(player.getBounds())) {
                gameOver = true;
            }

            if (block.getY() > HEIGHT) {
                blocks.remove(i);
                i--;
                score++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Player
        player.draw(g);

        // Blocks
        for (Block block : blocks) block.draw(g);

        // Score, high score, wave
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 20, 40);
        g.drawString("High Score: " + highScore, 20, 80);
        g.drawString("Wave: " + wave, 20, 120);

        // Pause message
        if (paused) {
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.setColor(Color.YELLOW);
            g.drawString("PAUSED", WIDTH / 3, HEIGHT / 2);
        }

        // Game over message
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", WIDTH / 4, HEIGHT / 2);
        }
    }
}
