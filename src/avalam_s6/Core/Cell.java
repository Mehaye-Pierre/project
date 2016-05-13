/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Core;

import java.util.ArrayList;


/**
 *
 * @author TheDoctor
 */
public class Cell {
    private ArrayList<Pawn> contenu;
    private State etat;
    
    public Cell(int owner) {
        this.contenu = new ArrayList<>();
        switch(owner) {
            case -1:
                this.etat = State.RESTRICTED;
                break;
            case 0:
                this.etat = State.EMPTY;
                break;
            case 1:
                this.contenu.add(Pawn.PLAYER_1);
                this.etat = State.TOWER;
                break;
            case 2:
                this.contenu.add(Pawn.PLAYER_2);
                this.etat = State.TOWER;                
                break;
        }
    }
    
    /**
     * Add a pawn on top of the tower
     * @param p the pawn added to the top of tower
     */
    public void add(Pawn p) {
        this.contenu.add(p);
        if (this.contenu.size() == 5)
            this.etat = State.FULL; // NORMAL
        if (this.contenu.size() == 1)
            this.etat = State.TOWER; // UNDO
    }
    

    /**
     * Remove an element at I (Undo function can undo in the right order)
     * Towers are not shuffled by undoing.
     * @param i
     * @return 
     */
    public Pawn removeAt(int i) {
        if(this.contenu.size() == 5)
            this.etat = State.TOWER; 
        if(this.contenu.size() == 1)
            this.etat = State.EMPTY;
        return this.contenu.remove(i);
    }
    
    /**
     * Get the height of the tower
     * @return height of the tower
     */
    public int getSize() {
        return this.contenu.size();
    } 
    
    /**
     * Get the peek of the tower 
     * @return peek of tower 
     */
    public Pawn getOwner() {
        if (! this.contenu.isEmpty())
            return this.contenu.get(getSize()-1);
        return Pawn.NO_OWNER;
    }
    
    /**
     * Getter
     * @return state 
     */
    public State getState() {
        return etat;
    }
}