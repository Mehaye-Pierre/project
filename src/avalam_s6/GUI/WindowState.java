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
    VICTORY(2), //Game Victory 
    PLAYERSELECT(3), //New Custom Game or Load Game
    SETTINGS(4), //Setting window
    ABOUT(5), //Credits
    SAVE(6), //Save/Load window
    LOAD(7), //Save/Load window
    RULES(8), //Rules Page
    TUTORIAL(9); //Tutorial window
    
    
    private final int id;
    WindowState(int id) { this.id = id; }
    public int getValue() { return this.id; }
    
}
