/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.*;
import entidades.OITT;


/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoOITT extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    OITT item; 
    
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
               item= new OITT();
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
                                            "WHERE T0.CODE=? AND T1.TYPE=4");
            ps.setString(1,itemCode);
            rs = ps.executeQuery();

            while(rs.next()){
               item= new OITT();
               item.setFather(rs.getString(1));
               item.setCode(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int mostrarEstandarH(String item, String code, String descripcion) throws ClassNotFoundException, SQLException{
        int estandarH=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "	CONVERT(numeric(19,0),ROUND((T1.Qauntity / T0.Quantity), 3)) 'EstadarH' " +
                                              "FROM ITT1 T0 " +
                                              "JOIN OITT T1 ON T1.Code = T0.FATHER " +
                                              "WHERE T0.Type = 290 " +
                                              "AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = T0.Code) = 'L' AND T0.CODE LIKE ? AND ISNULL(T0.Comment,'')=? AND T1.CODE=?");
            ps.setString(1,"%" + code + "%");
            ps.setString(2,descripcion.equals("Sin descripcion")?"":descripcion);
            ps.setString(3,item);
            rs = ps.executeQuery();
            while(rs.next()){
               estandarH = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarEstandarH(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return estandarH;
    }
    
    public ArrayList<Object> mostrarArticulosProyeccion() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                                "	T0.code, " +
                                                "	REPLACE(REPLACE(T0.Name,' - ', ''),',','') Name " +
                                                "FROM OITT T0 " +
                                                "WHERE T0.code LIKE '%CM-%' AND T0.code NOT LIKE '%-2DA%' AND T0.code NOT LIKE '%-P2%' AND T0.code NOT LIKE '%-EXP%'");
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new OITT();
               item.setCode(rs.getString(1));
               item.setName(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarArticulosProyeccion(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarArticulosCosto() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT  " +
                                              "	T0.itemCode, " +
                                              "	T0.itemName " +
                                              "FROM VW_ARTICULOS_PROD T0");
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new OITT();
               item.setCode(rs.getString(1));
               item.setName(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarArticulosCosto(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarArticulosHistorico() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                                "	T0.code, " +
                                                "	REPLACE(REPLACE(T0.Name,' - ', ''),',','') Name " +
                                                "FROM OITT T0 " +
                                                "WHERE T0.code LIKE '%CM-%' AND T0.code NOT LIKE '%-2DA%' AND T0.code NOT LIKE '%-P2%' AND T0.code NOT LIKE '%-EXP%'");
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new OITT();
               item.setCode(rs.getString(1));
               item.setName(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarArticulosHistorico(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarStatusArt() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT T0.* FROM SDO_ALSASA..STATUS_ART_INV T0");
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new OITT();
               item.setCode(rs.getString(1));
               item.setName(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarStatusArt(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarArticulosInventario() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT T0.* FROM SDO_ALSASA..VW_ARTICULOS_INV T0");
            rs = ps.executeQuery();
            
            while(rs.next()){
               item= new OITT();
               item.setCode(rs.getString(1));
               item.setName(rs.getString(2));
               ar.add(item);
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.mostrarArticulosInventario(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public String ejecutarProyeccion(String codeArt, String cant) throws ClassNotFoundException, SQLException {
        String resultado="";
        int res=0;
        try {
            String array=codeArt;
            String[] arrayCodes=array.split(",");
            String[] codes = new String[arrayCodes.length];
            for (int i = 0; i < codes.length; i++) {
                try {
                    codes[i] = arrayCodes[i];
                } catch (Exception e) {
                }
            }
            
            String arrayCant=cant;
            String[] arrayCRequerida=arrayCant.split(",");
            String[] cantRequerida = new String[arrayCRequerida.length];
            for (int i = 0; i < cantRequerida.length; i++) {
                try {
                    cantRequerida[i] = arrayCRequerida[i];
                } catch (Exception e) {
                }
            }
            ps = super.con().prepareStatement("TRUNCATE TABLE SDO_ALSASA..PRART");
            res=ps.executeUpdate();
            
            if (res!=0) {
                for (int i = 0; i < codes.length; i++) {
                    ps = super.con().prepareStatement("EXEC SDO_ALSASA..MOSTRAR_NIVELES_LISTAM " +
                                                    "	@itemCode=?, " +
                                                    "	@CANT_REQ=? ");
                    ps.setString(1,codes[i]); 
                    ps.setString(2,cantRequerida[i]); 
                    rs = ps.executeQuery();
                    while(rs.next()){
                       resultado=rs.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOITT.ejecutarProyeccion()" + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        
        return resultado;
    
    }
}
