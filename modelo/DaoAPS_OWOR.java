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
public class DaoAPS_OWOR extends ConexionSBO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    OWOR or; //Objeto de ordenes para almacenar la informacion de las ordenes
    APS_OIGE oige;
    OWOR owor;
    
    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try {
            or=(OWOR)Ob;
            if (comprobarOrden(or.getDocnum())==0) {
                String stardate=or.getStartdate()+"T00:00:00";
                String duedate=or.getDuedate()+"T00:00:00";
                ps=con().prepareStatement("INSERT INTO "
                        + "SDO_ALSASA..APS_OWOR(docNum, startDate, DueDate, itemCode, itemName, Status, PlannedQty, CmplQty, RjctQty) "
                        + "VALUES(?,CAST(? AS DATETIME),CAST(? AS DATETIME),?,?,?,?,?,?)");
                ps.setInt(1,or.getDocnum());
                ps.setString(2,stardate);
                ps.setString(3,duedate);
                ps.setString(4,or.getItemcode());
                ps.setString(5,or.getItemname());
                ps.setString(6,or.getStatus());
                ps.setString(7,or.getCantpln());
                ps.setString(8,or.getCantcmp());
                ps.setString(9,or.getCantrjc());
                res=ps.executeUpdate();
            }
            else{
            res=1;
            }
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OWOR.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    @Override
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private int comprobarOrden(int numOrden) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            ps = super.con().prepareStatement("Select count(idAPS_OWOR) FROM SDO_ALSASA..APS_OWOR WHERE docNum=?");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   r=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    
    public ArrayList<APS_OIGE> comprobarEstado() throws ClassNotFoundException, SQLException {
        String fecha=fecha();
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try{
            /*ps=super.con().prepareStatement("SELECT DISTINCT " +
                                            "	T0.docNum, " +
                                            "			CASE " +
                                            "				WHEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)=0 " +
                                            "				THEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "				WHEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)=1 " +
                                            "				THEN " +
                                            "					CASE  WHEN (SELECT TOP 1 K0.actividad FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)='126MLINEA1' " +
                                            "                                                       THEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "                                                       WHEN (SELECT " +
                                            "								COUNT(K2.actividad) " +
                                            "								FROM SDO_ALSASA..APS_OWOR K0 " +
                                            "								JOIN (SELECT DISTINCT J0.docNum,J1.actividad,J1.descActividad FROM SDO_ALSASA..APS_OWOR J0 JOIN SDO_ALSASA..APS_OIGE J1 ON J1.idAPS_OWOR=J0.idAPS_OWOR) K2 ON K2.docnum=K0.docNum " +
                                            "								WHERE K0.docnum=T0.docNum " +
                                            "								GROUP BY K0.docnum) " +
                                            "								< " +
                                            "								(SELECT DISTINCT " +
                                            "									Count(*) " +
                                            "								FROM OITT K0 " +
                                            "								JOIN ITT1 K1 ON K0.Code=K1.father " +
                                            "								WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L' AND K0.code=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS) " +
                                            "								THEN " +
                                            "									CASE WHEN (SELECT COUNT(K0.docnum) FROM OWOR K0 WHERE CAST(K0.startDate AS DATE)>=CAST(GETDATE() AS DATE) AND K0.docNum=T0.docNum)=1 " +
                                            "										THEN -1 " +
                                            "										ELSE 2 " +
                                            "									END " +
                                            "						ELSE " +
                                            "							(SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "					END " +
                                            "	END estado " +
                                            "FROM SDO_ALSASA..APS_OWOR T0 JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T0.startDate>=? AND estado IS NOT NULL");*/
            
            ps=super.con().prepareStatement("SELECT DISTINCT " +
                                            "	T0.docNum, " +
                                            "			CASE " +
                                            "				WHEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)=0 " +
                                            "				THEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "				WHEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)=1 " +
                                            "				THEN " +
                                            "					CASE  WHEN (SELECT TOP 1 K0.actividad FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC)='126MLINEA1' " +
                                            "                                                       THEN (SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "						ELSE " +
                                            "							(SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "					END " +
                                            "	END estado " +
                                            "FROM SDO_ALSASA..APS_OWOR T0 JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T0.startDate>=? AND estado IS NOT NULL");
            ps.setString(1,fecha); 
            rs = ps.executeQuery();
            while(rs.next()){
                or = new OWOR();
                or.setDocnum(rs.getInt(1));
                oige= new APS_OIGE();
                oige.setAPS_OWOR(or);
                oige.setEstado(rs.getInt(2));
                ar.add(oige);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarEstado(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> comprobarEstadoTabla() throws ClassNotFoundException, SQLException {
        String fecha=fecha();
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try{
            ps=super.con().prepareStatement("SELECT DISTINCT " +
                                            "	T0.docNum, " +
                                            "	(SELECT TOP 1 K0.estado FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OWOR=T0.idAPS_OWOR ORDER BY K0.inicio DESC) " +
                                            "FROM SDO_ALSASA..APS_OWOR T0 JOIN SDO_ALSASA..APS_OIGE T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T0.startDate>=? AND estado IS NOT NULL");
            ps.setString(1,fecha); 
            rs = ps.executeQuery();
            while(rs.next()){
                or = new OWOR();
                or.setDocnum(rs.getInt(1));
                oige= new APS_OIGE();
                oige.setAPS_OWOR(or);
                oige.setEstado(rs.getInt(2));
                ar.add(oige);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarEstadoTabla(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int comprobarInicioOrden(int numOrden) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              " COUNT( DISTINCT T1.idAPS_OIGE) " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T0.edicion=0 AND T1.inicio IS NULL AND T0.docNum=? ");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   r=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarInicioOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public int comprobarAprobacionOrden(int numOrden) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            ps = super.con().prepareStatement("SELECT DISTINCT" +
                                              " T0.edicion " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T1.inicio IS NULL AND T0.docNum=? ");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   r=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarAprobacionOrden(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public int solicitarEdicion(int docNum, String comentario) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR SET edicion=1, comentarioSolicitud=? WHERE docNum=?");
            ps.setString(1, comentario);
            ps.setInt(2, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OWOR.solicitarEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int AprobarSolicitudEdicion(int docNum) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR SET edicion=2, fechaSolicitud=GETDATE() WHERE docNum=?");
            ps.setInt(1, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OWOR.AprobarSolicitudEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int RechazarSolicitudEdicion(int docNum) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR SET edicion=0 WHERE docNum=?");
            ps.setInt(1, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
           System.out.println("modelo.DaoAPS_OWOR.RechazarSolicitudEdicion(): " + ex.getMessage()); 
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int comprobarEstadoCargas(int numOrden) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              " CASE " +
                                              "     WHEN estadoMateriales=1 AND (estadoTiempos=1 OR (SELECT COUNT(DISTINCT K0.idAPS_OWOR) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.docNum AND estadoCarga=1)>0) " +
                                              "     THEN 1 " +
                                              "     ELSE 0 " +
                                              " END " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 WHERE DOCNUM=? ");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   r=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.comprobarEstadoCargas(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public ArrayList<OWOR> mostrarOrdenesSolicitudEdicion() throws ClassNotFoundException, SQLException {
        String fecha=fecha();
        ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            ps=super.con().prepareStatement("SELECT " +
                                            "	T0.docNum, " +
                                            "	CONVERT(CHAR(20),T0.startDate,105) startDate, " +
                                            "	CONVERT(CHAR(20),T0.DueDate ,105) DueDate, " +
                                            "	T0.itemCode, " +
                                            "	T0.itemName, " +
                                            "	T0.PlannedQty, " +
                                            "	T0.CmplQty, " +
                                            "	T0.RjctQty, " +
                                            "	T0.comentarioSolicitud " +
                                            "FROM SDO_ALSASA..APS_OWOR T0 " +
                                            "WHERE T0.edicion=1");
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR();// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setDocnum(rs.getInt(1));
                or.setStartdate(rs.getString(2));
                or.setDuedate(rs.getString(3));
                or.setItemcode(rs.getString(4));
                or.setItemname(rs.getString(5));
                or.setCantpln(rs.getString(6));
                or.setCantcmp(rs.getString(7));
                or.setCantrjc(rs.getString(8));
                or.setComments(rs.getString(9));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OWOR.mostrarOrdenesSolicitudEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int guardarPlanAccion(int docNum,String planAccion,String observacion,String fechaAccion,String responsable) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR SET planAccion=?, observacion=?, fechaAccion=?, responsable=? WHERE docNum=?");
            ps.setString(1, planAccion);
            ps.setString(2, observacion);
            ps.setString(3, fechaAccion.replaceAll("-", ""));
            ps.setString(4, responsable);
            ps.setInt(5, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
           System.out.println("modelo.DaoAPS_OWOR.guardarPlanAccion(): " + ex.getMessage()); 
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int guardarEstadoCierre(int docNum) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR SET estadoCierre='Y' WHERE docNum=? ");
            ps.setInt(1, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
           System.out.println("modelo.DaoAPS_OWOR.guardarEstadoCierre(): " + ex.getMessage()); 
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    //METODO PARA DAR FORMATO A LA FECHA
    private String formatearCalendar(Calendar c) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.JAPANESE);
        return df.format(c.getTime());
    }
    
    //METODO PARA ENCONTRAR LA FECHA DE CONSULTA(TRES MESES ATRAS)
    private String fecha(){
        Calendar c = new GregorianCalendar();
        String fecha;
        String date;
        int dia;
        dia=Calendar.DATE;
        c.add(Calendar.MONTH, -2);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
    
}
