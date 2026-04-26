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
public class APS_OIGE {
    private int idAPS_OIGE;
    private OWOR APS_OWOR;
    private int codeEmp;
    private String nomEmp;
    private String actividad;
    private String descActividad;
    private String inicio;
    private String fin;
    private String totalTiempo;
    private String totalTiempoMuerto;
    private int estado;
    private Usuario usuario;
    private String insumo;
    private int cantAsignada;
    private int totalUnidades;
    private int totalUnidadesConformes;
    private int totalUnidadesNoConformes;
    private String codeActProd;
    private String descActividadProd;

    public APS_OIGE() {
    }

    public APS_OIGE(int idAPS_OIGE, OWOR APS_OWOR, int codeEmp, String nomEmp, String actividad, String inicio, String fin, String totalTiempo, String totalTiempoMuerto, int estado, Usuario usuario, String insumo, int cantAsignada,int totalUnidades, int totalUnidadesConformes) {
        this.idAPS_OIGE = idAPS_OIGE;
        this.APS_OWOR = APS_OWOR;
        this.codeEmp = codeEmp;
        this.nomEmp = nomEmp;
        this.actividad = actividad;
        this.inicio = inicio;
        this.fin = fin;
        this.totalTiempo = totalTiempo;
        this.totalTiempoMuerto = totalTiempoMuerto;
        this.estado = estado;
        this.usuario = usuario;
        this.insumo = insumo;
        this.cantAsignada = cantAsignada;
        this.totalUnidades = totalUnidades;
        this.totalUnidadesConformes = totalUnidadesConformes;
    }

    public APS_OIGE(OWOR APS_OWOR, int codeEmp, String nomEmp, String actividad, String inicio, String fin, String totalTiempo, String totalTiempoMuerto, int estado, Usuario usuario, String insumo, int cantAsignada, int totalUnidades, int totalUnidadesConformes) {
        this.APS_OWOR = APS_OWOR;
        this.codeEmp = codeEmp;
        this.nomEmp = nomEmp;
        this.actividad = actividad;
        this.inicio = inicio;
        this.fin = fin;
        this.totalTiempo = totalTiempo;
        this.totalTiempoMuerto = totalTiempoMuerto;
        this.estado = estado;
        this.usuario = usuario;
        this.insumo = insumo;
        this.cantAsignada = cantAsignada;
        this.totalUnidades = totalUnidades;
        this.totalUnidadesConformes = totalUnidadesConformes;
    }

    public APS_OIGE(int idAPS_OIGE) {
        this.idAPS_OIGE = idAPS_OIGE;
    }

    public int getIdAPS_OIGE() {
        return idAPS_OIGE;
    }

    public void setIdAPS_OIGE(int idAPS_OIGE) {
        this.idAPS_OIGE = idAPS_OIGE;
    }

    public OWOR getAPS_OWOR() {
        return APS_OWOR;
    }

    public void setAPS_OWOR(OWOR APS_OWOR) {
        this.APS_OWOR = APS_OWOR;
    }

    public int getCodeEmp() {
        return codeEmp;
    }

    public void setCodeEmp(int codeEmp) {
        this.codeEmp = codeEmp;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getTotalTiempo() {
        return totalTiempo;
    }

    public void setTotalTiempo(String totalTiempo) {
        this.totalTiempo = totalTiempo;
    }

    public String getTotalTiempoMuerto() {
        return totalTiempoMuerto;
    }

    public void setTotalTiempoMuerto(String totalTiempoMuerto) {
        this.totalTiempoMuerto = totalTiempoMuerto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public int getCantAsignada() {
        return cantAsignada;
    }

    public void setCantAsignada(int cantAsignada) {
        this.cantAsignada = cantAsignada;
    }

    public int getTotalUnidades() {
        return totalUnidades;
    }

    public void setTotalUnidades(int totalUnidades) {
        this.totalUnidades = totalUnidades;
    }

    public int getTotalUnidadesConformes() {
        return totalUnidadesConformes;
    }

    public void setTotalUnidadesConformes(int totalUnidadesConformes) {
        this.totalUnidadesConformes = totalUnidadesConformes;
    }

    public String getDescActividad() {
        return descActividad;
    }

    public void setDescActividad(String descActividad) {
        this.descActividad = descActividad;
    }

    public int getTotalUnidadesNoConformes() {
        return totalUnidadesNoConformes;
    }

    public void setTotalUnidadesNoConformes(int totalUnidadesNoConformes) {
        this.totalUnidadesNoConformes = totalUnidadesNoConformes;
    }

    public String getCodeActProd() {
        return codeActProd;
    }

    public void setCodeActProd(String codeActProd) {
        this.codeActProd = codeActProd;
    }

    public String getDescActividadProd() {
        return descActividadProd;
    }

    public void setDescActividadProd(String descActividadProd) {
        this.descActividadProd = descActividadProd;
    }
    
    
    
    @Override
    public String toString() {
        return "[ docNum:" + APS_OWOR.getDocnum() + ", nomEmp:" + nomEmp + ", actividad:" + actividad + ", inicio:" + inicio + ", fin:" + fin + ", totalTiempo:" + totalTiempo + ", totalTiempoMuerto:" + totalTiempoMuerto + "]";
    }
    
}
