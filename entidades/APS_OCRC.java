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
public class APS_OCRC {
    private int idAPS_OCRC;
    private int codigo;
    private String nombre;

    public APS_OCRC() {
    }

    public APS_OCRC(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public APS_OCRC(int idAPS_OCRC, int codigo, String nombre) {
        this.idAPS_OCRC = idAPS_OCRC;
        this.codigo = codigo;
        this.nombre = nombre;
    } 

    public int getIdAPS_OCRC() {
        return idAPS_OCRC;
    }

    public void setIdAPS_OCRC(int idAPS_OCRC) {
        this.idAPS_OCRC = idAPS_OCRC;
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
