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
public class APS_OIGE2 {
    private int idAPS_OIGE2;
    private String inicio;
    private String fin;
    private APS_OIGE APS_OIGE;

    public APS_OIGE2() {
    }

    public APS_OIGE2(int idAPS_OIGE2, String inicio, String fin, APS_OIGE APS_OIGE) {
        this.idAPS_OIGE2 = idAPS_OIGE2;
        this.inicio = inicio;
        this.fin = fin;
        this.APS_OIGE = APS_OIGE;
    }

    public APS_OIGE2(String inicio, String fin, APS_OIGE APS_OIGE) {
        this.inicio = inicio;
        this.fin = fin;
        this.APS_OIGE = APS_OIGE;
    }

    public APS_OIGE2(int idAPS_OIGE2) {
        this.idAPS_OIGE2 = idAPS_OIGE2;
    }

    public int getIdAPS_OIGE2() {
        return idAPS_OIGE2;
    }

    public void setIdAPS_OIGE2(int idAPS_OIGE2) {
        this.idAPS_OIGE2 = idAPS_OIGE2;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public APS_OIGE getAPS_OIGE() {
        return APS_OIGE;
    }

    public void setAPS_OIGE(APS_OIGE APS_OIGE) {
        this.APS_OIGE = APS_OIGE;
    }

    
}
