package com.blockdodge.core;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Block Dodge - preBeta 1.0.0");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel panel = new GamePanel();
        window.add(panel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGame();
    }
}
