    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template Game.this., choose Tools | Templates
 * and open the template in the editor.
 */
package com.owary.arrowshooter;

/**
 *
 * @author OwaryLtd
 */

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;
    import java.awt.event.WindowAdapter;
    import java.awt.event.WindowEvent;
    import java.util.LinkedList;
    import java.util.List;

public class Game extends JFrame /*implements KeyListener*/ {

    private static final String WINDOW_TITLE = "Arrow Shooter";
    private static int windowWidth = 1000;  // size in non-full-screen mode
    private static int windowHeight = 480;

    private Container joglMain;
    
    private GraphicsDevice device;
    private boolean fullScreen = true; // full-screen or windowed mode
    public List<KeyEvent> list = new LinkedList<>();

    // Constructor
    public Game() {
        joglMain = new Container();
        this.getContentPane().add(joglMain);

        // Get the default graphic device and try full screen mode
        device = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
            this.setSize(windowWidth, windowHeight);
            this.setResizable(false);
            fullScreen = false;
//        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        Container.animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }
        });

        // Enable keyboard input
        this.addKeyListener(new MultiKeyListener());
        this.setFocusable(true);  // To receive key event
        this.requestFocus();

        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Container.animator.start(); // start the animation loop
    }

    public static void main(String[] args) {
        new Game();
    }

    class MultiKeyListener implements KeyListener {

        
        @Override
        public synchronized void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
//            System.out.println(keyCode);
            joglMain.moveThePlayer(keyCode);
            switch (keyCode) {
                // F1 to toggle between full-screen and windowed modes
                case KeyEvent.VK_F1:
                    if (!fullScreen) {  // Saved the current size for restoration
                        Dimension screenSize = Game.this.getSize();
                        windowWidth = (int) screenSize.getWidth();
                        windowHeight = (int) screenSize.getHeight();
                    }
                    fullScreen = !fullScreen;
                    Game.this.setVisible(false); // Hide the display
                    if (Game.this.isDisplayable()) {
                        Game.this.dispose();      // For changing the decoration
                    }
                    if (fullScreen) {
                        if (device.isFullScreenSupported()) {
                            Game.this.setUndecorated(true);
                            Game.this.setResizable(false);
                            device.setFullScreenWindow(Game.this);
                        }
                    } else {
                        Game.this.setUndecorated(false);  // Put the title and fillobjects back
                        device.setFullScreenWindow(null); // Windowed mode
                        Game.this.setSize(windowWidth, windowHeight);
                        Game.this.setResizable(true);
                    }
                    Game.this.setVisible(true);  // Show it
                    break;

                // ESC to quit
                case KeyEvent.VK_ESCAPE:
                    // Use a dedicate thread to run the stop() to ensure that the
                    // animator stops before program exits.
                    new Thread() {
                        @Override
                        public void run() {
                            Container.animator.stop(); // stop the animator loop
                            System.exit(0);
                        }
                    }.start();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }
}
