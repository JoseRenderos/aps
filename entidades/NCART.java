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
public class NCART {
    private String itemcode;
    private String itemName;
    private String costo_calculado;

    public NCART() {
    }

    public NCART(String itemcode, String itemName, String costo_calculado) {
        this.itemcode = itemcode;
        this.itemName = itemName;
        this.costo_calculado = costo_calculado;
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

    public String getCosto_calculado() {
        return costo_calculado;
    }

    public void setCosto_calculado(String costo_calculado) {
        this.costo_calculado = costo_calculado;
    }
    
}
