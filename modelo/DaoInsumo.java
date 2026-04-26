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
public class DaoInsumo extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    InsumoSBO ins;
    
    public ArrayList<Object> mostrar(String orden) throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps= super.con().prepareStatement("SELECT "+ 
                    "T0.LineNum, "+ 
                    "T0.ITEMCODE, "+ 
                    "T2.ItemName, "+
                    "T0.PlannedQty, "+ 
                    "T0.IssuedQty "+ 
                    "FROM WOR1 T0 "+ 
                    "JOIN OWOR T1 ON T1.DocEntry=T0.DocEntry "+ 
                    "JOIN OITM T2 ON T2.ItemCode=T0.ItemCode "+ 
                    "WHERE T0.ItemType=4 AND T1.DocNum=?");
            
            ps.setString(1, orden);
            rs=ps.executeQuery();
            while(rs.next()){
                ins= new InsumoSBO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                ar.add(ins);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoInsumo.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
}
