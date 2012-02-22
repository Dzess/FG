package functiongenerator.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Fixes the problem with JTable deleting the character on the backspace button
 * hit. <br/>
 * 
 * Bases on the trick to change the event code. We are changing the 'backspace'
 * ASCII code to the 'delete' ASCII code
 * 
 * @author Piotr Jessa
 * 
 */
public class JTableBackspaceFixListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == 8) {
            // NOTE: this is workaround about the problem
            // backspace has known bug for swing, this is bugfix for now
            // we are changing the 'backspace' ASCII code to the
            // 'delete' ASCII code
            System.out.println("Consuming the : " + k.getKeyCode() + "replacing with: " + 127);
            k.setKeyCode(127);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // no code here
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // no code here
    }

}
