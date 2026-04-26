/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.*;
import entidades.ItemSBO;


/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoItemSBO extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    ItemSBO item; 
    
    public ArrayList<Object> mostrar(String itemCode) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "	T0.[Father], " +
                                              "	T0.[Code], " +
                                              "	T0.[Quantity] " +
                                              "FROM ITT1 T0 WHERE T0.[Code] = ?");
            ps.setString(1,itemCode);
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new ItemSBO();
               item.setFather(rs.getString(1));
               item.setCode(rs.getString(2));
               ar.add(item);
            }
            
            ps = super.con().prepareStatement("SELECT " +
                                            " T1.code 'Father', " +
                                            " T0.CODE 'Code', " +
                                            " T1.Quantity 'Quantity' " +
                                            "FROM OITT T0 " +
                                            "JOIN ITT1 T1 ON T1.FATHER=T0.CODE " +
                                            "WHERE T0.CODE=? AND T1.TYPE=4 AND (SELECT T2.PrchseItem FROM OITM T2 WHERE T2.ITEMCODE=T1.CODE AND T2.PrcrmntMtd='M')='N'");
            ps.setString(1,itemCode);
            rs = ps.executeQuery();

            while(rs.next()){
               item= new ItemSBO();
               item.setFather(rs.getString(1));
               item.setCode(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoItemSBO.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
}
