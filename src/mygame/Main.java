package mygame;

/**
 *
 * @author oat_s
 */
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
public class Main{
    public static final int GAME_SPEED=10;
    public static void main(String[] args) throws UnsupportedAudioFileException, InterruptedException, IOException{
        JFrame frame = new JFrame("Balloon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();
        frame.setSize(Game.SCREEN_WIDTH,Game.HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addKeyListener(game);
        frame.addMouseListener(game);
        
        while(true) {
           frame.add(game);
           game.move();
           sleep(GAME_SPEED);
        }
    }
}
