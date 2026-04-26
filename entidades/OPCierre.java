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
public class OPCierre {
    private String docNum;
    private String fechaInicio;
    private String fechaCierre;
    private String codArticulo;
    private String nomArticulo;
    private String cantPln;
    private String cantCmp;
    private String cantRjc;
    private String actividad;
    private String descActividad;
    private String horasCosumidas;
    private String horasEstandar;

    public OPCierre() {
    }

    public OPCierre(String docNum, String fechaInicio, String fechaCierre, String codArticulo, String nomArticulo, String cantPln, String cantCmp, String cantRjc, String actividad, String descActividad, String horasCosumidas, String horasEstandar) {
        this.docNum = docNum;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.codArticulo = codArticulo;
        this.nomArticulo = nomArticulo;
        this.cantPln = cantPln;
        this.cantCmp = cantCmp;
        this.cantRjc = cantRjc;
        this.actividad = actividad;
        this.descActividad = descActividad;
        this.horasCosumidas = horasCosumidas;
        this.horasEstandar = horasEstandar;
    }
    
    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getNomArticulo() {
        return nomArticulo;
    }

    public void setNomArticulo(String nomArticulo) {
        this.nomArticulo = nomArticulo;
    }

    public String getCantPln() {
        return cantPln;
    }

    public void setCantPln(String cantPln) {
        this.cantPln = cantPln;
    }

    public String getCantCmp() {
        return cantCmp;
    }

    public void setCantCmp(String cantCmp) {
        this.cantCmp = cantCmp;
    }

    public String getCantRjc() {
        return cantRjc;
    }

    public void setCantRjc(String cantRjc) {
        this.cantRjc = cantRjc;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getHorasCosumidas() {
        return horasCosumidas;
    }

    public void setHorasCosumidas(String horasCosumidas) {
        this.horasCosumidas = horasCosumidas;
    }

    public String getHorasEstandar() {
        return horasEstandar;
    }

    public void setHorasEstandar(String horasEstandar) {
        this.horasEstandar = horasEstandar;
    }

    public String getDescActividad() {
        return descActividad;
    }

    public void setDescActividad(String descActividad) {
        this.descActividad = descActividad;
    }
    
    
}
