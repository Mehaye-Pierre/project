/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.GUI;

/**
 *
 * @author ducruyy
 */
public enum WindowState {
    MAIN(0), // Homepage with access to other windows
    BOARD(1), //Game window
    PLAYERSELECT(2), //New Custom Game or Load Game
    SETTINGS(3), //Setting window
    ABOUT(4), //Credits
    SAVE(5), //Save/Load window
    LOAD(6), //Save/Load window
    RULES(7), //Rules Page
    TUTORIAL(8); //Tutorial window

    private final int id;

    WindowState(int id) {
        this.id = id;
    }

    public int getValue() {
        return this.id;
    }

}
