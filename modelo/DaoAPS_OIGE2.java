/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OIGE2 extends ConexionSDO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    APS_OIGE APS_OIGE; 
    APS_OIGE2 APS_OIGE2;

    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE2=(APS_OIGE2)Ob;
            //if(APS_OIGE2.getAPS_OIGE().getDescActividad().equals("126MLINEA1") && APS_OIGE2.getAPS_OIGE().getDescActividad().contains("106")){
                String inicio=APS_OIGE2.getInicio().replace(" ", "T");
                ps=con().prepareStatement("INSERT INTO APS_OIGE2(inicio,idAPS_OIGE) "+ 
                                          "VALUES(CAST(? AS DATETIME),(SELECT TOP 1 T0.idAPS_OIGE FROM APS_OIGE T0 JOIN APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC))");
                ps.setString(1, inicio);
                ps.setInt(2, APS_OIGE2.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE2.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE2.getAPS_OIGE().getCodeEmp());
                ps.setString(5,APS_OIGE2.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?"":APS_OIGE2.getAPS_OIGE().getDescActividad());
            /*}else{
                String inicio=APS_OIGE2.getInicio().replace(" ", "T");
                ps=con().prepareStatement("INSERT INTO APS_OIGE2(inicio,idAPS_OIGE) "+ 
                                          "VALUES(CAST(? AS DATETIME),(SELECT TOP 1 T0.idAPS_OIGE FROM APS_OIGE T0 JOIN APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND ISNULL(descActividad,' ')=? AND T0.estado=0 ORDER BY T0.idAPS_OIGE DESC))");
                ps.setString(1, inicio);
                ps.setInt(2, APS_OIGE2.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE2.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE2.getAPS_OIGE().getCodeEmp());
                ps.setString(5,APS_OIGE2.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?" ":APS_OIGE2.getAPS_OIGE().getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE2.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    @Override
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE2=(APS_OIGE2)Ob;
            //if(APS_OIGE2.getAPS_OIGE().getDescActividad().equals("126MLINEA1") && APS_OIGE2.getAPS_OIGE().getDescActividad().contains("106")){
                String fin=APS_OIGE2.getFin().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE2 " + 
                                          "SET fin=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OIGE2=(SELECT TOP 1 T0.idAPS_OIGE2 from APS_OIGE2 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE2 DESC)");
                ps.setString(1, fin);
                ps.setInt(2, APS_OIGE2.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE2.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE2.getAPS_OIGE().getCodeEmp());
                ps.setString(5,APS_OIGE2.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?"":APS_OIGE2.getAPS_OIGE().getDescActividad());
            /*}else{
                String fin=APS_OIGE2.getFin().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE2 " + 
                                          "SET fin=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OIGE2=(SELECT TOP 1 T0.idAPS_OIGE2 from APS_OIGE2 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND ISNULL(descActividad,' ')=? AND estado=0 ORDER BY T0.idAPS_OIGE2 DESC)");
                ps.setString(1, fin);
                ps.setInt(2, APS_OIGE2.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE2.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE2.getAPS_OIGE().getCodeEmp());
                ps.setString(5, APS_OIGE2.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?" ":APS_OIGE2.getAPS_OIGE().getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE2.modificar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    

    @Override
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
