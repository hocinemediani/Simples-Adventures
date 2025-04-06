import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    /* Canvas onto which the frame will draw */
    private Canvas canvas = new Canvas();


    /** Creates the game frame */
    public GameFrame() {
        /* Option to terminate the program upon closing the window */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Projet long");

        /* Sets base height and centers the window */
        this.setBounds(0, 0, 1000, 800);
        this.setLocationRelativeTo(null);

        /* Maximises the window */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(canvas);
        this.setVisible(true);
        canvas.createBufferStrategy(4);
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        int theta = 0;
        int radius = 200;
        int xRect = 0;
        int yRect = 0;
        int ticks = 0;

        while (true) {
            if ((ticks % 2) == 0) {
                theta++;
                xRect = (int) Math.floor(radius * Math.cos(theta));
                yRect = (int) Math.floor(radius * Math.sin(theta));
            }

            bufferStrategy = canvas.getBufferStrategy();
            Graphics graphics = bufferStrategy.getDrawGraphics();
            super.paint(graphics);
            graphics.setColor(Color.red);
            graphics.fill3DRect(xRect + 675, yRect + 375, 100, 60, true);
            graphics.dispose();
            bufferStrategy.show();
            ticks++;
        }
    }


    public static void main(String... args) {
        new GameFrame();
    }

}
