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
public class ObjGen {
    private int idComentCosto;
    private String comentario;

    public ObjGen() {
    }

    public ObjGen(int idComentCosto, String comentario) {
        this.idComentCosto = idComentCosto;
        this.comentario = comentario;
    }

    public int getIdComentCosto() {
        return idComentCosto;
    }

    public void setIdComentCosto(int idComentCosto) {
        this.idComentCosto = idComentCosto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
}
