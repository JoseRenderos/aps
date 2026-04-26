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
public class DaoWOR1 extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    WOR1 ins;
    
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
                    "WHERE T0.ItemType = 4 AND T1.DocNum=?");
            
            ps.setString(1, orden);
            rs=ps.executeQuery();
            while(rs.next()){
                ins= new WOR1(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                ar.add(ins);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoWOR1.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    
    public ArrayList<Object> mostrarMateriales(int orden) throws ClassNotFoundException, SQLException{
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps=super.con().prepareStatement("SELECT " +
                                            "T0.ItemCode, " +
                                            "CASE " +
                                            "WHEN T0.ItemType = 4 THEN (SELECT H0.ItemName FROM OITM H0 WHERE H0.ItemCode = T0.ItemCode) " +
                                            "WHEN T0.ItemType = 290 THEN (SELECT H0.ResName FROM ORSC H0 WHERE H0.VisResCode = T0.ItemCode) " +
                                            "END Descripcion, " +
                                            "T0.ReleaseQty, " +
                                            "ISNULL((SELECT  " +
                                            "		SUM(K0.Quantity) " +
                                            "	FROM WTR1 K0 " +
                                            "	JOIN OWTR K1 ON K1.DocEntry = K0.DocEntry " +
                                            "	WHERE K0.U_OrdenProduccion = CAST(T1.DocNum AS VARCHAR) AND K0.ItemCode = T0.ItemCode AND K0.WhsCode!='B003' " +
                                            "	),0)+ISNULL((SELECT SUM(J0.Quantity) FROM SDO_ALSASA..APS_WTR1 J0 JOIN SDO_ALSASA..APS_OWTR J1 ON J0.idAPS_OWTR=J1.idAPS_OWTR WHERE J0.ordenProduccion=T1.docNum AND J0.itemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS AND J1.estado='W'),0) CantEntregada " +
                                            "FROM WOR1 T0 " +
                                            "JOIN OWOR T1 ON T1.DocEntry = T0.DocEntry " +
                                            "WHERE T0.ItemType = 4 " +
                                            "AND T1.DocNum=?");
             ps=super.con().prepareStatement("select CONVERT(NUMERIC(38,6),0)*(100+0.01)");
            ps.setInt(1, orden);
            rs=ps.executeQuery();
            while(rs.next()){
                ins= new WOR1();
                ins.setItemCode(rs.getString(1));
                ins.setItemName(rs.getString(2));
                ins.setReleaseQty(rs.getString(3));
                ins.setCantEntregada(rs.getString(4));
                ar.add(ins);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoWOR1.mostrarMateriales(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
}
