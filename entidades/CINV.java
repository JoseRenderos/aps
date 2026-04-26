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
public class CINV {
    private int id_CINV;
    private String docDate;
    private String comentario;
    private int idUsuario;
    private String usuario;
    private String bodega;
    private String estado;

    public CINV() {
    }

    public CINV(int id_CINV, String docDate, String comentario, int idUsuario) {
        this.id_CINV = id_CINV;
        this.docDate = docDate;
        this.comentario = comentario;
        this.idUsuario = idUsuario;
    }

    public int getId_CINV() {
        return id_CINV;
    }

    public void setId_CINV(int id_CINV) {
        this.id_CINV = id_CINV;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
