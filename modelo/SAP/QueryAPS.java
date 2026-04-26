package modelo.SAP;

import java.sql.*;
import java.util.*;
import entidades.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Desarrollo Alsasa
 */
public class QueryAPS extends ConexionSQL{
    
    PreparedStatement ps;
    ResultSet rs;
    int res;
    OWOR or;
    APS_OIGE oige;
    APS_OIGE3 oige3;
    APS_OITT oitt;
    APS_IGN1 APS_IGN1;
    APS_OWTR aps_owtr;
    APS_WTR1 aps_wtr1;
    
    public ArrayList<OWOR> listarOrdenAPS(int docnum) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                              "T0.docNum, " +
                                              "T2.PlannedQty, " +
                                              "T2.startDate, " +
                                              "T0.itemCode, " +
                                              "ISNULL(T0.comentario,'') comentario " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=t0.idAPS_OWOR " +
                                              "JOIN OWOR T2 ON T0.docNum=T2.docnum " +
                                              "WHERE T0.docNum=? ");
            ps.setInt(1, docnum);
            rs = ps.executeQuery();
            while(rs.next()){
                or = new OWOR();
                or.setDocnum(rs.getInt(1));
                or.setCantpln(rs.getString(2));
                or.setStartdate(rs.getString(3));
                or.setItemcode(rs.getString(4));
                or.setComments(rs.getString(5));
                ar.add(or);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarOrdenAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> listarOrdenSBO(int docnum) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            ps=super.con().prepareStatement("SELECT DISTINCT " +
                                            "   T0.docNum, " +
                                            "   T0.PlannedQty, " +
                                            "   T0.startDate, " +
                                            "   T0.itemCode " +
                                            "FROM OWOR T0 " +
                                            "WHERE T0.docNum=?");
            ps.setInt(1, docnum);
            rs = ps.executeQuery();
            while(rs.next()){
                or = new OWOR();
                or.setDocnum(rs.getInt(1));
                or.setCantpln(rs.getString(2));
                or.setStartdate(rs.getString(3));
                or.setItemcode(rs.getString(4));
                ar.add(or);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarOrdenSBO(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int listarDocEntryOrden(int docNum) throws ClassNotFoundException, SQLException{
        int docEntry =0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "    T0.DocEntry "+ 
                                              "FROM OWOR T0 where T0.docNum=? ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                docEntry = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarDocEntryOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return docEntry;
    }
    
    public ArrayList<APS_OIGE> listarAPS_OIGE(int docnum) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "  T1.itemCode, " +
                                              "  REPLACE(T1.itemName, ' ', '') itemName, " +
                                              "  T0.codeEmp, " +
                                              "  SUBSTRING(T0.nomEmp,CHARINDEX('-', T0.nomEmp)+2, LEN(T0.nomEmp)) nomEmp, " +
                                              "  T0.actividad, " +
                                              "  ISNULL(T0.descActividad, '') 'descActividad', " +
                                              "  REPLACE(CONVERT(VARCHAR(5),T0.inicio,8), ':', '') inicio, " +
                                              "  REPLACE(CONVERT(VARCHAR(5),T0.fin,8), ':', '') fin, " +
                                              "  CONVERT(numeric(19,6),CONVERT(numeric(19,6), T0.totalTiempo)/60) TotalTiempo, " +
                                              "  T0.uniTotalesConformes, " +
                                              "  (SELECT SUM(K0.uniNoConformes+K0.uniRechazadas) FROM SDO_ALSASA..APS_OIGE1 K0 JOIN SDO_ALSASA..APS_OIGE K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE JOIN SDO_ALSASA..APS_OWOR K2 ON K2.idAPS_OWOR=T1.idAPS_OWOR WHERE k1.idAPS_OIGE=T0.idAPS_OIGE AND K2.docNum=T1.docNum) totalUniNoConformes, " +
                                              "  T0.codeActProd, " +
                                              "  T0.descActividadProd, " +
                                              "  T0.idAPS_OIGE " +
                                              "FROM SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T1.docnum=? AND T0.codeActProd IS NOT NULL AND T0.codeActProd!='' AND T0.estadoCarga=0");
            ps.setInt(1, docnum);
            rs = ps.executeQuery();
            
            while(rs.next()){
                OWOR owor = new OWOR();
                owor.setItemcode(rs.getString(1));
                owor.setItemname(rs.getString(2));
                APS_OIGE oige = new APS_OIGE();
                oige.setCodeEmp(rs.getInt(3));
                oige.setNomEmp(rs.getString(4));
                oige.setActividad(rs.getString(5));
                oige.setDescActividad(rs.getString(6));
                oige.setInicio(rs.getString(7));
                oige.setFin(rs.getString(8));
                oige.setTotalTiempo(rs.getString(9));
                oige.setTotalUnidadesConformes(rs.getInt(10));
                oige.setTotalUnidadesNoConformes(rs.getInt(11));
                oige.setCodeActProd(rs.getString(12));
                oige.setDescActividadProd(rs.getString(13));
                oige.setIdAPS_OIGE(rs.getInt(14));
                oige.setAPS_OWOR(owor);
                ar.add(oige);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarAPS_OIGE(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OITT> obtenerUltimaAct(String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        try {
            ps = super.con().prepareStatement("SELECT TOP 1"+ 
                                              "  T0.ultimaAct, "+ 
                                              "  T0.descActividad "+ 
                                              "FROM SDO_ALSASA..APS_OITT T0 "+ 
                                              "WHERE T0.itemCode=?");
            ps.setString(1, itemCode);
            rs=ps.executeQuery();
            while (rs.next()) {
                oitt = new APS_OITT();
                oitt.setActividad(rs.getString(1));
                oitt.setDescActividad(rs.getString(2));
                ar.add(oitt); 
            }
        } catch (Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.obtenerUltimaAct(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OITT> obtenerUltimaActSAP(String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        try {
            ps = super.con().prepareStatement("SELECT TOP 1 " +
                                              "  T1.Code, T1.Comment " +
                                              "FROM OITT T0 " +
                                              "JOIN ITT1 T1 ON T1.father=T0.code " +
                                              "WHERE T0.Code=? AND (SELECT K0.ResType FROM ORSC K0 WHERE K0.VisResCode=T1.Code)='L' AND T1.TYPE=290 ORDER BY visOrder DESC ");
            ps.setString(1, itemCode);
            rs=ps.executeQuery();
            while (rs.next()) {
                oitt = new APS_OITT();
                oitt.setActividad(rs.getString(1));
                oitt.setDescActividad(rs.getString(2));
                ar.add(oitt); 
            }
        } catch (Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.obtenerUltimaActSAP(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OITT> obtenerActividadL(String itemCode, String comment, String actividad) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        int con = 0;
        try {
            ps = super.con().prepareStatement("SELECT TOP 1 " +
                                              "T0.Code, " +
                                              "T0.VisOrder " +
                                              "FROM ITT1 T0 " +
                                              "JOIN OITT T1 ON T0.Father=T1.Code " +
                                              "WHERE ISNULL(T0.Comment,'')=? AND T1.Code=? AND T0.Code=? AND (SELECT ResType FROM ORSC WHERE ResCode=T0.Code)='L'");
            ps.setString(1, comment);
            ps.setString(2, itemCode);
            ps.setString(3, actividad);
            rs=ps.executeQuery();
            while (rs.next()) {
                oitt = new APS_OITT();
                oitt.setActividad(rs.getString(1));
                oitt.setVisOrder(rs.getInt(2));
                ar.add(oitt); 
            }
        } catch (Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.obtenerActividadL(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OITT> obtenerActividadM(String itemCode, String comment) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        try{
            ps = super.con().prepareStatement("SELECT TOP 1 " +
                                              "  T0.Code, " +
                                              "  T0.VisOrder " +
                                              "FROM ITT1 T0 " +
                                              "JOIN OITT T1 ON T0.Father=T1.Code " +
                                              "WHERE ISNULL(T0.Comment,'')=? AND T1.Code=? AND (SELECT ResType FROM ORSC WHERE ResCode=T0.Code)='M'");
            ps.setString(1, comment);
            ps.setString(2, itemCode);
            rs = ps.executeQuery();
            while(rs.next()){
                oitt = new APS_OITT();
                oitt.setActividad(rs.getString(1)); 
                oitt.setVisOrder(rs.getInt(2));
                ar.add(oitt);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerActividadM(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OITT> obtenerActividadEmpaque(String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "  T0.Code, " +
                                              "  T0.VisOrder " +
                                              "FROM ITT1 T0 " +
                                              "JOIN OITT T1 ON T0.Father=T1.Code " +
                                              "WHERE T0.Code LIKE ? AND T1.Code=? AND (SELECT ResType FROM ORSC WHERE ResCode=T0.Code)='M'");
            ps.setString(1, "%114Fempacar%");
            ps.setString(2, itemCode);
            rs = ps.executeQuery();
            while(rs.next()){
                oitt = new APS_OITT();
                oitt.setActividad(rs.getString(1)); 
                oitt.setVisOrder(rs.getInt(2));
                ar.add(oitt);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerActividadEmpaque(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int obtenerLineNumT(int docNum, int visOrder) throws ClassNotFoundException, SQLException{
        int lineNum =-1;
        try{
            ps = super.con().prepareStatement("SELECT TOP 1 " +
                                                "T0.LineNum " +
                                                "FROM WOR1 T0 " +
                                                "JOIN OWOR T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE T1.docNum=? AND T0.visOrder=?");
            ps.setInt(1, docNum);
            ps.setInt(2, visOrder);
            rs = ps.executeQuery();
            while(rs.next()){
                lineNum = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerLineNumT(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return lineNum;
    }
    
    
    public int validarLineaOrden(int docNum, int visOrder, String actividad) throws ClassNotFoundException, SQLException{
        int conta =0;
        try{
            ps = super.con().prepareStatement("SELECT COUNT(*) " +
                                              "FROM WOR1 T0 " +
                                              "JOIN OWOR T1 ON T0.DocEntry=T1.DocEntry " +
                                              "WHERE T0.VisOrder=? AND T0.ItemCode!=? AND T1.DocNum=?");
            ps.setInt(1, visOrder);
            ps.setString(2,actividad);
            ps.setInt(3, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                conta = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.validarLineaOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return conta;
    }
    
    public int obtenerLineNum(int docNum, String actividad, int visOrder) throws ClassNotFoundException, SQLException{
        int lineNum =-1;
        try{
            ps = super.con().prepareStatement("SELECT TOP 1 "+ 
                                              "  T0.LineNum "+ 
                                              "FROM WOR1 T0 "+ 
                                              "JOIN OWOR T1 ON T0.DocEntry=T1.DocEntry "+ 
                                              "WHERE T1.DOCNUM=? and T0.ItemCode like ? AND T0.visOrder=?");
            ps.setInt(1, docNum);
            ps.setString(2, "%" + actividad + "%");
            ps.setInt(3, visOrder);
            rs = ps.executeQuery();
            while(rs.next()){
                lineNum = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerLineNum(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return lineNum;
    }
    
    public int obtenerLineNum(int docNum, String actividad) throws ClassNotFoundException, SQLException{
        int lineNum =-1;
        try{
            ps = super.con().prepareStatement("SELECT TOP 1 "+ 
                                              "  T0.LineNum "+ 
                                              "FROM WOR1 T0 "+ 
                                              "JOIN OWOR T1 ON T0.DocEntry=T1.DocEntry "+ 
                                              "WHERE T1.DOCNUM=? and T0.ItemCode=? ");
            ps.setInt(1, docNum);
            ps.setString(2, actividad);
            rs = ps.executeQuery();
            while(rs.next()){
                lineNum = rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerLineNum(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return lineNum;
    }
    
    public String obtenerBodega(String itemCode) throws ClassNotFoundException, SQLException{
        String bodega ="";
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "   T0.ToWH " +
                                              "FROM OITT T0 " +
                                              "WHERE T0.Code=?");
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            while(rs.next()){
                bodega = rs.getString(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerBodega(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return bodega;
    }
    
    public ArrayList<OWOR> comprobarProduccion(int docNum) throws ClassNotFoundException, SQLException{
        ArrayList<OWOR> ar = new ArrayList<>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "  T0.PlannedQty, " +
                                              "  (T0.CmpltQty+T0.RjctQty) produccion, " + 
                                              "  T0.CmpltQty completado " +
                                              "FROM OWOR T0 " +
                                              "WHERE T0.DocNum=?");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                OWOR or = new OWOR();
                or.setCantpln(rs.getString(1));
                or.setCantcmp(rs.getString(2));
                or.setCantProcesos(rs.getString(3));
                ar.add(or);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.comprobarProduccion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
//    public ArrayList<APS_OIGE3> obtenerMateriales(int docNum, String itemCode) throws ClassNotFoundException, SQLException{
//        ArrayList<APS_OIGE3> ar1 = new ArrayList<APS_OIGE3>();
//        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
//        ar.addAll((Collection)obtenerUltimaActSAP(itemCode));
//        int count=0;
//        try {
//            for (APS_OITT oitt : ar) {
//                ps=super.con().prepareStatement("SELECT COUNT(T1.idAPS_OIGE3) CANT " +
//                                                "FROM SDO_ALSASA..APS_OIGE T0 " +
//                                                "JOIN SDO_ALSASA..APS_OIGE3 T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
//                                                "JOIN SDO_ALSASA..APS_OWOR T2 ON T0.idAPS_OWOR=T2.idAPS_OWOR " +
//                                                "WHERE T2.docNum=?");
//                ps.setInt(1, docNum);
//                rs= ps.executeQuery();
//                while (rs.next()) {
//                    count=rs.getInt(1);
//                }
//                if (count>0) {
//                    if (oitt.getActividad().contains("MLINEA1")) {
//                        ps=super.con().prepareStatement("SELECT DISTINCT " +
//                                                        "T0.itemCode, " +
//                                                        "(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) itemname, " +
//                                                        "(SELECT TOP 1 " +
//                                                        "	K0.cantAsignada " +
//                                                        "	FROM SDO_ALSASA..APS_OIGE3 K0 " +
//                                                        "	JOIN SDO_ALSASA..APS_OIGE K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE " +
//                                                        "	JOIN SDO_ALSASA..APS_OWOR K2 ON K1.idAPS_OWOR=K2.idAPS_OWOR " +
//                                                        "	WHERE K0.itemCode=T0.itemCode and K2.docnum=T2.docNum ORDER BY K0.cantAsignada DESC) cantAsignada, " +
//                                                        "   ISNULL((SELECT  " +
//                                                        "		K0.OnHand  " +
//                                                        "	FROM OITW K0  " +
//                                                        "	WHERE K0.itemcode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
//                                                        "	AND K0.whsCode=(SELECT TOP 1 " +
//                                                        "			J1.Warehouse  " +
//                                                        "		FROM OWOR J0  " +
//                                                        "		JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry  " +
//                                                        "		WHERE J0.DocNum=T2.docNum AND J1.ItemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS)),0) stock, " +
//                                                        "	(SELECT TOP 1 " +
//                                                        "		J1.Warehouse  " +
//                                                        "	FROM OWOR J0  " +
//                                                        "	JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry  " +
//                                                        "	WHERE J0.DocNum=T2.docNum AND J1.ItemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) bodega " +
//                                                        "FROM SDO_ALSASA..APS_OIGE3 T0 " +
//                                                        "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
//                                                        "JOIN SDO_ALSASA..APS_OWOR T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
//                                                        "WHERE T2.docNum=? GROUP BY T0.itemCode,T0.itemName,T0.cantAsignada, T2.docnum, T0.cantAsignada ");
//                        ps.setInt(1, docNum);
//                        rs= ps.executeQuery();
//                        while (rs.next()) {                
//                            oige3 = new APS_OIGE3();
//                            oige3.setItemCode(rs.getString(1));
//                            oige3.setItemName(rs.getString(2));
//                            oige3.setCantAsignada(rs.getString(3));
//                            oige3.setStock(rs.getString(4));
//                            oige3.setBodega(rs.getString(5));
//                            ar1.add(oige3);
//                        }
//                        
//                    }else{
//                        ps=super.con().prepareStatement("SELECT DISTINCT " +
//                                                        "T0.itemCode, " +
//                                                        "(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) itemname, " +
//                                                        "SUM(CAST(T0.cantAsignada AS FLOAT)) cantAsignada, " +
//                                                        "	ISNULL((SELECT  " +
//                                                        "		K0.OnHand  " +
//                                                        "	FROM OITW K0  " +
//                                                        "	WHERE K0.itemcode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
//                                                        "	AND K0.whsCode=(SELECT TOP 1 " +
//                                                        "			J1.Warehouse  " +
//                                                        "		FROM OWOR J0  " +
//                                                        "		JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry  " +
//                                                        "		WHERE J0.DocNum=T2.docNum AND J1.ItemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS)),0) stock, " +
//                                                        "	(SELECT TOP 1 " +
//                                                        "		J1.Warehouse  " +
//                                                        "	FROM OWOR J0  " +
//                                                        "	JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry  " +
//                                                        "	WHERE J0.DocNum=T2.docNum AND J1.ItemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) bodega " +
//                                                        "FROM SDO_ALSASA..APS_OIGE3 T0 " +
//                                                        "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
//                                                        "JOIN SDO_ALSASA..APS_OWOR T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
//                                                        "WHERE T2.docNum=? GROUP BY T0.itemCode,T2.docNum ");
//                        ps.setInt(1, docNum);
//                        rs= ps.executeQuery();
//                        while (rs.next()) {                
//                            oige3 = new APS_OIGE3();
//                            oige3.setItemCode(rs.getString(1));
//                            oige3.setItemName(rs.getString(2));
//                            oige3.setCantAsignada(rs.getString(3));
//                            oige3.setStock(rs.getString(4));
//                            oige3.setBodega(rs.getString(5));
//                            ar1.add(oige3);
//                        }
//                    }
//                        
//                } else {
//                    if (oitt.getActividad().contains("MLINEA1")) {
//                        ps=super.con().prepareStatement("SELECT DISTINCT " +
//                                                        "SUBSTRING(T0.insumo,0, CHARINDEX(' - ', T0.insumo)) itemCode, " +
//                                                        "SUBSTRING(T0.insumo, CHARINDEX(' - ', T0.insumo)+3, LEN(T0.insumo)) itemName, " +
//                                                        "T0.cantAsignada " +
//                                                        "FROM SDO_ALSASA..APS_OIGE T0 " +
//                                                        "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
//                                                        "WHERE T1.docNum=? GROUP BY T0.insumo,T0.cantAsignada ");
//                        ps.setInt(1, docNum);
//                        rs= ps.executeQuery();
//                        while (rs.next()) {                
//                            oige3 = new APS_OIGE3();
//                            oige3.setItemCode(rs.getString(1));
//                            oige3.setItemName(rs.getString(2));
//                            oige3.setCantAsignada(rs.getString(3));
//                            ar1.add(oige3);
//                        }
//                    }else{
//                        ps=super.con().prepareStatement("SELECT DISTINCT " +
//                                                        "SUBSTRING(T0.insumo,0, CHARINDEX(' - ', T0.insumo)) itemCode, " +
//                                                        "SUBSTRING(T0.insumo, CHARINDEX(' - ', T0.insumo)+3, LEN(T0.insumo)) itemName, " +
//                                                        "ISNULL(SUM(CAST(T0.cantAsignada AS FLOAT)),0) cantAsignada  " +
//                                                        "FROM SDO_ALSASA..APS_OIGE T0 " +
//                                                        "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
//                                                        "WHERE T1.docNum=? AND T0.actividad=? GROUP BY T0.insumo");
//                        ps.setInt(1, docNum);
//                        ps.setString(2,oitt.getActividad());
//                        rs= ps.executeQuery();
//                        while (rs.next()) {                
//                            oige3 = new APS_OIGE3();
//                            oige3.setItemCode(rs.getString(1));
//                            oige3.setItemName(rs.getString(2));
//                            oige3.setCantAsignada(rs.getString(3));
//                            ar1.add(oige3);
//                        }
//                    }
//                }
//            }
//                
//        } catch (Exception ex) {
//            System.out.println("modelo.SAP.QueryAPS.obtenerMateriales(): " + ex.getMessage());
//        }finally{
//            super.con().close();
//            ps.close();
//        }
//        return ar1;
//    }
    
    public ArrayList<APS_OIGE3> obtenerMateriales(int docNum, String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OIGE3> ar1 = new ArrayList<APS_OIGE3>();
//        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
//        ar.addAll((Collection)obtenerUltimaActSAP(itemCode));
        int count=0;
        try {
                        ps=super.con().prepareStatement("SELECT  " +
                                                        "	T1.itemCode, " +
                                                        "	(SELECT K0.itemName FROM SBO_ALSASA..OITM K0 WHERE K0.itemCode=T1.itemCode) itemName, " +
                                                        "	SUM(CASE WHEN T1.itemCode LIKE '%SMED%' THEN 1 ELSE T1.plannedQty END) cantAsignada, " +
                                                        "	ISNULL((SELECT   " +
                                                        "        K0.OnHand   " +
                                                        "    FROM OITW K0   " +
                                                        "    WHERE K0.itemcode=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS  " +
                                                        "    AND K0.whsCode=(SELECT TOP 1  " +
                                                        "            J1.Warehouse   " +
                                                        "        FROM OWOR J0   " +
                                                        "        JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry   " +
                                                        "        WHERE J0.DocNum=T0.docNum AND J1.ItemCode=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS)),0) stock,  " +
                                                        "    (SELECT TOP 1  " +
                                                        "        J1.Warehouse   " +
                                                        "    FROM OWOR J0   " +
                                                        "    JOIN WOR1 J1 ON J0.DocEntry=J1.DocEntry   " +
                                                        "    WHERE J0.DocNum=T0.docNum AND J1.ItemCode=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) bodega " +
                                                        "FROM OWOR T0  " +
                                                        "JOIN WOR1 T1 ON T0.docEntry=T1.docEntry " +
                                                        "WHERE T1.itemType='4' " +
                                                        "AND T0.docNum=? " +
                                                        "GROUP BY T1.itemCode,T0.DocNum ");
                        ps.setInt(1, docNum);
                        rs= ps.executeQuery();
                        while (rs.next()) {                
                            oige3 = new APS_OIGE3();
                            oige3.setItemCode(rs.getString(1));
                            oige3.setItemName(rs.getString(2));
                            oige3.setCantAsignada(rs.getString(3));
                            oige3.setStock(rs.getString(4));
                            oige3.setBodega(rs.getString(5));
                            ar1.add(oige3);
                        }
                
        } catch (Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.obtenerMateriales(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar1;
    }
    
    public int obtenerUniConfomes(int docNum, String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        ar.addAll((Collection)obtenerUltimaAct(itemCode));
        int conformes = 0;
        for (APS_OITT oitt : ar) {
            try {
                if (oitt.getActividad().contains("MLINEA1")) {
                    
                    ps=super.con().prepareStatement("SELECT TOP 1" +
                                                    "   T0.uniTotalesConformes " +
                                                    "FROM SDO_ALSASA..APS_OIGE T0 " +
                                                    "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                                    "WHERE T1.docNum=? ORDER BY fin DESC");
                    ps.setInt(1, docNum);
                    rs= ps.executeQuery();
                    while (rs.next()) {                
                        conformes= rs.getInt(1);
                    }
                }else{
                    ps=super.con().prepareStatement("SELECT " +
                                                    "   SUM(T0.uniTotalesConformes) uniTotalesConformes " +
                                                    "FROM SDO_ALSASA..APS_OIGE T0 " +
                                                    "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                                    "WHERE T1.docNum=? AND T0.actividad=? AND T0.descActividad is null " +
                                                    "OR T1.docNum=? AND T0.actividad=? AND T0.descActividad=?");
                    ps.setInt(1, docNum);
                    ps.setString(2, oitt.getActividad());
                    ps.setInt(3, docNum);
                    ps.setString(4, oitt.getActividad());
                    ps.setString(5, oitt.getDescActividad());
                    rs= ps.executeQuery();
                    while (rs.next()) {                
                        conformes= rs.getInt(1);
                    }
                }
                    
            } catch (Exception ex) {
                System.out.println("modelo.SAP.QueryAPS.obtenerUniConfomes(): " + ex.getMessage());
            }finally{
                super.con().close();
                ps.close();
            }
        }
        return conformes;
    }
    
    public int obtenerUniNoConfomes(int docNum, String itemCode) throws ClassNotFoundException, SQLException{
        ArrayList<APS_OITT> ar = new ArrayList<APS_OITT>();
        ar.addAll((Collection)obtenerUltimaAct(itemCode));
        int noConformes = 0;
        
        for (APS_OITT oitt : ar) {
            try {
                ps=super.con().prepareStatement("SELECT " +
                                                "   SUM(T0.uniNoConformes+T0.uniRechazadas) rechazadas " +
                                                "FROM SDO_ALSASA..APS_OIGE1 T0 " +
                                                "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
                                                "JOIN SDO_ALSASA..APS_OWOR T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
                                                "WHERE T2.docNum=?");
                ps.setInt(1, docNum);
                rs= ps.executeQuery();
                while (rs.next()) {                
                    noConformes= rs.getInt(1);
                }
            } catch (Exception ex) {
                System.out.println("modelo.SAP.QueryAPS.obtenerUniNoConfomes(): " + ex.getMessage());
            }finally{
                super.con().close();
                ps.close();
            }
        }
        return noConformes;
    }
    
    public ArrayList<APS_OIGE> cargarActividades(int docnum) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "  T1.idAPS_OIGE, " +
                                              "  T1.nomEmp, " +
                                              "  T1.actividad + ' ' + ISNULL(T1.descActividad, 'Sin descripción') actividad, " +
                                              "  T1.inicio, " + 
                                              "  T1.fin, " + 
                                              "  CONVERT(numeric(19,6),CONVERT(numeric(19,6), ISNULL(T1.totalTiempo,0))/60) TotalTiempo, " +
                                              "  T1.uniTotalesConformes, " +
                                              "  (SELECT SUM(K0.uniNoConformes+K0.uniRechazadas) FROM SDO_ALSASA..APS_OIGE1 K0 JOIN SDO_ALSASA..APS_OIGE K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T1.idAPS_OIGE) uniTotalesRechazadas, " +
                                              "  ISNULL(STUFF((SELECT ', '+K0.comentario " +
                                              "	  FROM SDO_ALSASA..APS_OIGE1 K0 " +
                                              "	  JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                              "	  WHERE K0.comentario!='' AND K0.comentario IS NOT NULL AND K1.idAPS_OIGE=T1.idAPS_OIGE FOR XML PATH ('')),1,2,''),'') 'Comentarios', " +
                                              "  T1.estadoCarga, " +
                                              "    CASE     " +
                                            "        WHEN     " +
                                            "            (CASE    " +
                                            "                WHEN    " +
                                            "                    (SELECT TOP 1     " +
                                            "                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                    FROM SBO_ALSASA..ITT1 K0     " +
                                            "                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                    WHERE K0.Type = 290     " +
                                            "                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "                    AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
                                            "					AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
                                            "                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL    " +
                                            "                THEN    " +
                                            "                    (SELECT TOP 1     " +
                                            "                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                    FROM SBO_ALSASA..ITT1 K0     " +
                                            "                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                    WHERE K0.Type = 290     " +
                                            "                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "                    AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS      " +
                                            "                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR     " +
                                            "                    K0.Type = 290)    " +
                                            "                ELSE    " +
                                            "                    (SELECT TOP 1     " +
                                            "                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                    FROM SBO_ALSASA..ITT1 K0     " +
                                            "                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                    WHERE K0.Type = 290     " +
                                            "                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "                    AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS   " +
                                            "					AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS     " +
                                            "                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )    " +
                                            "            END) IS NULL" +
                                            "			OR ISNULL(CASE  " +
                                            "				WHEN LEFT(T1.actividad,3)='114' " +
                                            "				THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T1.idAPS_OWOR)) " +
                                            "				ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T1.idAPS_OIGE))  " +
                                            "			END,0) =0" +
                                            "    THEN     " +
                                            "        0     " +
                                            "    ELSE     " +
                                            "        CONVERT(numeric(19,2),     " +
                                            "            (ISNULL((     " +
                                            "                CASE  " +
                                            "					WHEN LEFT(T1.actividad,3)='114' " +
                                            "					THEN ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T1.codeEmp AND K0.idAPS_OWOR=T1.idAPS_OWOR),0) " +
                                            "					ELSE ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T1.codeEmp AND K0.idAPS_OIGE=T1.idAPS_OIGE),0) " +
                                            "				END " +
                                            "            /     " +
                                            "            (ISNULL(CASE  " +
                                            "				WHEN LEFT(T1.actividad,3)='114' " +
                                            "				THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T1.idAPS_OWOR)) " +
                                            "				ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T1.idAPS_OIGE))  " +
                                            "			END,1) " +
                                            "                *     " +
                                            "            ISNULL((     " +
                                            "                CASE    " +
                                            "                    WHEN    " +
                                            "                        (SELECT TOP 1     " +
                                            "                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                        FROM SBO_ALSASA..ITT1 K0     " +
                                            "                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                        WHERE K0.Type = 290     " +
                                            "                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "                        AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
                                            "						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
                                            "                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL    " +
                                            "                    THEN    " +
                                            "                        (SELECT TOP 1     " +
                                            "                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                        FROM SBO_ALSASA..ITT1 K0     " +
                                            "                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                        WHERE K0.Type = 290     " +
                                            "                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS  " +
                                            "                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR     " +
                                            "                        K0.Type = 290)    " +
                                            "                    ELSE     " +
                                            "                        (SELECT TOP 1     " +
                                            "                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
                                            "                        FROM SBO_ALSASA..ITT1 K0     " +
                                            "                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
                                            "                        WHERE K0.Type = 290     " +
                                            "                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
                                            "                        AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
                                            "						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
                                            "                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )     " +
                                            "                END     " +
                                            "        ),1))     " +
                                            "        ),0)*100)     " +
                                            "    )     " +
                                            "END 'Eficiencia' "+
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T0.docNum=? AND T1.inicio IS NOT NULL ");
            ps.setInt(1, docnum);
            rs = ps.executeQuery();
            
            while(rs.next()){
                APS_OIGE oige = new APS_OIGE();
                oige.setIdAPS_OIGE(rs.getInt(1));
                oige.setNomEmp(rs.getString(2));
                oige.setActividad(rs.getString(3));
                oige.setInicio(rs.getString(4));
                oige.setFin(rs.getString(5));
                oige.setTotalTiempo(rs.getString(6));
                oige.setTotalUnidadesConformes(rs.getInt(7));
                oige.setTotalUnidadesNoConformes(rs.getInt(8));
                oige.setInsumo(rs.getString(9));
                oige.setEstado(rs.getInt(10));
                oige.setDescActividad(rs.getString(11));
                ar.add(oige);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.cargarActividades(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<ActividadProd> listarActividadesProd(String code) throws ClassNotFoundException, SQLException {
        ArrayList<ActividadProd> ar = new ArrayList<ActividadProd>();
        if (code.contains("CM-")) {
            code="VAIMP114";
        }
        try{
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                              "U_CODACT, U_DESCACTIVIDAD " +
                                              "FROM [@ACTIVIDADESPROD] T0 " +
                                              "WHERE T0.U_Ceco IN ('101','102','103','104','105','106','108','109','110','111','112','113','114','126','127','128') " +
                                              "AND T0.U_CODACT LIKE ? "+
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%FALLA%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%PERMISO%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%CONSULTA%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%MANTENIMIENTO%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%OFICIOS%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%FALTA%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%DIADEASUETO%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%MANEJO%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%LIMPIEZA%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%COMPLEMENTOS%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%SUPERVISION%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%INCAPACIDAD%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%DESCUENTO%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%VACACIONES%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%MUESTRAS%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%AUSENCIA%' " +
                                              "AND T0.U_DESCACTIVIDAD NOT LIKE '%INVENTARIO%'");
            ps.setString(1, "%"+code+"%");
            rs = ps.executeQuery();
            while(rs.next()){
                ActividadProd act = new ActividadProd(rs.getString(1), rs.getString(2));
                ar.add(act);
            }
            if(ar.isEmpty()){
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "U_CODACT, U_DESCACTIVIDAD " +
                                                  "FROM [@ACTIVIDADESPROD] T0 " +
                                                  "WHERE T0.U_Ceco IN ('101','102','103','104','105','106','108','109','110','111','112','113','114','126','127','128') " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%FALLA%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%PERMISO%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%CONSULTA%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%MANTENIMIENTO%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%OFICIOS%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%FALTA%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%DIADEASUETO%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%MANEJO%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%LIMPIEZA%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%COMPLEMENTOS%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%SUPERVISION%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%INCAPACIDAD%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%DESCUENTO%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%VACACIONES%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%MUESTRAS%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%AUSENCIA%' " +
                                                  "AND T0.U_DESCACTIVIDAD NOT LIKE '%INVENTARIO%'");
                rs = ps.executeQuery();
                while(rs.next()){
                    ActividadProd act = new ActividadProd(rs.getString(1), rs.getString(2));
                    ar.add(act);
                }
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarActividadesProd(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int eliminarActividadesProdAPS(int numorden) throws ClassNotFoundException, SQLException {
        try{
            
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OIGE "+ 
                                      "SET codeActProd=NULL, descActividadProd=NULL "+ 
                                      "WHERE idAPS_OWOR=(SELECT idAPS_OWOR FROM SDO_ALSASA..APS_OWOR WHERE docNum=?) AND estadoCarga=0");
            ps.setInt(1, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.eliminarActividadesProdAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoCarga(int numorden) throws ClassNotFoundException, SQLException {
        try{
            ArrayList<APS_OIGE> arr = new ArrayList<APS_OIGE>();
            arr.addAll((Collection)listarAPS_OIGE(numorden));
            for (APS_OIGE oige : arr) {
                
                ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OIGE "+ 
                                          "SET estadoCarga=1 "+ 
                                          "WHERE idAPS_OIGE=?");
                ps.setInt(1, oige.getIdAPS_OIGE());
                res=ps.executeUpdate();
            }
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoCarga(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoTiempos(int numorden) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR "+ 
                                      "SET estadoTiempos=1 "+ 
                                      "WHERE docnum=?");
            ps.setInt(1, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoTiempos(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoMateriales(int numorden) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR "+ 
                                      "SET estadoMateriales=1 "+ 
                                      "WHERE docnum=?");
            ps.setInt(1, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoMateriales(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoENMT(int id,int docEntry) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_ENMT "+ 
                                            "SET estado='S', docNum=(SELECT K0.docNum FROM OIGE K0 WHERE K0.docEntry=?) " + 
                                            "WHERE idAPS_ENMT=? ");
            ps.setInt(1, docEntry);
            ps.setInt(2, id);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoENMT(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoProduccion(int numorden) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR "+ 
                                      "SET estadoProduccion=1 "+ 
                                      "WHERE docnum=?");
            ps.setInt(1, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoProduccion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    public java.util.Date fechaFinalTiempos(int numorden) throws ClassNotFoundException, SQLException {
        java.util.Date date = new java.util.Date();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
            String dateInString ="";
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..sp_fechaFinalTiempos @docNum=?");
//            ps=super.con().prepareStatement("SELECT TOP 1 CONVERT(VARCHAR,CAST('20221130' AS DATETIME),20) fecha " +
//                                            "FROM SDO_ALSASA..APS_OWOR T0 " +
//                                            "JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
//                                            "WHERE T0.docNum=? ORDER BY T1.fin DESC");
            ps.setInt(1, numorden);
            rs=ps.executeQuery();
            while (rs.next()) {
                dateInString=rs.getString(1);
            }
            date = sdf.parse(dateInString);
        }catch(Exception ex) {
            System.out.println("error en metodo de tiempos:" + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return date;
    }
    
    public java.util.Date fechaFinal(int numorden) throws ClassNotFoundException, SQLException {
        java.util.Date date = new java.util.Date();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
            String dateInString ="";
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_FECHA_PRODUCCION @docNum=?");
//            ps=super.con().prepareStatement("SELECT TOP 1 CONVERT(VARCHAR,CAST('20221130' AS DATETIME),20) fecha " +
//                                            "FROM SDO_ALSASA..APS_OWOR T0 " +
//                                            "JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
//                                            "WHERE T0.docNum=? ORDER BY T1.fin DESC");
            ps.setInt(1, numorden);
            rs=ps.executeQuery();
            while (rs.next()) {
                dateInString=rs.getString(1);
            }
            date = sdf.parse(dateInString);
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.fechaFinal(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return date;
    }
    
    public String startDate(int numorden) throws ClassNotFoundException, SQLException {
        String date="";
        try{
            ps=super.con().prepareStatement("SELECT TOP 1 CONVERT(VARCHAR,T0.startDate,20) fecha " +
                                            "FROM  OWOR T0 " +
                                            "WHERE T0.docNum=?");
            ps.setInt(1, numorden);
            rs=ps.executeQuery();
            while (rs.next()) {
                date=rs.getString(1);
            }
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.startDate(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return date;
    }
    
    public int guardarLogFechas(int numorden,String oldDate, String newDate, int idUsuario) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..LOG_FECHAS_ORDENES(numOrden, fechaAntigua, fechaNueva, idUsuario)"+
                                            " VALUES(?, CAST(? AS DATETIME), CAST(? AS DATETIME), ?)");
            ps.setInt(1, numorden);
            ps.setString(2, oldDate);
            ps.setString(3, newDate);
            ps.setInt(4, idUsuario);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.guardarLogFechas(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    
    public int insertarCantOPs(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_IGN1=(APS_IGN1)Ob;
            ps=con().prepareStatement("INSERT INTO SDO_ALSASA..APS_IGN1(itemCode,cantCompletada,cantRechazada,idAPS_OWOR) "+ 
                                      "VALUES(?,(SELECT t0.cmpltQty FROM OWOR T0 WHERE T0.docNum=?)+?,(SELECT t0.RjctQty FROM OWOR T0 WHERE T0.docNum=?)+?,(SELECT idAPS_OWOR FROM SDO_ALSASA..APS_OWOR WHERE docNum=?))");
            ps.setString(1,APS_IGN1.getItemCode()); 
            ps.setInt(2,APS_IGN1.getAPS_OWOR().getDocnum());
            ps.setDouble(3, APS_IGN1.getCantCompletada());
            ps.setInt(4,APS_IGN1.getAPS_OWOR().getDocnum());
            ps.setDouble(5, APS_IGN1.getCantRechazada());
            ps.setInt(6,APS_IGN1.getAPS_OWOR().getDocnum());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_IGN1.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
        
    }
    
    public ArrayList<APS_OWTR> listarTransferenciasAPS(int id) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OWTR> ar = new ArrayList<APS_OWTR>();
        try{
            ps=super.con().prepareStatement("SELECT [idAPS_OWTR] " +
                                            "      ,[DocNum] " +
                                            "      ,[DocType] " +
                                            "      ,[Filler] " +
                                            "      ,[ToWhscode] " +
                                            "      ,[Series] " +
                                            "      ,[JrnlMemo] " +
                                            "      ,[Comments]  " +
                                            "      ,CASE  " +
                                            "		WHEN (SELECT K0.estadoFechaFija FROM SDO_ALSASA..COMPANY K0)='TRUE'  " +
                                            "		THEN (SELECT K0.fechaFija FROM SDO_ALSASA..COMPANY K0) " +
                                            "		ELSE T0.DocDate " +
                                            "       END DocDate  " +
                                            "      ,[idUsuario] " +
                                            "  FROM [SDO_ALSASA].[dbo].[APS_OWTR] T0 WHERE T0.[idAPS_OWTR]=? AND T0.estado='W'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                aps_owtr = new APS_OWTR();
                aps_owtr.setIdAPS_OWTR(rs.getInt(1));
                aps_owtr.setDocNum(rs.getString(2));
                aps_owtr.setDocType(rs.getString(3));
                aps_owtr.setFiller(rs.getString(4));
                aps_owtr.setToWhscode(rs.getString(5));
                aps_owtr.setSeries(rs.getString(6));
                aps_owtr.setJrnlMemo(rs.getString(7));
                aps_owtr.setComments(rs.getString(8));
                aps_owtr.setDocDate(rs.getString(9));
                aps_owtr.setIdUsuario(rs.getInt(10));
                ar.add(aps_owtr);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.listarTransferenciasAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_WTR1> obtenerLineasTransferenciasAPS(int id) throws ClassNotFoundException, SQLException {
        ArrayList<APS_WTR1> ar = new ArrayList<APS_WTR1>();
        try{
            ps=super.con().prepareStatement("SELECT [idAPS_WTR1] " +
                                            "      ,[ItemCode] " +
                                            "      ,[ItemName] " +
                                            "      ,[FromWhsCod] " +
                                            "      ,[WhsCode] " +
                                            "      ,[Quantity] " +
                                            "      ,[OrdenProduccion] " +
                                            "      ,[idAPS_OWTR] " +
                                            "  FROM [SDO_ALSASA].[dbo].[APS_WTR1] T0 WHERE T0.[idAPS_OWTR]=? ");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                aps_wtr1 = new APS_WTR1();
                aps_wtr1.setIdAPS_WTR1(rs.getInt(1));
                aps_wtr1.setItemCode(rs.getString(2));
                aps_wtr1.setItemName(rs.getString(3));
                aps_wtr1.setFromWhsCod(rs.getString(4));
                aps_wtr1.setWhsCode(rs.getString(5));
                aps_wtr1.setQuantity(rs.getString(6));
                aps_wtr1.setOrdenProduccion(rs.getString(7));
                aps_wtr1.setIdAPS_OWTR(rs.getInt(8));
                ar.add(aps_wtr1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.obtenerLineasTransferenciasAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int actualizarEstadoTransferencia(int id,int docnum) throws ClassNotFoundException, SQLException {
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWTR "+ 
                                      "SET estado='Y', DocNum=? "+ 
                                      "WHERE idAPS_OWTR=?");
            ps.setInt(1, docnum);
            ps.setInt(2, id);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.SAP.QueryAPS.actualizarEstadoTransferencia(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public String numTransferencia(int docEntry) throws ClassNotFoundException, SQLException{
        String docNum="";
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "	T0.DocNum  " +
                                              "FROM OWTR T0 WHERE DocEntry=?");
            
//            ps = super.con().prepareStatement("SELECT " +
//                                              "	T0.DocNum  " +
//                                              "FROM  OWTR T0 WHERE DocEntry=?");
            ps.setInt(1, docEntry);
            rs = ps.executeQuery();
            while(rs.next()){
                docNum=rs.getString(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.SAP.QueryAPS.numTransferencia(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return docNum;
    }
}