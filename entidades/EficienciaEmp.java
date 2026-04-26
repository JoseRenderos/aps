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
public class EficienciaEmp {
    private String nomEmp;
    private String docNum;
    private String itemCode;
    private String itemName;
    private String actividad;
    private String inicio;
    private String fin;
    private String descActividad;
    private String totalTiempo;
    private String uniTotalesConformes;
    private String estandarH;
    private String ritmoReal;
    private String eficiencia;
    private String porcentajeHoras;
    private String eficienciaPonderada;

    public EficienciaEmp() {
    }

    public EficienciaEmp(String nomEmp, String docNum, String itemCode, String itemName, String actividad, String inicio, String fin, String descActividad, String totalTiempo, String uniTotalesConformes, String estandarH, String ritmoReal, String eficiencia, String porcentajeHoras, String eficienciaPonderada) {
        this.nomEmp = nomEmp;
        this.docNum = docNum;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.actividad = actividad;
        this.inicio = inicio;
        this.fin = fin;
        this.descActividad = descActividad;
        this.totalTiempo = totalTiempo;
        this.uniTotalesConformes = uniTotalesConformes;
        this.estandarH = estandarH;
        this.ritmoReal = ritmoReal;
        this.eficiencia = eficiencia;
        this.porcentajeHoras = porcentajeHoras;
        this.eficienciaPonderada = eficienciaPonderada;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
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

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
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

    public String getDescActividad() {
        return descActividad;
    }

    public void setDescActividad(String descActividad) {
        this.descActividad = descActividad;
    }

    public String getTotalTiempo() {
        return totalTiempo;
    }

    public void setTotalTiempo(String totalTiempo) {
        this.totalTiempo = totalTiempo;
    }

    public String getUniTotalesConformes() {
        return uniTotalesConformes;
    }

    public void setUniTotalesConformes(String uniTotalesConformes) {
        this.uniTotalesConformes = uniTotalesConformes;
    }

    public String getEstandarH() {
        return estandarH;
    }

    public void setEstandarH(String estandarH) {
        this.estandarH = estandarH;
    }

    public String getRitmoReal() {
        return ritmoReal;
    }

    public void setRitmoReal(String ritmoReal) {
        this.ritmoReal = ritmoReal;
    }

    public String getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(String eficiencia) {
        this.eficiencia = eficiencia;
    }

    public String getPorcentajeHoras() {
        return porcentajeHoras;
    }

    public void setPorcentajeHoras(String porcentajeHoras) {
        this.porcentajeHoras = porcentajeHoras;
    }

    public String getEficienciaPonderada() {
        return eficienciaPonderada;
    }

    public void setEficienciaPonderada(String eficienciaPonderada) {
        this.eficienciaPonderada = eficienciaPonderada;
    }
    
    
}
