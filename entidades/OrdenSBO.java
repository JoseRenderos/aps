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
public class OrdenSBO {
    private int numerodeop;
    private String fechadeop;
    private String fechavencdeop;
    private String itemcode;
    private String itemname;
    private String status;
    private String tipo;
    private String cantpln;
    private String cantcmp;
    private String cantrjc;

    public OrdenSBO() {
    }

    public OrdenSBO(int numerodeop, String fechadeop, String fechavencdeop, String itemcode, String itemname, String status, String tipo, String cantpln, String cantcmp, String cantrjc) {
        this.numerodeop = numerodeop;
        this.fechadeop = fechadeop;
        this.fechavencdeop = fechavencdeop;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.status = status;
        this.tipo = tipo;
        this.cantpln = cantpln;
        this.cantcmp = cantcmp;
        this.cantrjc = cantrjc;
    }

    public OrdenSBO(int numerodeop, String fechadeop, String itemcode, String itemname, String status, String cantpln) {
        this.numerodeop = numerodeop;
        this.fechadeop = fechadeop;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.status = status;
        this.cantpln = cantpln;
    }

    

    
    public int getNumerodeop() {
        return numerodeop;
    }

    public void setNumerodeop(int numerodeop) {
        this.numerodeop = numerodeop;
    }

    public String getFechadeop() {
        return fechadeop;
    }

    public void setFechadeop(String fechadeop) {
        this.fechadeop = fechadeop;
    }

    public String getFechavencdeop() {
        return fechavencdeop;
    }

    public void setFechavencdeop(String fechavencdeop) {
        this.fechavencdeop = fechavencdeop;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCantpln() {
        return cantpln;
    }

    public void setCantpln(String cantpln) {
        this.cantpln = cantpln;
    }

    public String getCantcmp() {
        return cantcmp;
    }

    public void setCantcmp(String cantcmp) {
        this.cantcmp = cantcmp;
    }

    public String getCantrjc() {
        return cantrjc;
    }

    public void setCantrjc(String cantrjc) {
        this.cantrjc = cantrjc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numerodeop; 
        result = prime * result + ((fechadeop == null) ? 0 : fechadeop.hashCode());
        result = prime * result + ((fechavencdeop == null) ? 0 : fechavencdeop.hashCode());
        result = prime * result + ((itemcode == null) ? 0 : itemcode.hashCode());
        result = prime * result + ((itemname == null) ? 0 : itemname.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((cantpln == null) ? 0 : cantpln.hashCode());
        result = prime * result + ((cantcmp == null) ? 0 : cantcmp.hashCode());
        result = prime * result + ((cantrjc == null) ? 0 : cantrjc.hashCode());
        
        return result;
     }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrdenSBO other = (OrdenSBO) obj;
        if (numerodeop != other.numerodeop)
            return false;
        if (fechadeop == null) {
            if (other.fechadeop != null)
                return false;
        } else if (!fechadeop.equals(other.fechadeop))
            return false;
        if (fechavencdeop == null) {
            if (other.fechavencdeop != null)
                return false;
        } else if (!fechavencdeop.equals(other.fechavencdeop))
            return false;
        if (itemcode == null) {
            if (other.itemcode != null)
                return false;
        } else if (!itemcode.equals(other.itemcode))
            return false;
        if (itemname == null) {
            if (other.itemname != null)
                return false;
        } else if (!itemname.equals(other.itemname))
            return false;
        if (status == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (cantpln == null) {
            if (other.cantpln != null)
                return false;
        } else if (!cantpln.equals(other.cantpln))
            return false;
        if (cantcmp == null) {
            if (other.cantcmp != null)
                return false;
        } else if (!cantcmp.equals(other.cantcmp))
            return false;
        if (cantrjc == null) {
            if (other.cantrjc != null)
                return false;
        } else if (!cantrjc.equals(other.cantrjc))
            return false;
        return true;
    }
}
