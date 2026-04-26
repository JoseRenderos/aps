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
public class RTAV {
    private int id_RTAV;
    private int codeEmp;
    private String nomEmp;
    private String inicio;
    private String fin;
    private String tiempo;
    private Actividades actividades;

    public RTAV() {
    }

    public RTAV(int id_RTAV, int codeEmp, String nomEmp, String inicio, String fin, String tiempo, Actividades actividades) {
        this.id_RTAV = id_RTAV;
        this.codeEmp = codeEmp;
        this.nomEmp = nomEmp;
        this.inicio = inicio;
        this.fin = fin;
        this.tiempo = tiempo;
        this.actividades = actividades;
    }

    public int getId_RTAV() {
        return id_RTAV;
    }

    public void setId_RTAV(int id_RTAV) {
        this.id_RTAV = id_RTAV;
    }

    public int getCodeEmp() {
        return codeEmp;
    }

    public void setCodeEmp(int codeEmp) {
        this.codeEmp = codeEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Actividades getActividades() {
        return actividades;
    }

    public void setActividades(Actividades actividades) {
        this.actividades = actividades;
    }
    
}
