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
public class APS_WTR1 {
    private int idAPS_WTR1;
    private String ItemCode;
    private String ItemName;
    private String FromWhsCod;
    private String stockOrigen;
    private String WhsCode;
    private String stockDestino;
    private String Quantity;
    private String OrdenProduccion;
    private String costo;
    private String totalLinea;
    private int idAPS_OWTR;

    public APS_WTR1() {
    }

    public APS_WTR1(int idAPS_WTR1, String ItemCode, String ItemName, String FromWhsCod, String stockOrigen, String WhsCode, String stockDestino, String Quantity, String OrdenProduccion, int idAPS_OWTR) {
        this.idAPS_WTR1 = idAPS_WTR1;
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.FromWhsCod = FromWhsCod;
        this.stockOrigen = stockOrigen;
        this.WhsCode = WhsCode;
        this.stockDestino = stockDestino;
        this.Quantity = Quantity;
        this.OrdenProduccion = OrdenProduccion;
        this.idAPS_OWTR = idAPS_OWTR;
    }


    public int getIdAPS_WTR1() {
        return idAPS_WTR1;
    }

    public void setIdAPS_WTR1(int idAPS_WTR1) {
        this.idAPS_WTR1 = idAPS_WTR1;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public String getFromWhsCod() {
        return FromWhsCod;
    }

    public void setFromWhsCod(String FromWhsCod) {
        this.FromWhsCod = FromWhsCod;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String WhsCode) {
        this.WhsCode = WhsCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getOrdenProduccion() {
        return OrdenProduccion;
    }

    public void setOrdenProduccion(String OrdenProduccion) {
        this.OrdenProduccion = OrdenProduccion;
    }

    public int getIdAPS_OWTR() {
        return idAPS_OWTR;
    }

    public void setIdAPS_OWTR(int idAPS_OWTR) {
        this.idAPS_OWTR = idAPS_OWTR;
    }

    public String getStockOrigen() {
        return stockOrigen;
    }

    public void setStockOrigen(String stockOrigen) {
        this.stockOrigen = stockOrigen;
    }

    public String getStockDestino() {
        return stockDestino;
    }

    public void setStockDestino(String stockDestino) {
        this.stockDestino = stockDestino;
    }
 
    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(String totalLinea) {
        this.totalLinea = totalLinea;
    }

    
}
