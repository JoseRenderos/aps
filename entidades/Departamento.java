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
public class Departamento {
    private String codeDepto;
    private String depto;

    public Departamento() {
    }

    public Departamento(String codeDepto, String depto) {
        this.codeDepto = codeDepto;
        this.depto = depto;
    }

    public String getCodeDepto() {
        return codeDepto;
    }

    public void setCodeDepto(String codeDepto) {
        this.codeDepto = codeDepto;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }
    
    
}
