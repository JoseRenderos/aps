/*
 * To change this license header choose License Headers in Project Properties.
 * To change this template file choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Mario Valdez
 */
public class APS_ENMT {
    private int idAPS_ENMT;
    private String DocNum;
    private String DocDate;
    private int idUsuario;
    private String Usuario;
    private OWOR OWOR;
    private String estado;

    public APS_ENMT() {
    }

    public APS_ENMT(int idAPS_ENMT, String DocNum, String DocDate, int idUsuario, OWOR OWOR, String estado) {
        this.idAPS_ENMT = idAPS_ENMT;
        this.DocNum = DocNum;
        this.DocDate = DocDate;
        this.idUsuario = idUsuario;
        this.OWOR = OWOR;
        this.estado = estado;
    }

    public int getIdAPS_ENMT() {
        return idAPS_ENMT;
    }

    public void setIdAPS_ENMT(int idAPS_ENMT) {
        this.idAPS_ENMT = idAPS_ENMT;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String DocNum) {
        this.DocNum = DocNum;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public OWOR getOWOR() {
        return OWOR;
    }

    public void setOWOR(OWOR OWOR) {
        this.OWOR = OWOR;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }
    
}
