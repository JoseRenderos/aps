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
public class Costo_OP {
    private String docnum;
    private String itemCode_Orden;
    private String itemname_Orden;
    private String plnQty_Orden;
    private String cmptQty_Orden;
    private String rjctQty_Orden;
    private String lineNum;
    private String tipo;
    private String itemCode;
    private String itemName;
    private String plnQty;
    private String issueQty;
    private String variacion;
    private String eficiencia;
    private String costo_Pry;
    private String costo_Uni;
    private String total_Variacion_Costo;
    private String variacion_Uni_Costo;
    private String porCiento_Costo;
    private String total_Variacion_Consumo;
    private String variacion_Uni_Consumo;
    private String porCiento_Consumo;
    private String costo_Total_Pry;
    private String costo_Total_Uni;
    private String total_Variacion;
    private String variacion_Uni;
    private String porCiento_Variacion_Total;
    private String porCiento;
    private String startDate;
    private String dueDate;
    private String costo_real_insumo;
    private String costo_real_material;
    private String costo_real_recurso;
    private String tipo_lista;
    private String horas_Planificadas;
    private String horas_Presupuestadas;
    private String horas_Consumidas;
    private String total_Costo_Planificado;
    private String total_Costo_Presupuestado;
    private String total_Costo_Consumido;
    private String closeDate;
    private String estadoCarga;
    private String comentarios;
    private String comentariosProd;
    private String estadoCierre;

    public Costo_OP() {
    }

