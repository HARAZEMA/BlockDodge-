package com.blockdodge.core;

import java.awt.*;

public class Player {
    private int x, y, size, speed;

    public Player(int x, int y, int size, int speed) {
        this.x = x; this.y = y; this.size = size; this.speed = speed;
    }

    public void moveLeft() { x -= speed; }
    public void moveRight() { x += speed; }

    public void keepInBounds(int width) {
        if (x < 0) x = 0;
        if (x > width - size) x = width - size;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
    }

    public Rectangle getBounds() { return new Rectangle(x, y, size, size); }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
