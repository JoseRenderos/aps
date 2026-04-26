/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Mario Valdez
 */
public class OBIN {
    private int absEntry;
    private String binCode;

    public OBIN() {
    }

    public OBIN(int absEntry, String binCode) {
        this.absEntry = absEntry;
        this.binCode = binCode;
    }

    public int getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(int absEntry) {
        this.absEntry = absEntry;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }
    
}
