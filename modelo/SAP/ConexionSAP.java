/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.SAP;
import com.sap.smb.sbo.api.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class ConexionSAP {
    //Objecto de connexion
    public ICompany company;
    
    public int connect(){
   //-----------------------------
     //variable que retorna el resultado de la conexion
        // 0=conexion correcta && 1=error de conexion
        int connectionResult=0;
        try {
            company= SBOCOMUtil.newCompany();
            company.setSLDServer("207.139.25.140:40000");//Servidor y puerto de servidor de licencias de sap
            company.setDbServerType(SBOCOMConstants.BoDataServerTypes_dst_MSSQL2014);//Version de SQLSERVER
            company.setUserName("manager");//Usuario de SAP
                company.setPassword("Gr34tprd$");//Contraseña de SAP
            company.setServer("207.139.25.140");//Server de base de datos
//            company.setCompanyDB("ALSASA_PRUEBAS");//Base de datos de SAP
            company.setCompanyDB("SBO_ALSASA");//Base de datos de SAP
            company.setLanguage(SBOCOMConstants.BoSuppLangs_ln_Spanish_La);//Lenguaje de SAP
            
            connectionResult=company.connect();
            
// ---------------------------         
        } catch (Exception e) {
            connectionResult=-1;
        }
        
        return connectionResult; 
    }
    
    public void disconnect(){
        company.disconnect();
        System.out.println("Desconectado exitosamente");
    }
    
    public String companyName(){
        return company.getCompanyName();
    }
    
    public String error(){
        SBOErrorMessage errMsg= company.getLastError();
        String error = errMsg.getErrorCode()+ " " + errMsg.getErrorMessage();
        return error;
    }   
    
    public ICompany company(){
        return company;
    }
}
