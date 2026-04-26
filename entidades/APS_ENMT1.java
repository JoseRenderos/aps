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
public class APS_ENMT1 {
    public int idAPS_ENMT1;
    public String itemCode;
    public String itemName;
    public String bodega;
    public String stock;
    public String cantidad;
    public APS_ENMT APS_ENMT;

    public APS_ENMT1() {
    }

    public APS_ENMT1(int idAPS_ENMT1, String itemCode, String itemName, String bodega, String stock, String cantidad, APS_ENMT APS_ENMT) {
        this.idAPS_ENMT1 = idAPS_ENMT1;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.bodega = bodega;
        this.stock = stock;
        this.cantidad = cantidad;
        this.APS_ENMT = APS_ENMT;
    }

    public int getIdAPS_ENMT1() {
        return idAPS_ENMT1;
    }

    public void setIdAPS_ENMT1(int idAPS_ENMT1) {
        this.idAPS_ENMT1 = idAPS_ENMT1;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public APS_ENMT getAPS_ENMT() {
        return APS_ENMT;
    }

    public void setAPS_ENMT(APS_ENMT APS_ENMT) {
        this.APS_ENMT = APS_ENMT;
    }

        
}
