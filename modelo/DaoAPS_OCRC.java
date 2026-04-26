/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.APS_OCRC;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OCRC extends ConexionSDO{
    PreparedStatement ps;
    ResultSet rs;
    APS_OCRC APS_OCRC;
    
    public ArrayList<Object> listarAPS_OCRCUser(int idUsuario, int idRol) throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   T1.idAPS_OCRC, "+ 
                                              "   T0.code, "+ 
                                              "   T0.nombre "+ 
                                              "FROM APS_OCRC T0 "+ 
                                              "JOIN APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC "+ 
                                              "JOIN USUARIO US ON T1.idUsuario=US.idUsuario "+ 
                                              "WHERE US.idUsuario=?");
            ps.setInt(1, idUsuario);
           // ps.setInt(2, idRol);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OCRC = new APS_OCRC(rs.getInt(1), rs.getInt(2), rs.getString(3));
                ar.add(APS_OCRC);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoAPS_OCRC.listarAPS_OCRCUser(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> listarIdAPS_OCRCUser(int idUsuario, int idRol) throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   T1.idAPS_OCRC "+
                                              "FROM APS_OCRC T0 "+ 
                                              "JOIN APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC "+ 
                                              "JOIN USUARIO US ON T1.idUsuario=US.idUsuario "+ 
                                              "WHERE US.idUsuario=?");
            ps.setInt(1, idUsuario);
           // ps.setInt(2, idRol);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OCRC = new APS_OCRC();
                APS_OCRC.setIdAPS_OCRC(rs.getInt(1));
                ar.add(APS_OCRC);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoAPS_OCRC.listarIdAPS_OCRCUser(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> listarAPS_OCRC() throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   T0.idAPS_OCRC, "+ 
                                              "   T0.code, "+ 
                                              "   T0.nombre "+ 
                                              "FROM APS_OCRC T0 ");            
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OCRC = new APS_OCRC(rs.getInt(1), rs.getInt(2), rs.getString(3));
                ar.add(APS_OCRC);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoAPS_OCRC.listarAPS_OCRC(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
}
