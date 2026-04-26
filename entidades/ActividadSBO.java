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
public class ActividadSBO{
    private int lineNum;
    private String itemCode;
    private int ocrCode;
    private String plannedQty;
    private double IssedQty;
    private String comment;

    public ActividadSBO() {
    }

    public ActividadSBO(int lineNum, String itemCode, int ocrCode, String plannedQty, double IssedQty) {
        this.lineNum = lineNum;
        this.itemCode = itemCode;
        this.ocrCode = ocrCode;
        this.plannedQty = plannedQty;
        this.IssedQty = IssedQty;
    }

    public ActividadSBO(int lineNum, String itemCode) {
        this.lineNum = lineNum;
        this.itemCode = itemCode;
    }

    public ActividadSBO(String itemCode) {
        this.itemCode = itemCode;
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

    public int getOcrCode() {
        return ocrCode;
    }

    public void setOcrCode(int ocrCode) {
        this.ocrCode = ocrCode;
    }

    public String getPlannedQty() {
        return plannedQty;
    }

    public void setPlannedQty(String plannedQty) {
        this.plannedQty = plannedQty;
    }

    public double getIssedQty() {
        return IssedQty;
    }

    public void setIssedQty(double IssedQty) {
        this.IssedQty = IssedQty;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
