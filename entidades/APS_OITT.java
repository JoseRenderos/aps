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
public class APS_OITT {
    private String itemName;
    private String itemCode;
    private String actividad;
    private String descActividad;
    private int visOrder;
    public APS_OITT(){
    }

    public APS_OITT(String itemName, String itemCode, String actividad, String descActividad) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.actividad = actividad;
        this.descActividad = descActividad;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescActividad() {
        return descActividad;
    }

    public void setDescActividad(String descActividad) {
        this.descActividad = descActividad;
    }

    public int getVisOrder() {
        return visOrder;
    }

    public void setVisOrder(int visOrder) {
        this.visOrder = visOrder;
    }
    
}
