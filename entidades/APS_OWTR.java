/*
 * To change this license header choose License Headers in Project Properties.
 * To change this template file choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Mario Valdez
 */
public class APS_OWTR {
    private int idAPS_OWTR;
    private String DocNum;
    private String DocType;
    private String Filler;
    private String ToWhscode;
    private String Series;
    private String JrnlMemo;
    private String Comments;
    private String DocDate;
    private int idUsuario;
    private String estado;

    public APS_OWTR() {
    }

    public APS_OWTR(int idAPS_OWTR, String DocNum, String DocType, String Filler, String ToWhscode, String Series, String JrnlMemo, String Comments, String DocDate, int idUsuario, String estado) {
        this.idAPS_OWTR = idAPS_OWTR;
        this.DocNum = DocNum;
        this.DocType = DocType;
        this.Filler = Filler;
        this.ToWhscode = ToWhscode;
        this.Series = Series;
        this.JrnlMemo = JrnlMemo;
        this.Comments = Comments;
        this.DocDate = DocDate;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public int getIdAPS_OWTR() {
        return idAPS_OWTR;
    }

    public void setIdAPS_OWTR(int idAPS_OWTR) {
        this.idAPS_OWTR = idAPS_OWTR;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String DocNum) {
        this.DocNum = DocNum;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String DocType) {
        this.DocType = DocType;
    }

    public String getFiller() {
        return Filler;
    }

    public void setFiller(String Filler) {
        this.Filler = Filler;
    }

    public String getToWhscode() {
        return ToWhscode;
    }

    public void setToWhscode(String ToWhscode) {
        this.ToWhscode = ToWhscode;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String Series) {
        this.Series = Series;
    }

    public String getJrnlMemo() {
        return JrnlMemo;
    }

    public void setJrnlMemo(String JrnlMemo) {
        this.JrnlMemo = JrnlMemo;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
