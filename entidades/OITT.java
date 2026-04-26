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
public class OITT {
    private String father;
    private String code;
    private String quantity;
    private String name;

    public OITT() {
    }

    public OITT(String father, String code) {
        this.father = father;
        this.code = code;
    }

    public OITT(String father, String code, String quantity) {
        this.father = father;
        this.code = code;
        this.quantity = quantity;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
