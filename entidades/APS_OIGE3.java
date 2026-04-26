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
public class APS_OIGE3 {
    public int idAPS_OIGE3;
    public String itemCode;
    public String itemName;
    public String CantAsignada;
    public APS_OIGE APS_OIGE;
    public String stock;
    public String bodega;

    public APS_OIGE3() {
    }

    public APS_OIGE3(int idAPS_OIGE3, String itemCode, String itemName, String CantAsignada, APS_OIGE APS_OIGE) {
        this.idAPS_OIGE3 = idAPS_OIGE3;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.CantAsignada = CantAsignada;
        this.APS_OIGE = APS_OIGE;
    }

    public APS_OIGE3(String itemCode, String itemName, String CantAsignada, APS_OIGE APS_OIGE) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.CantAsignada = CantAsignada;
        this.APS_OIGE = APS_OIGE;
    }

    public int getIdAPS_OIGE3() {
        return idAPS_OIGE3;
    }

    public void setIdAPS_OIGE3(int idAPS_OIGE3) {
        this.idAPS_OIGE3 = idAPS_OIGE3;
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

    public String getCantAsignada() {
        return CantAsignada;
    }

    public void setCantAsignada(String CantAsignada) {
        this.CantAsignada = CantAsignada;
    }

    public APS_OIGE getAPS_OIGE() {
        return APS_OIGE;
    }

    public void setAPS_OIGE(APS_OIGE APS_OIGE) {
        this.APS_OIGE = APS_OIGE;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }
    
}
