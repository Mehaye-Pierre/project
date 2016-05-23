/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.GUI;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_F11;

/**
 *
 * @author sazeratj
 */
public class CustomKeyboardDispatcher implements KeyEventDispatcher {

    Main_Frame mainframe;

    public CustomKeyboardDispatcher(Main_Frame mf) {
        this.mainframe = mf;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == VK_F11) {
            this.mainframe.toggleWRM();
        } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == VK_ESCAPE) {
            this.mainframe.backWindow();
        }
        return true;
    }
}