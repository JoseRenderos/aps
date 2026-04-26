/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Desarrollo Alsasa
 */
public class InsumoSBO {
    private int lineNum;
    private String itemCode;
    private String itemName;
    private String plannedQty;
    private String IssedQty;

    public InsumoSBO() {
    }

    public InsumoSBO(int lineNum, String itemCode, String itemName, String plannedQty, String IssedQty) {
        this.lineNum = lineNum;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.plannedQty = plannedQty;
        this.IssedQty = IssedQty;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPlannedQty() {
        return plannedQty;
    }

    public void setPlannedQty(String plannedQty) {
        this.plannedQty = plannedQty;
    }

    public String getIssedQty() {
        return IssedQty;
    }

    public void setIssedQty(String IssedQty) {
        this.IssedQty = IssedQty;
    }
    
}