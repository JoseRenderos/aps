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
public class Area {
    private int idArea;
    private int codigo;
    private String nombre;

    public Area() {
    }

    public Area(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Area(int idArea, int codigo, String nombre) {
        this.idArea = idArea;
        this.codigo = codigo;
        this.nombre = nombre;
    } 

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
