/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Player;

import avalam_s6.Core.*;
import avalam_s6.Core.Globals.AvalamColor;

/**
 *
 * @author TheDoctor
 */
public abstract class AIPlayer extends Player {

    protected Game_INTERFACE game;
    
    private final Coordinate setInvalid;
    
    protected Coordinate[][] coord;

    /**
     * Constructor.
     *
     * @param name The name of the player.
     * @param color The color of the player's pawns.
     * @param owner
     */
    public AIPlayer(String name, AvalamColor color, Owner owner) {
        super(name, color, owner);
        setInvalid = new Coordinate(-1,-1);
    }

    /**
     * Max value
     *
     * @param a origin
     * @param b target
     * @return true if the move a on b would give us a 5 tour, b is opponent
     */
    protected boolean completeTourUsVsOp(Cell a, Cell b) {
        if (this.owner.getValue() == a.getOwner().getValue()) {
            if (this.owner.getValue() != b.getOwner().getValue()) {
                if (a.getSize() + b.getSize() == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ok value
     *
     * @param a origin
     * @param b target
     * @return true if the move a on b would give us a 5 tour
     */
    protected boolean completeTourUs(Cell a, Cell b) {
        if (this.owner.getValue() == a.getOwner().getValue()) {
            if (a.getSize() + b.getSize() == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * bad value
     */
    protected boolean completeTourOp(Cell a, Cell b) {
        if (this.owner.getValue() != a.getOwner().getValue()) {
            if (a.getSize() + b.getSize() == 5) {
                return true;
            }
        } else {
        }
        return false;
    }

    /**
     * meh value
     */
    protected boolean suppressAPawn(Cell a, Cell b) {
        if (this.owner.getValue() != b.getOwner().getValue()) {
            if (a.getSize() + b.getSize() < 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * meh- value, 3 seems to be a strong height if you don't have a 2 tower
     * around
     */
    protected boolean suppressAPawnCreate3Op(Cell a, Cell b) {
        if (this.owner.getValue() != b.getOwner().getValue()) {
            if (a.getSize() + b.getSize() == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if a coordinate is alone
     *
     * @param c0 coordinate
     * @return true if the coordinate is alone (no move possible) else false
     */
    protected boolean alone(Coordinate c0) {
        Coordinate[] tabCoord = new Coordinate[8];
        int i = c0.getY();
        int j = c0.getX();
        /**
         * 1 2 3
         * 4 0 5
         * 6 7 8
         */

        doCoord(i,j,tabCoord);
        for (int k = 0; k < 8; k++) {
            if (tabCoord[k].isValid() && this.game.getGrid().getCellAt(tabCoord[k]).getState().getValue() == CellState.TOWER.getValue()) {
                if (this.game.getGrid().canStack(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k]))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * best value
     *
     * @param c0
     * @param dest
     * @return
     */
    protected boolean createAloneUs(Coordinate c0, Coordinate dest) {
        Coordinate[] tabCoord = new Coordinate[8];
        int i = c0.getY();
        int j = c0.getX();
        /**
         * 1 2 3
         * 4 0 5
         * 6 7 8
         */
        doCoord(i,j,tabCoord);
        for (int k = 0; k < 8; k++) {
            Move m = new Move(c0, this.game.getGrid().getCellAt(c0).getSize(), dest, this.game.getGrid().getCellAt(dest).getSize(), this);
            this.game.getGrid().moveCell(c0, dest);
            this.game.addMoveToHistory(m);
            if (tabCoord[k].isValid() && this.game.getGrid().getCellAt(tabCoord[k]).getState().getValue() == CellState.TOWER.getValue()
                    && this.owner.getValue() == this.game.getGrid().getCellAt(tabCoord[k]).getOwner().getValue() && alone(tabCoord[k])) {
                this.game.undo();
                return true;
            }
            this.game.undo();
        }

        return false;
    }

    /**
     * bad, we give the Op a free point
     *
     * @param c0 origin
     * @param dest destination
     * @return true if we create an alone for the Op, else false
     */
    protected boolean createAloneOp(Coordinate c0, Coordinate dest) {
        Coordinate[] tabCoord = new Coordinate[8];
        int i = c0.getY();
        int j = c0.getX();
        /**
         * 1 2 3
         * 4 0 5
         * 6 7 8
         */
        doCoord(i,j,tabCoord);
        for (int k = 0; k < 8; k++) {
            Move m = new Move(c0, this.game.getGrid().getCellAt(c0).getSize(), dest, this.game.getGrid().getCellAt(dest).getSize(), this);
            this.game.getGrid().moveCell(c0, dest);
            this.game.addMoveToHistory(m);
            if (tabCoord[k].isValid() && this.game.getGrid().getCellAt(tabCoord[k]).getState().getValue() == CellState.TOWER.getValue()
                    && this.owner.getValue() != this.game.getGrid().getCellAt(tabCoord[k]).getOwner().getValue() && alone(tabCoord[k])) {
                this.game.undo();
                return true;
            }
            this.game.undo();
        }

        return false;
    }

    public int nbCoupsJouables() {
        int res = 0;
        Coordinate[] tabCoord = new Coordinate[8];
        for (int i = 0; i < this.game.getGrid().getWidth(); i++) {
            /**
             * 1 2 3
             * 4 0 5
             * 6 7 8
             */
            for (int j = 0; j < this.game.getGrid().getHeight(); j++) {
                Coordinate c0 = new Coordinate(j, i);
                doCoord(i,j,tabCoord);

                if (c0.isValid() && this.game.getGrid().getCellAt(c0).getState().getValue() == CellState.TOWER.getValue()) {
                    for (int k = 0; k < 8; k++) {
                        if (tabCoord[k].isValid() && this.game.getGrid().getCellAt(tabCoord[k]).getState().getValue() == CellState.TOWER.getValue()) {
                            if (this.game.getGrid().canStack(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k]))) {
                                res++;
                            }
                        }
                    }
                }
            }
        }

        return res;
    }

    public void setGame(Game_INTERFACE game) {
        this.game = game;
    }

    @Override
    public boolean isAI() {
        return true;
    }
    
    private boolean coordValid(int i, int j)
    {
        return !(i < 0 || j<0 || this.game.getGrid().getHeight()-1 < j || this.game.getGrid().getWidth()-1 < i);
    }
    
    protected void doCoord(int i,int j, Coordinate[] tabCoord){
        Coordinate c1 = setInvalid;
        Coordinate c2 = setInvalid;
        Coordinate c3 = setInvalid;
        Coordinate c4 = setInvalid;
        Coordinate c5 = setInvalid;
        Coordinate c6 = setInvalid;
        Coordinate c7 = setInvalid;
        Coordinate c8 = setInvalid;
        
        /**
             * 1 2 3
             * 4 0 5
             * 6 7 8
             */
        if (coordValid(j-1,i-1)){
            c1 = coord[j-1][i-1];
        }
        if (coordValid(j,i-1)){
            c2 = coord[j][i-1];
        }
        if (coordValid(j+1,i-1)){
            c3 = coord[j+1][i-1];
        }
        if (coordValid(j-1,i)){
            c4 = coord[j-1][i];
        }
        if (coordValid(j+1,i)){
            c5 = coord[j+1][i];
        }
        if (coordValid(j-1,i+1)){
            c6 = coord[j-1][i+1];
        }
        if (coordValid(j,i+1)){
            c7 = coord[j][i+1];
        }
        if (coordValid(j+1,i+1)){
            c8 = coord[j+1][i+1];
        }
        tabCoord[0] = c1;
        tabCoord[1] = c2;
        tabCoord[2] = c3;
        tabCoord[3] = c4;
        tabCoord[4] = c5;
        tabCoord[5] = c6;
        tabCoord[6] = c7;
        tabCoord[7] = c8;
    }
}
