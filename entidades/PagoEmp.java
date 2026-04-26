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
public class PagoEmp {
    private String numOrden;
    private String codeEmp;
    private String itemCode;
    private String itemName;
    private String fecha;
    private String unidadesConformes;
    private String dia;
    private String metaHora;
    private String metaDia;
    private String factorMeta;
    private String factorBajoMeta;
    private String factorSobreMeta;
    private String uniMeta;
    private String uniBajoMeta;
    private String uniSobreMeta;
    private String pagoMeta;
    private String pagoBajoMeta;
    private String pagoSobreMeta;
    private String pagoDia;

    public PagoEmp() {
    }

    public PagoEmp(String numOrden, String codeEmp, String itemCode, String itemName, String fecha, String unidadesConformes, String dia, String metaHora, String metaDia, String factorMeta, String factorBajoMeta, String factorSobreMeta, String uniMeta, String uniBajoMeta, String uniSobreMeta, String pagoMeta, String pagoBajoMeta, String pagoSobreMeta, String pagoDia) {
        this.numOrden = numOrden;
        this.codeEmp = codeEmp;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.fecha = fecha;
        this.unidadesConformes = unidadesConformes;
        this.dia = dia;
        this.metaHora = metaHora;
        this.metaDia = metaDia;
        this.factorMeta = factorMeta;
        this.factorBajoMeta = factorBajoMeta;
        this.factorSobreMeta = factorSobreMeta;
        this.uniMeta = uniMeta;
        this.uniBajoMeta = uniBajoMeta;
        this.uniSobreMeta = uniSobreMeta;
        this.pagoMeta = pagoMeta;
        this.pagoBajoMeta = pagoBajoMeta;
        this.pagoSobreMeta = pagoSobreMeta;
        this.pagoDia = pagoDia;
    }

    public String getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(String numOrden) {
        this.numOrden = numOrden;
    }

    public String getCodeEmp() {
        return codeEmp;
    }

    public void setCodeEmp(String codeEmp) {
        this.codeEmp = codeEmp;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUnidadesConformes() {
        return unidadesConformes;
    }

    public void setUnidadesConformes(String unidadesConformes) {
        this.unidadesConformes = unidadesConformes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMetaHora() {
        return metaHora;
    }

    public void setMetaHora(String metaHora) {
        this.metaHora = metaHora;
    }

    public String getMetaDia() {
        return metaDia;
    }

    public void setMetaDia(String metaDia) {
        this.metaDia = metaDia;
    }

    public String getFactorMeta() {
        return factorMeta;
    }

    public void setFactorMeta(String factorMeta) {
        this.factorMeta = factorMeta;
    }

    public String getFactorBajoMeta() {
        return factorBajoMeta;
    }

    public void setFactorBajoMeta(String factorBajoMeta) {
        this.factorBajoMeta = factorBajoMeta;
    }

    public String getFactorSobreMeta() {
        return factorSobreMeta;
    }

    public void setFactorSobreMeta(String factorSobreMeta) {
        this.factorSobreMeta = factorSobreMeta;
    }

    public String getUniMeta() {
        return uniMeta;
    }

    public void setUniMeta(String uniMeta) {
        this.uniMeta = uniMeta;
    }

    public String getUniBajoMeta() {
        return uniBajoMeta;
    }

    public void setUniBajoMeta(String uniBajoMeta) {
        this.uniBajoMeta = uniBajoMeta;
    }

    public String getUniSobreMeta() {
        return uniSobreMeta;
    }

    public void setUniSobreMeta(String uniSobreMeta) {
        this.uniSobreMeta = uniSobreMeta;
    }

    public String getPagoMeta() {
        return pagoMeta;
    }

    public void setPagoMeta(String pagoMeta) {
        this.pagoMeta = pagoMeta;
    }

    public String getPagoBajoMeta() {
        return pagoBajoMeta;
    }

    public void setPagoBajoMeta(String pagoBajoMeta) {
        this.pagoBajoMeta = pagoBajoMeta;
    }

    public String getPagoSobreMeta() {
        return pagoSobreMeta;
    }

    public void setPagoSobreMeta(String pagoSobreMeta) {
        this.pagoSobreMeta = pagoSobreMeta;
    }

    public String getPagoDia() {
        return pagoDia;
    }

    public void setPagoDia(String pagoDia) {
        this.pagoDia = pagoDia;
    }
    
}
