/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Player;

import avalam_s6.Core.*;
import avalam_s6.Core.Globals.AvalamColor;
import java.util.ArrayList;
import java.util.Random;

/**
 * easy AI
 * @author Team 7
 */
public class AIPlayerEasy extends AIPlayer {

    private final static int NERF = 60;

    public AIPlayerEasy(String name, AvalamColor color, Owner owner) {
        super(name, color, owner);
    }

    /**
     * Prioritize move with high/ok value ignore move with bad value unless it's
     * impossible to not do so
     *
     * @return the move choosen by the algorithm
     */
    @Override
    public Move play() {
        //System.out.println("Je suis " + this.name + " je vais jouer des coups faciles");
        ArrayList<Move> mesCoups = new ArrayList<>();
        ArrayList<Move> mesCoupsHighValue = new ArrayList<>();
        ArrayList<Move> mesCoupsOkValue = new ArrayList<>();
        ArrayList<Move> mesCoupsMehValue = new ArrayList<>();
        ArrayList<Move> mesCoupsBadValue = new ArrayList<>();
        Coordinate[] tabCoord = new Coordinate[8];
        coord = new Coordinate[this.game.getGrid().getHeight()][this.game.getGrid().getWidth()];
        for (int i = 0; i < this.game.getGrid().getWidth(); i++) {
            for (int j = 0; j < this.game.getGrid().getHeight(); j++) {
                coord[j][i] = new Coordinate(j, i);
            }
        }
        for (int i = 0; i < this.game.getGrid().getWidth(); i++) {
            /**
             * 1 2 3
             * 4 0 5
             * 6 7 8
             */
            for (int j = 0; j < this.game.getGrid().getHeight(); j++) {
                Coordinate c0 = new Coordinate(j, i);
                doCoord(i, j, tabCoord);
                if (c0.isValid() && this.game.getGrid().getCellAt(c0).getState().getValue() == CellState.TOWER.getValue()) {

                    for (int k = 0; k < 8; k++) {
                        if (tabCoord[k].isValid() && this.game.getGrid().getCellAt(tabCoord[k]).getState().getValue() == CellState.TOWER.getValue()) {
                            if (this.game.getGrid().canStack(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k]))) {
                                Move m = new Move(c0, this.game.getGrid().getCellAt(c0).getSize(), tabCoord[k], this.game.getGrid().getCellAt(tabCoord[k]).getSize(), this);
                                //we gain a point for free, great !
                                if (completeTourUsVsOp(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k])) || createAloneUs(c0, tabCoord[k])) {
                                    mesCoupsHighValue.add(m);
                                } //we can secure a point
                                else if (completeTourUs(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k]))) {
                                    mesCoupsOkValue.add(m);
                                } //reduce the numbers of pawn
                                else if (suppressAPawn(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k]))) {
                                    mesCoupsMehValue.add(m);
                                } //whatever 
                                else if (completeTourOp(this.game.getGrid().getCellAt(c0), this.game.getGrid().getCellAt(tabCoord[k])) || createAloneOp(c0, tabCoord[k])) {
                                    mesCoupsBadValue.add(m);
                                } else {
                                    mesCoups.add(m);
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(this.game.getGrid().toString());
        Random r = new Random();

        //select a move, from best to worst
        if (!mesCoupsHighValue.isEmpty()) {
            if (r.nextInt(100) < this.NERF) {
                return mesCoupsHighValue.get(r.nextInt(mesCoupsHighValue.size()));
            }
        }
        if (!mesCoupsOkValue.isEmpty()) {
            if (r.nextInt(100) < this.NERF) {
                return mesCoupsOkValue.get(r.nextInt(mesCoupsOkValue.size()));
            }
        }
        if (!mesCoupsMehValue.isEmpty()) {
            if (r.nextInt(100) < this.NERF) {
                return mesCoupsMehValue.get(r.nextInt(mesCoupsMehValue.size()));
            }
        }
        if (!mesCoups.isEmpty()) {
            if (r.nextInt(100) < this.NERF) {
                return mesCoups.get(r.nextInt(mesCoups.size()));
            }
        }
        if (!mesCoupsBadValue.isEmpty()) {
            return mesCoupsBadValue.get(r.nextInt(mesCoupsBadValue.size()));
        }

        //just in case, redo it the "classic" way if we missed the play
        if (!mesCoupsHighValue.isEmpty()) {
            return mesCoupsHighValue.get(r.nextInt(mesCoupsHighValue.size()));
        }
        if (!mesCoupsOkValue.isEmpty()) {
            return mesCoupsOkValue.get(r.nextInt(mesCoupsOkValue.size()));
        }
        if (!mesCoupsMehValue.isEmpty()) {
            return mesCoupsMehValue.get(r.nextInt(mesCoupsMehValue.size()));
        }
        if (!mesCoups.isEmpty()) {
            return mesCoups.get(r.nextInt(mesCoups.size()));
        }
        if (!mesCoupsBadValue.isEmpty()) {
            return mesCoupsBadValue.get(r.nextInt(mesCoupsBadValue.size()));
        }
        return null;
    }

}
