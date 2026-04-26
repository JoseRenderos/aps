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
public class CINV1 {
    private int id;
    private String itemcode;
    private String itemName;
    private String cantidadContada;
    private String stockSAP;
    private String fechaConto;
    private String comentarioConteo;
    private String usuario;
    private int idUsuarioConteo;
    private int id_CINV;
    private String estado;

    public CINV1() {
    }

    public CINV1(int id, String itemcode, String itemName, String cantidadContada, String stockSAP, String fechaConto, String comentarioConteo, String usuario, int idUsuarioConteo, int id_CINV) {
        this.id = id;
        this.itemcode = itemcode;
        this.itemName = itemName;
        this.cantidadContada = cantidadContada;
        this.stockSAP = stockSAP;
        this.fechaConto = fechaConto;
        this.comentarioConteo = comentarioConteo;
        this.usuario = usuario;
        this.idUsuarioConteo = idUsuarioConteo;
        this.id_CINV = id_CINV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCantidadContada() {
        return cantidadContada;
    }

    public void setCantidadContada(String cantidadContada) {
        this.cantidadContada = cantidadContada;
    }

    public String getStockSAP() {
        return stockSAP;
    }

    public void setStockSAP(String stockSAP) {
        this.stockSAP = stockSAP;
    }

    public String getFechaConto() {
        return fechaConto;
    }

    public void setFechaConto(String fechaConto) {
        this.fechaConto = fechaConto;
    }

    public String getComentarioConteo() {
        return comentarioConteo;
    }

    public void setComentarioConteo(String comentarioConteo) {
        this.comentarioConteo = comentarioConteo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdUsuarioConteo() {
        return idUsuarioConteo;
    }

    public void setIdUsuarioConteo(int idUsuarioConteo) {
        this.idUsuarioConteo = idUsuarioConteo;
    }

    public int getId_CINV() {
        return id_CINV;
    }

    public void setId_CINV(int id_CINV) {
        this.id_CINV = id_CINV;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
