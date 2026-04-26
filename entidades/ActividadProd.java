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
public class ActividadProd {
    public String codAct;
    public String descActividad;

    public ActividadProd() {
    }

    public ActividadProd(String codAct, String descActividad) {
        this.codAct = codAct;
        this.descActividad = descActividad;
    }

    public String getCodAct() {
        return codAct;
    }

    public void setCodAct(String codAct) {
        this.codAct = codAct;
    }

    public String getDescActividad() {
        return descActividad;
    }

    public void setDescActividad(String descActividad) {
        this.descActividad = descActividad;
    }
}
