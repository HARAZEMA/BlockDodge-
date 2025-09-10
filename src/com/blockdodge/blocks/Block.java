package com.blockdodge.blocks;

import java.awt.*;

public class Block {
    protected int x, y, size, speed;
    protected Color color;

    public Block(int x, int y, int size, int speed, Color color) {
        this.x = x; this.y = y; this.size = size; this.speed = speed; this.color = color;
    }

    public void update() { y += speed; }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public Rectangle getBounds() { return new Rectangle(x, y, size, size); }

    public int getY() { return y; }
}
