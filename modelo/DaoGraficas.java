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
 * @author Desarrollo Alsasa
 */
public class DaoGraficas extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    OWOR or; //Objeto de ordenes para almacenar la informacion de las ordenes
    Horas horas;
    String fecha;
    
    
    public ArrayList<OWOR> mostrarOrdenesPorDia() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps = super.con().prepareStatement("SELECT " +
                                                  "  CAST(T0.startdate AS DATE) 'STARTDATE', " +
                                                  "  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R')) CANTLBR, " +
                                                  "  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=T0.StartDate AND K0.Status in ('P')) CANTPLN, " +
                                                  "  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=T0.StartDate AND K0.Status in ('L')) CANTCRD " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE T0.StartDate>=? " +
                                                  "GROUP BY T0.StartDate ORDER BY STARTDATE ASC ");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setStartdate(rs.getString(1));
                    or.setCantcmp(rs.getString(2)); 
                    or.setCantpln(rs.getString(3));
                    or.setCantrjc(rs.getString(4));
                    ar.add(or);
                }
        }catch(Exception e){
            System.out.println("modelo.DaoGraficas.mostrarOrdenesPorDia(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<CantCecos> mostrarOrdenesPorCecos() throws ClassNotFoundException, SQLException {
        ArrayList<CantCecos> ar = new ArrayList<CantCecos>();
        CantCecos cecos;
        try{
            fecha=fecha1();
            ps=super.con().prepareStatement("SELECT " +
                                            "CAST(T0.startdate AS DATE) 'STARTDATE', " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%101%') TORNO, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%102%') PULIDO, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%103%') REMACHADO, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%104%') SELLADO, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%105%') PRENSAS, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%106%') FUNDICION, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%108%') TORNO_AUTO, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%109%') EMBUTICION, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%110%') REMACHADO_CAPE, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%111%') COCINETAS, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%112%') AUTOPULIT, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%113%') PINTURA, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%114%') EMPAQUE_PLANTA, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%126%') LINEA_PROD_1, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%127%') LINEA_PROD_2, " +
                                            "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R','P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE '%128%') REPARACION_ENSERES " +
                                            "FROM OWOR T0 " +
                                            "WHERE T0.StartDate>=? " +
                                            "GROUP BY T0.StartDate ORDER BY STARTDATE ASC ");
            ps.setString(1,fecha);
            rs = ps.executeQuery();
            while (rs.next()) { 
                cecos = new CantCecos();
                cecos.setFecha(rs.getString(1));
                cecos.setTorno(rs.getInt(2));
                cecos.setPulido(rs.getInt(3));
                cecos.setRemachado(rs.getInt(4));
                cecos.setSellado(rs.getInt(5));
                cecos.setPrensas(rs.getInt(6));
                cecos.setFundicion(rs.getInt(7));
                cecos.setTorno_auto(rs.getInt(8));
                cecos.setEmbuticion(rs.getInt(9));
                cecos.setRemachado_cape(rs.getInt(10));
                cecos.setCocinetas(rs.getInt(11));
                cecos.setAutopulit(rs.getInt(12));
                cecos.setPintura(rs.getInt(13));
                cecos.setEmpaque_planta(rs.getInt(14));
                cecos.setLinea_prod_1(rs.getInt(15));
                cecos.setLinea_prod_2(rs.getInt(16));
                cecos.setReparacion_enseres(rs.getInt(17));
                ar.add(cecos);
            }
        }catch(Exception e){
            System.out.println("modelo.DaoGraficas.mostrarOrdenesPorCecos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> mostrarOrdenesCecos(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT " +
                                                "CAST(T0.startdate AS DATE) 'STARTDATE', " +
                                                "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('R') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTLBR, " +
                                                "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTPLN, " +
                                                "(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=T0.StartDate AND K0.Status in ('L') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTCRD " +
                                                "FROM OWOR T0 " +
                                                "WHERE T0.StartDate>=? " +
                                                "GROUP BY T0.StartDate ORDER BY STARTDATE ASC");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                ps.setString(4,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setStartdate(rs.getString(1));
                    or.setCantcmp(rs.getString(2)); 
                    or.setCantpln(rs.getString(3));
                    or.setCantrjc(rs.getString(4));
                    ar.add(or);
                }
        }catch(Exception e){
            System.out.println("modelo.DaoGraficas.mostrarOrdenesCecos(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> contarOrdenes() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT " +
                                                "	SUM(T0.CANTLBR) CANTLBR, " +
                                                "	SUM(T0.CANTPLN) CANTPLN, " +
                                                "	SUM(T0.CANTCRD) CANTCRD " +
                                                "FROM ( " +
                                                "	SELECT " +
                                                "	  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=J0.StartDate AND K0.Status in ('R')) CANTLBR, " +
                                                "	  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=J0.StartDate AND K0.Status in ('P')) CANTPLN, " +
                                                "	  (SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 WHERE K0.StartDate=J0.StartDate AND K0.Status in ('L')) CANTCRD " +
                                                "	FROM OWOR J0 " +
                                                "	WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) ELSE J0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) ELSE J0.startDate END)=YEAR(GETDATE()) GROUP BY J0.startDate) T0 ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setCantcmp(rs.getString(1)); 
                    or.setCantpln(rs.getString(2));
                    or.setCantrjc(rs.getString(3));
                    ar.add(or);
                }
        }catch(Exception e){
            System.out.println("modelo.DaoGraficas.contarOrdenes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> contarOrdenesCecos(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT " +
                                                "	SUM(T0.CANTLBR) CANTLBR, " +
                                                "	SUM(T0.CANTPLN) CANTPLN, " +
                                                "	SUM(T0.CANTCRD) CANTCRD " +
                                                "FROM (SELECT " +
                                                "	(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=J0.StartDate AND K0.Status in ('R') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTLBR, " +
                                                "	(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=J0.StartDate AND K0.Status in ('P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTPLN, " +
                                                "	(SELECT COUNT(DISTINCT DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K1.DocEntry=K0.DocEntry WHERE K0.StartDate=J0.StartDate AND K0.Status in ('L') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) CANTCRD " +
                                                "FROM OWOR J0 " +
                                                "WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) ELSE J0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) ELSE J0.startDate END)=YEAR(GETDATE()) " +
                                                "GROUP BY J0.startDate ) T0");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setCantcmp(rs.getString(1)); 
                    or.setCantpln(rs.getString(2));
                    or.setCantrjc(rs.getString(3));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.contarOrdenesCecos(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> filtrarContarOrdenesCecos(String fecha1, String fecha2,String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT  " +
                                                "    SUM(T0.CANTLBR) CANTLBR,  " +
                                                "    SUM(T1.CANTPLN) CANTPLN,  " +
                                                "    SUM(T2.CANTCRD) CANTCRD  " +
                                                "FROM (SELECT  " +
                                                "		COUNT(DISTINCT J0.DocNum) CANTLBR " +
                                                "    FROM SBO_ALSASA..OWOR J0  " +
                                                "	JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=J0.DocEntry  " +
                                                "	WHERE J0.Status in ('L') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? " +
                                                "    AND (SELECT MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) BETWEEN ? AND ?) T0, " +
                                                "	(SELECT  " +
                                                "		COUNT(DISTINCT J0.DocNum) CANTPLN " +
                                                "    FROM SBO_ALSASA..OWOR J0 " +
                                                "	JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=J0.DocEntry  " +
                                                "	WHERE J0.Status in ('P') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? " +
                                                "    AND (SELECT MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) BETWEEN ? AND ?) T1, " +
                                                "	(SELECT  " +
                                                "		COUNT(DISTINCT J0.DocNum) CANTCRD " +
                                                "    FROM SBO_ALSASA..OWOR J0 " +
                                                "	JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=J0.DocEntry  " +
                                                "	WHERE J0.Status in ('R') AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? " +
                                                "    AND (SELECT MIN(K0.StartDate) FROM SBO_ALSASA..AWOR K0 WHERE K0.DocEntry=J0.DocEntry) BETWEEN ? AND ?) T2");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,fecha1.replaceAll("-", ""));
                ps.setString(3,fecha2.replaceAll("-", ""));
                ps.setString(4,"%"+cecos+"%");
                ps.setString(5,fecha1.replaceAll("-", ""));
                ps.setString(6,fecha2.replaceAll("-", ""));
                ps.setString(7,"%"+cecos+"%");
                ps.setString(8,fecha1.replaceAll("-", ""));
                ps.setString(9,fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setCantcmp(rs.getString(1)); 
                    or.setCantpln(rs.getString(2));
                    or.setCantrjc(rs.getString(3));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.filtrarContarOrdenesCecos(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Horas> mostrarHorasTrabajo(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<Horas> ar = new ArrayList<Horas>();
        try{
            if(idRol==2){
                ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                "  T0.nombre, " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "    FROM SBO_ALSASA..OWOR K0 " +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND K0.status!='C' " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Planificadas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "    FROM SBO_ALSASA..OWOR K0 " +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND K0.status!='C' " +
                                                "    AND (K0.status='R' OR K0.status='L') " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Liberadas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                "    FROM SBO_ALSASA..OWOR K0 " +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Consumidas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    CAST( " +
                                                "      (SELECT " +
                                                "        COUNT(DISTINCT L0.emp_code) " +
                                                "      FROM zkbiotime..iclock_transaction L0 " +
                                                "      WHERE L0.emp_code NOT IN (1,2) " +
                                                "      AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = L0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
                                                "      AND CONVERT(CHAR(10),L0.punch_time,112)=CONVERT(CHAR(10),GETDATE(),112)) " +
                                                "    AS INT)*7.2 " +
                                                "  ,0)) 'Horas Disponibles' " +
                                                "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                "WHERE T0.Dept_code IS NOT NULL  AND T1.idUsuario=?");
                ps.setInt(1, idUsuario);
                rs = ps.executeQuery();
                while(rs.next()){
                    horas = new Horas();
                    horas.setCecos(rs.getString(1));
                    horas.sethPlanificadas(rs.getString(2)); 
                    horas.sethLiberadas(rs.getString(3)); 
                    horas.sethCosumidas(rs.getString(4));
                    horas.sethDisponibles(rs.getString(5));
                    ar.add(horas);
                }
            }else{
                ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                "  T0.nombre, " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "    FROM SBO_ALSASA..OWOR K0 " +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND K0.status!='C' " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Planificadas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "    FROM SBO_ALSASA..OWOR K0 " +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND K0.status!='C' " +
                                                "    AND (K0.status='R' OR K0.status='L') " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Liberadas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    (SELECT " +
                                                "      SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                "    FROM SBO_ALSASA..OWOR K0" +
                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "    WHERE CONVERT(CHAR(10),K0.StartDate,112)=CONVERT(CHAR(10),GETDATE(),112) " +
                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "  ,0)) 'Horas Consumidas', " +
                                                "  CONVERT(numeric(19,2),ISNULL( " +
                                                "    CAST( " +
                                                "      (SELECT " +
                                                "        COUNT(DISTINCT L0.emp_code) " +
                                                "      FROM zkbiotime..iclock_transaction L0 " +
                                                "      WHERE L0.emp_code NOT IN (1,2) " +
                                                "      AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = L0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
                                                "      AND CONVERT(CHAR(10),L0.punch_time,112)=CONVERT(CHAR(10),GETDATE(),112)) " +
                                                "    AS INT)*7.2 " +
                                                "  ,0)) 'Horas Disponibles' " +
                                                "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                "WHERE T0.Dept_code IS NOT NULL ");
                rs = ps.executeQuery();
                while(rs.next()){
                    horas = new Horas();
                    horas.setCecos(rs.getString(1));
                    horas.sethPlanificadas(rs.getString(2)); 
                    horas.sethLiberadas(rs.getString(3)); 
                    horas.sethCosumidas(rs.getString(4));
                    horas.sethDisponibles(rs.getString(5));
                    ar.add(horas);
                }
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.mostrarHorasTrabajo(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Horas> mostrarHorasTrabajoFecha(int idUsuario, int idRol, String fecha, int mayor) throws ClassNotFoundException, SQLException {
         ArrayList<Horas> ar = new ArrayList<Horas>();
        try{
            if(mayor==1){
                if(idRol==2){
                    ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.nombre, " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "    WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Planificadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "	 WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (K0.status='R' OR K0.status='L') " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Liberadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "    WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Consumidas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    CAST( " +
                                                    "      (SELECT " +
                                                    "        COUNT(H0.emp_code) " +
                                                    "      FROM zkbiotime..personnel_employee H0 " +
                                                    "      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
                                                    "    AS INT)*7.2,0)) 'Horas Disponibles' " +
                                                    "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                    "WHERE T0.Dept_code IS NOT NULL AND T1.idUsuario=?");
                    ps.setString(1, fecha);
                    ps.setString(2, fecha);
                    ps.setString(3, fecha);
                    ps.setInt(3, idUsuario);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        horas = new Horas();
                        horas.setCecos(rs.getString(1));
                        horas.sethPlanificadas(rs.getString(2)); 
                        horas.sethLiberadas(rs.getString(3)); 
                        horas.sethCosumidas(rs.getString(4));
                        horas.sethDisponibles(rs.getString(5));
                        ar.add(horas);
                    }
                }else{
                    ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.nombre, " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "    WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Planificadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "	 WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (K0.status='R' OR K0.status='L') " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Liberadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "    WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Consumidas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    CAST( " +
                                                    "      (SELECT " +
                                                    "        COUNT(H0.emp_code) " +
                                                    "      FROM zkbiotime..personnel_employee H0 " +
                                                    "      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
                                                    "    AS INT)*7.2,0)) 'Horas Disponibles' " +
                                                    "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                    "WHERE T0.Dept_code IS NOT NULL");
                    ps.setString(1, fecha);
                    ps.setString(2, fecha);
                    ps.setString(3, fecha);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        horas = new Horas();
                        horas.setCecos(rs.getString(1));
                        horas.sethPlanificadas(rs.getString(2)); 
                        horas.sethLiberadas(rs.getString(3)); 
                        horas.sethCosumidas(rs.getString(4));
                        horas.sethDisponibles(rs.getString(5));
                        ar.add(horas);
                    }
                }
            }else{
                if(idRol==2){
                    ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                    "	T0.nombre, " +
                                                    "	CONVERT(numeric(19,2),ISNULL(" +
                                                    "       (SELECT " +
                                                    "           SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "       FROM SBO_ALSASA..OWOR K0 " +
                                                    "       JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "       WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "       AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "       AND K0.status!='C' " +
                                                    "       AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "	,0)) 'Horas Planificadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "	 WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (K0.status='R' OR K0.status='L') " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Liberadas', " +
                                                    "	CONVERT(numeric(19,2),ISNULL( " +
                                                    "       (SELECT " +
                                                    "           SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                    "       FROM SBO_ALSASA..OWOR K0 " +
                                                    "       JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "       WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "       AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "       AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "	,0)) 'Horas Consumidas', " +
                                                    "	CONVERT(numeric(19,2),ISNULL( " +
                                                    "       CAST( " +
                                                    "           (SELECT " +
                                                    "               COUNT(DISTINCT L0.emp_code) " +
                                                    "           FROM zkbiotime..iclock_transaction L0 " +
                                                    "           WHERE L0.emp_code NOT IN (1,2) " +
                                                    "           AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = L0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
                                                    "           AND CONVERT(CHAR(10),L0.punch_time,23)=?)" +
                                                    "       AS INT)*7.2,0)) 'Horas Disponibles' " +
                                                    "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                    "WHERE T0.Dept_code IS NOT NULL AND T1.idUsuario=?");
                    ps.setString(1, fecha);
                    ps.setString(2, fecha);
                    ps.setString(3, fecha);
                    ps.setString(4, fecha);
                    ps.setInt(4, idUsuario);

                    rs = ps.executeQuery();
                    while(rs.next()){
                        horas = new Horas();
                        horas.setCecos(rs.getString(1));
                        horas.sethPlanificadas(rs.getString(2)); 
                        horas.sethLiberadas(rs.getString(3)); 
                        horas.sethCosumidas(rs.getString(4));
                        horas.sethDisponibles(rs.getString(5));
                        ar.add(horas);
                    }
                }else{
                    ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                    "	T0.nombre, " +
                                                    "	CONVERT(numeric(19,2),ISNULL(" +
                                                    "		(SELECT " +
                                                    "			SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "		FROM SBO_ALSASA..OWOR K0 " +
                                                    "		JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "		WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "		AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "           AND K0.status!='C' " +
                                                    "		AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "	,0)) 'Horas Planificadas', " +
                                                    "  CONVERT(numeric(19,2),ISNULL( " +
                                                    "    (SELECT " +
                                                    "      SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                    "    FROM SBO_ALSASA..OWOR K0 " +
                                                    "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "	 WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "    AND K0.status!='C' " +
                                                    "    AND (K0.status='R' OR K0.status='L') " +
                                                    "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "  ,0)) 'Horas Liberadas', " +
                                                    "	CONVERT(numeric(19,2),ISNULL( " +
                                                    "		(SELECT " +
                                                    "			SUM(K1.IssuedQty) 'Horas Consumidas' " +
                                                    "		FROM SBO_ALSASA..OWOR K0 " +
                                                    "		JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                    "		WHERE CONVERT(CHAR(10),K0.StartDate,23)=? " +
                                                    "		AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                    "		AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                    "	,0)) 'Horas Consumidas', " +
                                                    "	CONVERT(numeric(19,2),ISNULL( " +
                                                    "		CAST( " +
                                                    "			(SELECT " +
                                                    "				COUNT(DISTINCT L0.emp_code) " +
                                                    "			FROM zkbiotime..iclock_transaction L0 " +
                                                    "			WHERE L0.emp_code NOT IN (1,2) " +
                                                    "                   AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = L0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
                                                    "			AND CONVERT(CHAR(10),L0.punch_time,23)=? )" +
                                                    "		AS INT)*7.2,0)) 'Horas Disponibles' " +
                                                    "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                    "WHERE T0.Dept_code IS NOT NULL ");
                    ps.setString(1, fecha);
                    ps.setString(2, fecha);
                    ps.setString(3, fecha);
                    ps.setString(4, fecha);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        horas = new Horas();
                        horas.setCecos(rs.getString(1));
                        horas.sethPlanificadas(rs.getString(2)); 
                        horas.sethLiberadas(rs.getString(3)); 
                        horas.sethCosumidas(rs.getString(4));
                        horas.sethDisponibles(rs.getString(5));
                        ar.add(horas);
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.mostrarHorasTrabajoFecha(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Horas> mostrarHorasTrabajoMes(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
        ArrayList<Horas> ar = new ArrayList<Horas>();
        try{
            if(idRol==2){
//                ps=super.con().prepareStatement("SET DATEFIRST 1 " +
//                                                "declare @FechaIni date = GETDATE(); " +
//                                                "declare @FechaFin date = EOMONTH(GETDATE()); " +
//                                                "declare @dias int=0; " +
//                                                "WHILE @FechaIni <= @FechaFin " +
//                                                "BEGIN   " +
//                                                "   if  datepart(dw, @FechaIni) not in (6,7) SET @dias=@dias+1 " +
//                                                "   set @FechaIni=dateadd(dd,1,@FechaIni) " +
//                                                "END  " +
//                                                "SELECT DISTINCT   " +
//                                                "T0.nombre,   " +
//                                                "CONVERT(numeric(19,2),ISNULL(   " +
//                                                "    (SELECT   " +
//                                                "        SUM(K1.PlannedQty) 'Horas Planificadas'   " +
//                                                "    FROM SBO_ALSASA..OWOR K0   " +
//                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry   " +
//                                                "    WHERE YEAR(K0.StartDate)=YEAR(GETDATE()) AND MONTH(K0.StartDate)=MONTH(GETDATE())   " +
//                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L'   " +
//                                                "    AND K0.status!='C'   " +
//                                                "    AND K0.status='P'   " +
//                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%'))   " +
//                                                ",0)) 'Horas Planificadas',   " +
//                                                "CONVERT(numeric(19,2),ISNULL(   " +
//                                                "    (CAST(   " +
//                                                "    (SELECT   " +
//                                                "        COUNT(H0.emp_code)   " +
//                                                "    FROM zkbiotime..personnel_employee H0   " +
//                                                "    WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END))   " +
//                                                "    AS INT)*7.2)*@dias,0)) 'Horas Disponibles', " +
//                                                "	@dias [Dias del mes] " +
//                                                "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC   " +
//                                                "WHERE T0.Dept_code IS NOT NULL AND T1.idUsuario=? ");
                ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                "	T0.nombre, " +
                                                "	CONVERT(numeric(19,2),ISNULL( " +
                                                "		(SELECT " +
                                                "			SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "		FROM SBO_ALSASA..OWOR K0 " +
                                                "		JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "		WHERE YEAR(K0.StartDate)=YEAR(GETDATE()) AND MONTH(K0.StartDate)=MONTH(GETDATE()) " +
                                                "		AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "		AND K0.status!='C' " +
                                                "		AND K0.status='P' " +
                                                "		AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "	,0)) 'Horas Planificadas', " +
                                                "	CONVERT(numeric(19,2),ISNULL( " +
                                                "    (CAST( " +
                                                "      (SELECT " +
                                                "        COUNT(H0.emp_code) " +
                                                "      FROM zkbiotime..personnel_employee H0 " +
                                                "      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
                                                "    AS INT)*7.2)*((DATEDIFF(DAY, GETDATE(),EOMONTH(GETDATE()))+1)),0)) 'Horas Disponibles' " +
                                                " FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                " WHERE T0.Dept_code IS NOT NULL AND T1.idUsuario=? ");
                ps.setInt(1, idUsuario);
                rs = ps.executeQuery();
                while(rs.next()){
                    horas = new Horas();
                    horas.setCecos(rs.getString(1));
                    horas.sethPlanificadas(rs.getString(2)); 
                    horas.sethDisponibles(rs.getString(3));
                    ar.add(horas);
                }
            }else{
//                ps=super.con().prepareStatement("SET DATEFIRST 1 " +
//                                                "declare @FechaIni date = GETDATE(); " +
//                                                "declare @FechaFin date = EOMONTH(GETDATE()); " +
//                                                "declare @dias int=0; " +
//                                                "WHILE @FechaIni <= @FechaFin " +
//                                                "BEGIN   " +
//                                                "   if  datepart(dw, @FechaIni) not in (6,7) SET @dias=@dias+1 " +
//                                                "   set @FechaIni=dateadd(dd,1,@FechaIni) " +
//                                                "END  " +
//                                                "SELECT DISTINCT   " +
//                                                "T0.nombre,   " +
//                                                "CONVERT(numeric(19,2),ISNULL(   " +
//                                                "    (SELECT   " +
//                                                "        SUM(K1.PlannedQty) 'Horas Planificadas'   " +
//                                                "    FROM SBO_ALSASA..OWOR K0   " +
//                                                "    JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry   " +
//                                                "    WHERE YEAR(K0.StartDate)=YEAR(GETDATE()) AND MONTH(K0.StartDate)=MONTH(GETDATE())   " +
//                                                "    AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L'   " +
//                                                "    AND K0.status!='C'   " +
//                                                "    AND K0.status='P'   " +
//                                                "    AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%'))   " +
//                                                ",0)) 'Horas Planificadas',   " +
//                                                "CONVERT(numeric(19,2),ISNULL(   " +
//                                                "    (CAST(   " +
//                                                "    (SELECT   " +
//                                                "        COUNT(H0.emp_code)   " +
//                                                "    FROM zkbiotime..personnel_employee H0   " +
//                                                "    WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END))   " +
//                                                "    AS INT)*7.2)*@dias,0)) 'Horas Disponibles', " +
//                                                "	@dias [Dias del mes] " +
//                                                "FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC   " +
//                                                "WHERE T0.Dept_code IS NOT NULL ");
                ps=super.con().prepareStatement("SELECT DISTINCT " +
                                                "	T0.nombre, " +
                                                "	CONVERT(numeric(19,2),ISNULL( " +
                                                "		(SELECT " +
                                                "			SUM(K1.PlannedQty) 'Horas Planificadas' " +
                                                "		FROM SBO_ALSASA..OWOR K0 " +
                                                "		JOIN SBO_ALSASA..WOR1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "		WHERE YEAR(K0.StartDate)=YEAR(GETDATE()) AND MONTH(K0.StartDate)=MONTH(GETDATE()) " +
                                                "		AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "		AND K0.status!='C' " +
                                                "		AND K0.status='P' " +
                                                "		AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')) " +
                                                "	,0)) 'Horas Planificadas', " +
                                                "	CONVERT(numeric(19,2),ISNULL( " +
                                                "    (CAST( " +
                                                "      (SELECT " +
                                                "        COUNT(H0.emp_code) " +
                                                "      FROM zkbiotime..personnel_employee H0 " +
                                                "      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
                                                "    AS INT)*7.2)*((DATEDIFF(DAY, GETDATE(),EOMONTH(GETDATE()))+1)),0)) 'Horas Disponibles' " +
                                                " FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC " +
                                                " WHERE T0.Dept_code IS NOT NULL ");
                rs = ps.executeQuery();
                while(rs.next()){
                    horas = new Horas();
                    horas.setCecos(rs.getString(1));
                    horas.sethPlanificadas(rs.getString(2)); 
                    horas.sethDisponibles(rs.getString(3));
                    ar.add(horas);
                }
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.mostrarHorasTrabajoMes(): "+ex.getMessage());
            
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Horas> mostrarHorasTrabajoDetalleMes() throws ClassNotFoundException, SQLException {
        ArrayList<Horas> ar = new ArrayList<Horas>();
        try{
            ps=super.con().prepareStatement("SELECT " +
"	CONVERT(numeric(19,2),(SELECT SUM(K0.PlannedQty) FROM SBO_ALSASA..WOR1 K0 JOIN SBO_ALSASA..OWOR K1 ON K0.docEntry=K1.docEntry WHERE MONTH(k0.StartDate)=MONTH(GETDATE()) AND YEAR(K0.StartDate)=YEAR(GETDATE()) AND K0.ItemType=290 AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode=K0.ItemCode)='L' AND K1.STATUS!='C')) HorasPlanificadasDelMes, " +
"	SUM(T2.HorasConsumidas) HorasPlanificadasDelMes, " +
"	SUM(T2.HorasDisponiblesALaFecha) HorasDisponiblesALaFecha, " +
"	SUM(T2.HorasProyectadas) HorasProyectadas, " +
"	SUM(T2.HorasDisponiblesDelMes) HorasDisponiblesDelMes, " +
"	CONVERT(numeric(19,2),(SELECT SUM(K0.PlannedQty) FROM SBO_ALSASA..WOR1 K0 JOIN SBO_ALSASA..OWOR K1 ON K0.docEntry=K1.docEntry WHERE MONTH(k0.StartDate)=MONTH(GETDATE()) AND YEAR(K0.StartDate)=YEAR(GETDATE()) AND K0.ItemType=290 AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode=K0.ItemCode)='L' AND K1.STATUS!='C' AND K1.STATUS='P')) HorasPlanificadasRestantes, " +
"	CONVERT(VARCHAR,CAST((SUM(T2.HorasConsumidas)/CONVERT(numeric(19,2),CONVERT(numeric(19,2),(SELECT SUM(K0.PlannedQty) FROM SBO_ALSASA..WOR1 K0 JOIN SBO_ALSASA..OWOR K1 ON K0.docEntry=K1.docEntry WHERE MONTH(k0.StartDate)=MONTH(GETDATE()) AND YEAR(K0.StartDate)=YEAR(GETDATE()) AND K0.ItemType=290 AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode=K0.ItemCode)='L' AND K1.STATUS!='C'))))*100 AS MONEY),1) 'porcentajeHorasConsumidasMes', " +
"	CONVERT(VARCHAR,CAST((CONVERT(numeric(19,2),(SELECT SUM(K0.PlannedQty) FROM SBO_ALSASA..WOR1 K0 JOIN SBO_ALSASA..OWOR K1 ON K0.docEntry=K1.docEntry WHERE MONTH(k0.StartDate)=MONTH(GETDATE()) AND YEAR(K0.StartDate)=YEAR(GETDATE()) AND K0.ItemType=290 AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode=K0.ItemCode)='L' AND K1.STATUS!='C' AND K1.STATUS='P'))/CONVERT(numeric(19,2),(SELECT SUM(K0.PlannedQty) FROM SBO_ALSASA..WOR1 K0 JOIN SBO_ALSASA..OWOR K1 ON K0.docEntry=K1.docEntry WHERE MONTH(k0.StartDate)=MONTH(GETDATE()) AND YEAR(K0.StartDate)=YEAR(GETDATE()) AND K0.ItemType=290 AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode=K0.ItemCode)='L' AND K1.STATUS!='C')))*100 AS MONEY),1) 'porcentajeHorasPlanificadasRestantes' " +
"FROM (SELECT DISTINCT " +
"    CONVERT(numeric(19,2),ISNULL(  " +
"        (ISNULL((SELECT " +
"		    SUM(K1.Quantity) 'HorasConsumidas' " +
"		FROM SBO_ALSASA..OIGE K0 " +
"		JOIN SBO_ALSASA..IGE1 K1 ON K1.DocEntry=K0.DocEntry " +
"        WHERE YEAR(K0.docDate)=YEAR(GETDATE()) AND MONTH(K0.docDate)=MONTH(GETDATE()) " +
"        AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
"        AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')),0)+ " +
"        ISNULL((SELECT " +
"		    SUM(-K1.Quantity) 'HorasDevueltas' " +
"		FROM SBO_ALSASA..OIGN K0 " +
"		JOIN SBO_ALSASA..IGN1 K1 ON K1.DocEntry=K0.DocEntry " +
"        WHERE YEAR(K0.docDate)=YEAR(GETDATE()) AND MONTH(K0.docDate)=MONTH(GETDATE()) " +
"		AND (SELECT L0.ResType FROM SBO_ALSASA..ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
"		AND (SELECT K2.VisResCode FROM SBO_ALSASA..ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE CONCAT('%',T0.code,'%')),0)) " +
"    ,0)) 'HorasConsumidas', " +
"    CONVERT(numeric(19,2),  " +
"            ISNULL((SELECT SUM(((T0.TIEMPO)*0.85)*0.95) FROM (SELECT DISTINCT " +
"				K0.emp_code, " +
"				(SELECT TOP 1 " +
"						L0.punch_time punch_time " +
"					FROM zkbiotime..iclock_transaction L0 " +
"					WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC) 'Primera Marcacion', " +
"				(SELECT TOP 1 " +
"					L0.punch_time punch_time " +
"				FROM zkbiotime..iclock_transaction L0 " +
"				WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC) 'Ultima Marcacion', " +
"				CASE " +
"					WHEN CONVERT(NUMERIC(19,6),(DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60)>=6 " +
"					THEN " +
"						CONVERT(NUMERIC(19,6),DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60-1 " +
"					WHEN CONVERT(NUMERIC(19,6),(DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60)= 0 " +
"					THEN " +
"						8.8 " +
"					ELSE " +
"						CONVERT(NUMERIC(19,6),DATEDIFF(MINUTE,(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60 " +
"				END TIEMPO " +
"			FROM zkbiotime..iclock_transaction K0 " +
"			WHERE K0.emp_code NOT IN (1,2) " +
"                       AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = K0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
"			AND CONVERT(CHAR(10),K0.punch_time,23) BETWEEN convert(char(8), getdate(), 23) + '01' AND CONVERT(CHAR(10),GETDATE(),23) GROUP BY K0.emp_code, K0.punch_time) AS T0),0) " +
"	) 'HorasDisponiblesALaFecha', " +
"	CONVERT(numeric(19,2),ISNULL( " +
"    (CAST( " +
"      (SELECT " +
"        COUNT(H0.emp_code) " +
"      FROM zkbiotime..personnel_employee H0 " +
"      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
"    AS INT)*7.2)*((DATEDIFF(DAY, GETDATE(),EOMONTH(GETDATE()))+1)),0)) 'HorasProyectadas', " +
"    CONVERT(numeric(19,2), " +
"            ISNULL((SELECT SUM(((T0.TIEMPO)*0.85)*0.95) FROM (SELECT DISTINCT " +
"				K0.emp_code, " +
"				(SELECT TOP 1 " +
"						L0.punch_time punch_time " +
"					FROM zkbiotime..iclock_transaction L0 " +
"					WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC) 'Primera Marcacion', " +
"				(SELECT TOP 1" +
"					L0.punch_time punch_time " +
"				FROM zkbiotime..iclock_transaction L0 " +
"				WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC) 'Ultima Marcacion', " +
"				CASE " +
"					WHEN CONVERT(NUMERIC(19,6),(DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60)>=6 " +
"					THEN " +
"						CONVERT(NUMERIC(19,6),DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60-1 " +
"					WHEN CONVERT(NUMERIC(19,6),(DATEDIFF(MINUTE,(SELECT TOP 1 " +
"								L0.punch_time punch_time " +
"							FROM zkbiotime..iclock_transaction L0 " +
"							WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60)= 0 " +
"					THEN " +
"						8.8 " +
"					ELSE " +
"						CONVERT(NUMERIC(19,6),DATEDIFF(MINUTE,(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time ASC), " +
"						(SELECT TOP 1 " +
"							L0.punch_time punch_time " +
"						FROM zkbiotime..iclock_transaction L0 " +
"						WHERE L0.emp_code NOT IN (1,2) AND L0.emp_code=K0.emp_code AND CONVERT(CHAR(10),L0.punch_time,23)=CONVERT(CHAR(10),K0.punch_time,23) GROUP BY L0.emp_code, L0.punch_time ORDER BY L0.punch_time DESC)))/60 " +
"				END TIEMPO " +
"			FROM zkbiotime..iclock_transaction K0 " +
"			WHERE K0.emp_code NOT IN (1,2) " +
"                       AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = K0.emp_code) In (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END) " +
"			AND CONVERT(CHAR(10),K0.punch_time,23) BETWEEN convert(char(8), getdate(), 23) + '01' AND CONVERT(CHAR(10),GETDATE(),23) GROUP BY K0.emp_code, K0.punch_time) AS T0),0) " +
"	) " +
"	+ " +
"	CONVERT(numeric(19,2),ISNULL( " +
"    (CAST( " +
"      (SELECT " +
"        COUNT(H0.emp_code) " +
"      FROM zkbiotime..personnel_employee H0 " +
"      WHERE H0.department_id in (CASE WHEN (T0.dept_code)=21 THEN 8 ELSE T0.dept_code END,CASE WHEN (T0.dept_code)=21 THEN 21 ELSE T0.dept_code END)) " +
"    AS INT)*7.2)*((DATEDIFF(DAY, GETDATE(),EOMONTH(GETDATE()))+1)),0)) 'HorasDisponiblesDelMes' " +
"FROM SDO_ALSASA..APS_OCRC T0 JOIN SDO_ALSASA..APS_OCRC1 T1 ON T0.idAPS_OCRC=T1.idAPS_OCRC WHERE T0.Dept_code IS NOT NULL ) T2 ");
            rs = ps.executeQuery();
            while(rs.next()){
                horas = new Horas();
                horas.sethPlanificadasMes(rs.getString(1)); 
                horas.sethCosumidas(rs.getString(2));
                horas.sethDisponiblesALaFecha(rs.getString(3));
                horas.sethProyectadas(rs.getString(4));
                horas.sethDisponiblesMes(rs.getString(5));
                horas.sethPlanificadasRestantes(rs.getString(6));
                horas.setPorcentajeHorasConsumidasPlanificadas(rs.getString(7));
                horas.setPorcentajeHorasPlanificadasRestantesDelMes(rs.getString(8));
                ar.add(horas);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.mostrarHorasTrabajoDetalleMes(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> conteoArticulos(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_CONTEO_ARTICULOS WHERE cecos=?");
                ps.setString(1, cecos);
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setItemcode(rs.getString(1)); 
                    or.setCantcmp(rs.getString(2));
                    or.setCantpln(rs.getString(3));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.conteoArticulos(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> conteoArticulosPlanta() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT 'Planta' 'cecos', SUM(unidadesProducidas) unidadesProducidas, SUM(unidadesPlanificadas) unidadesPlanificadas FROM SDO_ALSASA..VW_CONTEO_ARTICULOS ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setItemcode(rs.getString(1)); 
                    or.setCantcmp(rs.getString(2));
                    or.setCantpln(rs.getString(3));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.conteoArticulosPlanta(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
     
    public ArrayList<OWOR> avanceMes() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_AVANCE_PERIDO");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setCantcmp(rs.getString(1));
                    or.setCantpln(rs.getString(2));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.avanceMes(): "+ex.getMessage());
            
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> produccionGrupos(String grupo) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_PRODUCCION_GRUPOS T0 WHERE T0.grupo=?");
                ps.setString(1, grupo);
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setItemcode(rs.getString(1)); 
                    or.setCantcmp(rs.getString(2));
                    or.setCantpln(rs.getString(3));
                    or.setCantrjc(rs.getString(4));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.produccionGrupos(): "+ex.getMessage()+", CECOS: " +grupo);
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> produccionGruposDia() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_PRODUCCION_GRUPOS");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setItemcode(rs.getString(1)); 
                    or.setCantcmp(rs.getString(2));
                    or.setCantpln(rs.getString(3));
                    or.setCantrjc(rs.getString(4));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.produccionGruposDia(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> produccionGruposMes() throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            fecha=fecha1();
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_PRODUCCION_GRUPOS_MES");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setItemcode(rs.getString(1)); 
                    or.setCantcmp(rs.getString(2));
                    or.setCantpln(rs.getString(3));
                    or.setCantrjc(rs.getString(4));
                    ar.add(or);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoGraficas.produccionGruposMes(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    //METODO PARA DAR FORMATO A LA FECHA
    private String formatearCalendar(Calendar c) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.JAPANESE);
        return df.format(c.getTime());
    }
    
    //METODO PARA ENCONTRAR LA FECHA DE CONSULTA(UN MES ATRAS)
    private String fecha1(){
        Calendar c = new GregorianCalendar();
        String fecha;
        String date;
        int dia;
        dia=Calendar.DATE;
        c.add(Calendar.MONTH, -1);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
}

