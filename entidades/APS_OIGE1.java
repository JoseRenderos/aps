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
public class APS_OIGE1 {
    private int idAPS_OIGE1;
    private String inicio;
    private String fin;
    private int numCaptura;
    private String uniConformes;
    private String uniRechazadas;
    private String uniNoConformes;
    private String uniTotales;
    private String comentario;
    private APS_OIGE APS_OIGE;

    public APS_OIGE1() {
    }

    public APS_OIGE1(int idAPS_OIGE1, String inicio, String fin, int numCaptura, String uniConformes, String uniRechazadas, String uniNoConformes, String uniTotales, String comentario, APS_OIGE APS_OIGE) {
        this.idAPS_OIGE1= idAPS_OIGE1;
        this.inicio = inicio;
        this.fin = fin;
        this.numCaptura = numCaptura;
        this.uniConformes = uniConformes;
        this.uniRechazadas = uniRechazadas;
        this.uniNoConformes = uniNoConformes;
        this.uniTotales = uniTotales;
        this.comentario = comentario;
        this.APS_OIGE = APS_OIGE;
    }

    public APS_OIGE1(String inicio, String fin, int numCaptura, String uniConformes, String uniRechazadas, String uniNoConformes, String uniTotales, String comentario, APS_OIGE APS_OIGE) {
        this.inicio = inicio;
        this.fin = fin;
        this.numCaptura = numCaptura;
        this.uniConformes = uniConformes;
        this.uniRechazadas = uniRechazadas;
        this.uniNoConformes = uniNoConformes;
        this.uniTotales = uniTotales;
        this.comentario = comentario;
        this.APS_OIGE = APS_OIGE;
    }

    public int getIdAPS_OIGE1() {
        return idAPS_OIGE1;
    }

    public void setIdAPS_OIGE1(int idAPS_OIGE1) {
        this.idAPS_OIGE1 = idAPS_OIGE1;
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

    public int getNumCaptura() {
        return numCaptura;
    }

    public void setNumCaptura(int numCaptura) {
        this.numCaptura = numCaptura;
    }

    public String getUniConformes() {
        return uniConformes;
    }

    public void setUniConformes(String uniConformes) {
        this.uniConformes = uniConformes;
    }

    public String getUniRechazadas() {
        return uniRechazadas;
    }

    public void setUniRechazadas(String uniRechazadas) {
        this.uniRechazadas = uniRechazadas;
    }

    public String getUniNoConformes() {
        return uniNoConformes;
    }

    public void setUniNoConformes(String uniNoConformes) {
        this.uniNoConformes = uniNoConformes;
    }

    public String getUniTotales() {
        return uniTotales;
    }

    public void setUniTotales(String uniTotales) {
        this.uniTotales = uniTotales;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public APS_OIGE getAPS_OIGE() {
        return APS_OIGE;
    }

    public void setAPS_OIGE(APS_OIGE APS_OIGE) {
        this.APS_OIGE = APS_OIGE;
    }


}
