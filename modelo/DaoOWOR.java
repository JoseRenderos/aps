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
public class DaoOWOR extends ConexionSBO{ 
    PreparedStatement ps;
    ResultSet rs;
    OWOR or; //Objeto de ordenes para almacenar la informacion de las ordenes
    Costo_OP costo_op;
    ObjGen comments;
    String fecha;
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> mostrar( int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC(); //INSTANCIA DE OBJETO DAOAPS_OCRC PARA ENCONTRAR LOS CENTROS DE COSTO QUE TIENE ACCESO EL USUARIO
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>(); // ARRAY PARA GUARDAR LOS DATOS OBTENIDOS EN LA CONSULTA DE LOS CENTROS DE COSTO
        try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            if (idRol==2) {//SI EL USUARIO QUE INGRESO TIENE UN ROL DIFERENTE AL ADMINISTRADOR SE SELECCIONAN SOLO LAS ORDENES A LAS CUALES TIENE ACCESO EL USUARIO QUE INGRESO AL SISTEMA
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));//SE AGREGAN LOS DATOS DE LAS AREAS A ARREGLO DE TIPO ORDENY EN EL METODO LISTAR SE PANSAN LOS PARAMETROS DE ID USUARIO Y ID ROL
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R','P','C','L') " +
                                                    "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
                   ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
                   ps.setString(2,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
                    while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                       or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                        , rs.getString(4), rs.getString(5), rs.getString(6)
                                        , rs.getString(7), rs.getString(8), rs.getString(9)
                                        , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                       or.setCantProcesos(rs.getString(11));
                       or.setEstadoProduccion(rs.getInt(12));
                        ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
                    }
                }
            }else{//SI EL USUARIO ES UN ADMINISTRADOR SE SELECCIONAN TODAS LAS ORDENES DE PRODUCCION
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R','P','C','L')  " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setCantProcesos(rs.getString(11));
                   or.setEstadoProduccion(rs.getInt(12));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> filtrarOrdenes(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R','P','C','L')  " +
                                                    "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,"%"+cecos+"%");
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> filtrarOrdenesArt(String itemCode) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT  " +
                                                "    T0.DOCNUM 'NUMERODEOP',  " +
                                                "    CAST(T0.startdate AS DATE) 'FECHADEOP',  " +
                                                "    CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP',  " +
                                                "    T0.ITEMCODE 'ITEMCODE',  " +
                                                "    (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME',  " +
                                                "    T0.STATUS 'STATUS',  " +
                                                "    T0.TYPE 'TIPO',  " +
                                                "    T0.PlannedQty 'CANTPLN',  " +
                                                "    T0.CmpltQty 'CANTCMP',   " +
                                                "    T0.RjctQty 'CANTRJC',  " +
                                                "    COUNT(T2.code)/2 procesos,  " +
                                                "    (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga  " +
                                                "FROM OWOR T0  " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry  " +
                                                "LEFT JOIN (SELECT DISTINCT  " +
                                                "    K1.code,  " +
                                                "    ISNULL(k1.Comment,'') Comment,  " +
                                                "    K0.code itemCode  " +
                                                "FROM OITT K0  " +
                                                "JOIN ITT1 K1 ON K0.Code=K1.father  " +
                                                "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode  " +
                                                "LEFT JOIN SDO_ALSASA..COM_CMS T3 ON T3.ITEMCODE=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                                "WHERE T0.startdate >=? AND T0.Status IN ('R','P','C','L')  " +
                                                "AND (T3.ITEMCODE_MASTER=? OR T0.itemCode=?) " +
                                                "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,itemCode);
            ps.setString(3,itemCode);
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesArt(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
        
    //METODOS DE ORDENES PLANIFICADAS EN TABLA Y AGENDA
    public ArrayList<OWOR> mostrarP(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try {
            fecha=fecha();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT " + 
                                                      "  T0.DOCNUM 'NUMERODEOP', "+ 
                                                      "  CAST(T0.startdate AS DATE) 'FECHADEOP', "+ 
                                                      "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', "+ 
                                                      "  T0.ITEMCODE 'ITEMCODE', "+ 
                                                      "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', "+ 
                                                      "  T0.STATUS 'STATUS', "+ 
                                                      "  T0.TYPE 'TIPO', "+ 
                                                      "  T0.PlannedQty 'CANTPLN', "+ 
                                                      "  T0.CmpltQty 'CANTCMP', "+ 
                                                      "  T0.RjctQty 'CANTRJC' "+ 
                                                      "FROM OWOR T0 "+ 
                                                      "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "+ 
                                                      "WHERE T0.startdate >=? AND T0.Status='P' AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                   ps.setString(1,fecha);
                   ps.setString(2,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                       or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                        , rs.getString(4), rs.getString(5), rs.getString(6)
                                        , rs.getString(7), rs.getString(8), rs.getString(9)
                                        , rs.getString(10));
                       ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT " +
                                                  "	T0.DOCNUM 'NUMERODEOP'," +
                                                  "	CAST(T0.startdate AS DATE) 'FECHADEOP'," +
                                                  "	CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP'," +
                                                  "	T0.ITEMCODE 'ITEMCODE'," +
                                                  "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME'," +
                                                  "	T0.STATUS 'STATUS'," +
                                                  "	T0.TYPE 'TIPO'," +
                                                  "	T0.PlannedQty 'CANTPLN'," +
                                                  "	T0.CmpltQty 'CANTCMP'," +
                                                  "	T0.RjctQty 'CANTRJC'" +
                                                  "FROM OWOR T0 WHERE T0.startdate >=? AND T0.Status='P' ORDER BY T0.POSTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   ar.add(or);
                }
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
    public ArrayList<OWOR> filtrarOrdenesP(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT " + //CONSULTA PARA OBTENER ORDENES DEPENDIENDO DE LAS AREAS DE ACCESO DEL USUARIO
                                                "  T0.DOCNUM 'NUMERODEOP', "+ 
                                                "  CAST(T0.startdate AS DATE) 'FECHADEOP', "+ 
                                                "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', "+ 
                                                "  T0.ITEMCODE 'ITEMCODE', "+ 
                                                "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', "+ 
                                                "  T0.STATUS 'STATUS', "+ 
                                                "  T0.TYPE 'TIPO', "+ 
                                                "  T0.PlannedQty 'CANTPLN', "+ 
                                                "  T0.CmpltQty 'CANTCMP', "+ 
                                                "  T0.RjctQty 'CANTRJC' "+ 
                                                "FROM OWOR T0 "+ 
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "+ 
                                                "WHERE T0.startdate >=? AND T0.Status IN ('P') AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,"%"+cecos+"%");
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> filtrarOrdenesPArt(String itemCode) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT  " +
                                                "    T0.DOCNUM 'NUMERODEOP',  " +
                                                "    CAST(T0.startdate AS DATE) 'FECHADEOP',  " +
                                                "    CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP',  " +
                                                "    T0.ITEMCODE 'ITEMCODE',  " +
                                                "    (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME',  " +
                                                "    T0.STATUS 'STATUS',  " +
                                                "    T0.TYPE 'TIPO',  " +
                                                "    T0.PlannedQty 'CANTPLN',  " +
                                                "    T0.CmpltQty 'CANTCMP',   " +
                                                "    T0.RjctQty 'CANTRJC',  " +
                                                "    COUNT(T2.code)/2 procesos,  " +
                                                "    (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga  " +
                                                "FROM OWOR T0  " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry  " +
                                                "LEFT JOIN (SELECT DISTINCT  " +
                                                "    K1.code,  " +
                                                "    ISNULL(k1.Comment,'') Comment,  " +
                                                "    K0.code itemCode  " +
                                                "FROM OITT K0  " +
                                                "JOIN ITT1 K1 ON K0.Code=K1.father  " +
                                                "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode  " +
                                                "LEFT JOIN SDO_ALSASA..COM_CMS T3 ON T3.ITEMCODE=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                                "WHERE T0.startdate >=? AND T0.Status IN ('P')  " +
                                                "AND (T3.ITEMCODE_MASTER=? OR T0.itemCode=?) " +
                                                "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,itemCode);
            ps.setString(3,itemCode);
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesPArt(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES LIBERADAS EN TABLA 
    public ArrayList<OWOR> mostrarL(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try {
            fecha=fecha();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R') " +
                                                    "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
                    ps.setString(1,fecha);
                    ps.setString(2,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                       or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                        , rs.getString(4), rs.getString(5), rs.getString(6)
                                        , rs.getString(7), rs.getString(8), rs.getString(9)
                                        , rs.getString(10));
                       or.setCantProcesos(rs.getString(11));
                       or.setEstadoProduccion(rs.getInt(12));
                       ar.add(or);
                    }
                }
            }else{
                 ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R') " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setCantProcesos(rs.getString(11));
                   or.setEstadoProduccion(rs.getInt(12));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarL(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
    public ArrayList<OWOR> filtrarOrdenesL(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                    "  T0.DOCNUM 'NUMERODEOP', " +
                                                    "  CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                                    "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                                    "  T0.ITEMCODE 'ITEMCODE', " +
                                                    "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                    "  T0.STATUS 'STATUS', " +
                                                    "  T0.TYPE 'TIPO', " +
                                                    "  T0.PlannedQty 'CANTPLN', " +
                                                    "  T0.CmpltQty 'CANTCMP',  " +
                                                    "  T0.RjctQty 'CANTRJC', " +
                                                    "  COUNT(T2.code)/2 procesos, " +
                                                    "  (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga " +
                                                    "FROM OWOR T0 " +
                                                    "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                    "LEFT JOIN (SELECT DISTINCT " +
                                                    "	K1.code, " +
                                                    "	ISNULL(k1.Comment,'') Comment, " +
                                                    "	K0.code itemCode " +
                                                    "FROM OITT K0 " +
                                                    "JOIN ITT1 K1 ON K0.Code=K1.father " +
                                                    "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode " +
                                                    "WHERE T0.startdate >=? AND T0.Status IN ('R') " +
                                                    "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? " +
                                                    "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,"%"+cecos+"%");
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesL(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> filtrarOrdenesLArt(String itemCode) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT  " +
                                                "    T0.DOCNUM 'NUMERODEOP',  " +
                                                "    CAST(T0.startdate AS DATE) 'FECHADEOP',  " +
                                                "    CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP',  " +
                                                "    T0.ITEMCODE 'ITEMCODE',  " +
                                                "    (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME',  " +
                                                "    T0.STATUS 'STATUS',  " +
                                                "    T0.TYPE 'TIPO',  " +
                                                "    T0.PlannedQty 'CANTPLN',  " +
                                                "    T0.CmpltQty 'CANTCMP',   " +
                                                "    T0.RjctQty 'CANTRJC',  " +
                                                "    COUNT(T2.code)/2 procesos,  " +
                                                "    (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga  " +
                                                "FROM OWOR T0  " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry  " +
                                                "LEFT JOIN (SELECT DISTINCT  " +
                                                "    K1.code,  " +
                                                "    ISNULL(k1.Comment,'') Comment,  " +
                                                "    K0.code itemCode  " +
                                                "FROM OITT K0  " +
                                                "JOIN ITT1 K1 ON K0.Code=K1.father  " +
                                                "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode  " +
                                                "LEFT JOIN SDO_ALSASA..COM_CMS T3 ON T3.ITEMCODE=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                                "WHERE T0.startdate >=? AND T0.Status IN ('R')  " +
                                                "AND (T3.ITEMCODE_MASTER=? OR T0.itemCode=?) " +
                                                "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,itemCode);
            ps.setString(3,itemCode);
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesLArt(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES CERRADAS EN TABLA 
    public ArrayList<OWOR> mostrarC(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try {
            fecha=fecha();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT " + 
                                                      "  T0.DOCNUM 'NUMERODEOP', "+ 
                                                      "  CAST(T0.startdate AS DATE) 'FECHADEOP', "+ 
                                                      "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', "+ 
                                                      "  T0.ITEMCODE 'ITEMCODE', "+ 
                                                      "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', "+ 
                                                      "  T0.STATUS 'STATUS', "+ 
                                                      "  T0.TYPE 'TIPO', "+ 
                                                      "  T0.PlannedQty 'CANTPLN', "+ 
                                                      "  T0.CmpltQty 'CANTCMP', "+ 
                                                      "  T0.RjctQty 'CANTRJC' "+ 
                                                      "FROM OWOR T0 "+ 
                                                      "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "+ 
                                                      "WHERE T0.startdate >=? AND T0.Status='L' AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                    ps.setString(1,fecha);
                    ps.setString(2,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                       or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                        , rs.getString(4), rs.getString(5), rs.getString(6)
                                        , rs.getString(7), rs.getString(8), rs.getString(9)
                                        , rs.getString(10));
                       ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT " +
                                                  "	T0.DOCNUM 'NUMERODEOP'," +
                                                  "	CAST(T0.startdate AS DATE) 'FECHADEOP'," +
                                                  "	CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP'," +
                                                  "	T0.ITEMCODE 'ITEMCODE'," +
                                                  "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME'," +
                                                  "	T0.STATUS 'STATUS'," +
                                                  "	T0.TYPE 'TIPO'," +
                                                  "	T0.PlannedQty 'CANTPLN'," +
                                                  "	T0.CmpltQty 'CANTCMP'," +
                                                  "	T0.RjctQty 'CANTRJC'" +
                                                  "FROM OWOR T0 WHERE T0.startdate >=? AND T0.Status='L' ORDER BY T0.POSTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarC(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
    public ArrayList<OWOR> filtrarOrdenesC(String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT " + //CONSULTA PARA OBTENER ORDENES DEPENDIENDO DE LAS AREAS DE ACCESO DEL USUARIO
                                                "  T0.DOCNUM 'NUMERODEOP', "+ 
                                                "  CAST(T0.startdate AS DATE) 'FECHADEOP', "+ 
                                                "  CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', "+ 
                                                "  T0.ITEMCODE 'ITEMCODE', "+ 
                                                "  (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', "+ 
                                                "  T0.STATUS 'STATUS', "+ 
                                                "  T0.TYPE 'TIPO', "+ 
                                                "  T0.PlannedQty 'CANTPLN', "+ 
                                                "  T0.CmpltQty 'CANTCMP', "+ 
                                                "  T0.RjctQty 'CANTRJC' "+ 
                                                "FROM OWOR T0 "+ 
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "+ 
                                                "WHERE T0.startdate >=? AND T0.Status IN ('L') AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,"%"+cecos+"%");
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesC(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES DE FABRICACION EN TABLA Y AGENDA
        public ArrayList<OWOR> filtrarOrdenesCArt(String itemCode) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            ps = super.con().prepareStatement("SELECT DISTINCT  " +
                                                "    T0.DOCNUM 'NUMERODEOP',  " +
                                                "    CAST(T0.startdate AS DATE) 'FECHADEOP',  " +
                                                "    CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP',  " +
                                                "    T0.ITEMCODE 'ITEMCODE',  " +
                                                "    (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME',  " +
                                                "    T0.STATUS 'STATUS',  " +
                                                "    T0.TYPE 'TIPO',  " +
                                                "    T0.PlannedQty 'CANTPLN',  " +
                                                "    T0.CmpltQty 'CANTCMP',   " +
                                                "    T0.RjctQty 'CANTRJC',  " +
                                                "    COUNT(T2.code)/2 procesos,  " +
                                                "    (SELECT	Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum) carga  " +
                                                "FROM OWOR T0  " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry  " +
                                                "LEFT JOIN (SELECT DISTINCT  " +
                                                "    K1.code,  " +
                                                "    ISNULL(k1.Comment,'') Comment,  " +
                                                "    K0.code itemCode  " +
                                                "FROM OITT K0  " +
                                                "JOIN ITT1 K1 ON K0.Code=K1.father  " +
                                                "WHERE (SELECT ResType FROM ORSC WHERE ResCode=K1.Code)='L') T2 ON T2.itemCode=T0.itemCode  " +
                                                "LEFT JOIN SDO_ALSASA..COM_CMS T3 ON T3.ITEMCODE=T0.ItemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                                "WHERE T0.startdate >=? AND T0.Status IN ('L')  " +
                                                "AND (T3.ITEMCODE_MASTER=? OR T0.itemCode=?) " +
                                                "GROUP BY T0.docNum,T0.startDate,T0.dueDate,T0.STATUS,T0.TYPE, T0.plannedQty,T0.CmpltQty,T0.RjctQty,T0.itemCode ");
            ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
            ps.setString(2,itemCode);
            ps.setString(3,itemCode);
            rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
            while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                or.setCantProcesos(rs.getString(11));
                or.setEstadoProduccion(rs.getInt(12));
                ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarOrdenesCArt(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }

    //METODOS PARA BUSCAR UNA ORDEN
    public ArrayList<OWOR> buscarOrden(int docNum) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
            fecha=fecha();
            ps = super.con().prepareStatement("SELECT " +
                                              "	T0.DOCNUM 'NUMERODEOP', " +
                                              "	CAST(T0.startdate AS DATE) 'FECHADEOP', " +
                                              "	CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP', " +
                                              "	T0.ITEMCODE 'ITEMCODE', " +
                                              "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                              "	CASE " +
                                              "	  WHEN T0.status='P' THEN 'Planificada' " +
                                              "	  WHEN T0.status='R' THEN 'Liberada' " +
                                              "	  WHEN T0.status='C' THEN 'Cancelada' " +
                                              "	  WHEN T0.status='L' THEN 'Cerrada' " +
                                              " END 'STATUS', " +
                                              "	CASE " +
                                              "	  WHEN T0.Type='S' THEN 'Estandar' " +
                                              " ELSE " +
                                              "	  T0.Type " +
                                              " END 'TYPE', " +
                                              "	T0.PlannedQty 'CANTPLN', " +
                                              "	T0.CmpltQty 'CANTCMP', " +
                                              "	T0.RjctQty 'CANTRJC', " +
                                              " ISNULL((SELECT Count(*) FROM SDO_ALSASA..APS_ENMT K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum),0) 'estadoMateriales', " +
                                              " ISNULL((SELECT Count(*) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=K1.idAPS_OWOR WHERE K1.docNum=T0.DocNum),0) 'estadoProduccion' " +
                                              " FROM OWOR T0 JOIN SDO_ALSASA..APS_OWOR T1 ON T0.DocNum=T1.docNum WHERE T0.docNum=? ");
            ps.setInt(1,docNum);
            rs = ps.executeQuery();
            while(rs.next()){
               or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                , rs.getString(4), rs.getString(5), rs.getString(6)
                                , rs.getString(7), rs.getString(8), rs.getString(9)
                                , rs.getString(10));
               or.setEstadoMateriales(rs.getInt(11));
               or.setEstadoProduccion(rs.getInt(12));
               ar.add(or);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.buscarOrden(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> cantOrdenes(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
        int r=0;
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         ArrayList<OWOR> ar2 = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try{
            fecha=fecha1();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT "
                            + "T0.DOCNUM 'NUMERODEOP' "
                            + "FROM OWOR T0 "
                            + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                            + "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R','P','L') AND T0.Type='S' "
                            + "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ");
                    ps.setString(1,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                        or = new OWOR();
                        or.setDocnum(rs.getInt(1));
                        ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT DISTINCT "
                        + "T0.DOCNUM 'NUMERODEOP' "
                        + "FROM OWOR T0 "
                        + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                        + "WHERE YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R','P','L') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setDocnum(rs.getInt(1));
                    ar.add(or);
                }
                
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.cantOrdenes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public int contarOrdenes(String fechaI, String fechaF,int condicion) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            if (condicion==1) {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R','P','L') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            } else {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.Status IN ('R','P','L') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                ps.setString(1, fechaI.replaceAll("-", ""));
                ps.setString(2, fechaF.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.contarOrdenes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public ArrayList<OWOR> cantOrdenesP(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
        int r=0;
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         ArrayList<OWOR> ar2 = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try{
            fecha=fecha1();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT "
                            + "T0.DOCNUM 'NUMERODEOP' "
                            + "FROM OWOR T0 "
                            + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                            + "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('P') AND T0.Type='S' "
                            + "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602)");
                    ps.setString(1,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                        or = new OWOR();
                        or.setDocnum(rs.getInt(1));
                        ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT DISTINCT "
                        + "T0.DOCNUM 'NUMERODEOP' "
                        + "FROM OWOR T0 "
                        + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                        + "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('P') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setDocnum(rs.getInt(1));
                    ar.add(or);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.cantOrdenesP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public int contarOrdenesP(String fechaI, String fechaF,int condicion) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            if (condicion==1) {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('P') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            } else {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.Status IN ('P') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                ps.setString(1, fechaI.replaceAll("-", ""));
                ps.setString(2, fechaF.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.contarOrdenesP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public ArrayList<OWOR> cantOrdenesL(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
        int r=0;
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         ArrayList<OWOR> ar2 = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try{
            fecha=fecha1();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT "
                            + "T0.DOCNUM 'NUMERODEOP' "
                            + "FROM OWOR T0 "
                            + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                            + "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R') "
                            + "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                    ps.setString(1,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                        or = new OWOR();
                        or.setDocnum(rs.getInt(1));
                        ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT DISTINCT "
                        + "T0.DOCNUM 'NUMERODEOP' "
                        + "FROM OWOR T0 "
                        + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                        + "WHERE YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setDocnum(rs.getInt(1));
                    ar.add(or);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.cantOrdenesL(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public int contarOrdenesL(String fechaI, String fechaF,int condicion) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            if (condicion==1) {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('R') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            } else {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.Status IN ('R') AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Type='S' ");
                ps.setString(1, fechaI.replaceAll("-", ""));
                ps.setString(2, fechaF.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.contarOrdenesL(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public ArrayList<OWOR> cantOrdenesC(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
        int r=0;
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
         ArrayList<OWOR> ar2 = new ArrayList<OWOR>();
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC();
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try{
            fecha=fecha1();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT "
                            + "T0.DOCNUM 'NUMERODEOP' "
                            + "FROM OWOR T0 "
                            + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                            + "WHERE YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('L') AND T0.Type='S' "
                            + "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                    ps.setString(1,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                        or = new OWOR();
                        or.setDocnum(rs.getInt(1));
                        ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT DISTINCT "
                        + "T0.DOCNUM 'NUMERODEOP' "
                        + "FROM OWOR T0 "
                        + "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "
                        + "WHERE YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('L') AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    or = new OWOR();
                    or.setDocnum(rs.getInt(1));
                    ar.add(or);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.cantOrdenesC(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public int contarOrdenesC(String fechaI, String fechaF,int condicion) throws ClassNotFoundException, SQLException {
        int r=0;
        try{
            if (condicion==1) {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE  YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND T0.Status IN ('L') AND T0.Type='S' ");
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            } else {
                ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                  "	COUNT(T0.DOCNUM) 'NUMERODEOP' " +
                                                  "FROM OWOR T0 " +
                                                  "WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.Status IN ('L') AND T0.Type='S' ");
                ps.setString(1, fechaI.replaceAll("-", ""));
                ps.setString(2, fechaF.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    r=rs.getInt(1);
                }
            }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.contarOrdenesC(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return r;
    }
    
    public double comprobarCantEntregada(int numOrden, String insumo) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        double cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "ISNULL((SELECT " +
                                              "		SUM(K0.Quantity) " +
                                              "	FROM WTR1 K0 " +
                                              "	JOIN OWTR K1 ON K1.DocEntry = K0.DocEntry " +
                                              "	WHERE K0.U_OrdenProduccion = CAST(T1.DocNum AS VARCHAR) AND K0.ItemCode = T0.ItemCode " +
                                              "	),0)+ISNULL((SELECT SUM(J0.Quantity) FROM SDO_ALSASA..APS_WTR1 J0 JOIN SDO_ALSASA..APS_OWTR J1 ON J0.idAPS_OWTR=J1.idAPS_OWTR WHERE J0.ordenProduccion=T1.docNum AND J0.itemCode=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS AND J1.estado!='N'),0) CantEntregada " +
                                              "FROM WOR1 T0 " +
                                              "JOIN OWOR T1 ON T1.DocEntry = T0.DocEntry " +
                                              "WHERE T0.ItemType = 4 " +
                                              "AND T1.DocNum = ? " +
                                              "AND T0.ItemCode like ? ");
                ps.setInt(1,numOrden);
                ps.setString(2,"%"+insumo+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getDouble(1);
                }
        }catch(Exception e){
            System.out.println("modelo.DaoOWOR.comprobarCantEntregada(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }

    public ArrayList<OWOR> obtenerNumOrdenes( int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); 
         DaoAPS_OCRC D_APS_OCRC = new DaoAPS_OCRC(); 
         ArrayList<APS_OCRC> arrAPS_OCRC= new ArrayList<APS_OCRC>();
        try {
            fecha=fecha();
            if (idRol==2) {
                arrAPS_OCRC.addAll((Collection)D_APS_OCRC.listarAPS_OCRCUser(idUsuario, idRol));
                for (APS_OCRC OCRC : arrAPS_OCRC) {
                    ps = super.con().prepareStatement("SELECT DISTINCT " +
                                                      "  T0.DOCNUM 'NUMERODEOP' "+
                                                      "FROM OWOR T0 "+ 
                                                      "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry "+ 
                                                      "WHERE T0.startdate >=? AND T0.Status IN ('R','L') AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                   ps.setString(1,fecha); 
                   ps.setString(2,"%"+OCRC.getCodigo()+"%");
                    rs = ps.executeQuery(); 
                    while(rs.next()){
                       or= new OWOR(rs.getInt(1));
                        ar.add(or);
                    }
                }
            }else{
                ps = super.con().prepareStatement("SELECT " +
                                                  "	T0.DOCNUM 'NUMERODEOP' " +
                                                  "FROM OWOR T0 "+ 
                                                  "WHERE T0.startdate >=? AND T0.Status IN ('R','L') ORDER BY T0.STARTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.obtenerNumOrdenes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
        
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesSAP(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	T0.DOCNUM 'NUMERODEOP', " +
                                                "	CONVERT(VARCHAR,CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.startDate " +
                                                "			END,103) 'FECHADEOP', " +
                                                "	CONVERT(VARCHAR,CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.DueDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.DueDate " +
                                                "			END,103) 'FECHAVENCDEOP', " +
                                                "	T0.ItemCode 'ITEMCODE', " +
                                                "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                "	CASE " +
                                                "		WHEN T0.status='P' THEN 'Planificada' " +
                                                "		WHEN T0.status='R' THEN 'Liberada' " +
                                                "		WHEN T0.status='C' THEN 'Cancelada' " +
                                                "		WHEN T0.status='L' THEN 'Cerrada' " +
                                                "	END 'STATUS', " +
                                                "    	T0.Type 'TIPO', "+
                                                "	CASE " +
                                                "           WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "           THEN (SELECT TOP 1 K0.PlannedQty FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry ORDER BY K0.StartDate ASC) " +
                                                "           ELSE T0.PlannedQty " + 
                                                "       END 'CANTPLN', " +
                                                "	T0.CmpltQty 'CANTCMP', " +
                                                "	T0.RjctQty 'CANTRJC', " +
                                                "	ISNULL(STUFF((SELECT ', '+K1.descripcion " +
                                                "		FROM SDO_ALSASA..DETALLE_COM_OPS K0  " +
                                                "		JOIN SDO_ALSASA..COM_OPS K1 ON K1.id=K0.idCom_ops " +
                                                "		WHERE K0.idAPS_OWOR=(SELECT J0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR J0 WHERE J0.docnum=T0.DOCNUM) FOR XML PATH ('')),1,2,''),'') 'ComentariosCostos', " +
                                                "	CAST(ISNULL((SELECT SUM(K0.cantCompletada) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=k1.idAPS_OWOR WHERE K1.docnum=T0.DocNum),0) AS NUMERIC(19,6)) CANTCMP_APP " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                "WHERE CAST(CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.startDate " +
                                                "			END AS DATE) BETWEEN ? AND ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ORDER BY T0.startdate DESC");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setCantCmpAPP(rs.getString(12));
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesSAP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
        
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesSAPactual(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	T0.DOCNUM 'NUMERODEOP', " +
                                                "	CONVERT(VARCHAR,T0.startDate,103) 'FECHADEOP', " +
                                                "	CONVERT(VARCHAR,T0.DueDate,103) 'FECHAVENCDEOP', " +
                                                "	T0.ItemCode 'ITEMCODE', " +
                                                "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                "	CASE " +
                                                "		WHEN T0.status='P' THEN 'Planificada' " +
                                                "		WHEN T0.status='R' THEN 'Liberada' " +
                                                "		WHEN T0.status='C' THEN 'Cancelada' " +
                                                "		WHEN T0.status='L' THEN 'Cerrada' " +
                                                "	END 'STATUS', " +
                                                "    	T0.Type 'TIPO', "+
                                                "	T0.PlannedQty 'CANTPLN', " +
                                                "	T0.CmpltQty 'CANTCMP', " +
                                                "	T0.RjctQty 'CANTRJC', " +
                                                "	ISNULL(STUFF((SELECT ', '+K1.descripcion " +
                                                "		FROM SDO_ALSASA..DETALLE_COM_OPS K0  " +
                                                "		JOIN SDO_ALSASA..COM_OPS K1 ON K1.id=K0.idCom_ops " +
                                                "		WHERE K0.idAPS_OWOR=(SELECT J0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR J0 WHERE J0.docnum=T0.DOCNUM) FOR XML PATH ('')),1,2,''),'') 'ComentariosCostos', " +
                                                "	CAST(ISNULL((SELECT SUM(K0.cantCompletada) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=k1.idAPS_OWOR WHERE K1.docnum=T0.DocNum),0) AS NUMERIC(19,6)) CANTCMP_APP " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                "WHERE CAST(T0.startDate AS DATE) BETWEEN ? AND ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ORDER BY T0.startdate DESC");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setCantCmpAPP(rs.getString(12)); 
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesSAPactual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesCecosSAP(String fecha1, String fecha2, String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	T0.DOCNUM 'NUMERODEOP', " +
                                                "	CONVERT(VARCHAR,CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.startDate " +
                                                "			END,103) 'FECHADEOP', " +
                                                "	CONVERT(VARCHAR,CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.DueDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.DueDate " +
                                                "			END,103) 'FECHAVENCDEOP', " +
                                                "	T0.ItemCode 'ITEMCODE', " +
                                                "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                "	CASE " +
                                                "		WHEN T0.status='P' THEN 'Planificada' " +
                                                "		WHEN T0.status='R' THEN 'Liberada' " +
                                                "		WHEN T0.status='C' THEN 'Cancelada' " +
                                                "		WHEN T0.status='L' THEN 'Cerrada' " +
                                                "	END 'STATUS', " +
                                                "    	T0.Type 'TIPO', "+
                                                "	CASE " +
                                                "           WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "           THEN (SELECT TOP 1 K0.PlannedQty FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry ORDER BY K0.StartDate ASC) " +
                                                "           ELSE T0.PlannedQty " + 
                                                "       END 'CANTPLN', " +
                                                "	T0.CmpltQty 'CANTCMP', " +
                                                "	T0.RjctQty 'CANTRJC', " +
                                                "	ISNULL(STUFF((SELECT ', '+K1.descripcion " +
                                                "		FROM SDO_ALSASA..DETALLE_COM_OPS K0  " +
                                                "		JOIN SDO_ALSASA..COM_OPS K1 ON K1.id=K0.idCom_ops " +
                                                "		WHERE K0.idAPS_OWOR=(SELECT J0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR J0 WHERE J0.docnum=T0.DOCNUM) FOR XML PATH ('')),1,2,''),'') 'ComentariosCostos', " +
                                                "	CAST(ISNULL((SELECT SUM(K0.cantCompletada) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=k1.idAPS_OWOR WHERE K1.docnum=T0.DocNum),0) AS NUMERIC(19,6)) CANTCMP_APP " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                "WHERE CAST(CASE  " +
                                                "				WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "				THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "				ELSE T0.startDate " +
                                                "			END AS DATE) BETWEEN ? AND ? "+
                                                "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ORDER BY T0.startdate DESC");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                ps.setString(3,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setCantCmpAPP(rs.getString(12)); 
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesCecosSAP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesCecosSAPActual(String fecha1, String fecha2, String cecos) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	T0.DOCNUM 'NUMERODEOP', " +
                                                "	CONVERT(VARCHAR,T0.startDate,103) 'FECHADEOP', " +
                                                "	CONVERT(VARCHAR,T0.DueDate,103) 'FECHAVENCDEOP', " +
                                                "	T0.ItemCode 'ITEMCODE', " +
                                                "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                                "	CASE " +
                                                "		WHEN T0.status='P' THEN 'Planificada' " +
                                                "		WHEN T0.status='R' THEN 'Liberada' " +
                                                "		WHEN T0.status='C' THEN 'Cancelada' " +
                                                "		WHEN T0.status='L' THEN 'Cerrada' " +
                                                "	END 'STATUS', " +
                                                "    	T0.Type 'TIPO', "+
                                                "	T0.PlannedQty 'CANTPLN', " +
                                                "	T0.CmpltQty 'CANTCMP', " +
                                                "	T0.RjctQty 'CANTRJC', " +
                                                "	ISNULL(STUFF((SELECT ', '+K1.descripcion " +
                                                "		FROM SDO_ALSASA..DETALLE_COM_OPS K0  " +
                                                "		JOIN SDO_ALSASA..COM_OPS K1 ON K1.id=K0.idCom_ops " +
                                                "		WHERE K0.idAPS_OWOR=(SELECT J0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR J0 WHERE J0.docnum=T0.DOCNUM) FOR XML PATH ('')),1,2,''),'') 'ComentariosCostos', " +
                                                "	CAST(ISNULL((SELECT SUM(K0.cantCompletada) FROM SDO_ALSASA..APS_IGN1 K0 JOIN SDO_ALSASA..APS_OWOR K1 ON K0.idAPS_OWOR=k1.idAPS_OWOR WHERE K1.docnum=T0.DocNum),0) AS NUMERIC(19,6)) CANTCMP_APP " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T1.DocEntry=T0.DocEntry " +
                                                "WHERE CAST(T0.startDate AS DATE) BETWEEN ? AND ? "+
                                                "AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ORDER BY T0.startdate DESC");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                ps.setString(3,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setCantCmpAPP(rs.getString(12));
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesCecosSAPActual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesCecosSAP_total(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_ORDENES_CECOS T0 WHERE T0.FECHADEOP BETWEEN ? AND ?");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setNom_cecos(rs.getString(12));
                   or.setCantCmpAPP(rs.getString(13));
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesCecosSAP_total(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES PLANIFICADAS EN TABLA EXCEL
    public ArrayList<OWOR> mostrarOrdenesCecosSAP_totalOriginal(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_ORDENES_CECOS_FECHA_ORIGINAL T0 WHERE T0.FECHADEOP BETWEEN ? AND ?");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   or.setNom_cecos(rs.getString(12));
                   or.setCantCmpAPP(rs.getString(13));
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOrdenesCecosSAP_totalOriginal(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
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
        c.add(Calendar.MONTH, -1);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
    
    //METODO PARA ENCONTRAR LA FECHA DE CONSULTA(TRES MESES ATRAS)
    private String fecha1(){
        Calendar c = new GregorianCalendar();
        String fecha;
        String date;
        int dia;
        dia=Calendar.DATE;
        c.add(Calendar.MONTH,0);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
    
    //METODOS DE ORDENES DE FABRICACION PARA MODAL
        public ArrayList<OWOR> obtenerOrden(int numOrden) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps = super.con().prepareStatement("SELECT " +
                                                  "	T0.DOCNUM 'NUMERODEOP'," +
                                                  "	CAST(T0.startdate AS DATE) 'FECHADEOP'," +
                                                  "	CAST(T0.DueDate AS DATE) 'FECHAVENCDEOP'," +
                                                  "	T0.ITEMCODE 'ITEMCODE'," +
                                                  "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME'," +
                                                  "	CASE " +
                                                  "	  WHEN T0.status='P' THEN 'Planificada' " +
                                                  "	  WHEN T0.status='R' THEN 'Liberada' " +
                                                  "	  WHEN T0.status='C' THEN 'Cancelada' " +
                                                  "	  WHEN T0.status='L' THEN 'Cerrada' " +
                                                  "     END 'Status', " +
                                                  "	T0.TYPE 'TIPO', " +
                                                  "	T0.PlannedQty 'CANTPLN', " +
                                                  "	T0.CmpltQty 'CANTCMP', " +
                                                  "	T0.RjctQty 'CANTRJC', " +
                                                  "	ISNULL(T0.Comments,'') 'COMMENTS' " +
                                                  "FROM OWOR T0 "+ 
                                                  "WHERE T0.docNum=?");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   or.setComments(rs.getString(11));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.obtenerOrden(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    //METODOS PARA COMPROBAR SI UNA ORDEN PERTENECEA LINEA O EMPAQUE
    public int comprobarCecosOrden(int numOrden) throws ClassNotFoundException, SQLException {
         int count =-1;
        try {
                ps=super.con().prepareStatement("SELECT Count(*) Count FROM (SELECT DISTINCT K0.docnum " +
                                                "FROM OWOR K0 " +
                                                "JOIN WOR1 K1 ON K0.docEntry=K1.docEntry " +
                                                "WHERE K1.ItemType=290 " +
                                                "AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K1.ItemCode) = 'L' " +
                                                "AND K1.ItemCode LIKE '%114%' " +
                                                "OR K1.ItemType=290 " +
                                                "AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K1.ItemCode) = 'L' " +
                                                "AND K1.ItemCode LIKE '%126%') T0 WHERE T0.docNum=?");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                    count=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.comprobarCecosOrden(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return count;
    }
        
    public ArrayList<OWOR> cumplimientoPlanta() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT * "+ 
                                                "FROM SDO_ALSASA..VW_CUMPLIMIENTO_PLANTA ");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
        
    public ArrayList<OWOR> cumplimientoPlantaFecha() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_CUMPLIMIENTO_FECHA_ACTUAL ");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoPlantaFecha(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }     
        
    public ArrayList<OWOR> filtrarCumplimientoPlanta(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "    COUNT(*) ORDENES_COMPLETADAS,  " +
                                                "    (SELECT COUNT(*) FROM OWOR K0 WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END BETWEEN ? AND ? AND K0.Status!='C' AND K0.Type='S' )  ORDENES_DEL_MES,  " +
                                                "    (CONVERT(NUMERIC(38,6),COUNT(*)+(SELECT COUNT(*) FROM OWOR T0 WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.CmpltQty/T0.PlannedQty!=1.0 AND T0.CmpltQty!=0 AND T0.Status!='C' AND T0.Type='S'))/CONVERT(NUMERIC(38,6),(SELECT COUNT(*) FROM OWOR K0 WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END BETWEEN ? AND ? AND K0.Status!='C' AND K0.Type='S'  )))*100 CUMPLIMIENTO_DEL_MES,  " +
                                                "    (SELECT COUNT(*) FROM OWOR T0 WHERE CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END BETWEEN ? AND ? AND T0.CmpltQty/T0.PlannedQty!=1.0 AND T0.CmpltQty!=0 AND T0.Status!='C' AND T0.Type='S') ORDENES_IMCOMPLETAS  " +
                                                "FROM OWOR T0 WHERE CASE  " +
                                                "                WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 " +
                                                "                THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) " +
                                                "                ELSE T0.startDate " +
                                                "            END BETWEEN ? AND ? AND T0.CmpltQty/T0.PlannedQty=1.0 AND T0.Status!='C' AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602)  AND T0.Type='S' ");
                
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                ps.setString(3,fecha1.replaceAll("-", ""));
                ps.setString(4,fecha2.replaceAll("-", ""));
                ps.setString(5,fecha1.replaceAll("-", ""));
                ps.setString(6,fecha2.replaceAll("-", ""));
                ps.setString(7,fecha1.replaceAll("-", ""));
                ps.setString(8,fecha2.replaceAll("-", ""));
                ps.setString(9,fecha1.replaceAll("-", ""));
                ps.setString(10,fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarCumplimientoPlanta(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
        
    public ArrayList<OWOR> cumplimientoPlantaArticulos() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	L0.ARTICULOS_COMPLETADOS, " +
                                                "	L0.ARTICULOS_PLANIFICADOS_DEL_MES, " +
                                                "	CASE WHEN L0.ARTICULOS_COMPLETADOS=0 AND L0.ARTICULOS_PLANIFICADOS_DEL_MES!=0 " +
                                                "		THEN 0 " +
                                                "		ELSE (L0.ARTICULOS_COMPLETADOS/L0.ARTICULOS_PLANIFICADOS_DEL_MES)*100  " +
                                                "	END CUMPLIMIENTO " +
                                                "FROM (SELECT  " +
                                                "	T0.ARTICULOS_COMPLETADOS ARTICULOS_COMPLETADOS, " +
                                                "	ISNULL(T0.ARTICULOS_PLANIFICADOS_DEL_MES,0.000001) ARTICULOS_PLANIFICADOS_DEL_MES  " +
                                                "FROM SDO_ALSASA..VW_CUMPLIMIENTO_PLANTA_ARTICULOS T0) L0 ");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoPlantaArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
        
    public ArrayList<OWOR> cumplimientoPlantaFechaArticulos() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	SUM(ARTICULOS_COMPLETADOS), " +
                                                "	SUM(ARTICULOS_PLANIFICADOS), " +
                                                "	CASE WHEN SUM(ARTICULOS_COMPLETADOS)=0 AND SUM(ARTICULOS_PLANIFICADOS)!=0 " +
                                                "		THEN 0 " +
                                                "		ELSE (SUM(ARTICULOS_COMPLETADOS)/SUM(ARTICULOS_PLANIFICADOS))*100  " +
                                                "	END CUMPLIMIENTO " +
                                                "FROM SDO_ALSASA..VW_DETALLE_ARTICULOS_V4 T0 WHERE T0.startDate BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoPlantaFechaArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
        
    public ArrayList<OWOR> filtrarCumplimientoPlantaArticulos(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	SUM(ARTICULOS_COMPLETADOS), " +
                                                "	SUM(ARTICULOS_PLANIFICADOS), " +
                                                "	CASE WHEN SUM(ARTICULOS_COMPLETADOS)=0 AND SUM(ARTICULOS_PLANIFICADOS)!=0 " +
                                                "		THEN 0 " +
                                                "		ELSE (SUM(ARTICULOS_COMPLETADOS)/SUM(ARTICULOS_PLANIFICADOS))*100  " +
                                                "	END CUMPLIMIENTO " +
                                                "FROM SDO_ALSASA..VW_DETALLE_ARTICULOS_V4 T0 WHERE T0.startDate BETWEEN ? AND ? ");
                
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarCumplimientoPlantaArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<OWOR> horasDelMes() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	CONVERT(NUMERIC(19,2),ISNULL((SELECT SUM(K0.HorasConsumidas) FROM SDO_ALSASA..[VW_ORDENES_FECHA_ACTUAL] K0 WHERE YEAR(K0.FECHADEOP) = YEAR(GETDATE()) AND MONTH(K0.FECHADEOP) = MONTH(GETDATE())),0)) HORAS_REPORTADAS, " +
                                                "	CONVERT(NUMERIC(19,2),SUM(T0.HORAS_PLANIFICADAS)) HORAS_PLANIFICADAS " +
                                                "FROM SDO_ALSASA..VW_ORDENES_DEL_MES T0 ");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.horasDelMes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<OWOR> horasDelMesFechaActual() throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT "+
                                                "   CONVERT(NUMERIC(19,2),ISNULL((SELECT SUM(K0.HorasConsumidas) FROM SDO_ALSASA..[VW_ORDENES_FECHA_ACTUAL] K0 WHERE CAST(K0.FECHADEOP AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)) ,0)) HORAS_REPORTADAS, " +
                                                "   CONVERT(NUMERIC(19,2),SUM(K0.HORAS_PLANIFICADAS)) HORAS_PLANIFICADAS " +
                                                "FROM SDO_ALSASA..VW_ORDENES_DEL_MES K0 " +
                                                "WHERE CAST(K0.FECHA_INICIO AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) ");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.horasDelMesFechaActual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<OWOR> filtrarHorasDelMes(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "    CONVERT(numeric(19,2),ISNULL( " +
                                                "        (ISNULL((SELECT " +
                                                "            SUM(K1.Quantity) 'HorasConsumidas' " +
                                                "        FROM OIGE K0 " +
                                                "        JOIN IGE1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "        WHERE K0.docDate BETWEEN ? AND ? " +
                                                "        AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L'),0)+ " +
                                                "        ISNULL((SELECT " +
                                                "            SUM(-K1.Quantity) 'HorasDevueltas' " +
                                                "        FROM OIGN K0 " +
                                                "        JOIN IGN1 K1 ON K1.DocEntry=K0.DocEntry " +
                                                "        WHERE K0.docDate BETWEEN ? AND ? " +
                                                "        AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' ),0)) " +
                                                "    ,0)) HORAS_REPORTADAS, " +
                                                "    CONVERT(NUMERIC(19,2),SUM(T1.PlannedQty)) HORAS_PLANIFICADAS " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE T0.StartDate BETWEEN ? AND ? " +
                                                " AND T1.ItemType=290 AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode=T1.ItemCode)='L' AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) AND T0.Status!='C' AND T0.Type='S' ");
                
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                ps.setString(3,fecha1.replaceAll("-", ""));
                ps.setString(4,fecha2.replaceAll("-", ""));
                ps.setString(5,fecha1.replaceAll("-", ""));
                ps.setString(6,fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarHorasDelMes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OWOR> cumplimientoCecos(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "    COUNT(DISTINCT DocNum) ORDENES_COMPLETADAS, " +
                                                "    (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=YEAR(GETDATE()) AND K0.Status!='C' AND K0.Type='S' AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_DEL_MES, " +
                                                "    (CONVERT(NUMERIC(38,6),COUNT(DISTINCT DocNum)+(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=YEAR(GETDATE()) AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?))/CONVERT(NUMERIC(38,6),(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=YEAR(GETDATE()) AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?)))*100 CUMPLIMIENTO_DEL_MES, " +
                                                "        (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END)=YEAR(GETDATE())  AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_IMCOMPLETAS " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE MONTH(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=MONTH(GETDATE()) AND YEAR(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END)=YEAR(GETDATE()) AND T0.CmpltQty/T0.PlannedQty=1.0 AND T0.Status!='C' AND T0.Type='S' " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                ps.setString(4,"%"+cecos+"%");
                ps.setString(5,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoCecos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
    
    public ArrayList<OWOR> cumplimientoCecosFechaActual(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "    COUNT(DISTINCT DocNum) ORDENES_COMPLETADAS, " +
                                                "    (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND K0.Status!='C' AND K0.Type='S' AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_DEL_MES, " +
                                                "    (CONVERT(NUMERIC(38,6),COUNT(DISTINCT DocNum)+(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?))/CONVERT(NUMERIC(38,6),(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND K0.Status!='C' AND K0.Type='S' AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?)))*100 CUMPLIMIENTO_DEL_MES, " +
                                                "        (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_IMCOMPLETAS " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND T0.CmpltQty/T0.PlannedQty=1.0 AND T0.Status!='C' AND T0.Type='S' " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                ps.setString(4,"%"+cecos+"%");
                ps.setString(5,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoCecosFechaActual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
    
    public ArrayList<OWOR> filtrarCumplimientoCecos(String fecha1, String fecha2,String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "    COUNT(DISTINCT DocNum) ORDENES_COMPLETADAS, " +
                                                "    (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN ? AND ? AND K0.Status!='C' AND K0.Type='S' AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_DEL_MES, " +
                                                "    (CONVERT(NUMERIC(38,6),COUNT(DISTINCT DocNum)+(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN ? AND ? AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?))/CONVERT(NUMERIC(38,6),(SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN ? AND ? AND K0.Status!='C' AND K0.Type='S' AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?)))*100 CUMPLIMIENTO_DEL_MES, " +
                                                "        (SELECT COUNT(DISTINCT K0.DocNum) FROM OWOR K0 JOIN WOR1 K1 ON K0.DocEntry=K1.DocEntry WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry)>0 THEN (SELECT TOP 1 MIN(J0.StartDate) FROM AWOR J0 WHERE J0.DocEntry=K0.DocEntry) ELSE K0.startDate END AS DATE) BETWEEN ? AND ? AND K0.CmpltQty/K0.PlannedQty<1.0 AND K0.CmpltQty!=0 AND K0.Status!='C' AND K0.Type='S'  AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?) ORDENES_IMCOMPLETAS " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE CAST(CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T0.DocEntry) ELSE T0.startDate END AS DATE) BETWEEN ? AND ? AND T0.CmpltQty/T0.PlannedQty=1.0 AND T0.Status!='C' AND T0.Type='S' " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ");
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                ps.setString(3,"%"+cecos+"%");
                ps.setString(4,fecha1.replaceAll("-", ""));
                ps.setString(5,fecha2.replaceAll("-", ""));
                ps.setString(6,"%"+cecos+"%");
                ps.setString(7,fecha1.replaceAll("-", ""));
                ps.setString(8,fecha2.replaceAll("-", ""));
                ps.setString(9,"%"+cecos+"%");
                ps.setString(10,fecha1.replaceAll("-", ""));
                ps.setString(11,fecha2.replaceAll("-", ""));
                ps.setString(12,"%"+cecos+"%");
                ps.setString(13,fecha1.replaceAll("-", ""));
                ps.setString(14,fecha2.replaceAll("-", ""));
                ps.setString(15,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   or.setDuedate(rs.getString(4));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarCumplimientoCecos(): " + e.getMessage() + ". Cecos: " +cecos);
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
    
    public ArrayList<OWOR> cumplimientoCecosArticulos(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS)),0) ARTICULOS_COMPLETADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)),0) ARTICULOS_PLANIFICADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,6),(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS))/CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)))*100),0) PORCENTAJE_CUMPLIMIENTO " +
                                                "FROM SDO_ALSASA..VW_DETALLE_ARTICULOS_ACUMULADO T0 WHERE T0.CECOS=?");
                ps.setString(1,cecos);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoCecosArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
    
    public ArrayList<OWOR> cumplimientoCecosArticulosFechaActual(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS)),0) ARTICULOS_COMPLETADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)),0) ARTICULOS_PLANIFICADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,6),(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS))/CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)))*100),0) PORCENTAJE_CUMPLIMIENTO " +
                                                "FROM SDO_ALSASA..VW_DETALLE_ARTICULOS_V2 T0 WHERE T0.CECOS=?");
                ps.setString(1,cecos);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.cumplimientoCecosArticulosFechaActual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
    
    public ArrayList<OWOR> filtrarCumplimientoCecosArticulos(String fecha1, String fecha2,String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS)),0) ARTICULOS_COMPLETADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)),0) ARTICULOS_PLANIFICADOS, " +
                                                "	ISNULL(CONVERT(NUMERIC(19,6),(CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_COMPLETADOS))/CONVERT(NUMERIC(19,0),SUM(T0.ARTICULOS_PLANIFICADOS)))*100),0) PORCENTAJE_CUMPLIMIENTO " +
                                                "FROM SDO_ALSASA..VW_DETALLE_ARTICULOS_V3 T0 WHERE T0.StartDate BETWEEN ? AND ? AND T0.CECOS=? ");
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                ps.setString(3,cecos);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantrjc(rs.getString(3));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarCumplimientoCecosArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
       
    public ArrayList<OWOR> horasDelMesCecos(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "CONVERT(numeric(19,2),ISNULL(  " +
                                                "    (ISNULL((SELECT  " +
                                                "        SUM(K1.Quantity) 'HorasConsumidas'  " +
                                                "    FROM OIGE K0  " +
                                                "    JOIN IGE1 K1 ON K1.DocEntry=K0.DocEntry  " +
                                                "    WHERE CAST(K0.docDate AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)  " +
                                                "    AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?),0)+  " +
                                                "    ISNULL((SELECT  " +
                                                "        SUM(-K1.Quantity) 'HorasDevueltas'  " +
                                                "    FROM OIGN K0  " +
                                                "    JOIN IGN1 K1 ON K1.DocEntry=K0.DocEntry  " +
                                                "    WHERE CAST(K0.docDate AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)  " +
                                                "    AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? ),0))  " +
                                                ",0)) HORAS_REPORTADAS,  " +
                                                "ISNULL(CONVERT(NUMERIC(19,2),SUM(ISNULL(T1.PlannedQty,0))),0) HORAS_PLANIFICADAS  " +
                                                "FROM OWOR T0  " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry  " +
                                                "WHERE MONTH(T0.startDate)=MONTH(GETDATE()) AND YEAR(T0.startDate)=YEAR(GETDATE()) AND T0.Status!='C' AND T0.Type='S' " +
                                                "AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=T1.ItemCode)='L' " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602)");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.horasDelMesCecos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    } 
       
    public ArrayList<OWOR> horasDelMesCecosFechaActual(String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " + 
                                                "CONVERT(numeric(19,2),ISNULL(  " +
                                                "    (ISNULL((SELECT  " +
                                                "        SUM(K1.Quantity) 'HorasConsumidas'  " +
                                                "    FROM OIGE K0  " +
                                                "    JOIN IGE1 K1 ON K1.DocEntry=K0.DocEntry  " +
                                                "    WHERE CAST(K0.docDate AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)  " +
                                                "    AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?),0)+  " +
                                                "    ISNULL((SELECT  " +
                                                "        SUM(-K1.Quantity) 'HorasDevueltas'  " +
                                                "    FROM OIGN K0  " +
                                                "    JOIN IGN1 K1 ON K1.DocEntry=K0.DocEntry  " +
                                                "    WHERE CAST(K0.docDate AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE)  " +
                                                "    AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L' " +
                                                "    AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? ),0))  " +
                                                ",0)) HORAS_REPORTADAS,  " +
                                                "ISNULL(CONVERT(NUMERIC(19,2),SUM(ISNULL(T1.PlannedQty,0))),0) HORAS_PLANIFICADAS  " +
                                                "FROM OWOR T0 " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry " +
                                                "WHERE CAST(T0.StartDate AS DATE) BETWEEN CAST(DATEADD(dd,-(DAY(GETDATE())-1),GETDATE()) AS DATE) AND CAST(GETDATE() AS DATE) AND T0.Status!='C' AND T0.Type='S' " +
                                                "AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=T1.ItemCode)='L' " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602) ");
                ps.setString(1,"%"+cecos+"%");
                ps.setString(2,"%"+cecos+"%");
                ps.setString(3,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.horasDelMesCecosFechaActual(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<OWOR> filtrarHorasDelMesCecos(String fecha1, String fecha2,String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT     " +
                                                "	CONVERT(numeric(19,2),ISNULL(    " +
                                                "		(ISNULL((SELECT    " +
                                                "			SUM(K1.Quantity) 'HorasConsumidas'    " +
                                                "		FROM OIGE K0    " +
                                                "		JOIN IGE1 K1 ON K1.DocEntry=K0.DocEntry    " +
                                                "		WHERE K0.docDate BETWEEN ? AND ? " +
                                                "		AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L'   " +
                                                "		AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ?),0)+    " +
                                                "		ISNULL((SELECT    " +
                                                "			SUM(-K1.Quantity) 'HorasDevueltas'    " +
                                                "		FROM OIGN K0    " +
                                                "		JOIN IGN1 K1 ON K1.DocEntry=K0.DocEntry    " +
                                                "		WHERE K0.docDate BETWEEN ? AND ? " +
                                                "		AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=K1.ItemCode)='L'   " +
                                                "		AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=K1.ITEMCODE AND K1.ItemType=290) LIKE ? ),0))    " +
                                                "	,0)) HORAS_REPORTADAS,    " +
                                                "	ISNULL(CONVERT(NUMERIC(19,2),SUM(ISNULL(T1.PlannedQty,0))),0) HORAS_PLANIFICADAS    " +
                                                "FROM OWOR T0   " +
                                                "JOIN WOR1 T1 ON T0.DocEntry=T1.DocEntry   " +
                                                "WHERE T0.StartDate BETWEEN ? AND ? AND T0.Status!='C' AND T0.Type='S'   " +
                                                "AND (SELECT L0.ResType FROM ORSC L0 WHERE L0.VisResCode=T1.ItemCode)='L'   " +
                                                "AND (SELECT K2.VisResCode FROM ORSC K2 WHERE K2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ? AND T0.docNum NOT IN (64442,64445,64448,64449,64601,64602)");
                ps.setString(1,fecha1.replaceAll("-", ""));
                ps.setString(2,fecha2.replaceAll("-", ""));
                ps.setString(3,"%"+cecos+"%");
                ps.setString(4,fecha1.replaceAll("-", ""));
                ps.setString(5,fecha2.replaceAll("-", ""));
                ps.setString(6,"%"+cecos+"%");
                ps.setString(7,fecha1.replaceAll("-", ""));
                ps.setString(8,fecha2.replaceAll("-", ""));
                ps.setString(9,"%"+cecos+"%");
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setCantcmp(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   ar.add(or);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.filtrarHorasDelMesCecos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public int contarOpsMayor_cincoPorciento() throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]>5.0");
                
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMayor_cincoPorciento(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    public int contarOpsMenor_cincoPorciento() throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]<5.0");
                
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMenor_cincoPorciento(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMayor_cincoPorciento() throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "   T0.DOCNUM, " +
                                                "   T0.ITEMCODE_ORDEN, " +
                                                "   T0.ITEMNAME_ORDEN, " +
                                                "   T0.PLNQTY_ORDEN, " +
                                                "   T0.CMPTQTY_ORDEN, " +
                                                "   T0.RJCTQTY_ORDEN, " +
                                                "   T0.COSTO_UNI_REAL, " +
                                                "   T0.COSTO_PRY_PPTO, " +
                                                "   T0.[%_VARIACION_PRY], " +
                                                "   CONVERT(VARCHAR, T0.STARTDATE_ORIGINAL,105) STARTDATE_ORIGINAL, " +
                                                "   CONVERT(VARCHAR, T0.CLOSEDATE,105) CLOSEDATE " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]>5.0 ");
                
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMayor_cincoPorciento(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMenor_cincoPorciento() throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "   T0.DOCNUM, " +
                                                "   T0.ITEMCODE_ORDEN, " +
                                                "   T0.ITEMNAME_ORDEN, " +
                                                "   T0.PLNQTY_ORDEN, " +
                                                "   T0.CMPTQTY_ORDEN, " +
                                                "   T0.RJCTQTY_ORDEN, " +
                                                "   T0.COSTO_UNI_REAL, " +
                                                "   T0.COSTO_PRY_PPTO, " +
                                                "   T0.[%_VARIACION_PRY], " +
                                                "   CONVERT(VARCHAR, T0.STARTDATE_ORIGINAL,105) STARTDATE_ORIGINAL, " +
                                                "   CONVERT(VARCHAR, T0.CLOSEDATE,105) CLOSEDATE " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]<5.0 ");
                
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMenor_cincoPorciento(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public int contarOpsMayor_cincoPorciento_Mes() throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE_MES T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]>5.0");
                
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMayor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    public int contarOpsMenor_cincoPorciento_Mes() throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE_MES T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]<5.0");
                
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMenor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMayor_cincoPorciento_Mes() throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "   T0.DOCNUM, " +
                                                "   T0.ITEMCODE_ORDEN, " +
                                                "   T0.ITEMNAME_ORDEN, " +
                                                "   T0.PLNQTY_ORDEN, " +
                                                "   T0.CMPTQTY_ORDEN, " +
                                                "   T0.RJCTQTY_ORDEN, " +
                                                "   T0.COSTO_UNI_REAL, " +
                                                "   T0.COSTO_PRY_PPTO, " +
                                                "   T0.[%_VARIACION_PRY], " +
                                                "   CONVERT(VARCHAR, T0.STARTDATE_ORIGINAL,105) STARTDATE_ORIGINAL, " +
                                                "   CONVERT(VARCHAR, T0.CLOSEDATE,105) CLOSEDATE " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE_MES T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]>5.0 ");
                
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMayor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMenor_cincoPorciento_Mes() throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "   T0.DOCNUM, " +
                                                "   T0.ITEMCODE_ORDEN, " +
                                                "   T0.ITEMNAME_ORDEN, " +
                                                "   T0.PLNQTY_ORDEN, " +
                                                "   T0.CMPTQTY_ORDEN, " +
                                                "   T0.RJCTQTY_ORDEN, " +
                                                "   T0.COSTO_UNI_REAL, " +
                                                "   T0.COSTO_PRY_PPTO, " +
                                                "   T0.[%_VARIACION_PRY], " +
                                                "   CONVERT(VARCHAR, T0.STARTDATE_ORIGINAL,105) STARTDATE_ORIGINAL, " +
                                                "   CONVERT(VARCHAR, T0.CLOSEDATE,105) CLOSEDATE " +
                                                "FROM SDO_ALSASA..VW_OPS_COSTO_CIERRE_MES T0  " +
                                                "WHERE T0.[%_VARIACION_PRY]<5.0 ");
                
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMenor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public int contarOpsMayor_cincoPorciento_Filtro(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS T0 " +
                                                "JOIN OWOR T1 ON T0.docNum=T1.docnum " +
                                                "WHERE T1.closeDate BETWEEN ? AND ?  " +
                                                "AND T0.[%_VARIACION_PRY]>5.0 ");
                ps.setString(1, fecha1.replaceAll("-", ""));
                ps.setString(2, fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMayor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
       
    public int contarOpsMenor_cincoPorciento_Filtro(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        int result=0;
        
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	COUNT(*) CONTEO " +
                                                "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS T0 " +
                                                "JOIN OWOR T1 ON T0.docNum=T1.docnum " +
                                                "WHERE T1.closeDate BETWEEN ? AND ?  " +
                                                "AND T0.[%_VARIACION_PRY]<5.0 ");
                ps.setString(1, fecha1.replaceAll("-", ""));
                ps.setString(2, fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                    result=rs.getInt(1);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.contarOpsMenor_cincoPorciento_Mes(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMayor_cincoPorciento_Filtro(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	T0.DOCNUM,  " +
                                                "	T0.ITEMCODE_ORDEN,  " +
                                                "	T0.ITEMNAME_ORDEN,  " +
                                                "	T0.PLNQTY_ORDEN,  " +
                                                "	T0.CMPTQTY_ORDEN,  " +
                                                "	T0.RJCTQTY_ORDEN,  " +
                                                "	T0.COSTO_UNI_REAL,  " +
                                                "	T0.COSTO_PRY_PPTO,  " +
                                                "	T0.[%_VARIACION_PRY], " +
                                                "	CONVERT(VARCHAR, T1.CloseDate,105) CLOSEDATE, " +
                                                "	CONVERT(VARCHAR, CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T1.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T1.DocEntry) ELSE T1.startDate END,105) STARTDATE_ORIGINAL " +
                                                "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS T0 " +
                                                "JOIN OWOR T1 ON T0.docNum=T1.docnum " +
                                                "WHERE T1.closeDate BETWEEN ? AND ?  " +
                                                "AND T0.[%_VARIACION_PRY]>5.0 ");
                ps.setString(1, fecha1.replaceAll("-", ""));
                ps.setString(2, fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMayor_cincoPorciento_Filtro(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
       
    public ArrayList<Costo_OP> mostrarOPS_costoMenor_cincoPorciento_Filtro(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
        try {
                ps=super.con().prepareStatement("SELECT  " +
                                                "	T0.DOCNUM,  " +
                                                "	T0.ITEMCODE_ORDEN,  " +
                                                "	T0.ITEMNAME_ORDEN,  " +
                                                "	T0.PLNQTY_ORDEN,  " +
                                                "	T0.CMPTQTY_ORDEN,  " +
                                                "	T0.RJCTQTY_ORDEN,  " +
                                                "	T0.COSTO_UNI_REAL,  " +
                                                "	T0.COSTO_PRY_PPTO,  " +
                                                "	T0.[%_VARIACION_PRY], " +
                                                "	CONVERT(VARCHAR, T1.CloseDate,105) CLOSEDATE, " +
                                                "	CONVERT(VARCHAR, CASE WHEN (SELECT COUNT(*) FROM AWOR K0 WHERE K0.DocEntry=T1.DocEntry)>0 THEN (SELECT TOP 1 MIN(K0.StartDate) FROM AWOR K0 WHERE K0.DocEntry=T1.DocEntry) ELSE T1.startDate END,105) STARTDATE_ORIGINAL " +
                                                "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS T0 " +
                                                "JOIN OWOR T1 ON T0.docNum=T1.docnum " +
                                                "WHERE T1.closeDate BETWEEN ? AND ?  " +
                                                "AND T0.[%_VARIACION_PRY]<5.0 ");
                ps.setString(1, fecha1.replaceAll("-", ""));
                ps.setString(2, fecha2.replaceAll("-", ""));
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_op= new Costo_OP();
                   costo_op.setDocnum(rs.getString(1));
                   costo_op.setItemCode_Orden(rs.getString(2));
                   costo_op.setItemname_Orden(rs.getString(3));
                   costo_op.setPlnQty_Orden(rs.getString(4));
                   costo_op.setCmptQty_Orden(rs.getString(5));
                   costo_op.setRjctQty_Orden(rs.getString(6));
                   costo_op.setCosto_Uni(rs.getString(7));
                   costo_op.setCosto_Pry(rs.getString(8));
                   costo_op.setVariacion(rs.getString(9));
                   costo_op.setStartDate(rs.getString(10));
                   costo_op.setCloseDate(rs.getString(11));
                   ar.add(costo_op);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarOPS_costoMenor_cincoPorciento_Filtro(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    //METODO PARA QUITAR LAS ORDENES DUPLICADAS DEL ARREGLO AL REPETIR LA CONSULTA DE ORDENES POR AREA
    private ArrayList<OWOR> quitarDuplicados(ArrayList<OWOR> arr){
        ArrayList<OWOR> arr2 = new ArrayList<OWOR>();
        for (OWOR element : arr) {
            if (!arr2.contains(element)) {
                arr2.add(element);
            }
        }
        return arr2;
    }
    
    public int actualizarComentarioOp(int numorden, String comentario) throws ClassNotFoundException, SQLException {
        int res=0;
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR "+ 
                                      "SET comentario=? "+ 
                                      "WHERE docNum=? ");
            ps.setString(1, comentario);
            ps.setInt(2, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoOWOR.actualizarComentarioOp(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int actualizarEstadoAutorizacionOp(int numorden) throws ClassNotFoundException, SQLException {
        int res=0;
        try{
            ps=super.con().prepareStatement("UPDATE SDO_ALSASA..APS_OWOR "+ 
                                      "SET estadoAutorizacion=1 "+ 
                                      "WHERE docNum=?");
            ps.setInt(1, numorden);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoOWOR.actualizarEstadoAutorizacionOp(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public String obtenerComentario(int docNum) throws ClassNotFoundException, SQLException{
        String result ="";
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "   ISNULL(T0.comentario,'') " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "WHERE T0.docNum=?");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getString(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoOWOR.obtenerComentario(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    public ArrayList<ObjGen> obtenerComentarioCostos() throws ClassNotFoundException, SQLException{
        String result ="";
        ArrayList<ObjGen> ar = new ArrayList<ObjGen>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              " T0.* " +
                                              "FROM SDO_ALSASA..COM_OPS T0 ");
            rs = ps.executeQuery();
            while(rs.next()){
                comments = new ObjGen();
                comments.setIdComentCosto(rs.getInt(1));
                comments.setComentario(rs.getString(2));
                ar.add(comments);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoOWOR.obtenerComentarioCostos(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<ObjGen> obtenerComentarioOP(int docnum) throws ClassNotFoundException, SQLException{
        String result ="";
        ArrayList<ObjGen> ar = new ArrayList<ObjGen>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                                "T0.* " +
                                                "FROM SDO_ALSASA..COM_OPS T0 " +
                                                "JOIN SDO_ALSASA..DETALLE_COM_OPS T1 ON T0.id=T1.idCom_ops " +
                                                "JOIN SDO_ALSASA..APS_OWOR T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
                                                "WHERE T2.docNum=?  ");
            ps.setInt(1, docnum);
            rs = ps.executeQuery();
            while(rs.next()){
                comments = new ObjGen();
                comments.setIdComentCosto(rs.getInt(1));
                comments.setComentario(rs.getString(2));
                ar.add(comments);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoOWOR.obtenerComentarioCostos(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int guardarComentariosCostos(int docnum,String ComentariosCosto) throws ClassNotFoundException, SQLException{
        int res =0;
        try{
            String comentarios=ComentariosCosto;
            String[] comment=comentarios.split(",");
            String[] idComments = new String[comment.length];

            for (int i = 0; i < idComments.length; i++) {
                try {
                    idComments[i]=comment[i];
                } catch (Exception e) {
                }
            }
            
            ps=con().prepareStatement("DELETE FROM SDO_ALSASA..DETALLE_COM_OPS WHERE idAPS_OWOR=(SELECT K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.docnum=?) ");
            ps.setInt(1, docnum);
            res=ps.executeUpdate();
                for (int i = 0; i < idComments.length; i++) {
                    ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..DETALLE_COM_OPS(idAPS_OWOR,idCom_ops) VALUES((SELECT K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.docnum=?),?); ");

                    ps.setInt(1, docnum);
                    ps.setString(2, idComments[i]);
                    res=ps.executeUpdate();
                }
        }catch(Exception ex){
                ps=con().prepareStatement("DELETE FROM SDO_ALSASA..DETALLE_COM_OPS WHERE idAPS_OWOR=(SELECT K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.docnum=?) ");
                ps.setInt(1, docnum);
                ps.executeUpdate();
                System.out.println("modelo.DaoOWTR.guardarTransferenciaAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<OWOR> mostrarComparativaPlan(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
         ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try {
                ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_COMPARATIVA_PLAN_CECOS @FECHA1=?,@FECHA2=? ");
                ps.setString(1,fecha1);
                ps.setString(2,fecha2);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OWOR();
                   or.setComments(rs.getString(1));
                   or.setCantpln(rs.getString(2));
                   or.setCantcmp(rs.getString(3));
                   ar.add(or);
                }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.mostrarComparativaPlan(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int guardarComentarios(Object obj) throws ClassNotFoundException, SQLException {
        ArrayList<OWOR> ar = new ArrayList<OWOR>();
        CVAR cvar = new CVAR();
        cvar = (CVAR)obj;
        int res=0;
        try {
                ps=super.con().prepareStatement("INSERT INTO SDO_ALSASA..CVAR(idAPS_OWOR,lineNum,itemCode,variacion,COMENTARIO) VALUES((SELECT TOP 1 K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.DocNum=?),?,?,?,?)");
                ps.setInt(1,cvar.getDocNum());
                ps.setInt(2,cvar.getLineNum());
                ps.setString(3,cvar.getItemCode());
                ps.setDouble(4,cvar.getVariacion());
                ps.setString(5,cvar.getComentario());
                res = ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.guardarComentarios(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int eliminarComentarios(int docNum) throws ClassNotFoundException, SQLException {
        
        int res=0;
        try {
                ps=super.con().prepareStatement("DELETE FROM SDO_ALSASA..CVAR WHERE idAPS_OWOR=(SELECT TOP 1 K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.DocNum=?) ");
                ps.setInt(1,docNum);
                res = ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOWOR.eliminarComentarios(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
} 