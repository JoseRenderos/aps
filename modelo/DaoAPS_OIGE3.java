/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OIGE3 extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    APS_OIGE APS_OIGE; 
    APS_OIGE3 APS_OIGE3;
    APS_ENMT APS_ENMT;
    APS_ENMT1 APS_ENMT1;

    public ArrayList<Object> mostrar(String docnum, int id) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "  T2.itemCode, " +
                                              "  T2.itemName, " +
                                              "  T2.cantAsignada " +
                                              "FROM SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "JOIN SDO_ALSASA..APS_OIGE3 T2 ON T2.idAPS_OIGE=T0.idAPS_OIGE " +
                                              "WHERE T1.docNum=? AND T0.idAPS_OIGE=?");
            ps.setString(1,docnum);
            ps.setInt(2,id);
            rs = ps.executeQuery();
            
            while(rs.next()){
               APS_OIGE3= new APS_OIGE3();
               APS_OIGE3.setItemCode(rs.getString(1));
               APS_OIGE3.setItemName(rs.getString(2));
               APS_OIGE3.setCantAsignada(rs.getString(3));
               ar.add(APS_OIGE3);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.mostrar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrar1(String docnum, int codeEmp) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT DISTINCT" +
                                              "  T2.itemCode, " +
                                              "  (SELECT TOP 1 K0.itemName FROM OITM K0 WHERE K0.itemCode=T2.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) itemName " +
                                              "FROM SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "JOIN SDO_ALSASA..APS_OIGE3 T2 ON T2.idAPS_OIGE=T0.idAPS_OIGE " +
                                              "WHERE T1.docNum=? AND T0.codeEmp=?");
            ps.setString(1,docnum);
            ps.setInt(2,codeEmp);
            rs = ps.executeQuery();
            
            while(rs.next()){
               APS_OIGE3= new APS_OIGE3();
               APS_OIGE3.setItemCode(rs.getString(1));
               APS_OIGE3.setItemName(rs.getString(2).replaceAll(",", ""));
               ar.add(APS_OIGE3);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.mostrar1(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrar2(String docnum, int codeEmp) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                              "  T2.itemCode, " +
                                              "  T2.itemName, " +
                                              "  T2.cantAsignada " +
                                              "FROM SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "JOIN SDO_ALSASA..APS_OIGE3 T2 ON T2.idAPS_OIGE=T0.idAPS_OIGE " +
                                              "WHERE T1.docNum=? AND T0.codeEmp=?");
            ps.setString(1,docnum);
            ps.setInt(2,codeEmp);
            rs = ps.executeQuery();
            
            while(rs.next()){
               APS_OIGE3= new APS_OIGE3();
               APS_OIGE3.setItemCode(rs.getString(1));
               APS_OIGE3.setItemName(rs.getString(2));
               APS_OIGE3.setCantAsignada(rs.getString(3));
               ar.add(APS_OIGE3);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.mostrar2(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }

    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE3=(APS_OIGE3)Ob;
            ps=con().prepareStatement("INSERT INTO SDO_ALSASA..APS_OIGE3(itemCode, itemName, cantAsignada, idAPS_OIGE) "+ 
                                      "VALUES(?,?,?,(SELECT TOP 1 T0.idAPS_OIGE FROM SDO_ALSASA..APS_OIGE T0 JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 ORDER BY T0.idAPS_OIGE DESC));");
            ps.setString(1, APS_OIGE3.getItemCode());
            ps.setString(2, APS_OIGE3.getItemName());
            ps.setString(3, APS_OIGE3.getCantAsignada());
            ps.setInt(4, APS_OIGE3.getAPS_OIGE().getAPS_OWOR().getDocnum());
            ps.setString(5, APS_OIGE3.getAPS_OIGE().getActividad());
            ps.setInt(6, APS_OIGE3.getAPS_OIGE().getCodeEmp());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int guardarENMT(String ItemCode,String Quantity,int idUsuario,int numOrden) throws ClassNotFoundException, SQLException{
        try{
            
            
            String codes=ItemCode;
            String[] codeArticulo=codes.split(",");
            String[] codeArt = new String[codeArticulo.length];

            String Qty=Quantity;
            String[] Quty=Qty.split(",");
            String[] cantidad = new String[Quty.length];

            for (int i = 0; i < codeArt.length; i++) {
                try {
                    codeArt[i]=codeArticulo[i];
                    cantidad[i]=Quty[i];
                } catch (Exception e) {
                }
            }
            
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..APS_ENMT " +
                                            "           ([DocDate] " +
                                            "           ,[idUsuario] " +
                                            "           ,[idAPS_OWOR] " +
                                            "           ,[estado]) " +
                                            "     VALUES " +
                                            "           (GETDATE(),?,(SELECT K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.docNum=?),'S'); ");
            
            ps.setInt(1, idUsuario);
            ps.setInt(2, numOrden);
            res=ps.executeUpdate();
            if(res>0){
                res=0;
                for (int i = 0; i < codeArt.length; i++) {
                    ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..[APS_ENMT1] " +
                                                    "           ([itemCode] " +
                                                    "           ,[itemName] " +
                                                    "           ,[cantidad] " +
                                                    "           ,[idAPS_ENMT]) " +
                                                    "     VALUES " +
                                                    "           (?,(SELECT K0.itemName FROM OITM K0 WHERE K0.itemCode=?),?,(SELECT TOP 1 K0.idAPS_ENMT FROM SDO_ALSASA..APS_ENMT K0 WHERE K0.idUsuario=? ORDER BY K0.idAPS_ENMT DESC)) ");
                    
                    ps.setString(1, codeArt[i]);
                    ps.setString(2, codeArt[i]);
                    ps.setString(3, cantidad[i]);
                    ps.setDouble(4, idUsuario);
                    res=ps.executeUpdate();
                }
                
            }
        }catch(Exception ex){
                ps=con().prepareStatement("DELETE FROM SDO_ALSASA..APS_ENMT WHERE idAPS_ENMT=(SELECT TOP 1 T0.idAPS_ENMT FROM SDO_ALSASA..APS_ENMT T0 ORDER BY T0.idAPS_ENMT DESC)");
                ps.executeUpdate();
                System.out.println("modelo.DaoAPS_OIGE3.guardarENMT(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<Object> mostrarENMT(String docnum) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.idAPS_ENMT, " +
                                            "	ISNULL(CONVERT(VARCHAR,T0.docNum),'') docNum, " +
                                            "	CAST(T0.DocDate AS DATE) docDate, " +
                                            "	(SELECT k0.Usuario FROM SDO_ALSASA..USUARIO K0 WHERE K0.idUsuario=T0.idUsuario) usuario, " +
                                            "	CASE  " +
                                            "		WHEN T0.estado='W' THEN 'EN ESPERA DE CARGA' " +
                                            "		WHEN T0.estado='C' THEN 'CANCELADO' " +
                                            "		WHEN T0.estado='S' THEN 'CARGADO A SAP' " +
                                            "	END estado " +
                                            "FROM SDO_ALSASA..APS_ENMT T0  " +
                                            "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                            "WHERE T1.docNum=?");
            ps.setString(1,docnum);
            rs = ps.executeQuery();
            
            while(rs.next()){
               APS_ENMT= new APS_ENMT();
               APS_ENMT.setIdAPS_ENMT(rs.getInt(1));
               APS_ENMT.setDocNum(rs.getString(2));
               APS_ENMT.setDocDate(rs.getString(3));
               APS_ENMT.setUsuario(rs.getString(4));
               APS_ENMT.setEstado(rs.getString(5));
               ar.add(APS_ENMT);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.mostrarENMT(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarENMT1(String id) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps=super.con().prepareStatement("SELECT   " +
                                            "    T0.idAPS_ENMT1,  " +
                                            "    T0.itemCode,  " +
                                            "    (SELECT K0.itemName FROM  OITM K0 WHERE K0.itemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) itemName,   " +
                                            "    (SELECT TOP 1 K0.wareHouse FROM  WOR1 K0 JOIN OWOR K1 ON K0.DocEntry=K1.DocEntry WHERE K1.docNum=T2.docNum AND K0.itemcode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) bodega,  " +
                                            "    (SELECT J0.Onhand FROM  OITW J0 WHERE J0.itemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS AND J0.WhsCode=(SELECT TOP 1 K0.wareHouse FROM  WOR1 K0 JOIN  OWOR K1 ON K0.DocEntry=K1.DocEntry WHERE K1.docNum=T2.docNum AND K0.itemcode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ORDER BY K0.docEntry DESC)) stock,  " +
                                            "    T0.cantidad  " +
                                            "FROM SDO_ALSASA..APS_ENMT1 T0   " +
                                            "JOIN SDO_ALSASA..APS_ENMT T1 ON T1.idAPS_ENMT=t0.idAPS_ENMT " +
                                            "JOIN SDO_ALSASA..APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR  " +
                                            "WHERE T0.idAPS_ENMT=?");
            ps.setString(1,id);
            rs = ps.executeQuery();
            
            while(rs.next()){
               APS_ENMT1= new APS_ENMT1();
               APS_ENMT1.setIdAPS_ENMT1(rs.getInt(1));
               APS_ENMT1.setItemCode(rs.getString(2));
               APS_ENMT1.setItemName(rs.getString(3));
               APS_ENMT1.setBodega(rs.getString(4));
               APS_ENMT1.setStock(rs.getString(5));
               APS_ENMT1.setCantidad(rs.getString(6));
               ar.add(APS_ENMT1);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE3.mostrarENMT1(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int cancelarENMT(String id) throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_ENMT SET estado='C' WHERE idAPS_ENMT=? ");
            
            ps.setString(1, id);
            res=ps.executeUpdate();
        }catch(Exception ex){
                System.out.println("modelo.DaoAPS_OIGE3.cancelarENMT(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    
    public int cancelarENMTOrden(String id) throws ClassNotFoundException, SQLException{
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_ENMT SET estado='C' WHERE idAPS_OWOR=(SELECT idAPS_OWOR FROM SDO_ALSASA..APS_OWOR WHERE docNum=?) ");
            
            ps.setString(1, id);
            res=ps.executeUpdate();
        }catch(Exception ex){
                System.out.println("modelo.DaoAPS_OIGE3.cancelarENMTOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
