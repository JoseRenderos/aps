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
public class DaoOrdenSBO extends ConexionSBO{
    PreparedStatement ps;
    ResultSet rs;
    OrdenSBO or; //Objeto de ordenes para almacenar la informacion de las ordenes
    String fecha;
    
    //METODOS DE ORDENES DE FRABRICACION EN TABLA Y AGENDA
        public ArrayList<OrdenSBO> mostrar( int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OrdenSBO> ar = new ArrayList<OrdenSBO>(); //ARRAY QUE RETORNA LOS DATOS DE LA CONSULTA
         DaoArea DArea = new DaoArea(); //INSTANCIA DE OBJETO DAOAREA PARA ENCONTRAR LOS CENTROS DE COSTO QUE TIENE ACCESO EL USUARIO
         ArrayList<Area> arrDArea= new ArrayList<Area>(); // ARRAY PARA GUARDAR LOS DATOS OBTENIDOS EN LA CONSULTA DE LAS AREAS
        try {
            fecha=fecha();//SE CARGA LA FECHA DE CONSULTA
            if (idRol!=1) {//SI EL USUARIO QUE INGRESO TIENE UN ROL DIFERENTE AL ADMINISTRADOR SE SELECCIONAN SOLO LAS ORDENES A LAS CUALES TIENE ACCESO EL USUARIO QUE INGRESO AL SISTEMA
                arrDArea.addAll((Collection)DArea.listarAreasUser(idUsuario, idRol));//SE AGREGAN LOS DATOS DE LAS AREAS A ARREGLO DE TIPO ORDENY EN EL METODO LISTAR SE PANSAN LOS PARAMETROS DE ID USUARIO Y ID ROL
                for (Area area : arrDArea) {
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
                                                      "WHERE T0.startdate >=? AND T0.Status IN ('R','P') AND T0.ITEMCODE NOT LIKE '%SMED%' AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                   ps.setString(1,fecha); // EN ENVIAN LOS PARAMETROS DE LA CONSULTA
                   ps.setString(2,"%"+area.getCodigo()+"%");
                    rs = ps.executeQuery(); // SE HACE LA CONSULTA A LA BASE
                    while(rs.next()){//SE RECORRE LOS DATOS DE LA CONSULTA
                       or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
                                        , rs.getString(4), rs.getString(5), rs.getString(6)
                                        , rs.getString(7), rs.getString(8), rs.getString(9)
                                        , rs.getString(10));// SE CREA EL OBJETO ORDEN CON LOS DATOS OBTENIDOS
                        ar.add(or);//SE AGREGA EL OBJETO ORDEN CON LOS DATOS AL ARREGLO
                    }
                }
            }else{//SI EL USUARIO ES UN ADMINISTRADOR SE SELECCIONAN TODAS LAS ORDENES DE PRODUCCION
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
                                                  "FROM OWOR T0 "+ 
                                                  "WHERE T0.startdate >=? AND T0.Status IN ('R', 'P') AND T0.ITEMCODE NOT LIKE '%SMED%' ORDER BY T0.STARTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOrdenSBO.mostrar(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
        
    //METODOS DE ORDENES PLANIFICADAS EN TABLA Y AGENDA
    
    public ArrayList<OrdenSBO> mostrarP(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OrdenSBO> ar = new ArrayList<OrdenSBO>();
         DaoArea DArea = new DaoArea();
         ArrayList<Area> arrDArea= new ArrayList<Area>();
        try {
            fecha=fecha();
            if (idRol!=1) {
                arrDArea.addAll((Collection)DArea.listarAreasUser(idUsuario, idRol));
                for (Area area : arrDArea) {
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
                                                      "WHERE T0.startdate >=? AND T0.Status='P' AND T0.ITEMCODE NOT LIKE '%SMED%' AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                   ps.setString(1,fecha);
                   ps.setString(2,"%"+area.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                       or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
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
                                                  "FROM OWOR T0 WHERE T0.startdate >=? AND T0.Status='P' AND T0.ITEMCODE NOT LIKE '%SMED%' ORDER BY T0.POSTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   ar.add(or);
                }
            }
            
        } catch (Exception e) {
            System.out.println("modelo.DaoOrdenSBO.mostrarP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    //METODOS DE ORDENES LIBERADAS EN TABLA Y AGENDA
    
    public ArrayList<OrdenSBO> mostrarL(int idUsuario, int idRol) throws ClassNotFoundException, SQLException {
         ArrayList<OrdenSBO> ar = new ArrayList<OrdenSBO>();
         DaoArea DArea = new DaoArea();
         ArrayList<Area> arrDArea= new ArrayList<Area>();
        try {
            fecha=fecha();
            if (idRol!=1) {
                arrDArea.addAll((Collection)DArea.listarAreasUser(idUsuario, idRol));
                for (Area area : arrDArea) {
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
                                                      "WHERE T0.startdate >=? AND T0.Status='R' AND T0.ITEMCODE NOT LIKE '%SMED%' AND (SELECT T2.VisResCode FROM ORSC T2 WHERE T2.VisResCode=T1.ITEMCODE AND T1.ItemType=290) LIKE ?");
                    ps.setString(1,fecha);
                    ps.setString(2,"%"+area.getCodigo()+"%");
                    rs = ps.executeQuery();
                    while(rs.next()){
                       or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
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
                                                  "FROM OWOR T0 WHERE T0.startdate >=? AND T0.Status='R' AND T0.ITEMCODE NOT LIKE '%SMED%' ORDER BY T0.POSTDATE DESC");
                ps.setString(1,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   or= new OrdenSBO(rs.getInt(1), rs.getString(2), rs.getString(3)
                                    , rs.getString(4), rs.getString(5), rs.getString(6)
                                    , rs.getString(7), rs.getString(8), rs.getString(9)
                                    , rs.getString(10));
                   ar.add(or);
                }
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoOrdenSBO.mostrarL(): " + e.getMessage());
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
        c.add(Calendar.MONTH, -2);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
    
    //METODO PARA QUITAR LAS ORDENES DUPLICADAS DEL ARREGLO AL REPETIR LA CONSULTA DE ORDENES POR AREA
    private ArrayList<OrdenSBO> quitarDuplicados(ArrayList<OrdenSBO> arr){
        ArrayList<OrdenSBO> arr2 = new ArrayList<OrdenSBO>();
        for (OrdenSBO element : arr) {
            if (!arr2.contains(element)) {
                arr2.add(element);
            }
        }
        return arr2;
    }
}