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
public class Articulo {
    private String codeArticulo;
    private String nomArticulo;
    private String stock;
    private String pedido;
    private String cbm;
    private String cantPresupuestada;
    private String cantVendida;
    private String precio;
    private String desc;
    private String cantDesc;
    private String precioDesc;
    private String noDescuento;
    private String volumen;

    public Articulo() {
    }

    public Articulo(String codeArticulo, String nomArticulo) {
        this.codeArticulo = codeArticulo;
        this.nomArticulo = nomArticulo;
    }

    public Articulo(String codeArticulo, String nomArticulo, String stock, String pedido, String cbm, String cantPresupuestada, String cantVendida, String precio, String desc, String cantDesc, String precioDesc) {
        this.codeArticulo = codeArticulo;
        this.nomArticulo = nomArticulo;
        this.stock = stock;
        this.pedido = pedido;
        this.cbm = cbm;
        this.cantPresupuestada = cantPresupuestada;
        this.cantVendida = cantVendida;
        this.precio=precio;
        this.desc=desc;
        this.cantDesc=cantDesc;
        this.precioDesc=precioDesc;
    }

    public String getCodeArticulo() {
        return codeArticulo;
    }

    public void setCodeArticulo(String codeArticulo) {
        this.codeArticulo = codeArticulo;
    }

    public String getNomArticulo() {
        return nomArticulo;
    }

    public void setNomArticulo(String nomArticulo) {
        this.nomArticulo = nomArticulo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getCbm() {
        return cbm;
    }

    public void setCbm(String cbm) {
        this.cbm = cbm;
    }

    public String getCantPresupuestada() {
        return cantPresupuestada;
    }

    public void setCantPresupuestada(String cantPresupuestada) {
        this.cantPresupuestada = cantPresupuestada;
    }

    public String getCantVendida() {
        return cantVendida;
    }

    public void setCantVendida(String cantVendida) {
        this.cantVendida = cantVendida;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCantDesc() {
        return cantDesc;
    }

    public void setCantDesc(String cantDesc) {
        this.cantDesc = cantDesc;
    }

    public String getPrecioDesc() {
        return precioDesc;
    }

    public void setPrecioDesc(String precioDesc) {
        this.precioDesc = precioDesc;
    }

    public String getNoDescuento() {
        return noDescuento;
    }

    public void setNoDescuento(String noDescuento) {
        this.noDescuento = noDescuento;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    
}