    public Costo_OP(String docnum, String itemCode_Orden, String itemname_Orden, String plnQty_Orden, String cmptQty_Orden, String rjctQty_Orden, String lineNum, String tipo, String itemCode, String itemName, String plnQty, String issueQty, String variacion, String eficiencia, String costo_Pry, String costo_Uni, String total_Variacion_Costo, String variacion_Uni_Costo, String porCiento_Costo, String total_Variacion_Consumo, String variacion_Uni_Consumo, String porCiento_Consumo, String costo_Total_Pry, String costo_Total_Uni, String total_Variacion, String variacion_Uni, String porCiento_Variacion_Total, String porCiento) {
        this.docnum = docnum;
        this.itemCode_Orden = itemCode_Orden;
        this.itemname_Orden = itemname_Orden;
        this.plnQty_Orden = plnQty_Orden;
        this.cmptQty_Orden = cmptQty_Orden;
        this.rjctQty_Orden = rjctQty_Orden;
        this.lineNum = lineNum;
        this.tipo = tipo;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.plnQty = plnQty;
        this.issueQty = issueQty;
        this.variacion = variacion;
        this.eficiencia = eficiencia;
        this.costo_Pry = costo_Pry;
        this.costo_Uni = costo_Uni;
        this.total_Variacion_Costo = total_Variacion_Costo;
        this.variacion_Uni_Costo = variacion_Uni_Costo;
        this.porCiento_Costo = porCiento_Costo;
        this.total_Variacion_Consumo = total_Variacion_Consumo;
        this.variacion_Uni_Consumo = variacion_Uni_Consumo;
        this.porCiento_Consumo = porCiento_Consumo;
        this.costo_Total_Pry = costo_Total_Pry;
        this.costo_Total_Uni = costo_Total_Uni;
        this.total_Variacion = total_Variacion;
        this.variacion_Uni = variacion_Uni;
        this.porCiento_Variacion_Total = porCiento_Variacion_Total;
        this.porCiento = porCiento;
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getItemCode_Orden() {
        return itemCode_Orden;
    }

    public void setItemCode_Orden(String itemCode_Orden) {
        this.itemCode_Orden = itemCode_Orden;
    }

    public String getItemname_Orden() {
        return itemname_Orden;
    }

    public void setItemname_Orden(String itemname_Orden) {
        this.itemname_Orden = itemname_Orden;
    }

    public String getPlnQty_Orden() {
        return plnQty_Orden;
    }

    public void setPlnQty_Orden(String plnQty_Orden) {
        this.plnQty_Orden = plnQty_Orden;
    }

    public String getCmptQty_Orden() {
        return cmptQty_Orden;
    }

    public void setCmptQty_Orden(String cmptQty_Orden) {
        this.cmptQty_Orden = cmptQty_Orden;
    }

    public String getRjctQty_Orden() {
        return rjctQty_Orden;
    }

    public void setRjctQty_Orden(String rjctQty_Orden) {
        this.rjctQty_Orden = rjctQty_Orden;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getPlnQty() {
        return plnQty;
    }

    public void setPlnQty(String plnQty) {
        this.plnQty = plnQty;
    }

    public String getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(String issueQty) {
        this.issueQty = issueQty;
    }

    public String getVariacion() {
        return variacion;
    }

    public void setVariacion(String variacion) {
        this.variacion = variacion;
    }

    public String getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(String eficiencia) {
        this.eficiencia = eficiencia;
    }

    public String getCosto_Pry() {
        return costo_Pry;
    }

    public void setCosto_Pry(String costo_Pry) {
        this.costo_Pry = costo_Pry;
    }

    public String getCosto_Uni() {
        return costo_Uni;
    }

    public void setCosto_Uni(String costo_Uni) {
        this.costo_Uni = costo_Uni;
    }

    public String getTotal_Variacion_Costo() {
        return total_Variacion_Costo;
    }

    public void setTotal_Variacion_Costo(String total_Variacion_Costo) {
        this.total_Variacion_Costo = total_Variacion_Costo;
    }

    public String getVariacion_Uni_Costo() {
        return variacion_Uni_Costo;
    }

    public void setVariacion_Uni_Costo(String variacion_Uni_Costo) {
        this.variacion_Uni_Costo = variacion_Uni_Costo;
    }

    public String getPorCiento_Costo() {
        return porCiento_Costo;
    }

    public void setPorCiento_Costo(String porCiento_Costo) {
        this.porCiento_Costo = porCiento_Costo;
    }

    public String getTotal_Variacion_Consumo() {
        return total_Variacion_Consumo;
    }

    public void setTotal_Variacion_Consumo(String total_Variacion_Consumo) {
        this.total_Variacion_Consumo = total_Variacion_Consumo;
    }

    public String getVariacion_Uni_Consumo() {
        return variacion_Uni_Consumo;
    }

    public void setVariacion_Uni_Consumo(String variacion_Uni_Consumo) {
        this.variacion_Uni_Consumo = variacion_Uni_Consumo;
    }

    public String getPorCiento_Consumo() {
        return porCiento_Consumo;
    }

    public void setPorCiento_Consumo(String porCiento_Consumo) {
        this.porCiento_Consumo = porCiento_Consumo;
    }

    public String getCosto_Total_Pry() {
        return costo_Total_Pry;
    }

    public void setCosto_Total_Pry(String costo_Total_Pry) {
        this.costo_Total_Pry = costo_Total_Pry;
    }

    public String getCosto_Total_Uni() {
        return costo_Total_Uni;
    }

    public void setCosto_Total_Uni(String costo_Total_Uni) {
        this.costo_Total_Uni = costo_Total_Uni;
    }

    public String getTotal_Variacion() {
        return total_Variacion;
    }

    public void setTotal_Variacion(String total_Variacion) {
        this.total_Variacion = total_Variacion;
    }

    public String getVariacion_Uni() {
        return variacion_Uni;
    }

    public void setVariacion_Uni(String variacion_Uni) {
        this.variacion_Uni = variacion_Uni;
    }

    public String getPorCiento_Variacion_Total() {
        return porCiento_Variacion_Total;
    }

    public void setPorCiento_Variacion_Total(String porCiento_Variacion_Total) {
        this.porCiento_Variacion_Total = porCiento_Variacion_Total;
    }

    public String getPorCiento() {
        return porCiento;
    }

    public void setPorCiento(String porCiento) {
        this.porCiento = porCiento;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCosto_real_insumo() {
        return costo_real_insumo;
    }

    public void setCosto_real_insumo(String costo_real_insumo) {
        this.costo_real_insumo = costo_real_insumo;
    }

    public String getCosto_real_material() {
        return costo_real_material;
    }

    public void setCosto_real_material(String costo_real_material) {
        this.costo_real_material = costo_real_material;
    }

    public String getCosto_real_recurso() {
        return costo_real_recurso;
    }

    public void setCosto_real_recurso(String costo_real_recurso) {
        this.costo_real_recurso = costo_real_recurso;
    }

    public String getTipo_lista() {
        return tipo_lista;
    }

    public void setTipo_lista(String tipo_lista) {
        this.tipo_lista = tipo_lista;
    }

    public String getHoras_Planificadas() {
        return horas_Planificadas;
    }

    public void setHoras_Planificadas(String horas_Planificadas) {
        this.horas_Planificadas = horas_Planificadas;
    }

    public String getHoras_Presupuestadas() {
        return horas_Presupuestadas;
    }

    public void setHoras_Presupuestadas(String horas_Presupuestadas) {
        this.horas_Presupuestadas = horas_Presupuestadas;
    }

    public String getHoras_Consumidas() {
        return horas_Consumidas;
    }

    public void setHoras_Consumidas(String horas_Consumidas) {
        this.horas_Consumidas = horas_Consumidas;
    }

    public String getTotal_Costo_Planificado() {
        return total_Costo_Planificado;
    }

    public void setTotal_Costo_Planificado(String total_Costo_Planificado) {
        this.total_Costo_Planificado = total_Costo_Planificado;
    }

    public String getTotal_Costo_Presupuestado() {
        return total_Costo_Presupuestado;
    }

    public void setTotal_Costo_Presupuestado(String total_Costo_Presupuestado) {
        this.total_Costo_Presupuestado = total_Costo_Presupuestado;
    }

    public String getTotal_Costo_Consumido() {
        return total_Costo_Consumido;
    }

    public void setTotal_Costo_Consumido(String total_Costo_Consumido) {
        this.total_Costo_Consumido = total_Costo_Consumido;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public void setEstadoCarga(String estadoCarga) {
        this.estadoCarga = estadoCarga;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentariosProd() {
        return comentariosProd;
    }

    public void setComentariosProd(String comentariosProd) {
        this.comentariosProd = comentariosProd;
    }

    public String getEstadoCierre() {
        return estadoCierre;
    }

    public void setEstadoCierre(String estadoCierre) {
        this.estadoCierre = estadoCierre;
    }
    
}
