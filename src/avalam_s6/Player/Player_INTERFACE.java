/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Player;

import avalam_s6.Core.Move;
import avalam_s6.Core.Owner;

/**
 *
 * @author Team 7
 */
public interface Player_INTERFACE {

    /**
     * Player play on a coordinate /!\ DON'T ASK A BOT FOR A PLAY THERE IS NO
     * POSSIBLE PLAY LEFT /!\
     *
     * @return the coordinate chose
     */
    public Move play();

    /**
     * Tells if the player is a bot.
     *
     * @return true if the player is an AI, false otherwise.
     */
    public boolean isAI();

    /**
     * getter
     * @return name of the player
     */
    public String getName();
    
    /**
     * getter
     * @return owner
     */
    public Owner getOwner();
}
