/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avalam_s6.Core.File_IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * List informations contained into a SaveFile (for Load or Save slots).
 * This class is only called onto basic slots (1-5).
 * This enables to GUI_Save and GUI_Load to show informations about the slot content.
 * @author Team 7
 */
public class SaveInfoLister {

    private final String aFilePath;
    private String aDate, aJ1, aJ2, aGrid;
    private Boolean emptyslot;

    /**
     * Constructor
     * @param s Name of the Save File
     * @throws IOException File not Found
     */
    public SaveInfoLister(String s) throws IOException {
        this.aFilePath = "./ressources/Saves/" + s;
        this.emptyslot = false;
        this.load();
    }

    /**
     * Load SaveFile informations into local attributes.
     * @throws IOException File Not Found
     */
    private void load() throws IOException {
        File f = new File(this.aFilePath);
        if (f.length() != 0) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(aFilePath));
                this.emptyslot = false;
                this.aDate = br.readLine();
                this.aJ1 = br.readLine();
                this.aJ2 = br.readLine();
                br.readLine(); // Current Player
                br.readLine(); // NB Turns
                this.aGrid = br.readLine();
                br.close();
                this.cleanStrings();
            } catch (IOException ex) {
                Logger.getLogger(SaveInfoLister.class.getName()).log(Level.WARNING, null, ex);
                throw (ex);
            }
        } else {
            this.emptyslot = true;
        }
    }

    /**
     * Update local attributes to clean the user_friendly characters.
     * Example : [GName] default --(Becomes)-> default
     */
    private void cleanStrings() {
        this.aDate = this.aDate.substring(this.aDate.lastIndexOf("[Date] ") + 7);
        this.aDate = this.aDate.replaceAll("(\r\n|\n)", "");
        this.aJ1 = this.aJ1.substring(this.aJ1.lastIndexOf("|") + 2);
        this.aJ1 = this.aJ1.replaceAll("(\r\n|\n)", "");
        this.aJ2 = this.aJ2.substring(this.aJ2.lastIndexOf("|") + 2);
        this.aJ2 = this.aJ2.replaceAll("(\r\n|\n)", "");
        this.aGrid = this.aGrid.substring(this.aGrid.lastIndexOf("[GName] ") + 8);
        this.aGrid = this.aGrid.replaceAll("(\r\n|\n)", "");
    }

    /**
     * Getter
     * @return The Date when the save have been saved
     */
    public String getDate() {
        return aDate;
    }

    /**
     * Getter
     * @return The First Player
     */
    public String getPlayer1() {
        return aJ1;
    }

    /**
     * Getter
     * @return The Second Player
     */
    public String getPlayer2() {
        return aJ2;
    }

    /**
     * Getter
     * @return The Grid Name
     */
    public String getGrid() {
        return aGrid;
    }

    /**
     * Getter
     * @return True if the SaveFile is empty 
     */
    public Boolean getEmptyslot() {
        return emptyslot;
    }

}
