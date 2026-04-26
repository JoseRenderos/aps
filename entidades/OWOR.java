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
public class OWOR {
    private int docnum;
    private String startdate;
    private String duedate;
    private String itemcode;
    private String itemname;
    private String status;
    private String type;
    private String cantpln;
    private String cantcmp;
    private String cantrjc;
    private int estadoTiempos;
    private int estadoMateriales;
    private int estadoProduccion;
    private String comments;
    private String cantProcesos;
    private String nom_cecos;
    private String cantCmpAPP;

    public OWOR() {
    }

    public OWOR(int docnum) {
        this.docnum = docnum;
    }

    public OWOR(int docnum, String startdate, String duedate, String itemcode, String itemname, String status, String type, String cantpln, String cantcmp, String cantrjc) {
        this.docnum = docnum;
        this.startdate = startdate;
        this.duedate = duedate;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.status = status;
        this.type = type;
        this.cantpln = cantpln;
        this.cantcmp = cantcmp;
        this.cantrjc = cantrjc;
    }

    public OWOR(int docnum, String startdate, String itemcode, String itemname, String status, String cantpln) {
        this.docnum = docnum;
        this.startdate = startdate;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.status = status;
        this.cantpln = cantpln;
    }

    public int getDocnum() {
        return docnum;
    }

    public void setDocnum(int docnum) {
        this.docnum = docnum;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getEstadoTiempos() {
        return estadoTiempos;
    }

    public void setEstadoTiempos(int estadoTiempos) {
        this.estadoTiempos = estadoTiempos;
    }

    public int getEstadoMateriales() {
        return estadoMateriales;
    }

    public void setEstadoMateriales(int estadoMateriales) {
        this.estadoMateriales = estadoMateriales;
    }

    public int getEstadoProduccion() {
        return estadoProduccion;
    }

    public void setEstadoProduccion(int estadoProduccion) {
        this.estadoProduccion = estadoProduccion;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCantProcesos() {
        return cantProcesos;
    }

    public void setCantProcesos(String cantProcesos) {
        this.cantProcesos = cantProcesos;
    }

    public String getNom_cecos() {
        return nom_cecos;
    }

    public void setNom_cecos(String nom_cecos) {
        this.nom_cecos = nom_cecos;
    }

    public String getCantCmpAPP() {
        return cantCmpAPP;
    }

    public void setCantCmpAPP(String cantCmpAPP) {
        this.cantCmpAPP = cantCmpAPP;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + docnum; 
        result = prime * result + ((startdate == null) ? 0 : startdate.hashCode());
        result = prime * result + ((duedate == null) ? 0 : duedate.hashCode());
        result = prime * result + ((itemcode == null) ? 0 : itemcode.hashCode());
        result = prime * result + ((itemname == null) ? 0 : itemname.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((cantpln == null) ? 0 : cantpln.hashCode());
        result = prime * result + ((cantcmp == null) ? 0 : cantcmp.hashCode());
        result = prime * result + ((cantrjc == null) ? 0 : cantrjc.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
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
        OWOR other = (OWOR) obj;
        if (docnum != other.docnum)
            return false;
        if (startdate == null) {
            if (other.startdate != null)
                return false;
        } else if (!startdate.equals(other.startdate))
            return false;
        if (duedate == null) {
            if (other.duedate != null)
                return false;
        } else if (!duedate.equals(other.duedate))
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
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
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
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        return true;
    }
}
