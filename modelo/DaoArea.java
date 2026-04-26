/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.Area;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoArea extends ConexionSDO{
    PreparedStatement ps;
    ResultSet rs;
    Area area;
    
    public ArrayList<Object> listarAreasUser(int idUsuario, int idRol) throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   DA.idArea, "+ 
                                              "   A.code, "+ 
                                              "   A.nombre "+ 
                                              "FROM AREA A "+ 
                                              "JOIN DETALLE_AREA DA ON A.idArea=DA.idArea "+ 
                                              "JOIN USUARIO US ON DA.idUsuario=US.idUsuario "+ 
                                              "WHERE US.idUsuario=? AND US.idRol=?");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idRol);
            rs = ps.executeQuery();
            while(rs.next()){
                area = new Area(rs.getInt(1), rs.getInt(2), rs.getString(3));
                ar.add(area);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoArea.listarAreasUser(): " + e.getMessage()); 
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> listarAreas() throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   A.idArea, "+ 
                                              "   A.code, "+ 
                                              "   A.nombre "+ 
                                              "FROM AREA A");            
            rs = ps.executeQuery();
            while(rs.next()){
                area = new Area(rs.getInt(1), rs.getInt(2), rs.getString(3));
                ar.add(area);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoArea.listarAreas(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
}
