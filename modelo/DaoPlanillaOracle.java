/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.*;
import java.text.*;
import entidades.*;

/**
 *
 * @author Mario Valdez
 */
public class DaoPlanillaOracle extends ConexionOracle{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Planilla planilla;
    
    public Planilla mostrar() throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            ps=super.con().prepareStatement("SELECT " +
                                            "	TO_CHAR(T0.FECHA_INICIO_HX, 'YYYY-MM-DD') FECHA_INICIO_HX, " +
                                            "	TO_CHAR(T0.FECHA_FIN_HX, 'YYYY-MM-DD') FECHA_FIN_HX " +
                                            "FROM PROGRAMACION_PLA T0 " +
                                            "WHERE T0.COD_TIPOPLA=1 " +
                                            "AND T0.STATUS ='E' " + 
                                            "AND TO_CHAR(T0.FECHA_FIN_HX, 'YYYY')=TO_CHAR(SYSDATE, 'YYYY') ");
                rs = ps.executeQuery();
                while(rs.next()){
                   planilla = new Planilla();
                   planilla.setFechaInicio(rs.getString(1));
                   planilla.setFechaFin(rs.getString(2));
                }
        }catch(Exception ex){
            System.out.println("pruebaoracle.DaoPlanillaOracle.mostrar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return planilla;
    }
    
}
