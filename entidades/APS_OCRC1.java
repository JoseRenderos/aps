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
public class APS_OCRC1 {
    private int idAPS_OCRC1;
    private Usuario usuario;
    private APS_OCRC APS_OCRC;

    public APS_OCRC1() {
    }

    public APS_OCRC1(Usuario usuario, APS_OCRC APS_OCRC) {
        this.usuario = usuario;
        this.APS_OCRC = APS_OCRC;
    }

    public APS_OCRC1(int idAPS_OCRC1, Usuario usuario, APS_OCRC APS_OCRC) {
        this.idAPS_OCRC1 = idAPS_OCRC1;
        this.usuario = usuario;
        this.APS_OCRC = APS_OCRC;
    }

    public int getIdAPS_OCRC1() {
        return idAPS_OCRC1;
    }

    public void setIdAPS_OCRC1(int idAPS_OCRC1) {
        this.idAPS_OCRC1 = idAPS_OCRC1;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public APS_OCRC getAPS_OCRC() {
        return APS_OCRC;
    }

    public void setAPS_OCRC(APS_OCRC APS_OCRC) {
        this.APS_OCRC = APS_OCRC;
    }
    
    
}
