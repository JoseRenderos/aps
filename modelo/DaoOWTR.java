/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.APS_OIGE;
import entidades.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoOWTR extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    OWTR owtr; 
    Articulo articulo;
    APS_OWTR APS_OWTR;
    APS_WTR1 APS_WTR1;
    
    public ArrayList<OWTR> mostrar(String numOrden) throws ClassNotFoundException, SQLException {
         ArrayList<OWTR> ar = new ArrayList<OWTR>();
        try {
            ps=super.con().prepareStatement("SELECT " +
                                            "	T1.docnum 'Num Traslado', " +
                                            "	T0.ItemCode 'Codigo', " +
                                            "	(SELECT ItemName FROM OITM K0 WHERE K0.ItemCode=T0.ItemCode) 'Nombre', " +
                                            "	CONVERT(numeric(16,2),T0.Quantity) 'Cantidad', " +
                                            "	CONVERT(VARCHAR(10),T0.ShipDate,3) 'Fecha de entrega', " +
                                            "	T0.WhsCode 'Bodega' " +
                                            "FROM WTR1 T0 " +
                                            "JOIN OWTR T1 ON T1.DocEntry = T0.DocEntry " +
                                            "JOIN OWOR T2 ON CONVERT(VARCHAR, T2.DocNum)=T0.U_OrdenProduccion "+
                                            "WHERE T2.docnum=?  "+
                                            "UNION ALL  " +
                                            "SELECT   " +
                                            "	T1.idAPS_OWTR 'Num Traslado',  " +
                                            "	T0.ItemCode COLLATE SQL_Latin1_General_CP1_CI_AS 'Codigo',   " +
                                            "	(SELECT ItemName FROM OITM K0 WHERE K0.ItemCode=T0.ItemCode COLLATE SQL_Latin1_General_CP1_CI_AS) 'Nombre',   " +
                                            "	CONVERT(numeric(16,2),T0.Quantity) 'Cantidad',   " +
                                            "	CONVERT(VARCHAR(10),T1.docDate,3) 'Fecha de entrega',   " +
                                            "	T0.WhsCode COLLATE SQL_Latin1_General_CP1_CI_AS 'Bodega'   " +
                                            "FROM SDO_ALSASA..APS_WTR1 T0   " +
                                            "JOIN SDO_ALSASA..APS_OWTR T1 ON T0.idAPS_OWTR = T1.idAPS_OWTR " +
                                            "WHERE T0.OrdenProduccion=? AND T1.estado='W'");
            ps.setString(1, numOrden);
            ps.setString(2, numOrden);
            rs=ps.executeQuery();
            while(rs.next()){
                owtr = new OWTR();
                owtr.setDocNum(rs.getString(1));
                owtr.setItemCode(rs.getString(2));
                owtr.setItemName(rs.getString(3));
                owtr.setQuantity(rs.getString(4));
                owtr.setShipDate(rs.getString(5));
                owtr.setBodega(rs.getString(6));
                ar.add(owtr);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }

    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Articulo> mostrarArticulos() throws ClassNotFoundException, SQLException {
         ArrayList<Articulo> ar = new ArrayList<Articulo>();
        try {
            ps=super.con().prepareStatement("SELECT   " +
                                            "	T0.itemCode,  " +
                                            "	T0.ItemName  " +
                                            "FROM OITM T0   " +
                                            "WHERE T0.ItemCode NOT LIKE '%-EXP%'  " +
                                            "AND T0.ItemCode NOT LIKE '%-AV%'  " +
                                            "AND T0.ItemCode NOT LIKE '%JU%'  " +
                                            "AND T0.ItemCode NOT LIKE '%CM-%'  " +
                                            "AND T0.ItemCode NOT LIKE '%-2DA%'  " +
                                            "AND T0.ItemCode NOT LIKE '%YT-4679B%' " +
                                            "AND T0.ItemCode NOT LIKE '%MOB-%' " +
                                            "AND T0.ItemCode NOT LIKE '%MAQ-%' " +
                                            "AND T0.frozenFor='N' " +
                                            "AND T0.ItemName NOT LIKE '%INACTIVO%' ");
            rs=ps.executeQuery();
            while(rs.next()){
                articulo = new Articulo();
                articulo.setCodeArticulo(rs.getString(1));
                articulo.setNomArticulo(rs.getString(2));
                ar.add(articulo);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.mostrarArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Articulo> mostrarArticulosCINV() throws ClassNotFoundException, SQLException {
         ArrayList<Articulo> ar = new ArrayList<Articulo>();
        try {
            ps=super.con().prepareStatement("SELECT   " +
                                            "	T0.itemCode,  " +
                                            "	T0.ItemName  " +
                                            "FROM OITM T0   " +
                                            "WHERE T0.ItemCode NOT LIKE '%-EXP%'  " +
                                            "AND T0.ItemCode NOT LIKE '%YT-4679B%' " +
                                            "AND T0.ItemCode NOT LIKE '%MOB-%' " +
                                            "AND T0.ItemCode NOT LIKE '%MAQ-%' " +
                                            "AND T0.frozenFor='N' " +
                                            "AND T0.ItemName NOT LIKE '%INACTIVO%' ");
            rs=ps.executeQuery();
            while(rs.next()){
                articulo = new Articulo();
                articulo.setCodeArticulo(rs.getString(1));
                articulo.setNomArticulo(rs.getString(2));
                ar.add(articulo);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.mostrarArticulosCINV(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public String obtenerCantBodega(String itemCode, String bodega) throws ClassNotFoundException, SQLException {
         String result="0.000000";
        try {
            ps=super.con().prepareStatement("SELECT T0.onHand FROM OITW T0 WHERE T0.itemCode=? AND T0.WhsCode=? ");
            ps.setString(1, itemCode);
            ps.setString(2, bodega);
            rs=ps.executeQuery();
            while(rs.next()){
                result=(rs.getString(1));
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.obtenerCantBodega(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    public int guardarTransferenciaAPS(String ItemCode,String FromWhsCod,String WhsCode,String Quantity,String OrdenProduccion,String Comments,String docDate,int idUsuario) throws ClassNotFoundException, SQLException{
        try{
            
            
            String codes=ItemCode;
            String[] codeArticulo=codes.split(",");
            String[] codeArt = new String[codeArticulo.length];

            String FromWhs=FromWhsCod;
            String[] bOrigen=FromWhs.split(",");
            String[] bodegaOrigen = new String[bOrigen.length];

            String Whs=WhsCode;
            String[] bDestino=Whs.split(",");
            String[] BodegaDestino = new String[bDestino.length];

            String Qty=Quantity;
            String[] Quty=Qty.split(",");
            String[] cantidad = new String[Quty.length];

            String orden=OrdenProduccion;
            String[] docnum=orden.split(",");
            String[] op = new String[docnum.length];

            for (int i = 0; i < codeArt.length; i++) {
                try {
                    codeArt[i]=codeArticulo[i];
                    bodegaOrigen[i]=bOrigen[i];
                    BodegaDestino[i]=bDestino[i];
                    cantidad[i]=Quty[i];
                    op[i]=docnum[i];
                } catch (Exception e) {
                }
            }
            
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..APS_OWTR(DocType,Filler,ToWhscode,Series,JrnlMemo,Comments,DocDate,idUsuario,estado) " +
                                            "VALUES('I',?,?,'182',(SELECT TOP 1 CONCAT('Traslado de Inventario Serie TRBOD002 No.',K0.docnum+1) FROM OWTR K0 WHERE K0.series='182' ORDER BY K0.docNum DESC),?,?,?,'W');");
            
            ps.setString(1, bodegaOrigen[0]);
            ps.setString(2, BodegaDestino[0]);
            ps.setString(3, Comments);
            ps.setString(4, docDate.replaceAll("-", ""));
            ps.setInt(5, idUsuario);
            res=ps.executeUpdate();
            System.out.println(op.length);
            if(res>0){
                res=0;
                for (int i = 0; i < codeArt.length; i++) {
                    ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..APS_WTR1(ItemCode,ItemName,FromWhsCod,WhsCode,Quantity,OrdenProduccion,idAPS_OWTR) " +
                                                " VALUES(?,(SELECT K0.itemName FROM OITM K0 WHERE K0.itemcode=?),?,?,?,?,(SELECT TOP 1 idAPS_OWTR FROM SDO_ALSASA..APS_OWTR T0 WHERE T0.idUsuario=? ORDER BY T0.idAPS_OWTR DESC)) ");
                    
                    ps.setString(1, codeArt[i]);
                    ps.setString(2, codeArt[i]);
                    ps.setString(3, bodegaOrigen[i]);
                    ps.setString(4, BodegaDestino[i]);
                    ps.setString(5, cantidad[i]);
                    ps.setString(6, op[i]);
                    ps.setDouble(7, idUsuario);
                    res=ps.executeUpdate();
                }
                
            }
        }catch(Exception ex){
                ps=con().prepareStatement("DELETE FROM SDO_ALSASA..APS_OWTR WHERE idAPS_OWTR=(SELECT TOP 1 T0.idAPS_OWTR FROM SDO_ALSASA..APS_OWTR T0 ORDER BY T0.idAPS_OWTR DESC)");
                ps.executeUpdate();
                System.out.println("modelo.DaoOWTR.guardarTransferenciaAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<APS_OWTR> mostrarTrasferencias() throws ClassNotFoundException, SQLException {
         ArrayList<APS_OWTR> ar = new ArrayList<APS_OWTR>();
        try {
            ps=super.con().prepareStatement("SELECT [idAPS_OWTR] " +
                                            "      ,ISNULL(CAST([DocNum] AS VARCHAR),'') DocNum " +
                                            "      ,[DocType] " +
                                            "      ,[Filler] " +
                                            "      ,[ToWhscode] " +
                                            "      ,[Series] " +
                                            "      ,[JrnlMemo] " +
                                            "      ,[Comments] " +
                                            "      ,CAST([DocDate] AS DATE) DocDate " +
                                            "      ,[idUsuario] " +
                                            "      ,CASE WHEN [estado]='Y' THEN 'TRANSFERENCIA CARGADA' WHEN [estado]='N' THEN 'TRANSFERENCIA CANCELADA' ELSE 'EN ESPERA' END estado " +
                                            "  FROM SDO_ALSASA..[APS_OWTR] T0 WHERE T0.estado!='N'  ");
            rs=ps.executeQuery();
            while(rs.next()){
                APS_OWTR = new APS_OWTR();
                APS_OWTR.setIdAPS_OWTR(rs.getInt(1));
                APS_OWTR.setDocNum(rs.getString(2));
                APS_OWTR.setDocType(rs.getString(3));
                APS_OWTR.setFiller(rs.getString(4));
                APS_OWTR.setToWhscode(rs.getString(5));
                APS_OWTR.setSeries(rs.getString(6));
                APS_OWTR.setJrnlMemo(rs.getString(7));
                APS_OWTR.setComments(rs.getString(8));
                APS_OWTR.setDocDate(rs.getString(9));
                APS_OWTR.setIdUsuario(rs.getInt(10));
                APS_OWTR.setEstado(rs.getString(11));
                ar.add(APS_OWTR);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.mostrarTrasferencias(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_WTR1> mostrarLineasTrasferencia(int id) throws ClassNotFoundException, SQLException {
         ArrayList<APS_WTR1> ar = new ArrayList<APS_WTR1>();
        try {
            ps=super.con().prepareStatement("SELECT ROW_NUMBER() OVER(ORDER BY [idAPS_WTR1] DESC) linenum " +
                                            "      ,[ItemCode] " +
                                            "      ,[ItemName] " +
                                            "      ,[FromWhsCod] " +
                                            "      ,(SELECT K0.onHand FROM OITW K0 WHERE K0.itemCode=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS AND K0.WhsCode=T0.FromWhsCod COLLATE SQL_Latin1_General_CP850_CI_AS) stockOrigen " +
                                            "      ,[WhsCode] " +
                                            "      ,(SELECT K0.onHand FROM OITW K0 WHERE K0.itemCode=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS AND K0.WhsCode=T0.WhsCode COLLATE SQL_Latin1_General_CP850_CI_AS) stockDestino " +
                                            "      ,[Quantity] " +
                                            "      ,[OrdenProduccion] " +
                                            "      ,[idAPS_OWTR] " +
                                            "  FROM [SDO_ALSASA].[dbo].[APS_WTR1] T0 WHERE T0.[idAPS_OWTR]=?  ");
            ps.setInt(1, id);
            rs=ps.executeQuery();
            while(rs.next()){
                APS_WTR1 = new APS_WTR1();
                APS_WTR1.setIdAPS_WTR1(rs.getInt(1));
                APS_WTR1.setItemCode(rs.getString(2));
                APS_WTR1.setItemName(rs.getString(3));
                APS_WTR1.setFromWhsCod(rs.getString(4));
                APS_WTR1.setStockOrigen(rs.getString(5));
                APS_WTR1.setWhsCode(rs.getString(6));
                APS_WTR1.setStockDestino(rs.getString(7));
                APS_WTR1.setQuantity(rs.getString(8));
                APS_WTR1.setOrdenProduccion(rs.getString(9));
                APS_WTR1.setIdAPS_OWTR(rs.getInt(10));
                ar.add(APS_WTR1);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWTR.mostrarLineasTrasferencias(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int cancelarTransferencia(int id) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWTR "+ 
                                      "SET estado='N' "+ 
                                      "WHERE idAPS_OWTR=?");
            ps.setInt(1, id);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.cancelarTransferencia(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
