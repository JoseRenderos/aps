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
public class Horas {
    String cecos;
    String hPlanificadas;
    String hLiberadas;
    String hCosumidas;
    String hDisponibles;
    String hPlanificadasMes;
    String hPlanificadasRestantes;
    String hProyectadas;
    String hDisponiblesMes;
    String hDisponiblesALaFecha;
    String porcentajeHorasConsumidasPlanificadas;
    String porcentajeHorasPlanificadasRestantesDelMes;

    public Horas() {
    }

    public Horas(String cecos, String hPlanificadas, String hCosumidas, String hDisponibles) {
        this.cecos = cecos;
        this.hPlanificadas = hPlanificadas;
        this.hCosumidas = hCosumidas;
        this.hDisponibles = hDisponibles;
    }

    public Horas(String cecos, String hPlanificadas, String hLiberadas, String hCosumidas, String hDisponibles) {
        this.cecos = cecos;
        this.hPlanificadas = hPlanificadas;
        this.hLiberadas = hLiberadas;
        this.hCosumidas = hCosumidas;
        this.hDisponibles = hDisponibles;
    }
    
    public String getCecos() {
        return cecos;
    }

    public void setCecos(String cecos) {
        this.cecos = cecos;
    }

    public String gethPlanificadas() {
        return hPlanificadas;
    }

    public void sethPlanificadas(String hPlanificadas) {
        this.hPlanificadas = hPlanificadas;
    }

    public String gethCosumidas() {
        return hCosumidas;
    }

    public void sethCosumidas(String hCosumidas) {
        this.hCosumidas = hCosumidas;
    }

    public String gethDisponibles() {
        return hDisponibles;
    }

    public void sethDisponibles(String hDisponibles) {
        this.hDisponibles = hDisponibles;
    }

    public String gethLiberadas() {
        return hLiberadas;
    }

    public void sethLiberadas(String hLiberadas) {
        this.hLiberadas = hLiberadas;
    }

    public String gethPlanificadasMes() {
        return hPlanificadasMes;
    }

    public void sethPlanificadasMes(String hPlanificadasMes) {
        this.hPlanificadasMes = hPlanificadasMes;
    }

    public String gethPlanificadasRestantes() {
        return hPlanificadasRestantes;
    }

    public void sethPlanificadasRestantes(String hPlanificadasRestantes) {
        this.hPlanificadasRestantes = hPlanificadasRestantes;
    }

    public String gethProyectadas() {
        return hProyectadas;
    }

    public void sethProyectadas(String hProyectadas) {
        this.hProyectadas = hProyectadas;
    }

    public String gethDisponiblesMes() {
        return hDisponiblesMes;
    }

    public void sethDisponiblesMes(String hDisponiblesMes) {
        this.hDisponiblesMes = hDisponiblesMes;
    }

    public String gethDisponiblesALaFecha() {
        return hDisponiblesALaFecha;
    }

    public void sethDisponiblesALaFecha(String hDisponiblesALaFecha) {
        this.hDisponiblesALaFecha = hDisponiblesALaFecha;
    }

    public String getPorcentajeHorasConsumidasPlanificadas() {
        return porcentajeHorasConsumidasPlanificadas;
    }

    public void setPorcentajeHorasConsumidasPlanificadas(String porcentajeHorasConsumidasPlanificadas) {
        this.porcentajeHorasConsumidasPlanificadas = porcentajeHorasConsumidasPlanificadas;
    }

    public String getPorcentajeHorasPlanificadasRestantesDelMes() {
        return porcentajeHorasPlanificadasRestantesDelMes;
    }

    public void setPorcentajeHorasPlanificadasRestantesDelMes(String porcentajeHorasPlanificadasRestantesDelMes) {
        this.porcentajeHorasPlanificadasRestantesDelMes = porcentajeHorasPlanificadasRestantesDelMes;
    }
    
    
}
