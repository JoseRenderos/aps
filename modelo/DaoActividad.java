/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.*;
import entidades.*;
/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoActividad extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    ActividadSBO ac; 
    
    public ArrayList<Object> mostrar(String itemCode) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "  T0.Code, " +
                                              "  ISNULL(T0.Comment, 'Sin descripcion') 'comment' " +
                                              "FROM ITT1 T0 " +
                                              "JOIN OITT T1 ON T0.Father=T1.Code " +
                                              "WHERE T1.Code=? and (SELECT ResType FROM ORSC WHERE ResCode=T0.Code)='L'");
            ps.setString(1, itemCode);
            rs=ps.executeQuery();
            while(rs.next()){
                ac= new ActividadSBO();
                ac.setItemCode(rs.getString(1));
                ac.setComment(rs.getString(2));
                ar.add(ac);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoActividad.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
}
