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
public class APS_IGN1 {
    private int idAPS_IGN1;
    private String itemCode;
    private int cantCompletada;
    private int cantRechazada;
    private OWOR APS_OWOR;

    public APS_IGN1() {
    }

    public APS_IGN1(int idAPS_IGN1, int cantCompletada, int cantRechazada, OWOR APS_OWOR) {
        this.idAPS_IGN1 = idAPS_IGN1;
        this.cantCompletada = cantCompletada;
        this.cantRechazada = cantRechazada;
        this.APS_OWOR = APS_OWOR;
    }

    public APS_IGN1(int cantCompletada, int cantRechazada, OWOR APS_OWOR) {
        this.cantCompletada = cantCompletada;
        this.cantRechazada = cantRechazada;
        this.APS_OWOR = APS_OWOR;
    }

    public int getIdAPS_IGN1() {
        return idAPS_IGN1;
    }

    public void setIdAPS_IGN1(int idAPS_IGN1) {
        this.idAPS_IGN1 = idAPS_IGN1;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getCantCompletada() {
        return cantCompletada;
    }

    public void setCantCompletada(int cantCompletada) {
        this.cantCompletada = cantCompletada;
    }

    public int getCantRechazada() {
        return cantRechazada;
    }

    public void setCantRechazada(int cantRechazada) {
        this.cantRechazada = cantRechazada;
    }

    public OWOR getAPS_OWOR() {
        return APS_OWOR;
    }

    public void setAPS_OWOR(OWOR APS_OWOR) {
        this.APS_OWOR = APS_OWOR;
    }

}
