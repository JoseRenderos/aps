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
public class HorasTrabajo {
    private String fecha;
    private String horasPlanificadas;
    private String horasDisponibles;
    private String horasConsumidas;
    private String horasConsumidasSMED;
    private String horasEstandarCompletadas;
    private String horasEstandarRechazadas;
    private String horasEstandarTotal;
    private String hPropPD;
    private String hPropCP;
    private String hPropEP;
    private String hPropCD;
    private String hPropEC;
    private String costoHEstandarTotal;
    private String costoHPlanificadas;
    private String costoHConsumidas;
    private String costoHConsumidasSMED;
    private String costoHDisponibles;
    private String costoHNoConsumidas;
    private String horasDisponiblesActivas;
    private String costoHDisponiblesActivas;
    private String costoHNoConsumidasActivas;
    private String CECOS;

    public HorasTrabajo() {
    }

    public HorasTrabajo(String fecha, String horasPlanificadas, String horasDisponibles, String horasConsumidas, String horasEstandarCompletadas, String horasEstandarRechazadas, String horasEstandarTotal, String hPropPD, String hPropCP, String hPropEP, String hPropCD, String hPropEC, String costoHEstandarTotal, String costoHPlanificadas, String costoHConsumidas, String costoHDisponibles, String costoHNoConsumidas) {
        this.fecha = fecha;
        this.horasPlanificadas = horasPlanificadas;
        this.horasDisponibles = horasDisponibles;
        this.horasConsumidas = horasConsumidas;
        this.horasEstandarCompletadas = horasEstandarCompletadas;
        this.horasEstandarRechazadas = horasEstandarRechazadas;
        this.horasEstandarTotal = horasEstandarTotal;
        this.hPropPD = hPropPD;
        this.hPropCP = hPropCP;
        this.hPropEP = hPropEP;
        this.hPropCD = hPropCD;
        this.hPropEC = hPropEC;
        this.costoHEstandarTotal = costoHEstandarTotal;
        this.costoHPlanificadas = costoHPlanificadas;
        this.costoHConsumidas = costoHConsumidas;
        this.costoHDisponibles = costoHDisponibles;
        this.costoHNoConsumidas = costoHNoConsumidas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorasPlanificadas() {
        return horasPlanificadas;
    }

    public void setHorasPlanificadas(String horasPlanificadas) {
        this.horasPlanificadas = horasPlanificadas;
    }

    public String getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(String horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public String getHorasConsumidas() {
        return horasConsumidas;
    }

    public void setHorasConsumidas(String horasConsumidas) {
        this.horasConsumidas = horasConsumidas;
    }

    public String getHorasEstandarCompletadas() {
        return horasEstandarCompletadas;
    }

    public void setHorasEstandarCompletadas(String horasEstandarCompletadas) {
        this.horasEstandarCompletadas = horasEstandarCompletadas;
    }

    public String getHorasEstandarRechazadas() {
        return horasEstandarRechazadas;
    }

    public void setHorasEstandarRechazadas(String horasEstandarRechazadas) {
        this.horasEstandarRechazadas = horasEstandarRechazadas;
    }

    public String getHorasEstandarTotal() {
        return horasEstandarTotal;
    }

    public void setHorasEstandarTotal(String horasEstandarTotal) {
        this.horasEstandarTotal = horasEstandarTotal;
    }

    public String gethPropPD() {
        return hPropPD;
    }

    public void sethPropPD(String hPropPD) {
        this.hPropPD = hPropPD;
    }

    public String gethPropCP() {
        return hPropCP;
    }

    public void sethPropCP(String hPropCP) {
        this.hPropCP = hPropCP;
    }

    public String gethPropEP() {
        return hPropEP;
    }

    public void sethPropEP(String hPropEP) {
        this.hPropEP = hPropEP;
    }

    public String gethPropCD() {
        return hPropCD;
    }

    public void sethPropCD(String hPropCD) {
        this.hPropCD = hPropCD;
    }

    public String gethPropEC() {
        return hPropEC;
    }

    public void sethPropEC(String hPropEC) {
        this.hPropEC = hPropEC;
    }

    public String getCostoHEstandarTotal() {
        return costoHEstandarTotal;
    }

    public void setCostoHEstandarTotal(String costoHEstandarTotal) {
        this.costoHEstandarTotal = costoHEstandarTotal;
    }

    public String getCostoHPlanificadas() {
        return costoHPlanificadas;
    }

    public void setCostoHPlanificadas(String costoHPlanificadas) {
        this.costoHPlanificadas = costoHPlanificadas;
    }

    public String getCostoHConsumidas() {
        return costoHConsumidas;
    }

    public void setCostoHConsumidas(String costoHConsumidas) {
        this.costoHConsumidas = costoHConsumidas;
    }

    public String getCostoHDisponibles() {
        return costoHDisponibles;
    }

    public void setCostoHDisponibles(String costoHDisponibles) {
        this.costoHDisponibles = costoHDisponibles;
    }

    public String getCostoHNoConsumidas() {
        return costoHNoConsumidas;
    }

    public void setCostoHNoConsumidas(String costoHNoConsumidas) {
        this.costoHNoConsumidas = costoHNoConsumidas;
    }

    public String getHorasDisponiblesActivas() {
        return horasDisponiblesActivas;
    }

    public void setHorasDisponiblesActivas(String horasDisponiblesActivas) {
        this.horasDisponiblesActivas = horasDisponiblesActivas;
    }

    public String getCostoHNoConsumidasActivas() {
        return costoHNoConsumidasActivas;
    }

    public void setCostoHNoConsumidasActivas(String costoHNoConsumidasActivas) {
        this.costoHNoConsumidasActivas = costoHNoConsumidasActivas;
    }

    public String getCECOS() {
        return CECOS;
    }

    public void setCECOS(String CECOS) {
        this.CECOS = CECOS;
    }

    public String getHorasConsumidasSMED() {
        return horasConsumidasSMED;
    }

    public void setHorasConsumidasSMED(String horasConsumidasSMED) {
        this.horasConsumidasSMED = horasConsumidasSMED;
    }

    public String getCostoHConsumidasSMED() {
        return costoHConsumidasSMED;
    }

    public void setCostoHConsumidasSMED(String costoHConsumidasSMED) {
        this.costoHConsumidasSMED = costoHConsumidasSMED;
    }

    public String getCostoHDisponiblesActivas() {
        return costoHDisponiblesActivas;
    }

    public void setCostoHDisponiblesActivas(String costoHDisponiblesActivas) {
        this.costoHDisponiblesActivas = costoHDisponiblesActivas;
    }
}
