/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.*;
import entidades.*;
import java.text.*;
/**
 *
 * @author Mario Valdez
 */
public class DaoCINV extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Usuario us;
    CINV CINV;
    CINV1 CINV1;
    OBIN OBIN;
    OWHS OWHS;
    
    public int guardarCINV(String ItemCode,String Comments,int idUsuario,String fecha,String bodegas) throws ClassNotFoundException, SQLException{
        try{
            
            
            String codes=ItemCode;
            String[] codeArticulo=codes.split(",");
            String[] codeArt = new String[codeArticulo.length];
            String whs=bodegas;
            String[] bg=whs.split(",");
            String[] bodega = new String[bg.length];

            for (int i = 0; i < codeArt.length; i++) {
                try {
                    codeArt[i]=codeArticulo[i];
                    bodega[i]=bg[i];
                } catch (Exception e) {
                }
            }
            
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..[CINV] " +
                                            "           ([docDate] " +
                                            "           ,[comentario] " +
                                            "           ,[idUsuario] " +
                                            "           ,[bodega]) " +
                                            "     VALUES " +
                                            "           (?,?,?,?)");
            
            ps.setString(1, fecha.replaceAll("-", ""));
            ps.setString(2, Comments);
            ps.setInt(3, idUsuario);
            ps.setString(4, bodega[0]);
            res=ps.executeUpdate();
            if(res>0){
                res=0;
                for (int i = 0; i < codeArt.length; i++) {
                    ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..[CINV1] " +
                                                    "           ([itemcode] " +
                                                    "           ,[itemName] " +
                                                    "           ,[id_CINV] " +
                                                    "           ,[bodega]) " +
                                                    "     VALUES " +
                                                    "           (?,(SELECT K0.itemName FROM OITM K0 WHERE K0.itemcode=?),(SELECT TOP 1 T0.id_CINV FROM SDO_ALSASA..[CINV] T0 WHERE T0.idUsuario=? ORDER BY T0.id_CINV DESC),?)");
                    
                    ps.setString(1, codeArt[i]);
                    ps.setString(2, codeArt[i]);
                    ps.setDouble(3, idUsuario);
                    ps.setString(4, bodega[i]);
                    res=ps.executeUpdate();
                }
                
            }
        }catch(Exception ex){
                ps=con().prepareStatement("DELETE FROM SDO_ALSASA..CINV WHERE id_CINV=(SELECT TOP 1 T0.id_CINV FROM SDO_ALSASA..CINV T0 ORDER BY T0.id_CINV DESC)");
                ps.executeUpdate();
                System.out.println("modelo.DaoCINV.guardarCINV(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    
    public int actualizarCantidades(String ItemCode,String cantidad,String Comments,String bodega,String ubicacion,int idUsuario,int id) throws ClassNotFoundException, SQLException{
        try{
            
            String codes=ItemCode;
            String[] codeArticulo=codes.split(",");
            String[] codeArt = new String[codeArticulo.length];
            
            String cant=cantidad;
            String[] Qty=cant.split(",");
            String[] Quantity = new String[Qty.length];
            
            String Com=Comments;
            String[] comentario=Com.split(",");
            String[] comentarios = new String[comentario.length];
            
            String bog=bodega;
            String[] whs=bog.split(",");
            String[] bodegas = new String[whs.length];
            
            String ubi=ubicacion;
            String[] ubic=ubi.split(",");
            String[] ubicaciones = new String[ubic.length];

            for (int i = 0; i < codeArt.length; i++) {
                try {
                    codeArt[i]=codeArticulo[i];
                    Quantity[i]=Qty[i];
                    comentarios[i]=comentario[i];
                    ubicaciones[i]=ubic[i];
                    bodegas[i]=whs[i];
                } catch (Exception e) {
                }
            }
            
            for (int i = 0; i < codeArt.length; i++) {
                ps=super.con().prepareStatement("INSERT INTO [SDO_ALSASA]..[CINV2] " +
                                                "           ([itemCode] " +
                                                "           ,[cantidad] " +
                                                "           ,[comentario] " +
                                                "           ,[ubicacion] " +
                                                "           ,[avgPrice] " +
                                                "           ,[stockSAP] " +
                                                "           ,[fechaConteo] " +
                                                "           ,[idUsuario] " +
                                                "           ,[id_CINV1] " +
                                                "           ,[bodega]) " +
                                                "     VALUES " +
                                                "           (?,?,?,?,(SELECT T0.AVGPRICE FROM OITM T0 WHERE T0.ItemCode=?),(SELECT SUM(K0.onHand) FROM OITW K0 WHERE K0.itemCode=?),GETDATE(),?,(SELECT K0.id FROM SDO_ALSASA..CINV1 K0 WHERE K0.itemcode=? AND K0.id_CINV=?),?) ");

                ps.setString(1, codeArt[i]);
                ps.setString(2, Quantity[i]);
                ps.setString(3, comentarios[i]);
                ps.setString(4, ubicaciones[i]);
                ps.setString(5, codeArt[i]);
                ps.setString(6, codeArt[i]);
                ps.setInt(7, idUsuario);
                ps.setString(8, codeArt[i]);
                ps.setInt(9, id);
                ps.setString(10, bodegas[i]);
                res=ps.executeUpdate();
            }
            
            for (int i = 0; i < codeArt.length; i++) {
                ps=super.con().prepareStatement("UPDATE SDO_ALSASA..CINV1 " +
                                                " SET fechaConto=GETDATE(), "+
                                                " cantidadContada=(SELECT SUM(T0.cantidad) FROM SDO_ALSASA..CINV2 T0 WHERE T0.id_CINV1=(SELECT K0.id FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=? AND K0.itemCode=? )) "+
                                                "WHERE id_CINV=? AND itemcode=? ");

                ps.setInt(1, id);
                ps.setString(2, codeArt[i]);
                ps.setInt(3, id);
                ps.setString(4, codeArt[i]);
                res=ps.executeUpdate();
            }
        }catch(Exception ex){
                System.out.println("modelo.DaoCINV.actualizarCantidades(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<CINV> mostrar() throws ClassNotFoundException, SQLException {
         ArrayList<CINV> ar = new ArrayList<CINV>();
        try {
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.id_CINV,  " +
                                            "	CAST(T0.docDate AS DATE) docDate, " +
                                            "	T0.comentario, " +
                                            "	(SELECT K0.usuario FROM SDO_ALSASA..USUARIO K0 WHERE K0.idUsuario=T0.idUsuario) usuario, " +
                                            "   T0.bodega, " + 
                                            "   CASE " +
                                            "		WHEN (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV)=(SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV AND K0.estado='O') " +
                                            "		THEN 'SIN CONTEO' " +
                                            "		WHEN (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV) > (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV AND K0.estado='R') " +
                                            "		THEN 'EN PROCESO DE CONTEO' " +
                                            "		WHEN (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV) = (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV AND K0.estado='R') " +
                                            "		THEN 'CONTADO' " +
                                            "		WHEN (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV) > (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV AND K0.estado='L') " +
                                            "		THEN 'EN PROCESO DE AUDITORIA' " +
                                            "		WHEN (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV) = (SELECT COUNT(*) FROM SDO_ALSASA..CINV1 K0 WHERE K0.id_CINV=T0.id_CINV AND K0.estado='L') " +
                                            "		THEN 'AUDITADO' " +
                                            "	END " +
                                            "FROM SDO_ALSASA..CINV T0");
            rs=ps.executeQuery();
            while(rs.next()){
                CINV = new CINV();
                CINV.setId_CINV(rs.getInt(1));
                CINV.setDocDate(rs.getString(2));
                CINV.setComentario(rs.getString(3));
                CINV.setUsuario(rs.getString(4));
                CINV.setBodega(rs.getString(5));
                CINV.setEstado(rs.getString(6));
                ar.add(CINV);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoCINV.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<CINV1> mostrarLineas(int id) throws ClassNotFoundException, SQLException {
         ArrayList<CINV1> ar = new ArrayList<CINV1>();
        try {
            ps=super.con().prepareStatement("SELECT " +
                                            "	T0.id," +
                                            "	T0.itemcode,  " +
                                            "	T0.itemName,  " +
                                            "	(SELECT SUM(K0.onHand) FROM OITW K0 WHERE K0.itemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) stockSAP," +
                                            "	CAST(T0.fechaConto AS DATE) fechaConto," +
                                            "	ISNULL(T0.cantidadContada,0) cantidadContada,  " +
                                            "	'' comentarioConteo, " + 
                                            "	T0.estado " + 
                                            "FROM SDO_ALSASA..CINV1 T0 " +
                                            "WHERE T0.id_CINV=? ");
            ps.setInt(1, id);
            rs=ps.executeQuery();
            while(rs.next()){
                CINV1 = new CINV1();
                CINV1.setId(rs.getInt(1));
                CINV1.setItemcode(rs.getString(2));
                CINV1.setItemName(rs.getString(3));
                CINV1.setStockSAP(rs.getString(4));
                CINV1.setFechaConto(rs.getString(5));
                CINV1.setCantidadContada(rs.getString(6));
                CINV1.setComentarioConteo(rs.getString(7));
                CINV1.setEstado(rs.getString(8));
                ar.add(CINV1);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoCINV.mostrarLineas(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OBIN> listarUbicaciones(String bodega) throws ClassNotFoundException, SQLException {
         ArrayList<OBIN> ar = new ArrayList<OBIN>();
        try {
            ps=super.con().prepareStatement("SELECT T0.AbsEntry,T0.BinCode FROM OBIN T0 WHERE T0.WhsCode=?");
            ps.setString(1, bodega);
            rs=ps.executeQuery();
            while(rs.next()){
                OBIN = new OBIN();
                OBIN.setAbsEntry(rs.getInt(1));
                OBIN.setBinCode(rs.getString(2));
                ar.add(OBIN);
            }
            
        }catch(Exception e){
            System.out.println("modelo.DaoCINV.listarUbicaciones(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    
    public ArrayList<OWHS> listarBodega() throws ClassNotFoundException, SQLException {
         ArrayList<OWHS> ar = new ArrayList<OWHS>();
        try {
            ps=super.con().prepareStatement("SELECT T0.* FROM SDO_ALSASA..VW_BODEGAS T0");
            rs=ps.executeQuery();
            while(rs.next()){
                OWHS = new OWHS();
                OWHS.setCode(rs.getString(1));
                OWHS.setName(rs.getString(2));
                ar.add(OWHS);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoCINV.listarBodega(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int actualizarEstado(String ItemCode,String estado,int id) throws ClassNotFoundException, SQLException{
        try{
            
            String codes=ItemCode;
            String[] codeArticulo=codes.split(",");
            String[] codeArt = new String[codeArticulo.length];
            
            String es=estado;
            String[] est=es.split(",");
            String[] status = new String[est.length];

            for (int i = 0; i < codeArt.length; i++) {
                try {
                    codeArt[i]=codeArticulo[i];
                    status[i]=est[i];
                } catch (Exception e) {
                }
            }
            
            for (int i = 0; i < codeArt.length; i++) {
                ps=super.con().prepareStatement("UPDATE SDO_ALSASA..CINV1 " +
                                                " SET estado=? "+
                                                "WHERE id_CINV=? AND itemcode=? ");

                ps.setString(1, status[i]);
                ps.setInt(2, id);
                ps.setString(3, codeArt[i]);
                res=ps.executeUpdate();
            }
        }catch(Exception ex){
                System.out.println("modelo.DaoCINV.actualizarEstado(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
