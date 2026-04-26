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
public class Empleado_pago {
    private String codEmp;
    private String dia;
    private String semana;
    private String fecha;
    private String valor;
    private String sueldoDia;
    private String bono;
    private String codDepto;
    
    public Empleado_pago() {
    }

    public Empleado_pago(String codEmp, String dia, String semana, String fecha, String valor, String sueldoDia, String bono) {
        this.codEmp = codEmp;
        this.dia = dia;
        this.semana = semana;
        this.fecha = fecha;
        this.valor = valor;
        this.sueldoDia = sueldoDia;
        this.bono = bono;
    }

    public String getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSueldoDia() {
        return sueldoDia;
    }

    public void setSueldoDia(String sueldoDia) {
        this.sueldoDia = sueldoDia;
    }

    public String getBono() {
        return bono;
    }

    public void setBono(String bono) {
        this.bono = bono;
    }

    public String getCodDepto() {
        return codDepto;
    }

    public void setCodDepto(String codDepto) {
        this.codDepto = codDepto;
    }
    
}