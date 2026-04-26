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
public class NART {
    private String nivel;
    private String itemcode;
    private String itemName;
    private String cantidad;
    private String material;
    private String materialName;
    private String materialTipo;
    private String numinbuy;
    private String peso;
    private String cant_material;
    private String cant_requerimiento;
    private String costo_calculado;
    private String cecos;
    private String itemCode_padre;

    public NART() {
    }

    public NART(String nivel, String itemcode, String itemName, String cantidad, String material, String materialName, String materialTipo, String numinbuy, String peso, String cant_material, String cant_requerimiento, String costo_calculado, String cecos, String itemCode_padre) {
        this.nivel = nivel;
        this.itemcode = itemcode;
        this.itemName = itemName;
        this.cantidad = cantidad;
        this.material = material;
        this.materialName = materialName;
        this.materialTipo = materialTipo;
        this.numinbuy = numinbuy;
        this.peso = peso;
        this.cant_material = cant_material;
        this.cant_requerimiento = cant_requerimiento;
        this.costo_calculado = costo_calculado;
        this.cecos = cecos;
        this.itemCode_padre = itemCode_padre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialTipo() {
        return materialTipo;
    }

    public void setMaterialTipo(String materialTipo) {
        this.materialTipo = materialTipo;
    }

    public String getNuminbuy() {
        return numinbuy;
    }

    public void setNuminbuy(String numinbuy) {
        this.numinbuy = numinbuy;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCant_material() {
        return cant_material;
    }

    public void setCant_material(String cant_material) {
        this.cant_material = cant_material;
    }

    public String getCant_requerimiento() {
        return cant_requerimiento;
    }

    public void setCant_requerimiento(String cant_requerimiento) {
        this.cant_requerimiento = cant_requerimiento;
    }

    public String getCosto_calculado() {
        return costo_calculado;
    }

    public void setCosto_calculado(String costo_calculado) {
        this.costo_calculado = costo_calculado;
    }

    public String getCecos() {
        return cecos;
    }

    public void setCecos(String cecos) {
        this.cecos = cecos;
    }

    public String getItemCode_padre() {
        return itemCode_padre;
    }

    public void setItemCode_padre(String itemCode_padre) {
        this.itemCode_padre = itemCode_padre;
    }
    
}
