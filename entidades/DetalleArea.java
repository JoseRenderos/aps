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
public class DetalleArea {
    private int idDetalleArea;
    private Usuario usuario;
    private Area area;

    public DetalleArea() {
    }

    public DetalleArea(Usuario usuario, Area area) {
        this.usuario = usuario;
        this.area = area;
    }

    public DetalleArea(int idDetalleArea, Usuario usuario, Area area) {
        this.idDetalleArea = idDetalleArea;
        this.usuario = usuario;
        this.area = area;
    }

    public int getIdDetalleArea() {
        return idDetalleArea;
    }

    public void setIdDetalleArea(int idDetalleArea) {
        this.idDetalleArea = idDetalleArea;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    
}
