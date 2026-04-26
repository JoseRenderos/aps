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
public class DaoQuerysSQL extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Empleado_pago empleado;
    
    
    public ArrayList<Empleado_pago> obtenerEmpleados(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        int r=0;
        ArrayList<Empleado_pago> empleados = new ArrayList<Empleado_pago>();
        try{
//            ps=super.con().prepareStatement("SELECT DISTINCT " +
//                                            "	CASE WHEN T0.codeEmp=9999 THEN 219 ELSE T0.codeEmp END CODEEMP " +
//                                            "FROM SDO_ALSASA..APS_OIGE T0 " +
//                                            "WHERE CAST(T0.inicio AS DATE) BETWEEN ? AND ?  AND T0.codeEmp NOT IN (9999,380,10061,10077) " +
//                                            "UNION ALL " +
//                                            "SELECT T0.codEmp FROM SDO_ALSASA..[EMPLEADOS_ALSASA] T0 WHERE T0.codEmp not in (SELECT DISTINCT " +
//                                            "	CASE WHEN T0.codeEmp=9999 THEN 40 ELSE T0.codeEmp END CODEEMP " +
//                                            "FROM SDO_ALSASA..APS_OIGE T0 " +
//                                            "WHERE CAST(T0.inicio AS DATE) BETWEEN ? AND ?  AND T0.codeEmp NOT IN (9999,380,10061,10077) ) ");
            ps=super.con().prepareStatement("SELECT T0.codEmp,T0.cod_Depto FROM SDO_ALSASA..[EMPLEADOS_ALSASA] T0  ");
            /*ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            ps.setString(3, fecha1);
            ps.setString(4, fecha2);*/
            rs = ps.executeQuery();
            while(rs.next()){
               empleado = new Empleado_pago();
               empleado.setCodEmp(rs.getString(1)); 
               empleado.setCodDepto(rs.getString(2)); 
               empleados.add(empleado);
                System.out.println("modelo.DaoQuerysSQL.obtenerEmpleados(): " +rs.getString(1));
            }
        }catch(Exception ex){
            System.out.println("pruebaoracle.DaoQuerysSQL.obtenerEmpleados(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return empleados;
    }
     
    public ArrayList<Empleado_pago> obtenerPago(String codeEmp, String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        int r=0;
        ArrayList<Empleado_pago> empleados = new ArrayList<Empleado_pago>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_VALORIZACION_PROD_V3 @codeEmp=? , @FECHA1=?, @FECHA2=? ");
            ps.setString(1, codeEmp);
            ps.setString(2, fecha1);
            ps.setString(3, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                
                empleado = new Empleado_pago();
                empleado.setCodEmp(rs.getString(1));
                empleado.setDia(rs.getString(2));
                empleado.setSemana(rs.getString(3));
                empleado.setFecha(rs.getString(4));
                empleado.setValor(rs.getString(5));
                empleado.setSueldoDia(rs.getString(8));
                empleado.setBono(rs.getString(7));
                empleados.add(empleado);
            }
        }catch(Exception ex){
            System.out.println("pruebaoracle.DaoQuerysSQL.obtenerPago(): " + ex.getMessage());
        }finally{
            super.con().close(); 
            ps.close();
        }
        return empleados;
    }
     
    public ArrayList<Empleado_pago> obtenerPagoTorno(String codeEmp, String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        int r=0;
        ArrayList<Empleado_pago> empleados = new ArrayList<Empleado_pago>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_VALORIZACION_TORNO] @codeEmp=? , @FECHA1=?, @FECHA2=? ");
            ps.setString(1, codeEmp);
            ps.setString(2, fecha1);
            ps.setString(3, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                
                empleado = new Empleado_pago();
                empleado.setCodEmp(rs.getString(1));
                empleado.setDia(rs.getString(2));
                empleado.setSemana(rs.getString(3));
                empleado.setFecha(rs.getString(4));
                empleado.setValor(rs.getString(5));
                empleado.setSueldoDia(rs.getString(8));
                empleado.setBono(rs.getString(7));
                empleados.add(empleado);
            }
        }catch(Exception ex){
            System.out.println("pruebaoracle.DaoQuerysSQL.obtenerPagoTorno(): " + ex.getMessage());
        }finally{
            super.con().close(); 
            ps.close();
        }
        return empleados;
    }
     
    public int insertarEmpleado(Object Empleado) throws ClassNotFoundException, SQLException{
        try{
            empleado=(Empleado_pago)Empleado;
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..PAGO_EMP(codeEmp,dia,semana,fecha,valor,sueldoDia) VALUES(?,?,?,?,?,?);");
            ps.setInt(1, Integer.parseInt(empleado.getCodEmp()));
            ps.setString(2, empleado.getDia().toString());
            ps.setString(3, empleado.getSemana().toString());
            ps.setString(4, empleado.getFecha().toString());
            ps.setString(5, empleado.getValor().toString());
            ps.setString(6, empleado.getSueldoDia().toString());
            res = ps.executeUpdate();
            
        }catch(Exception ex){
            System.out.println("modelo.DaoQuerysSQL.insertarEmpleado(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int insertarPagoQuincena(String codeEmp, String desde, String hasta, double pago) throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..PAGO_QUINCENA(codeEmp,desde,hasta,Pago) VALUES(?,?,?,?); ");
            ps.setInt(1, Integer.parseInt(codeEmp));
            ps.setString(2, desde.replaceAll("-", ""));
            ps.setString(3, hasta.replaceAll("-", ""));
            ps.setDouble(4, pago);
            res = ps.executeUpdate();
            
        }catch(Exception ex){
            System.out.println("modelo.DaoQuerysSQL.insertarPagoQuincena(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int insertarPagoDia(String codeEmp, String desde, String hasta, String fecha, double pago) throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_GUARDAR_PAGO_DIA @codeEmp=?, @FECHA1=?, @FECHA2=?, @FECHA=?, @PAGO=? ");
            ps.setInt(1, Integer.parseInt(codeEmp));
            ps.setString(2, desde);
            ps.setString(3, hasta);
            ps.setString(4, fecha);
            ps.setDouble(5, pago);
            res = ps.executeUpdate();
            
        }catch(Exception ex){
            System.out.println("modelo.DaoQuerysSQL.insertarPagoDia(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int truncarEmpleados() throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("TRUNCATE TABLE SDO_ALSASA..PAGO_EMP");
            res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoEmpleadoSQL.truncarEmpleados(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int truncarPagoQuincena() throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("TRUNCATE TABLE SDO_ALSASA..PAGO_QUINCENA");
            res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoEmpleadoSQL.truncarPagoQuincena(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int truncarPagoDia() throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("DELETE FROM SDO_ALSASA..PAGO_DIA_QUINCENA ");
            res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoEmpleadoSQL.truncarPagoDia(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
