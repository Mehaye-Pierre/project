/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Player;

import avalam_s6.Core.Coordinate;
import avalam_s6.Core.Input;
import avalam_s6.Core.Move;
import avalam_s6.Core.Owner;
import java.awt.Color;

/**
 *
 * @author TheDoctor
 */
public class ControlledPlayer extends Player{

    public ControlledPlayer(String n, Color c, Owner owner) {
        super(n,c,owner);
    }
    
    @Override
    public boolean isAI() {
        return false;
    }

    @Override
    public Move play() {
        if(Input.hasClicked()){
            Move m;
            Coordinate src = Input.getMouseSrcPosition();
            Coordinate dest = Input.getMouseDestPosition();
            int srcSize = this.game.getGrid().getCellAt(src).getSize();
            int destSize = this.game.getGrid().getCellAt(dest).getSize();
            m = new Move(src,srcSize, dest, destSize,this);
            Input.updateMouseDestPosition(new Coordinate());
            Input.updateMouseSrcPosition(new Coordinate());
            if(m.isValid(this.game.getGrid()));
            return m;
        } else{
            return null;
        }
    }

}
